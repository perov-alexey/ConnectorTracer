package ru.rsreu.tracer.algorithms;

import ru.rsreu.tracer.pojo.Field;

import java.util.List;

public interface Algorithm {

    List<Field> execute(Field field, boolean debugEnabled);

}
