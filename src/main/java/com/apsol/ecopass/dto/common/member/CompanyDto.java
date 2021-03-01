package com.apsol.ecopass.dto.common.member;

import com.apsol.ecopass.core.enums.CompanyType;
import com.apsol.ecopass.entity.area.District;
import com.apsol.ecopass.entity.member.Company;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
public class CompanyDto {

    private long code;
    private String districtName;
    private CompanyType type;
    private String name;
    private String phone;
    private boolean deleted;

    public void of(Company company) {
        this.code = company.getCode();
        this.districtName = company.getDistrict().getName();
        this.type = company.getType();
        this.name = company.getName();
        this.phone = company.getPhone();
        this.deleted = company.isDeleted();
    }

}
