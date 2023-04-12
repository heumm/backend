package com.hanheum.backend.domain.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginRequestDto {
    private String email;
    private String password;

}
