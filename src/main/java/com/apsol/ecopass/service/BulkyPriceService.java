package com.apsol.ecopass.service;

import com.apsol.ecopass.entity.bulky.BulkyItem;

import java.util.List;

public interface BulkyPriceService {

    List<BulkyItem> getPriceList();
    List<String> getCategoryNames();


}
