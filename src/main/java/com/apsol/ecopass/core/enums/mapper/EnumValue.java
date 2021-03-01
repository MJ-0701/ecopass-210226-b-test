package com.apsol.ecopass.core.enums.mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class EnumValue {
    @Getter
    private String name;
    @Getter
    private String title;

    public EnumValue(EnumType enumType) {
        this.name = enumType.getName();
        this.title = enumType.getTitle();
    }

}
