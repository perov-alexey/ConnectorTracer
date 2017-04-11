package ru.rsreu.tracer.algorithms;

import ru.rsreu.tracer.pojo.Field;

import java.util.List;

public interface Algorithm {

    /**
     * Run algorithm.
     * @param field Field to be traced.
     * @param debugEnabled Enable debug mode. In this case this method return list with all algorithm steps.
     *                     Be aware, this method requires much more memory and steps list big a lot.
     * @param requireBestSolution If enable this option, algorithm find best solution. Be aware, this option can
     *                            significantly increase algorithm execution time and required memory.
     * @return List with solution (field with traced links).
     */
    List<Field> execute(Field field, boolean debugEnabled, boolean requireBestSolution) throws InterruptedException;

}
