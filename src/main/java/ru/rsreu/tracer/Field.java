package ru.rsreu.tracer;

import io.swagger.annotations.ApiModel;

@ApiModel("Field")
public class Field {

    public Field() {

    }

    private String test;
    private Field field;


    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
