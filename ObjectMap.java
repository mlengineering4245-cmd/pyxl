package com.fepoc.test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ObjectMap {
    private Map<String, String> locators;

    public ObjectMap(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Reads a JSON file like: {"login.btn": "#login", "chat.input": "#userInput"}
            locators = mapper.readValue(new File("src/main/resources/locators/" + filePath), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ObjectMap: " + filePath, e);
        }
    }

    public String getLocator(String logicalName) {
        if (!locators.containsKey(logicalName)) {
            throw new RuntimeException("Locator not found in map: " + logicalName);
        }
        return locators.get(logicalName);
    }
}