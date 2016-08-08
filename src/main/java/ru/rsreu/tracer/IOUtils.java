package ru.rsreu.tracer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import ru.rsreu.tracer.pojo.Field;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IOUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Field readField(String path) {
        Field field = null;
        try {
            ClassPathResource resource = new ClassPathResource("config.json");
            field = mapper.readValue(resource.getFile(), Field.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return field;
    }

    public static void writeField(Field field, String path) {
        try {
            mapper.writeValue(new File(path), field);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(Field field) {
        String res = "";
        try {
            res = mapper.writeValueAsString(field);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void writeString(String field, String path) {
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write(field);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Field[] readFieldsArray(String path) {
        Field[] field = null;
        try {
            field = mapper.readValue(new File(path), Field[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return field;
    }

}
