package com.example.zuulexample.filter;


import com.example.zuulexample.util.FilterUtils;
import com.example.zuulexample.util.UserContext;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 주울 리버스 프록시 게이트웨이로 들어오는 모든 응답을 처리하는 사후 필터
 * 모든 주울 필터를 거친 후 클라이언트로 응답을 내보낼 때 수행된다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseFilter extends ZuulFilter {

    public static final int ORDER = 1;

    private final FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
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
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String correlationId = filterUtils.getCorrelationId();
        log.debug("Adding the correlation id to the outbound headers. {}", correlationId);
        requestContext.getResponse().addHeader(UserContext.CORRELATION_ID, correlationId);
        log.debug("Completing outgoing request for {}.", requestContext.getRequest().getRequestURI());
        return null;
    }

}
