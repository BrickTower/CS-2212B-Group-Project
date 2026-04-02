package com.mapstob.group35;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * DataManager is a utility class responsible for handling the serialization and
 * deserialization of data to and from JSON files.
 * <p>
 * It utilizes the Jackson library's {@code ObjectMapper} to provide generic
 * methods for saving and loading lists of objects.
 * </p>
 *
 */
public class DataManager {

    /**
     *
     * Jackson object mapper object configured with {@link SerializationFeature#INDENT_OUTPUT}
     * for human-readable JSON formatting.
     */
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Saves a list of objects in specified file.
     * <p>
     * Saves the data in the file from the given filename into a list of any object type
     * using the {@code writeValue()} method from the ObjectMapper class from FasterXML in the Jackson Library.
     * </p>
     *
     *
     * @param fileName The name of the file
     * @param data The list of objects to be saved to
     * @param <T> The generic type of the objects
     * @throws IOException Error thrown if error occurs while saving
     */
    public static <T> void saveList(String fileName, List<T> data) throws IOException {
        mapper.writeValue(new File(fileName), data);
    }

    /**
     * Loads data from a JSON file into a list of a specific type.
     * <p>
     * If the specified file does not exist, an empty {@link ArrayList} is returned
     * to prevent null pointer exceptions.
     * </p>
     *
     * @param fileName      The path and name of the file to read from.
     * @param typeReference A {@link TypeReference} used to preserve the generic type
     * information of the list at runtime.
     * @param <T>           The generic type of the objects to be returned in the list.
     * @return A {@link List} containing the deserialized objects, or an empty list if
     * the file is not found.
     * @throws IOException If the file exists but an error occurs during reading or parsing.
     */
    public static <T> List<T> loadList(String fileName, TypeReference<List<T>> typeReference) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) return new ArrayList<>();
        return mapper.readValue(file, typeReference);
    }
}