package com.example.board.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
@Log4j2
public class MemberController {

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 폼 요청");
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 폼 요청");
    }

    @GetMapping("/register")
    public void getRegister() {
    }

    @PostMapping("/register")
    public void postRegister() {
    }

    @ResponseBody
    @GetMapping("/auth")
    public Authentication gAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }

}
