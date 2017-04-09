package ru.rsreu.junit.utils;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.rsreu.tracer.algorithms.BranchAndBoundAlgorithm;
import ru.rsreu.tracer.pojo.Field;
import ru.rsreu.tracer.utils.FieldDiskStorageManager;
import ru.rsreu.tracer.utils.FieldGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for field disk storage manager behavior verification
 */
public class FieldDiskStorageManagerTestSuit {

    private static final String STORAGE_PATH = "F:/teststorage";
    private static final int BOUND_SIZE = 100;
    private static final int AMOUNT_OF_LOADED_FILES = 5;

    private List<Field> bound;

    @Before
    public void setUp() {
        bound = new LinkedList<>();
        for (int i = 0; i < BOUND_SIZE; i++) {
            Field field = new FieldGenerator().generateField(4, 3, 2,
                    8, 10, 10);
            bound.add(new BranchAndBoundAlgorithm().execute(field, false, true).get(0));
        }
        bound = bound.stream()
                .sorted(Comparator.comparingInt(Field::getRating).reversed())
                .collect(Collectors.toList());
    }

    @After
    @Before
    public void tearDown() throws IOException {
        File storage = new File(STORAGE_PATH);
        FileUtils.deleteDirectory(storage);
    }

    /**
     * Tests saving part of the branch to the storage
     */
    @Test
    public void testSavingToStorage() throws IOException {
        FieldDiskStorageManager storageManager = new FieldDiskStorageManager(STORAGE_PATH);
        List<Field> restOfBound = storageManager.saveToStorage(bound);

        assertEquals("Returned part of bound has wrong size", bound.size() / 2, restOfBound.size());
        assertEquals("List fast check failed", bound.get(0), restOfBound.get(0));

        File storageFolder = new File(STORAGE_PATH);
        assertTrue("Created wrong amount of storage files",
                bound.size() / 2 >= storageFolder.listFiles().length);
    }

    /**
     * Tests getting part of the saved branch to the memory
     */
    @Test
    public void testGettingFromStorage() throws IOException {
        FieldDiskStorageManager storageManager = new FieldDiskStorageManager(STORAGE_PATH);
        List<Field> restOfBound = storageManager.saveToStorage(bound);

        List<Field> savedPart = storageManager.loadFromStorage();
        assertEquals("Loaded wrong bound part", bound.subList(bound.size() / 2, bound.size()), savedPart);

        // I need to add one more field to storage (which already in storage) to test correctness of fetching of
        // concrete amount of fields.
        storageManager.saveToStorage(savedPart.subList(0, 1));

        List<Field> partOfStorage = storageManager.loadFromStorage(AMOUNT_OF_LOADED_FILES);
        assertEquals("Wrong amount of returned fields", AMOUNT_OF_LOADED_FILES, partOfStorage.size());
    }

}
