package com.example.zuulexample.filter;

import com.example.zuulexample.util.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * 주울 리버스 프록시 게이트웨이로 들어오는 모든 요청을 처리하는 사전 필터, 필요한 헤더 정보 셋팅이나 인증 및 인가 처리 수행
 * 서블릿 필터를 거친 후, 수행된 후 수행된다.
 */
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

    @Override
    public boolean shouldFilter() { //필터 활성화 여부
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
