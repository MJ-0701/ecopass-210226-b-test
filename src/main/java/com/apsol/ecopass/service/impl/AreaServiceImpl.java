package com.apsol.ecopass.service.impl;

import com.apsol.ecopass.core.enums.WasteType;
import com.apsol.ecopass.core.juso.gokr.model.Juso;
import com.apsol.ecopass.entity.area.Area;
import com.apsol.ecopass.entity.area.QArea;
import com.apsol.ecopass.service.AreaService;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Area> findByHemdAndWasteType(String hemd, WasteType wasteType) {
        QArea table = QArea.area;
        return jpaQueryFactory.selectFrom(table)
            .where(table.hemd.eq(hemd))
            .where(table.wasteType.eq(wasteType))
            .fetch();
    }

}
