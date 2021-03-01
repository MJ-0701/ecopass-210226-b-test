package com.apsol.ecopass.dto.common.bulky;

import com.apsol.ecopass.entity.bulky.BulkyItem;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class BulkyPriceDto {

    private Long bulkyItemCode;

    private String categoryName;

    private String itemName;

    private String itemStandard;

    private BigDecimal itemPrice = BigDecimal.ZERO;

    private int quantity = 1;

    private BigDecimal amount = BigDecimal.ZERO;

    private boolean deleted = false;

    /**
     * Entity to DTO
     * @param bulkyItem
     */
    public void of(BulkyItem bulkyItem) {
        this.bulkyItemCode = bulkyItem.getCode();
        this.categoryName = bulkyItem.getBulkyCategory().getName();
        this.itemName = bulkyItem.getName();
        this.itemStandard = bulkyItem.getStandard();
        this.itemPrice = bulkyItem.getPrice();
        this.amount = bulkyItem.getPrice();
        this.deleted = bulkyItem.isDeleted();
    }

}
