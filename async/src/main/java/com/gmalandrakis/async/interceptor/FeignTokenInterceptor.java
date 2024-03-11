package com.gmalandrakis.async.interceptor;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import java.util.concurrent.TimeUnit;

public class FeignTokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Perfectly-valid-header");
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5, TimeUnit.SECONDS, 30, TimeUnit.SECONDS, false);
    }

}
