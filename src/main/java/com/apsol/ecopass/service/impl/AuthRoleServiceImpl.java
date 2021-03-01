package com.apsol.ecopass.service.impl;

import com.apsol.ecopass.entity.auth.Auth;
import com.apsol.ecopass.entity.auth.AuthItem;
import com.apsol.ecopass.entity.auth.QAuthItem;
import com.apsol.ecopass.service.AuthRoleService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AuthItem> findAuthItemList(Long authCode) {
        // find by id
        QAuthItem qAuthItem = QAuthItem.authItem;
        return jpaQueryFactory.selectFrom(qAuthItem)
                .where(qAuthItem.authCode.eq(authCode)).fetch();
    }

    @Override
    public List<AuthItem> findAuthItemList(Auth auth) {
        // overloading
        return findAuthItemList(auth.getCode());
    }

}
