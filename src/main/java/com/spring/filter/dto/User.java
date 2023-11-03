package com.spring.filter.dto;

import lombok.*;

//@Getter
//@Setter
@Data
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 전체 생성자
public class User {

    private String name;
    private int age;

}
