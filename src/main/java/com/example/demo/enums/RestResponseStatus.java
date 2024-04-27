package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum RestResponseStatus {

    SUCCESS("Success"),
    FAIL("Fail")
    ;

    private final String code;

    RestResponseStatus(String code) {
        this.code = code;
    }
}
