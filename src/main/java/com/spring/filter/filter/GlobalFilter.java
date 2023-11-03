package com.spring.filter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j // log기록
@Component // 스프링에 의해서 관리되도록 등록
public class GlobalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 전처리

        /* HttpServletRequest httpServletRequest = (HttpServletRequest)request; 형변환
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        누구던지 read를 한번 하면 클라이언트에서 요청이 오는 내용을 더이상 읽을 수 없는 상태로 500 error 발생  */

        // 해결 방안
        ContentCachingRequestWrapper httpServletRequest = (ContentCachingRequestWrapper)request;
        ContentCachingResponseWrapper httpServletResponse = (ContentCachingResponseWrapper)response;
        // ContentCachingRequestWrapper 클래스 : 캐싱을 해서 아래단에서 내용을 읽어도 계속해서 읽을 수 있다


        String url = httpServletRequest.getRequestURI();

        // BufferedReader 예제
        BufferedReader br = httpServletRequest.getReader();

        br.lines().forEach(line -> {
            log.info("url : {}, line : {}", url, line);
        });

        br.read(); // read()메서드 사용시 커서단 위로 읽어들인다

        chain.doFilter(httpServletRequest, httpServletResponse);

        // 후처리

    }
}
