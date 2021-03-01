package com.apsol.ecopass.core.security.model;

import com.apsol.ecopass.dto.common.member.CompanyDto;
import com.apsol.ecopass.dto.common.member.EmployeeDto;
import com.apsol.ecopass.entity.member.Company;
import com.apsol.ecopass.entity.member.Employee;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
public class AccessedUser {

    private String username;

    private String name;

    private EmployeeDto employee;

    private CompanyDto company;

    public AccessedUser(Employee employee) {
        this.username = employee.getUsername();
        this.name = employee.getName();
        setDtos(employee);
    }

    public void setDtos(Employee employee) {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.of(employee);
        CompanyDto companyDto = new CompanyDto();
        if (employee.getCompany() != null) companyDto.of(employee.getCompany());

        this.employee = employeeDto;
        this.company = companyDto;
    }
}
