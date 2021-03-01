package com.apsol.ecopass.core.enums;

import com.apsol.ecopass.core.enums.mapper.EnumType;
import lombok.Getter;

public enum CompanyType implements EnumType {

    CITY_HALL("시청"),
    DISTRICT_OFFICE("구청"),
    DONG_CENTER("주민센터"),
    CLEAN_COMPANY("수거업체"),
    CALL_CENTER("콜센터");

    private String title;

    CompanyType(String title) {
        this.title = title;
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
