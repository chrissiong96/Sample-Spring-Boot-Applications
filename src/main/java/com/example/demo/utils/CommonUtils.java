package com.example.demo.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class CommonUtils {

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static void exportJsonObject(Object response, String fileName, Object... request) throws IOException {
        Path path = Paths.get("generated_file/");
        Files.createDirectories(path);
        getObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("generated_file/" + fileName + "-request" + ".json"), request);
        getObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("generated_file/" + fileName + "-response" + ".json"), response);
    }
}
