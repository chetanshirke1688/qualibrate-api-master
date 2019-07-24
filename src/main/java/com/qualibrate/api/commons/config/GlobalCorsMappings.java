package com.qualibrate.api.commons.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * Allows selected domains to perform cors requests.
 *  @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */
@SuppressWarnings("deprecation")
@Configuration
@Slf4j
public class GlobalCorsMappings extends WebMvcConfigurerAdapter {

    @Value("#{'${allowed.origins}'.split(',')}")
    private String[] rawOrigins;

    @Autowired
    private Environment environment;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String origins = environment.getProperty("allowed.origins");
        log.debug("Configured origins--" + origins);
        System.out.println("Configured Origins " + origins);
        registry.addMapping("/api/**")
                .allowedOrigins(rawOrigins);
    }
}
