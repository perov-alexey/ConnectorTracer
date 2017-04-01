package ru.rsreu.tracer.algorithms;

import com.rits.cloning.Cloner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rsreu.tracer.pojo.Field;
import ru.rsreu.tracer.pojo.Link;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This algorithm use Dynamic Programming method to solve task.
 * Be aware, this algorithm suitable only for strong fields.
 * With weak fields this algorithm degenerating to brute force algorithm.
 */
public class DynamicProgrammingAlgorithm implements Algorithm {

    private long combinationsAmount;
    private Logger logger = LogManager.getLogger(DynamicProgrammingAlgorithm.class);

    @Override
    public List<Field> execute(Field field, boolean debugEnabled, boolean requireBestSolution) {
        if (debugEnabled) {
            logger.warn("Dynamic programming algorithm doesn't support debug mode yet");
        }

        combinationsAmount = (long) Math.pow(2, field.getLinks().size());
        logger.debug("Maximum amount of combinations: {}", combinationsAmount);


        List<Field> solutions = new LinkedList<>();
        solutions.add(field);

        for (Link link : field.getLinks()) {
            ListIterator<Field> iterator = solutions.listIterator();
            while (iterator.hasNext()) {
                Field solution = iterator.next();
                iterator.remove();

                Field topFixedField = new Cloner().deepClone(solution);
                topFixedField.traceLink(link, true);

                Field bottomFixedField = new Cloner().deepClone(solution);
                bottomFixedField.traceLink(link, false);

                if (topFixedField.isAcceptableField()) iterator.add(topFixedField);
                if (bottomFixedField.isAcceptableField()) iterator.add(bottomFixedField);

                combinationsAmount -= 1;
                logger.debug("There are no more than {} combinations left", combinationsAmount);
            }
        }

        return solutions.stream()
                .sorted(Comparator.comparingLong(Field::getRating))
                .collect(Collectors.toList());
    }

}
