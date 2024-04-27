package com.example.demo.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class CommonUtils {

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static void exportJsonObject(Object response, String fileName, Object... request) throws IOException {
        getObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("generated_file/" + fileName + "-request" + ".json"), request);
        getObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("generated_file/" + fileName + "-response" + ".json"), response);
    }
}
