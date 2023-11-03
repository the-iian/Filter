package com.spring.filter.controller;

import com.spring.filter.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j // log.info 사용할 수 있는 lombok 라이브러리
@RestController
@RequestMapping("/api/user")
public class ApiController {

    @PostMapping("")
    public User user(@RequestBody User user){

         log.info("User: {},{}", user, user); // 문자열 뒤에 들어가는 객체와 중괄호는 매칭된다 (System 찍어서 +연산자와 출력하지않아도 됨)

    }
}
