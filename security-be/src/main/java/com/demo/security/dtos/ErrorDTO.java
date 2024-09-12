package com.demo.security.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter @Getter
@Builder
public class ErrorDTO {
    @Builder.Default
    private Date timestamp = new Date();
    private int status;
    private String path;
    private String error;

}
