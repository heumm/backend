package com.hanheum.backend.domain.member.service;

import com.hanheum.backend.domain.member.domain.Member;
import com.hanheum.backend.domain.member.domain.Provider;
import com.hanheum.backend.domain.member.domain.Role;
import com.hanheum.backend.domain.member.dto.LoginRequestDto;
import com.hanheum.backend.domain.member.dto.JoinRequestDto;
import com.hanheum.backend.domain.member.repository.MemberRepository;
import com.hanheum.backend.global.exception.BusinessException;
import com.hanheum.backend.global.exception.BusinessStatus;
import com.hanheum.backend.global.security.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public void save(JoinRequestDto requestDto) {
        //저장 전 중복 체크
        memberRepository.findByEmail(requestDto.getEmail()).ifPresent((s) -> {
            throw new BusinessException(BusinessStatus.EMAIL_ALREADY_EXIST);
        });

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .provider(Provider.OUR)
                .roles(Set.of(Role.USER, Role.ADMIN))
                .build();
        memberRepository.save(member);
    }

    public String signIn(LoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

        //authenticationManager에서 authenticate() 호출 시 직접 구현해준 loadUserByUsername() 메서드가 실행 됩니다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String token = jwtTokenProvider.createToken(authentication);
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername 수행, email: {}", username);
        return memberRepository.findByEmail(username).orElseThrow(() -> new BusinessException(BusinessStatus.NOT_USER));
    }
}
