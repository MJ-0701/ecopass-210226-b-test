package com.apsol.ecopass.core.tui;

import lombok.Data;

@Data
public class TuiPageGridError implements TuiPageGridResult {

    private final boolean result = false;

    private String message = "";

    protected TuiPageGridError(String message) {
        this.message = message;
    }

}
