package com.hanheum.backend.domain.user.service;

import com.hanheum.backend.domain.user.domain.Provider;
import com.hanheum.backend.domain.user.domain.Role;
import com.hanheum.backend.domain.user.domain.User;
import com.hanheum.backend.domain.user.dto.UserRequestDto;
import com.hanheum.backend.domain.user.repository.UserRepository;
import com.hanheum.backend.global.exception.BusinessException;
import com.hanheum.backend.global.exception.BusinessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void save(UserRequestDto requestDto) {
        //유저정보를 저장
        //저장 전 중복 체크
        userRepository.findByEmail(requestDto.getEmail()).ifPresent((s) -> {
            throw new BusinessException(BusinessStatus.EMAIL_ALREADY_EXIST);
        });
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .provider(Provider.OUR)
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
}
