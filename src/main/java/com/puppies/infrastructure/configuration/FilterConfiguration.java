package com.puppies.infrastructure.configuration;

import com.puppies.infrastructure.Filter.JwtRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfiguration {

    private final JwtRequestFilter jwtRequestFilter;

    public FilterConfiguration(JwtRequestFilter jwtRequestFilter){
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public FilterRegistrationBean<JwtRequestFilter> loggingFilter(){
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtRequestFilter);
        registrationBean.addUrlPatterns("/post/*");
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 5);

        return registrationBean;
    }
}
