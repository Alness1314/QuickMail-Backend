package com.alness.quickmail.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestExceptionHandler extends RuntimeException{
    private String code;
    private HttpStatus status;

    public RestExceptionHandler(String code, HttpStatus status, String message){
        super(message);
        this.code = code;
        this.status = status;
    }
}
