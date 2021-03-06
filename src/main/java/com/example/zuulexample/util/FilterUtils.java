package com.example.zuulexample.util;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FilterUtils {

    // 필터 타입에 따라 요청 전, 후, 요청 라우팅 시 주울 필터 로직을 수행
    public static final String PRE_FILTER_TYPE = "pre";

    public static final String POST_FILTER_TYPE = "post";

    public static final String ROUTE_FILTER_TYPE = "route";

    public String getCorrelationId() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String correlationId = requestContext.getRequest().getHeader(UserContext.CORRELATION_ID);
        if (StringUtils.hasText(correlationId)) {
            return correlationId;
        } else {
            return requestContext.getZuulRequestHeaders().get(UserContext.CORRELATION_ID);
        }
    }

    public void setCorrelationId(String correlationId) {
        RequestContext.getCurrentContext().addZuulRequestHeader(UserContext.CORRELATION_ID, correlationId);
    }

    public final String getOrgId() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String orgId = requestContext.getRequest().getHeader(UserContext.ORG_ID);
        if (StringUtils.hasText(orgId)) {
            return orgId;
        } else {
            return requestContext.getZuulRequestHeaders().get(UserContext.ORG_ID); // 주울 서버가 대상 서버를 호출할 때 합쳐지는 Map 데이터
        }
    }

    public void setOrgId(String orgId) {
        RequestContext.getCurrentContext().addZuulRequestHeader(UserContext.ORG_ID, orgId);
    }

    public final String getUserId() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String userId = requestContext.getRequest().getHeader(UserContext.USER_ID);
        if (StringUtils.hasText(userId)) {
            return userId;
        } else {
            return requestContext.getZuulRequestHeaders().get(UserContext.USER_ID);
        }
    }

    public void setUserId(String userId) {
        RequestContext.getCurrentContext().addZuulRequestHeader(UserContext.USER_ID, userId);
    }

    public final String getAuthToken() {
        return RequestContext.getCurrentContext().getRequest().getHeader(UserContext.AUTH_TOKEN);
    }

    public String getServiceId() {
        Object serviceId = RequestContext.getCurrentContext().get("serviceId");
        return serviceId != null ? serviceId.toString() : "";
    }


}
