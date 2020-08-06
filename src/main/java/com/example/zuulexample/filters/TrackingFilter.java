package com.example.zuulexample.filters;

import com.example.zuulexample.utils.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrackingFilter extends ZuulFilter {

    private final FilterUtils filterUtils;

    private boolean isCorrelationIdPresent() { // 상관 관계 ID 존재 유무 검사
        return StringUtils.hasText(filterUtils.getCorrelationId());
    }

    private String generateCorrelationId() { // 상관 관계 ID 생성
        return UUID.randomUUID().toString();
    }

    @Override
    public String filterType() { // 필터 타입
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() { // 필터 적용 순서
        return 1;
    }

    //필터 활성화 여부


    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() { // 서비스가 필터를 통과할때마다 실행하는 비즈니스 로직, 상관 관계 ID 생성
        if (isCorrelationIdPresent()) {
            log.debug("nuguri-correlation-id found in tracking filter : {}.", filterUtils.getCorrelationId());
        } else {
            String correlationId = generateCorrelationId();
            filterUtils.setCorrelationId(correlationId);
            log.debug("nuguri-correlation-id generated in tracking filter : {}.", correlationId);
        }
        RequestContext requestContext = RequestContext.getCurrentContext();
        log.debug("processing incoming request for {}.", requestContext.getRequest().getRequestURI());
        return null;
    }


}
