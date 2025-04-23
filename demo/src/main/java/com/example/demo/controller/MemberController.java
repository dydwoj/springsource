package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/member")
@Log4j2
@Controller
public class MemberController {
    // 회원가입 : /member/register
    // 로그인 : /member/login
    // 로그아웃 : /member/logout
    // 비밀번호변경 : /member/change

    // http://localhost:8080/member/register
    // void : templates/member/register.html

    @GetMapping("/register")
    public void getRegister(@ModelAttribute("mDto") MemberDTO memberDTO) {
        log.info("회원가입");
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("mDto") @Valid MemberDTO memberDTO, BindingResult result,
            RedirectAttributes rttr) {
        log.info("등록 {}", memberDTO);

        // 유효성 검사 통과하지 못했다면 원래 입력 페이지로 돌아가기
        if (result.hasErrors()) {
            return "/member/register";
        }

        // redirect 방식으로 움직이면서 값을 보내는 방법
        // 방법 1. addAttribute
        rttr.addAttribute("userid", memberDTO.getUserid());
        // => /member/login?userid=1
        // 방법 2. addFlashAttribute
        rttr.addFlashAttribute("password", memberDTO.getPassword());
        // =>
        // 로그인 페이지로 이동
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인");
    }

    @PostMapping("/login")
    // public void postLogin(String userid, String password) {
    // public void postLogin(LoginDTO loginDTO) {
    public void postLogin(HttpServletRequest request) {

        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String remote = request.getRemoteAddr();
        String local = request.getLocalAddr();

        log.info("로그인 요청 {}, {}", userid, password);
        log.info("클라이언트 정보 {}, {}", remote, local);
        // templates 찾기
    }

    @GetMapping("/logout")
    public void getLogout() {
        log.info("로그아웃");
    }

    @GetMapping("/change")
    public void getChange() {
        log.info("비밀번호변경");
    }

}
