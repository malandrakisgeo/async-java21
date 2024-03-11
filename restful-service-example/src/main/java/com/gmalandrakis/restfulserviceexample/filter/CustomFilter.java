package com.gmalandrakis.restfulserviceexample.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println("Request received on: " + System.currentTimeMillis());

        final var auth = req.getHeader("Authorization");
        if (auth != null && auth.equals("Perfectly-valid-header")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Auth failed");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
