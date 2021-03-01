package com.apsol.ecopass.core.enums;

import com.apsol.ecopass.core.enums.mapper.EnumType;

public enum FreeType implements EnumType {

    SUBSIDY("수급자"),
    SINGLE("한부모가정"),
    NATIONAL("국가유공자");

    private String title;

    FreeType(String title) {
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
