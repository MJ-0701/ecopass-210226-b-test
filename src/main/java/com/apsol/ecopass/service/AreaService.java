package com.apsol.ecopass.service;

import com.apsol.ecopass.core.enums.WasteType;
import com.apsol.ecopass.core.juso.gokr.model.Juso;
import com.apsol.ecopass.entity.area.Area;

import java.util.List;

public interface AreaService {

    List<Area> findByHemdAndWasteType(String hemd, WasteType wasteType);

}
