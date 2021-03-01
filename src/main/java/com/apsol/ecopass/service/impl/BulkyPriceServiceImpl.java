package com.apsol.ecopass.service.impl;

import com.apsol.ecopass.dto.common.bulky.BulkyPriceDto;
import com.apsol.ecopass.entity.bulky.BulkyItem;
import com.apsol.ecopass.entity.bulky.QBulkyCategory;
import com.apsol.ecopass.entity.bulky.QBulkyItem;
import com.apsol.ecopass.service.BulkyPriceService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulkyPriceServiceImpl implements BulkyPriceService {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    public List<BulkyItem> getPriceList() {

        QBulkyItem table = QBulkyItem.bulkyItem;
        List<BulkyItem> bulkyItemList = jpaQueryFactory
                .selectFrom(table)
                .where(table.deleted.isFalse())
                .where(table.bulkyCategory.deleted.isFalse())
                .fetch();

        return bulkyItemList;
    }

    public List<String> getCategoryNames() {
        QBulkyCategory table = QBulkyCategory.bulkyCategory;

        return jpaQueryFactory.select(table.name).from(table).where(table.deleted.isFalse()).fetch();
    }

}
