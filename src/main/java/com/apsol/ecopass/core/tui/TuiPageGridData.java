package com.apsol.ecopass.core.tui;

import com.apsol.ecopass.core.juso.gokr.model.Juso;
import lombok.Data;

import java.util.List;

@Data
public class TuiPageGridData implements TuiPageGridResult {

    private final boolean result = true;

    private TuiPageGrid data = new TuiPageGrid();

    protected TuiPageGridData(TuiPage tuiPage, List<? extends Object> contents) {
        this.data.setPagination(tuiPage);
        this.data.setContents(contents);
    }

}
