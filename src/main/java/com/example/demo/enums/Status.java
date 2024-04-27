package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum Status {

    Active("A"),
    Deleted("D")
    ;

    private final String code;

    Status(String code) {
        this.code = code;
    }
}
