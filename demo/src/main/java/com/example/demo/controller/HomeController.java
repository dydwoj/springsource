package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class HomeController {

    // http://localhost:8080/
    @GetMapping("/") // => / 는 8080 의미
    public String getHome() {
        log.info("home 요청");
        return "home";
        // 해당 리턴값의 이름은 무조건 tamplates 의 파일명이 되어야 함
        // 로컬호스트로 들어가서 홈페이지를 작성해야지 home.html 에서 오픈 하면 안됨!!
    }

    // http://localhost:8080/basic
    @GetMapping("/basic")
    public String getMethodName() {
        return "info";
    }

}
