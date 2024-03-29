package com.qualibrate.api.commons.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author <a href="mailto:chetan.shirke1688@gmail.com">Chetan Shirke</a>
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String ADMIN_ROLE = "ADMIN";

    @Value("${secure.admin.password}")
    private String adminPassword;

    @Value("${allowed.origins}")
    private String rawOrigins;

    @Autowired
    private Environment environment;

    public SecurityConfiguration() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().and()

                // Allow anonymous access
                .anonymous().and()

                // Enable Basic Authentication
                .httpBasic().realmName("Qualibrate API 2.0").and()

                .servletApi().and()

                // Stateless session management
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // Disable CSRF & Frame Options since we're not serving HTML content
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .headers().xssProtection().disable().and()


                .authorizeRequests()
                // Allow anonymous resource requests
                .antMatchers("/").permitAll()
                // yet to enable beanstalk health  and info url
                .antMatchers("/health").permitAll()
                .antMatchers("/info").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                // Secure all APIs
                .regexMatchers("/api/v[0-9]+/.*").hasAnyRole(ADMIN_ROLE)
                // allow cross orgin requests
                .and().cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(adminPassword).roles(ADMIN_ROLE);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        String origins = environment.getProperty("allowed.origins");
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(origins);
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("accept");
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("Access-Control-Request-Method");
        config.addAllowedHeader("Access-Control-Request-Method");
        config.addExposedHeader("Access-Control-Allow-Origin");
        config.addExposedHeader("Access-Control-Allow-Credentials");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(rawOrigins, config);
        return source;
    }

    @Bean
    public HttpFirewall allowUrlEncodedPercentHttpFirewall() {
        List<String> httpMethods = new ArrayList<String>();
        httpMethods.add("OPTIONS");
        httpMethods.add("HEAD");
        httpMethods.add("GET");
        httpMethods.add("PUT");
        httpMethods.add("POST");
        httpMethods.add("DELETE");
        httpMethods.add("PATCH");
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowedHttpMethods(httpMethods);
        firewall.setAllowSemicolon(true);
        firewall.setAllowUrlEncodedPeriod(true);
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setUnsafeAllowAnyHttpMethod(true);
        return firewall;
    }
}
