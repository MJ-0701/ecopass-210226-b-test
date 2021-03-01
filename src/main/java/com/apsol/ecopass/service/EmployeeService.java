package com.apsol.ecopass.service;

import com.apsol.ecopass.core.security.model.AccessedUser;
import com.apsol.ecopass.entity.member.Employee;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface EmployeeService {

    AccessedUser authenticate(String username) throws AuthenticationException;

    Employee authenticate(String username, String password) throws AuthenticationException;

    Employee authenticate(Authentication authentication) throws AuthenticationException;

    Employee getEmployeeByAccessedUser(AccessedUser user);

}
