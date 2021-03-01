package com.apsol.ecopass.service.impl;

import com.apsol.ecopass.controller.admin.bulky.AdminBulkyRequestController;
import com.apsol.ecopass.core.enums.*;
import com.apsol.ecopass.dto.admin.bulky.AdminBulkyRequestDto;
import com.apsol.ecopass.dto.common.bulky.BulkyRequestDto;
import com.apsol.ecopass.dto.common.bulky.BulkyResponseDto;
import com.apsol.ecopass.entity.area.Addr;
import com.apsol.ecopass.entity.area.Area;
import com.apsol.ecopass.entity.area.District;
import com.apsol.ecopass.entity.bulky.BulkyOrder;
import com.apsol.ecopass.entity.bulky.QBulkyCategory;
import com.apsol.ecopass.entity.bulky.QBulkyOrder;
import com.apsol.ecopass.entity.member.Company;
import com.apsol.ecopass.entity.member.Employee;
import com.apsol.ecopass.service.AddrService;
import com.apsol.ecopass.service.AreaService;
import com.apsol.ecopass.service.BulkyRequestService;
import com.apsol.ecopass.util.DateFormatHelper;
import com.apsol.ecopass.util.HolidayUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BulkyRequestServiceImpl implements BulkyRequestService {

    private static final Logger logger = LoggerFactory.getLogger(BulkyRequestServiceImpl.class);

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    AddrService addrService;

    @Autowired
    AreaService areaService;

    @Override
    @Transactional
    public void createRequest(AdminBulkyRequestDto dto, Employee reqEmployee) throws ParseException {

        Date orderDatetime = new Date();
        Date payDatetime = null;

        // todo :: 접수자, 결제유형 등을 정확히 정의할 것
        // todo :: "dto"는 "payGroup"을 입력받지 않아도 됨 -> dto에서 삭제

        // 오프라인 결제건만 주문 생성이 가능하게 한다
        if (dto.getPayGroup() != PayGroup.OFFLINE)
            throw new RuntimeException("잘못된 결제그룹입니다");

        // 수수료면제 건이 아닌데 면제 사유가 null이 아닌 경우
        if (dto.getPayType() != PayType.OFF_FREE && dto.getFreeType() != null )
            throw new RuntimeException("수수료면제가 아니면서 면제 사유가 입력되었습니다");
        // 방문결제가 아니면서
        if (!dto.isVisitPay() && dto.getPayGroup() == null)
            throw new RuntimeException("수수료면제가 아니면서 면제 사유가 입력되었습니다");
        // 무통장입금이 아니고, 방문결제가 아니라면 결제시간을 입력한다.
        if (dto.getPayType() != PayType.OFF_BANK && !dto.isVisitPay() )
            payDatetime = orderDatetime;

        BulkyOrder bulkyOrder = generateRequestOrder(dto, orderDatetime);
        setJurisdiction(bulkyOrder, reqEmployee);


//        bulkyOrder.completeEntityBuildUp(
//            reqEmployee,
//            dto.isVisitPay() ? null : reqEmployee,
//            null,
//            payDatetime,
//            dto.getPayGroup(),
//            dto.getPayType(),
//            dto.
//        );

    }

    /**
     * [지정 수거업체, 담당 업체 배열, 구 단위 관할] 데이터 등록
     * 오버로딩
     * @param bulkyOrder        엔티티
     */
    public void setJurisdiction(BulkyOrder bulkyOrder) {
        setJurisdiction(bulkyOrder, null);
    }

    /**
     * [지정 수거업체, 담당 업체 배열, 구 단위 관할] 데이터 등록
     * @param bulkyOrder        엔티티
     * @param reqEmployee       신청 직원 (null 가능)
     */
    public void setJurisdiction(BulkyOrder bulkyOrder, Employee reqEmployee) {
        // 행정구역 정보
        Addr addr = addrService.findByEmds(bulkyOrder.getBemd(), bulkyOrder.getHemd());
        if (addr == null)
            throw new RuntimeException("시스템 관할 지역이 아닙니다");
        // 관할구역 정보
        Company compClean = null;   // 실수거 업체
        String companies = "";      // 관할구역 업체들
        // 내 소속 집단이 수거업체인 경우 할당
        if (reqEmployee != null) {
            if (reqEmployee.getCompany() != null) {
                if (reqEmployee.getCompany().getType() == CompanyType.CLEAN_COMPANY) {
                    compClean = reqEmployee.getCompany(); //소속 집단이 수거업체일 경우 바로 할당
                    companies = String.valueOf(reqEmployee.getCompany().getCode());
                }
            } else {
                throw new RuntimeException("어디에도 소속되지 않은 계정으로는 주문건을 작성할 수 없습니다");
            }
        }
        // 내 소속 집단이 없거나 수거업체가 아닌 경우 ... 미할당 / 관할구역 검색
        if (compClean == null) {
            List<Area> areas = areaService.findByHemdAndWasteType(bulkyOrder.getHemd(), WasteType.BULKY);
            if (areas.size() == 0) {
                throw new RuntimeException("해당 지역을 관할중인 업체가 존재하지 않습니다");
            } else if (areas.size() == 1) {
                compClean = areas.get(0).getCompany();
                companies = String.valueOf(areas.get(0).getCompany().getCode());
            } else {
                compClean = null;
                List<String> companyList = new ArrayList<>();
                for (Area area : areas) {
                    if (!StringUtils.isBlank(companies)) {
                        companies += ",";
                    }
                    companies += area.getCompanyCode();
                }
            }
        }
        // set data
        bulkyOrder.setJurisdiction(compClean, companies, addr.getDistrict());
    }

    /**
     * 대형폐기물 주문건 생성 (공통)
     * build() 에서 누락된 부분은 개별적인 setter 를 만들어 구현해야 한다
     * @param dto
     * @return
     * @throws ParseException
     */
    public BulkyOrder generateRequestOrder(BulkyRequestDto dto, Date orderDatetime) throws ParseException {
        // 배출예정일시
        String scheduleDatetimeString = dto.getScheduleDate() + " " + dto.getScheduleTime();
        Date scheduleDatetime = new SimpleDateFormat("yyyy.MM.dd hh.mm.ss").parse(scheduleDatetimeString);
        // 주문일시, 주문번호 생성

        String orderNo = getNextOrderNo(orderDatetime);
        // Entity 객체 생성
        BulkyOrder bulkyOrder = BulkyOrder.builder()
            // DTO 데이터
                .name(dto.getName())
                .phone(dto.getPhone())
                .position(dto.getPosition())
                .smsAgree(dto.isSmsAgree())
                .totalAmount(dto.getTotalAmount())
                .addrJibun(dto.getAddrJibun())
                .addrRoad(dto.getAddrRoad())
                .addrDetail(dto.getAddrDetail())
                .zipCode(dto.getZipCode())
                .sido(dto.getSido())
                .sgg(dto.getSgg())
                .bemd(dto.getBemd())
                .hemd(dto.getHemd())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .orderDatetime(orderDatetime)
                .orderNo(orderNo)
            .build();

        // 배출예정일시, 색변 기준일 세팅
        updateScheduleDateTime(bulkyOrder, scheduleDatetime);

        logger.info(bulkyOrder.toString());
        return bulkyOrder;
    }

    /**
     * 엔티티에 배출예약일, 마커 색변 기준일을 세팅한다
     * @param bulkyOrder
     * @param scheduleDatetime
     * @throws ParseException
     */
    private void updateScheduleDateTime(BulkyOrder bulkyOrder, Date scheduleDatetime) throws ParseException {

        String markerWarnString = (HolidayUtil.isHoliday(DateFormatHelper.formatDate8(scheduleDatetime), 2));
        String markerExcessString = (HolidayUtil.isHoliday(DateFormatHelper.formatDate8(scheduleDatetime), 4));

        Date markerWarn = new SimpleDateFormat("yyyyMMdd").parse(markerWarnString);
        Date markerExcess = new SimpleDateFormat("yyyyMMdd").parse(markerExcessString);

        bulkyOrder.updateScheduleDateTime(scheduleDatetime, markerWarn, markerExcess);
    }

    /**
     * 마지막 주문번호를 쿼리하고 새로운 주문번호를 생성한다
     * @param orderDatetime
     * @return
     */
    private String getNextOrderNo(Date orderDatetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderDatetime);
        QBulkyOrder table = QBulkyOrder.bulkyOrder;
        String maxOrderNo = jpaQueryFactory.select(table.orderNo.max()).from(table)
            .where(table.orderDatetime.year().eq(calendar.get(Calendar.YEAR)))
            .where(table.orderDatetime.month().eq(calendar.get(Calendar.MONTH)))
            .where(table.orderDatetime.dayOfMonth().eq(calendar.get(Calendar.DAY_OF_MONTH)))
            .fetchOne();
        long maxOrderNoLong;
        if (maxOrderNo == null) {
            maxOrderNoLong = 0;
        } else {
            maxOrderNoLong = Long.parseLong(maxOrderNo.substring(6));
        }
        return String.format("%s%04d", DateFormatHelper.formatDate6(orderDatetime), maxOrderNoLong + 1);
    }



}
