package com.apsol.ecopass.controller;

import com.apsol.ecopass.core.crypto.Crypto;
import com.apsol.ecopass.core.enums.CryptoType;
import com.apsol.ecopass.entity.member.Employee;
import com.apsol.ecopass.entity.member.QEmployee;
import com.apsol.ecopass.repository.DistrictRepository;
import com.apsol.ecopass.repository.EmployeeRepository;
import com.apsol.ecopass.util.SuperClassReflectionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class TestController {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TextEncryptor textEncryptor;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DistrictRepository districtRepository;

    @GetMapping()
    public String routeRoot() {



        return "main";
    }

    @GetMapping(value = "/test/annotation")
    public String testAnnotation() {
        List<Field> cryptoFields = new ArrayList<>();

        for (Field field : SuperClassReflectionUtils.getAllFields(Employee.class)) {
            if (field.isAnnotationPresent(Crypto.class)) {
                cryptoFields.add(field);

                System.out.println(field.getName());

                /**/
                CryptoType cryptoType = field.getAnnotation(Crypto.class).cryptoType();
                if (cryptoType == CryptoType.TWO_WAY)
                    System.out.println(cryptoType);
            }
        }
        System.out.println(cryptoFields.toString());

        return "index";
    }

    @GetMapping(value = "/test/crypto")
    public String testCrypto() {

        String enc = textEncryptor.encrypt("ㅎㅇ");
        String enc3 = textEncryptor.encrypt("ㅎㅇ");
        System.out.println(enc);
        System.out.println(enc3);

        return "index";
    }

    @GetMapping(value = "/test/mk-emp")
    public String makeEmployee() {

        QEmployee qEmployee;

        return "index";
    }

    @GetMapping(value = "/test/juso")
    public String testJuso() {



        return "index";
    }

}


