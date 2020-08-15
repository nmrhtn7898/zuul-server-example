package com.example.zuulexample;

import com.example.zuulexample.interceptor.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@EnableZuulProxy
@SpringBootApplication
public class ZuulExampleApplication {

    /**
     * 주울 API 게이트 웨이에서 서비스 인스턴스로 요청을 보낼떄는 별도의 Http Client르 사용하지만
     * RouteFilter에서 요청을 보내주는 경우 직접 사용할 클라이언트를 생성해서 사용할 수 있음
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(new UserContextInterceptor()); // 서비스 인스턴스들로 라우팅 되는 요청을 인터셉트하여 헤더를 설정하는 인터셉터 설정
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulExampleApplication.class, args);
    }

}
