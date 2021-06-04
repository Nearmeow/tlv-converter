package com.mariakh;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

public class JsonSaver {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void save(Path outputFile, Object object) {
        try {
            mapper.writeValue(outputFile.toFile(), object);
            System.out.println("Success!");
        } catch (IOException e) {
            System.out.println("Error occurred while saving the file.");
        }
    }
}
