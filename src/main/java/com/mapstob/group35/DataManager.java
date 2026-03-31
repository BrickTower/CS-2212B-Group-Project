package com.mapstob.group35;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataManager {


    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Saves the data in the file from the given filename into a list of any object type
     * using the writeValue() method from the ObjectMapper class from FasterXML in the Jackson Library.
     *
     *
     * @param fileName
     * @param data
     * @param <T>
     * @throws IOException
     */
    public static <T> void saveList(String fileName, List<T> data) throws IOException {
        mapper.writeValue(new File(fileName), data);
    }

    /**
     * Loads the data from a file with the given filename into a list of any type using the
     * reaadValue() method from the ObjectMapper class.
     *
     * @param fileName
     * @param typeReference
     * @return
     * @param <T>
     * @throws IOException
     */
    public static <T> List<T> loadList(String fileName, TypeReference<List<T>> typeReference) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) return new ArrayList<>();
        return mapper.readValue(file, typeReference);
    }
}