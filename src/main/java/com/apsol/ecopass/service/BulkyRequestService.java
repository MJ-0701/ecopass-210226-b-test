package com.apsol.ecopass.service;

import com.apsol.ecopass.dto.admin.bulky.AdminBulkyRequestDto;
import com.apsol.ecopass.entity.member.Employee;

import java.text.ParseException;
import java.util.List;

public interface BulkyRequestService {

    void createRequest(AdminBulkyRequestDto dto, Employee reqEmployee) throws ParseException;

}
