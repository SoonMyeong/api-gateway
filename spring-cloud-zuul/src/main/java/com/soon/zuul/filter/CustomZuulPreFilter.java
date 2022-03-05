package com.soon.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomZuulPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        //시큐리티 필터를 거쳐 유효성 검사가 완료된 토큰이다. 이를 ZuulRequestHeader 에 넣어주자.
        //근데 이렇게 될 경우 모든 마이크로서비스가 jwt 를 파싱할 중복의 클래스를 필요로 하게 된다.
        //팀 컨벤션만 맞춘다면, 여기서 token 정보를 파싱하여 정보를 헤더에 담아 전달해 줄 경우 굳이 jwt 를 파싱하지 않아도 된다.
        // 아니 이렇게되면 토큰을 릴레이 할 필요도 없다.
        requestContext.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, token);
        return null;
    }
}
