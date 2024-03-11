package com.gmalandrakis.async.config;

import com.gmalandrakis.async.interceptor.FeignTokenInterceptor;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {

    @Bean
    public RequestInterceptor tokenInterceptor() {
        return new FeignTokenInterceptor();
    }

    @Bean
    public Logger.Level log() {
        return Logger.Level.FULL;
    }
}
