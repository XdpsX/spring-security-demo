package com.demo.security.exceptions.handlers;

import com.demo.security.dtos.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleBadCredentials(HttpServletRequest request, BadCredentialsException e){
        log.error(e.getMessage(), e);
        return ErrorDTO.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getRequestURI())
                .error("Wrong email or password")
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(), e);
        return ErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .error(e.getMessage())
                .build();
    }
}
