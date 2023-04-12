package com.hanheum.backend.domain.member.controller;

import com.hanheum.backend.domain.member.dto.JwtResponseDto;
import com.hanheum.backend.domain.member.dto.LoginRequestDto;
import com.hanheum.backend.domain.member.dto.JoinRequestDto;
import com.hanheum.backend.domain.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        log.info("login메서드 호출 {}", requestDto);
        String token = memberServiceImpl.signIn(requestDto);


        return ResponseEntity.ok(
                JwtResponseDto.builder()
                        .grantType("Bearer")
                        .accessToken(token)
                        .build()
        );
    }

    @PostMapping("/join")
    public ResponseEntity<Objects> join(@RequestBody JoinRequestDto requestDto) {
        log.debug("REQUEST DATA:: {}", requestDto);
        memberServiceImpl.save(requestDto);
        return ResponseEntity.ok().build();
    }
}
