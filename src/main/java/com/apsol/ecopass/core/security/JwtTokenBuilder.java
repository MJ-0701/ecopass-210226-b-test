package com.apsol.ecopass.core.security;

import com.apsol.ecopass.core.properties.JwtProperties;
import com.apsol.ecopass.core.security.model.AccessedUser;
import com.apsol.ecopass.core.security.model.JwtTokenWrapper;
import com.apsol.ecopass.entity.auth.AuthItem;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JwtTokenBuilder {

    public JwtTokenWrapper generateToken (AccessedUser user, JwtProperties jwtProperties, List<AuthItem> authItemList){

        int expirationHours = jwtProperties.getExpirationHours();

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date());               // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, expirationHours);      // adds one hour
        Date expiresAt = cal.getTime();

        String jwtToken = JWT.create()
            .withIssuer("apsol")                // 발급자     iss
            .withSubject(user.getUsername())    // 제목       sub
            .withAudience(user.getUsername())   // 대상자     aud
            .withExpiresAt(expiresAt)           // 만료일시   exp
            .withIssuedAt(new Date())           // 발급일시   iat
            .sign(Algorithm.HMAC512(jwtProperties.getSecret().getBytes())); // crypto

        JwtTokenWrapper jwtTokenWrapper = new JwtTokenWrapper();
        jwtTokenWrapper.setToken(jwtToken);
        jwtTokenWrapper.setExpiresAt(expiresAt);
        jwtTokenWrapper.setAccessedUser(user);
        jwtTokenWrapper.setAuthItemList(authItemList);

        return jwtTokenWrapper;

    }

}
