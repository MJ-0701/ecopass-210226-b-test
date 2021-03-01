package com.apsol.ecopass.core.enums;

import com.apsol.ecopass.core.enums.mapper.EnumType;

/**
 * 폐기물 종류
 * (관할구역 구분을 위한)
 */
public enum WasteType implements EnumType {

    TRASH("일반쓰레기"),
    GARBAGE("음식물쓰레기"),
    BULKY("대형폐기물"),
    UNAUTH("무단폐기물"),
    NEGLECT("미수거쓰레기"),
    ROAD("가로변쓰레기"),
    CONSTRUCT("건축폐기물"),
    SACK("자루폐기물"),
    WATER("정화조");

    private String title;

    WasteType(String title) {
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
