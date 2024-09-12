package com.demo.security.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UserProfile {
    private Long id;
    private String name;
    private String email;
}
