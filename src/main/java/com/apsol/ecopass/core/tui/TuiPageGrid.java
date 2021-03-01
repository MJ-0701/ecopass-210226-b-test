package com.apsol.ecopass.core.tui;

import com.apsol.ecopass.core.juso.gokr.model.Juso;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TuiPageGrid {

    private TuiPage pagination;

    private List<? extends Object> contents;

}
