package com.spring.filter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j // log기록
// @Component
@WebFilter(urlPatterns = "/api/user/*") // 특정 클래스, 컨트롤러에 적용시키고싶을 때 url를 지정한다 (user 하위 모든 주소를 맵핑)
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 전처리
        /* HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        누구던지 read를 한번 하면 클라이언트에서 요청이 오는 내용을 더이상 읽을 수 없는 상태로 500 error 발생  */

        // 해결 방안
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest)request); // 형변환시켜서 매개변수로 넘겨줌
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse)response);
        // ContentCachingRequestWrapper 클래스 : 캐싱을 해서 아래단에서 내용을 읽어도 계속해서 읽을 수 있다

        chain.doFilter(httpServletRequest, httpServletResponse);

        String url = httpServletRequest.getRequestURI();


        // 후처리
        // 스프링이 안에서 모든걸 맵핑한 다음 읽어야한다
        String reqContent = new String(httpServletRequest.getContentAsByteArray());

        log.info("request url : {}, request body : {}", url, reqContent);

        // 컨트롤러를 다 타고 response가 담겨서 올 것
        String resContent = new String(httpServletResponse.getContentAsByteArray());

        int httpStatus = httpServletResponse.getStatus(); // 응답 찍어보기

        // 읽었던 내용만큼 다시 한번 복사해주는 메소드 (json을 읽어들일 때 body가 비어있지않도록)
        httpServletResponse.copyBodyToResponse();

        log.info("response status : {}, responseBody : {}", httpStatus, resContent);
    }
}
