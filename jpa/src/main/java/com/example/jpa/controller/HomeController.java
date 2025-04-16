package com.example.jpa.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/main")
    public void getMain() {
        log.info("main 요청");
    }

}
