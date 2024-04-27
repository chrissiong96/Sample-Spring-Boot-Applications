package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum Status {

    SUCCESS("Success"),
    FAIL("Fail")
    ;

    private final String code;

    Status(String code) {
        this.code = code;
    }
}
