package com.hanheum.backend.domain.user.service;

import com.hanheum.backend.domain.user.domain.Provider;
import com.hanheum.backend.domain.user.domain.Role;
import com.hanheum.backend.domain.user.dto.UserRequestDto;
import com.hanheum.backend.domain.user.domain.User;
import com.hanheum.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void save(UserRequestDto requestDto) {
        //유저정보를 저장
        //저장 전 중복 체크
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .provider(Provider.OUR)
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
}
