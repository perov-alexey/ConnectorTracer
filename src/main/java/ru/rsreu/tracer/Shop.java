package ru.rsreu.tracer;

import java.util.Map;

public class Shop {
    String name;
    String staffName[];
    Map<String, Object> testMap;
    Field field;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getStaffName() {
        return staffName;
    }

    public void setStaffName(String[] staffName) {
        this.staffName = staffName;
    }

    public Map<String, Object> getTestMap() {
        return testMap;
    }

    public void setTestMap(Map<String, Object> testMap) {
        this.testMap = testMap;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
