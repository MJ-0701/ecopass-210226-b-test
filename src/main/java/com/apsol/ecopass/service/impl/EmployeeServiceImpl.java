package com.apsol.ecopass.service.impl;

import com.apsol.ecopass.core.security.model.AccessedUser;
import com.apsol.ecopass.entity.member.Employee;
import com.apsol.ecopass.entity.member.QEmployee;
import com.apsol.ecopass.service.EmployeeService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 로그인
     * @param username                  아이디
     * @param password                  패스워드
     * @return Employee                 직원 정보
     * @throws AuthenticationException  인증 예외 처리
     */
    @Override
    public Employee authenticate(String username, String password) throws AuthenticationException {
        // query account
        logger.info("AUTH :: " + username + " .................");
        Employee employee = findByUsername(username);

        // password miss-match
        if (!passwordEncoder.matches(password, employee.getPassword())) {
            logger.info("AUTH :: " + username + " Authentication Fail password miss-match");
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }
        // success
        logger.info("AUTH :: " + username + " Succeed in Authentication");
        return employee;
    }

    /**
     * 토큰 유저 ID를 이용하여 AccessedUser 객체 생성, 반환
     * @param username      ID
     * @return              AccessedUser
     */
    @Override
    public AccessedUser authenticate(String username) throws AuthenticationException {

        logger.info("API-AUTH :: " + username + " .................");
        Employee employee = findByUsername(username);

        AccessedUser user = new AccessedUser(employee);

        user.setDtos(employee);

        return user;
    }

    /**
     * 유저 아이디 기반으로 유저데이터를 쿼리
     * @param username      ID
     * @return              Employee
     */
    private Employee findByUsername(String username) throws AuthenticationException {
        QEmployee table = QEmployee.employee;

        Employee employee = jpaQueryFactory
            .selectFrom(table)
            .where(table.username.eq(username))
            .fetchOne();
        // not found
        if (employee == null) {
            logger.info("AUTH :: " + username + " Authentication Fail - username not found");
            throw new UsernameNotFoundException("찾을 수 없는 사용자");
        }
        // deleted
        if (employee.isDeleted()) {
            logger.info("AUTH :: " + username + " Authentication Fail deleted user");
            throw new AuthenticationCredentialsNotFoundException("찾을 수 없는 사용자");
        }
        return employee;
    }

    /**
     * (오버로딩)
     * 로그인
     * @param authentication        인증요청 객체
     * @return Employee
     */
    @Override
    public Employee authenticate(Authentication authentication) {
        // getting id, pw
        String username = (String) authentication.getPrincipal();
        String password	= (String) authentication.getCredentials();
        // overloading
        return authenticate(username, password);
    }

    /**
     * 로그인된 사용자의 정보를 바탕으로 사용자 <Entity>를 검색한다
     * @param user          로그인된 사용자 정보
     * @return Employee     사용자 엔티티
     */
    @Override
    public Employee getEmployeeByAccessedUser(AccessedUser user) {

        QEmployee table = QEmployee.employee;
        Employee employee = jpaQueryFactory.selectFrom(table)
                .where(table.username.eq(user.getUsername()))
                .where(table.deleted.isFalse())
                .fetchOne();

        return employee;
    }
}
