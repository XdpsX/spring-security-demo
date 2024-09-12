package com.demo.security.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TokenResponse {
    private String accessToken;
}
