package com.apsol.ecopass.controller.api;

import com.apsol.ecopass.core.properties.JwtProperties;
import com.apsol.ecopass.core.security.model.AccessedUser;
import com.apsol.ecopass.core.security.ErrorResource;
import com.apsol.ecopass.core.exception.AuthorizeException;
import com.apsol.ecopass.service.EmployeeService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbstractApiController extends CorsController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private EmployeeService employeeService;

    /**
     * 토큰기간 만료시 에러메시지 반환
     * @param e     TokenExpiredException
     * @return      ErrorResource
     */
    @ExceptionHandler(TokenExpiredException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResource handleTokenExpirationExceptions(Exception e){
        return new ErrorResource(401, "토큰 기간 만료");
    }

    /**
     * 인증 실패시 에러메시지 반환
     * @param e     AuthorizeException
     * @return      ErrorResource
     */
    @ExceptionHandler(AuthorizeException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorResource handleExceptions(Exception e){

        return new ErrorResource(401, e.getMessage());
    }

    /**
     * 토큰에 헤더를 세팅한다
     * @param response
     * @param method
     * @param contentType
     */
    public void setHeader(HttpServletResponse response, String method, String contentType){

        if(contentType.equals("json")){
            response.setContentType("application/json");
        }
        else if(contentType.equals("text")){
            response.setContentType("text/plain");
        }
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Methods", method);
        response.addHeader("Access-Control-Allow-Headers", "Authorization");
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    // Api 접속자의 정보를 가져옴
    protected AccessedUser access(HttpServletRequest request) throws AuthorizeException {

        // 헤더에 토큰이 없는 경우 예외처리
        String token = request.getHeader(jwtProperties.getHeaderString());
        if (token == null)
            throw new AuthorizeException("헤더에 접근 토큰이 없습니다.");
        // 토큰 복호화
        DecodedJWT djwt = JWT.require(Algorithm.HMAC512(jwtProperties.getSecret().getBytes())).build()
            .verify(token.replace(jwtProperties.getTokenPrefix(), ""));
        // username get
        String username = djwt.getSubject();

        return access(username);
    }

    protected AccessedUser access(String username) throws AuthorizeException {
        return employeeService.authenticate(username);
    }





}


