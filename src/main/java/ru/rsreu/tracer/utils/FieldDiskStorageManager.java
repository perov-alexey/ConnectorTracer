package ru.rsreu.tracer.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rsreu.tracer.pojo.Field;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This manager made for storing a part of bound (for branch and bound algorithm) in consistent state.
 */
public class FieldDiskStorageManager {

    private final Logger logger = LogManager.getLogger(FieldDiskStorageManager.class);
    private File storageFolder;

    public FieldDiskStorageManager(String path) {
        storageFolder = new File(path);
        if (!storageFolder.exists()) {
            storageFolder.mkdir();
        }
    }

    /**
     * Save part of bound to the disk
     * @param bound Bound form branch and bound algorithm
     * @throws IOException When serialization gone wrong
     * @return The rest of the bound
     */
    public List<Field> saveToStorage(List<Field> bound) throws IOException {
        List<Field> restOfBound = bound.subList(bound.size() / 2, bound.size());
        ObjectMapper mapper = new ObjectMapper();
        for (Field field : restOfBound) {
            File fieldFile = new File(storageFolder.getPath() + File.separator + field.getRating() + ".json");
            if (fieldFile.exists()) {
                logger.info("Add field to already created record in storage {}", fieldFile.getName());
                List<Field> fields = mapper.readValue(fieldFile, new TypeReference<List<Field>>(){});
                fields.add(field);
                mapper.writeValue(fieldFile, fields);
            } else {
                logger.info("Create record in storage {}", fieldFile.getName());
                mapper.writeValue(fieldFile, Arrays.asList(field));
            }
        }
        return bound.subList(0, bound.size() / 2);
    }

    /**
     * Load all fields from storage. Be aware, this method can overload memory
     * @return All fields from storage
     * @throws IOException When unmarshalling gone wrong
     */
    public List<Field> loadFromStorage() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File fieldFile = new File(storageFolder.getPath());
        List<Field> loadedFields = new LinkedList<>();
        List<File> fieldFiles = Arrays.asList(fieldFile.listFiles()).stream()
                .sorted(Comparator.comparing(File::getName).reversed())
                .collect(Collectors.toList());

        for (File file : fieldFiles) {
            loadedFields.addAll(mapper.readValue(file, new TypeReference<List<Field>>(){}));
        }
        return loadedFields;
    }

    /**
     * Load concrete amount of fields from storage. Be aware, this method can overload memory
     * @param amountOfFiles Amount of loaded files from storage
     * @return Fields from storage
     * @throws IOException When unmarshalling gone wrong
     */
    public List<Field> loadFromStorage(int amountOfFiles) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File fieldFile = new File(storageFolder.getPath());
        List<Field> loadedFields = new LinkedList<>();
        List<File> fieldFiles = Arrays.asList(fieldFile.listFiles()).stream()
                .sorted(Comparator.comparing(File::getName).reversed())
                .limit(amountOfFiles)
                .collect(Collectors.toList());

        for (File file : fieldFiles) {
            loadedFields.addAll(mapper.readValue(file, new TypeReference<List<Field>>(){}));
        }
        return loadedFields.subList(0, amountOfFiles);
    }

}
