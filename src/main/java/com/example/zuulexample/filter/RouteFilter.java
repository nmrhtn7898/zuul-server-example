package com.example.zuulexample.filter;

import com.example.zuulexample.util.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteFilter extends ZuulFilter {

    private static final int ORDER = 1;

    private final FilterUtils filterUtils;

    private final RestTemplate restTemplate;

    @Override
    public String filterType() {
        return FilterUtils.ROUTE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.debug("nuguri-correlation-id found in route filter : {}.", filterUtils.getCorrelationId());
        return null;
    }
}
