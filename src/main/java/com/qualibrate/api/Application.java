package com.qualibrate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.cache.ElastiCacheAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */
@SpringBootApplication(scanBasePackages = { "com.qualibrate.api.*" },
                       exclude = {
                                   ElastiCacheAutoConfiguration.class,
                                   ContextStackAutoConfiguration.class
    })
@ComponentScan("com.qualibrate.api.*")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
