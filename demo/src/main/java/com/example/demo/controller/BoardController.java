package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@RequestMapping("board") // => 중복되는 위치를 한번에 잡아주는 어노테이션
@Controller
public class BoardController {

    @GetMapping("/create")
    public void getCreate() {
        // return "board/create";
    }

    @PostMapping("/create")
    // public String postCreate(@ModelAttribute("name") String name,
    // RedirectAttributes rttr) {
    public void postCreate(String name, HttpSession session) {
        log.info("name 값 가져오기 : {}", name);

        // HttpSession
        session.setAttribute("name1", name);

        // 어느 페이지로 이동을 하던지간에 name 유지시키고 싶다면
        // 방법 1. 커맨드 객체 이용
        // 방법 2. ModelAttribute 객체 or @ModelAttribute 이용
        // return "board/list";

        // redirect 값 유지하기
        // rttr.addAttribute("name", name);
        // rttr.addFlashAttribute("name", name);
        // return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void getList() {
        // return "board/list";
    }

    @GetMapping("/read")
    public void getRead() {
        // return "board/read";
    }

    @GetMapping("/update")
    public void getUpdate() {
        // return "board/update";
    }

}
