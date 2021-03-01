package com.apsol.ecopass.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ecopass.terms")
@Getter
@Setter
public class EcopassTermsProperties {

    private int cancelLimit;

    private int overPeriod;

}
