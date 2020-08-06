package com.example.zuulexample.filters;

import com.example.zuulexample.utils.UserContext;
import com.example.zuulexample.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        UserContext context = UserContextHolder.getContext();
        String correlationId = httpServletRequest.getHeader(UserContext.CORRELATION_ID);
        context.setCorrelationId(correlationId);
        context.setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        context.setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));
        context.setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        log.debug("route service incoming correlation id : {}", correlationId);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
