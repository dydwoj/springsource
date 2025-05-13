package com.example.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.ReplyDTO;
import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/board/{bno}")
    public List<ReplyDTO> getList(@PathVariable Long bno) {
        log.info("댓글 리스트 요청 bno : {}", bno);

        return replyService.getList(bno);
    }

    @GetMapping("/{rno}")
    public ReplyDTO getReply(@PathVariable Long rno) {
        log.info("댓글 한개 요청 rno : {}", rno);
        return replyService.getReply(rno);
    }

    @PutMapping("/{rno}")
    public Long updateReply(@PathVariable Long rno, @RequestBody ReplyDTO dto) {
        log.info("수정 요청 rno : {}", rno);
        return replyService.updateReply(dto);
    }

    @PreAuthorize("authentication.name == #dto.replyerEmail")
    @DeleteMapping("/{rno}")
    public void deleteReply(@PathVariable Long rno, @RequestBody ReplyDTO dto) {
        log.info("삭제 요청 rno : {}, {} ", rno, dto);
        replyService.deleteReply(rno);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/new")
    public Long postMethodName(@RequestBody ReplyDTO dto) {
        log.info("댓글 삽입 rno : {}", dto);
        return replyService.createReply(dto);
    }

}
