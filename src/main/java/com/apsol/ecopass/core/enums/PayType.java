package com.apsol.ecopass.core.enums;

import com.apsol.ecopass.core.enums.mapper.EnumType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PayType implements EnumType {

    // 오프라인
    OFF_CASH("현금", Arrays.asList(CompanyType.CLEAN_COMPANY)),
    OFF_CARD("카드", Collections.emptyList()),
    OFF_BANK("무통장", Arrays.asList(CompanyType.CLEAN_COMPANY)),
    OFF_FREE("수수료면제", Collections.emptyList()),
    // 올앳
    CARD("신용카드", Collections.emptyList()),
    VBANK("가상계좌", Collections.emptyList()),
    ABANK("계좌이체", Collections.emptyList()),
    HP("휴대폰", Collections.emptyList()),
    TICKET("상품권", Collections.emptyList());

    private String title;   // 이름

    private List<CompanyType> usage; // 사용처

    PayType(String title, List<CompanyType> usage) {
        this.title = title;
        this.usage = usage;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }
}
