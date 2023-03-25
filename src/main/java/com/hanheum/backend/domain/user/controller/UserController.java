package com.hanheum.backend.domain.user.controller;

import com.hanheum.backend.domain.user.dto.UserRequestDto;
import com.hanheum.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<String> login() {
        String user = "user";
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/v1/user")
    public ResponseEntity<Objects> signUp(@RequestBody UserRequestDto requestDto) {
//        log.info("input data === {}", requestDto.toString());
        userService.save(requestDto);
        return ResponseEntity.ok().build();
    }
}
