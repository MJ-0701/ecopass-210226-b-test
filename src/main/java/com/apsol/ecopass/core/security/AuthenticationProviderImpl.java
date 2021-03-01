package com.apsol.ecopass.core.security;

import com.apsol.ecopass.core.security.model.AccessedUser;
import com.apsol.ecopass.entity.auth.AuthItem;
import com.apsol.ecopass.entity.member.Employee;
import com.apsol.ecopass.service.AuthRoleService;
import com.apsol.ecopass.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 로그인 요청시 처리
 */
@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthRoleService authRoleService;

    @Override
    public Authentication authenticate(Authentication authentication)  {
        // auth user
        Employee employee = employeeService.authenticate(authentication);
        Long authCode = employee.getAuth() != null ? employee.getAuth().getCode() : null;
        // query role
        List<AuthItem> authItemList = new ArrayList<>();
        if (authCode != null) {
            authItemList = authRoleService.findAuthItemList(authCode);
        }
        // register roles
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (AuthItem authItem : authItemList) {
            authorities.add(new SimpleGrantedAuthority(authItem.getRoleName()));
        }
        // register a master role
        if(employee.isMaster()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MASTER"));
        }
        // build 'accessed user data'
        AccessedUser user = new AccessedUser(employee);
        // success
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // warn : the return value must always be 'true'
        return true;
    }

}



















































