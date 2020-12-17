package com.admin.common.starter;

import com.admin.common.starter.aspect.LogAspect;
import com.admin.common.starter.handler.GlobalExceptionHandler;
import com.admin.common.starter.handler.RestResultHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author trayvonn.
 * @since 2020/12/16 11:59
 */
@Configuration
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestResultHandler restResultHandler(){
        return new RestResultHandler(new String[]{});
    }

    @Bean
    public LogAspect logAspect(){
        return new LogAspect();
    }
}

