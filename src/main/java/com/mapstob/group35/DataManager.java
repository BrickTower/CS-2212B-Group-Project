// TODO: GET AI CONVERSATION CHAT FOR THIS CODE FROM ERIC
package com.mapstob.group35;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mapstob.group35.core.PlayerProfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    // The ObjectMapper is the "engine" that handles the JSON conversion
    private static final ObjectMapper mapper = new ObjectMapper();
    
    // This defines where the file lives in your IntelliJ project folder
    private static final String FILE_PATH = "data.json";

    static {
        // This makes the JSON look "pretty" (indented) so it's readable for your TAs
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Saves the entire list of members to the data.json file.
     */
    public static void saveList(List<PlayerProfile> members) throws IOException {
        File file = new File(FILE_PATH);
        mapper.writeValue(file, members);
    }

    /**
     * Loads the list of members from the data.json file.
     * If the file doesn't exist, it returns an empty list so the app doesn't crash.
     */
    public static List<PlayerProfile> loadList() throws IOException {
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return new ArrayList<>();
        }

        // We use TypeReference because Java "erases" generic types at runtime.
        // This tells Jackson: "Expect a List of main.java.com.mapstob.group35.Member objects specifically."
        return mapper.readValue(file, new TypeReference<List<PlayerProfile>>() {});
    }
}