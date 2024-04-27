package com.example.demo.model;

import com.example.demo.utils.CommonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

    private String status;
    private T data;
    private String errorMessage;

    public static <T> RestResponse<T> success(T data, String methodName, Object... request) throws IOException {
        RestResponse<T> restResponse = new RestResponse<>("Success", data, null);
        CommonUtils.exportJsonObject(restResponse, methodName, request);
        return restResponse;
    }

    public static <T> RestResponse<T> fail(String errorMessage, String methodName, Object... request) throws IOException {
        RestResponse<T> restResponse = new RestResponse<>("Failed", null, errorMessage);
        CommonUtils.exportJsonObject(restResponse, methodName, request);
        return restResponse;
    }
}
