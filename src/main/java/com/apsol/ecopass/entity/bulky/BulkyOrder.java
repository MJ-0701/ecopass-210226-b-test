package com.apsol.ecopass.entity.bulky;

import com.apsol.ecopass.core.enums.FreeType;
import com.apsol.ecopass.core.enums.PayGroup;
import com.apsol.ecopass.core.enums.PayType;
import com.apsol.ecopass.entity.Payment;
import com.apsol.ecopass.entity.area.District;
import com.apsol.ecopass.entity.member.Company;
import com.apsol.ecopass.entity.member.Employee;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bulky_orders")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class BulkyOrder {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long code;

    /**
     * 접수 직원 FK
     */
    @ManyToOne
    @JoinColumn(name = "emp_clerk", updatable = false)
    @Setter(AccessLevel.NONE)
    private Employee empClerk;

    /**
     * 수납 직원 FK
     */
    @ManyToOne
    @JoinColumn(name = "emp_cashier")
    @Setter(AccessLevel.NONE)
    private Employee empCashier;

    /**
     * 구 관할 FK
     */
    @ManyToOne
    @JoinColumn(name = "district", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private District district;

    /**
     * 결제 FK
     */
    @ManyToOne
    @JoinColumn(name = "payment")
    @Setter(AccessLevel.NONE)
    private Payment payment;

    /**
     * 실 수거업체
     */
    @ManyToOne
    @JoinColumn(name = "comp_clean")
    private Company compClean;

    /**
     * 주문번호
     */
    @Column(name = "order_no", nullable = false, updatable = false, unique = true, length = 10)
    @Setter(AccessLevel.NONE)
    private String orderNo;

    /**
     * 담당 업체 FK 배열
     */
    @Column(name = "companies")
    @Setter(AccessLevel.NONE)
    private String companies;

    /**
     * 비용 총액
     */
    @Column(name = "total_amount", nullable = false)
    @Setter(AccessLevel.NONE)
    private BigDecimal totalAmount;

    /**
     * 배출자 이름
     */
    @Column(name = "name", nullable = false)
    @NotNull(message = "이름은 필수항목 입니다.")
    private String name;

    /**
     * 전화번호(연락처)
     */
    @Column(name = "phone", nullable = false)
    @NotNull(message = "전화번호는 필수항목 입니다.")
    private String phone;

    /**
     * 주문일시
     */
    @Column(name = "order_datetime", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDatetime;

    /**
     * 결제완료일시
     */
    @Column(name = "pay_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDatetime;

    /**
     * 배출예정일시
     */
    @Column(name = "schedule_datetime", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "배출 예정 일자는 필수항목입니다.")
    private Date scheduleDatetime;

    /**
     * 지번 주소
     */
    @Column(name = "addr_jibun", nullable = false)
    @NotNull(message = "주소는 null일수 없습니다.")
    private String addrJibun;

    /**
     * 도로명 주소
     */
    @Column(name = "addr_road", nullable = false)
    @NotNull(message = "주소는 null일수 없습니다.")
    private String addrRoad;

    /**
     * 도로명 주소
     */
    @Column(name = "addr_detail", nullable = false)
    @NotNull(message = "상세 주소는 필수항목 입니다.")
    private String addrDetail;

    /**
     * 상세 배출 위치
     */
    @NotNull(message = "상세 위치는 필수항목 입니다.")
    @Column(name = "position", nullable = false)
    private String position;

    /**
     * 시도
     */
    @NotNull(message = "시도는 null일수 없습니다.")
    @Column(name = "sido", nullable = false)
    private String sido;

    /**
     * 시군구
     */
    @NotNull(message = "시군구는 null일수 없습니다.")
    @Column(name = "sgg", nullable = false)
    private String sgg;

    /**
     * 우편번호
     */
    @NotNull(message = "우편번호는 null일수 없습니다.")
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    /**
     * 법정동
     */
    @NotNull(message = "법정동은 null일수 없습니다.")
    @Column(name = "bemd", nullable = false)
    private String bemd;

    /**
     * 행정동
     */
    @NotNull(message = "행정동은 필수항목 입니다.")
    @Column(name = "hemd", nullable = false)
    private String hemd;

    /**
     * 위도
     */
    @NotNull(message = "위도는 null일수 없습니다.")
    @Column(name = "lat", nullable = false)
    private Double lat;

    /**
     * 경도
     */
    @NotNull(message = "경도는 null일수 없습니다.")
    @Column(name = "lng", nullable = false)
    private Double lng;

    /**
     * 마커 - 경고
     */
    @Column(name = "marker_warn", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date markerWarn;

    /**
     * 마커 - 초과
     */
    @Column(name = "marker_excess", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date markerExcess;

    /**
     * 결제사
     */
    @Column(name = "pay_group")
    private PayGroup payGroup;

    /**
     * 결제 유형
     */
    @Column(name = "pay_type")
    private PayType payType;

    /**
     * 수수료 면제 유형
     */
    @Column(name = "free_type")
    private FreeType freeType;

    /**
     * 취소건 구분
     */
    @Column(name = "canceled", nullable = false, updatable = false)
    private boolean canceled = false;

    /**
     * 방문결제 유무
     */
    @Column(name = "visit_pay", nullable = false)
    private boolean visitPay = false;

    /**
     * 방문수거 유무
     */
    @Column(name = "visit_collect", nullable = false)
    private boolean visitCollect = false;

    /**
     * SMS 수신동의
     */
    @Column(name = "sms_agree", nullable = false)
    private boolean smsAgree = false;

    /**
     * 삭제 유무
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    /**
     * 마커 색변 기준일 세팅
     * @param scheduleDatetime
     * @param markerWarn
     * @param markerExcess
     */
    public void updateScheduleDateTime(Date scheduleDatetime, Date markerWarn, Date markerExcess) {
        this.scheduleDatetime = scheduleDatetime;
        this.markerWarn = markerWarn;
        this.markerExcess = markerExcess;
    }

    /**
     * 엔티티 생성을 마무리
     */
    public void completeEntityBuildUp(Employee empClerk, Employee empCashier, Payment payment, Date payDatetime, PayGroup payGroup, PayType payType, FreeType freeType, boolean canceled, boolean visitPay, boolean visitCollect, boolean deleted) {
        this.empClerk = empClerk;
        this.empCashier = empCashier;
        this.payment = payment;
        this.payDatetime = payDatetime;
        this.payGroup = payGroup;
        this.payType = payType;
        this.freeType = freeType;
        this.canceled = canceled;
        this.visitPay = visitPay;
        this.visitCollect = visitCollect;
        this.deleted = deleted;
    }

    /**
     * 지정업체, 관할 업체 목록, 관할 구 데이터 입력
     * @param compClean
     * @param companies
     * @param district
     */
    public void setJurisdiction(Company compClean, String companies, District district) {
        this.compClean = compClean;
        this.companies = companies;
        this.district = district;
    }

}
