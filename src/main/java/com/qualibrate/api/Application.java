package com.qualibrate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.cache.ElastiCacheAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.mail.MailSenderAutoConfiguration;

/**
 *
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        ElastiCacheAutoConfiguration.class,
        MailSenderAutoConfiguration.class,
        ContextStackAutoConfiguration.class
    })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
