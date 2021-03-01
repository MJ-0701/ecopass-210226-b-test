package com.apsol.ecopass.core.tui;

import com.apsol.ecopass.core.juso.gokr.model.Juso;

import java.util.List;

public class TuiPageGridResultBuilder {

    public TuiPageGridResult buildData(int page, int totalCount, List<? extends Object> contents) {

        return new TuiPageGridData(new TuiPage(page, totalCount), contents);
    }

    public TuiPageGridResult buildError(String message) {

        return new TuiPageGridError(message);
    }

}
