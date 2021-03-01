package com.apsol.ecopass.controller.api;

import com.apsol.ecopass.core.properties.JwtProperties;
import com.apsol.ecopass.core.security.*;
import com.apsol.ecopass.core.security.model.AccessedUser;
import com.apsol.ecopass.core.security.model.JwtTokenWrapper;
import com.apsol.ecopass.core.security.model.LoginRequest;
import com.apsol.ecopass.entity.auth.AuthItem;
import com.apsol.ecopass.entity.member.Employee;
import com.apsol.ecopass.service.AuthRoleService;
import com.apsol.ecopass.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class ApiAuthController extends AbstractApiController {

    @Autowired
    JwtTokenBuilder jwtTokenBuilder;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AuthRoleService authRoleService;

    @Autowired
    AuthenticationProviderImpl authenticationProvider;

    @RequestMapping(value = "authorize", method = { RequestMethod.POST })
    @ResponseBody
    public JwtTokenWrapper authorize(HttpServletResponse response, @RequestBody LoginRequest loginRequest) throws AuthenticationException {

        setHeader(response, "POST", "text");

        Employee employee = employeeService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        AccessedUser accessedUser = new AccessedUser(employee);

        // query role
        Long authCode = employee.getAuth() != null ? employee.getAuth().getCode() : null;
        List<AuthItem> authItemList = new ArrayList<>();
        if (authCode != null) {
            authItemList = authRoleService.findAuthItemList(authCode);
        }

        return jwtTokenBuilder.generateToken(accessedUser, jwtProperties, authItemList);
    }

    // todo :: refreash 구현할 것

}
