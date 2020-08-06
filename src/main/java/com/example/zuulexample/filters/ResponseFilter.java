package com.example.zuulexample.filters;


import com.example.zuulexample.utils.FilterUtils;
import com.example.zuulexample.utils.UserContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseFilter extends ZuulFilter {

    private final FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String correlationId = filterUtils.getCorrelationId();
        log.debug("Adding the correlation id to the outbound headers. {}", correlationId);
        requestContext.getResponse().addHeader(UserContext.CORRELATION_ID, correlationId);
        log.debug("Completing outgoing request for {}.", requestContext.getRequest().getRequestURI());
        return null;
    }

}
