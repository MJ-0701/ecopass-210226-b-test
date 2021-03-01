package com.apsol.ecopass.core.enums;

import com.apsol.ecopass.core.enums.mapper.EnumType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PayGroup implements EnumType {

    /**
     * Array.asList 의 항목이 중복되면 안 된다
     */
    OFFLINE("오프라인", Arrays.asList(PayType.OFF_CASH, PayType.OFF_CARD, PayType.OFF_BANK, PayType.OFF_FREE)),
    KG_ALLAT("KG 올앳", Arrays.asList(PayType.ABANK, PayType.VBANK, PayType.HP, PayType.TICKET, PayType.CARD)),
    TOSS_PAY("토스 페이먼츠", Collections.emptyList());

    private String title;
    private List<PayType> payTypeList;

    PayGroup(String title, List<PayType> payTypeList) {
        this.title = title;
        this.payTypeList = payTypeList;
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
