package com.mapstob.group35;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mapstob.group35.core.ParentProfile;
import com.mapstob.group35.core.PlayerProfile;

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
 * @author Eric Chen
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


    /**
     * Loads all PlayerProfile objects from the players.json file.
     * <p>
     * If the file cannot be found or read, this method returns an empty list
     * instead of causing an error.
     * </p>
     * @author Amlan Tamaki Konan
     * @return a List of PlayerProfile objects, or an empty list if loading fails
     */
    public static List<PlayerProfile> loadAllPlayerProfiles() {
        try {
            return loadList("players.json", new TypeReference<List<PlayerProfile>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Loads all ParentProfile objects from the parents.json file.
     * <p>
     * If the file cannot be found or read, this method returns an empty list
     * to keep the program running safely.
     * </p>
     *
     * @return a list of ParentProfile objects, or an empty list if loading fails
     */
    public static List<ParentProfile> loadAllParentProfiles() {
        try {
            return loadList("parents.json", new TypeReference<List<ParentProfile>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Combines all PlayerProfile and ParentProfile users and sorts them
     * by their highest wave count (peak waves), from highest to lowest.
     * <p>
     * This method is used to build the leaderboard on the main menu.
     * </p>
     *
     * @return a sorted list of all users, with the highest wave players first
     */
    public static List<Object> getAllUsersSortedByWaves() {
        List<Object> users = new ArrayList<>();

        users.addAll(loadAllPlayerProfiles());
        users.addAll(loadAllParentProfiles());

        users.sort((a, b) -> {
            int waveA = (a instanceof PlayerProfile)
                    ? ((PlayerProfile) a).getPlayerStats().getPeakWaves()
                    : ((ParentProfile) a).getPlayerStats().getPeakWaves();

            int waveB = (b instanceof PlayerProfile)
                    ? ((PlayerProfile) b).getPlayerStats().getPeakWaves()
                    : ((ParentProfile) b).getPlayerStats().getPeakWaves();

            return Integer.compare(waveB, waveA);
        });

        return users;
    }

}