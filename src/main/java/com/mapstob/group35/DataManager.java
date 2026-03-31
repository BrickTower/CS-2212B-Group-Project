package com.mapstob.group35;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    // object mapper for json file
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static <T> void saveList(String fileName, List<T> data) throws IOException {
        mapper.writeValue(new File(fileName), data);
    }

    public static <T> List<T> loadList(String fileName, TypeReference<List<T>> typeReference) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) return new ArrayList<>();
        return mapper.readValue(file, typeReference);
    }
}