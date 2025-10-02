package com.manthan.auth_service.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginResponseDTO {
    private final String token;
}
