package com.apsol.ecopass.dto.admin.bulky;

import com.apsol.ecopass.core.enums.FreeType;
import com.apsol.ecopass.core.enums.PayGroup;
import com.apsol.ecopass.core.enums.PayType;
import com.apsol.ecopass.dto.common.bulky.BulkyRequestDto;
import com.apsol.ecopass.entity.bulky.BulkyOrder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
public class AdminBulkyRequestDto extends BulkyRequestDto {

    private boolean visitCollect = false;

    private boolean visitPay = false;

    private PayGroup payGroup = PayGroup.OFFLINE;

    private PayType payType;

    private FreeType freeType;


}
