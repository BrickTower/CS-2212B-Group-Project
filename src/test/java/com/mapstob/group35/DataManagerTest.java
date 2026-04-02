package com.mapstob.group35;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataManagerTest {

    private final String TEST_FILE = "test_data.json";

    // Simple inner class to test generic serialization
    static class TestObject {
        public String name;
        public int value;

        // Jackson needs a default constructor
        public TestObject() {}

        public TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    @BeforeEach
    @AfterEach
    void cleanup() {
        // Delete the test file before and after each test to stay clean
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    @DisplayName("Should save and load a list of strings correctly")
    void testSaveAndLoadStrings() throws IOException {
        List<String> original = List.of("Apple", "Banana", "Cherry");
        
        DataManager.saveList(TEST_FILE, original);
        
        List<String> loaded = DataManager.loadList(TEST_FILE, new TypeReference<List<String>>() {});
        
        assertEquals(3, loaded.size());
        assertEquals("Apple", loaded.get(0));
        assertEquals("Cherry", loaded.get(2));
    }

    @Test
    @DisplayName("Should save and load a list of custom objects")
    void testSaveAndLoadObjects() throws IOException {
        List<TestObject> original = new ArrayList<>();
        original.add(new TestObject("Unit1", 100));
        original.add(new TestObject("Unit2", 200));

        DataManager.saveList(TEST_FILE, original);

        List<TestObject> loaded = DataManager.loadList(TEST_FILE, new TypeReference<List<TestObject>>() {});

        assertEquals(2, loaded.size());
        assertEquals("Unit1", loaded.get(0).name);
        assertEquals(200, loaded.get(1).value);
    }

    @Test
    @DisplayName("Should return an empty list if the file does not exist")
    void testLoadMissingFile() throws IOException {
        // Ensure file definitely doesn't exist
        File file = new File("non_existent.json");
        if(file.exists()) file.delete();

        List<String> result = DataManager.loadList("non_existent.json", new TypeReference<List<String>>() {});
        
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Should return an empty ArrayList when file is missing");
    }

    @Test
    @DisplayName("Should overwrite existing file when saving a new list")
    void testOverwriteBehavior() throws IOException {
        // Save first list
        DataManager.saveList(TEST_FILE, List.of("First"));
        
        // Save second list to same file
        DataManager.saveList(TEST_FILE, List.of("Second", "Third"));

        List<String> loaded = DataManager.loadList(TEST_FILE, new TypeReference<List<String>>() {});
        
        assertEquals(2, loaded.size());
        assertEquals("Second", loaded.get(0));
    }
}