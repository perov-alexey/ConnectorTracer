package ru.rsreu.tracer.algorithms;

import com.rits.cloning.Cloner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rsreu.tracer.pojo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This algorithm calculate all combination. This method good enough for "weak" requirements (thin traces, wide channels),
 * but too bad for strong requirements.
 */
public class BruteForceAlgorithm implements Algorithm {

    private Logger logger = LogManager.getLogger(BruteForceAlgorithm.class);

    @Override
    public List<Field> execute(Field field, boolean debugEnabled, boolean requireBestSolution) {
        logger.debug("Start brute force algorithm");
        Field originalField = new Cloner().deepClone(field);

        if (debugEnabled) {
            logger.error("Brute force method doesn't enable debug mode now");
            return Arrays.asList(originalField);
        }

        List<Field> solutions = new ArrayList<>();
        long amountOfCombinations = (long) Math.pow(2, field.getLinks().size());
        logger.debug("Needs {} solves to find best solution", amountOfCombinations);
        for (long i = 0; i < amountOfCombinations; i++) {
            field = new Cloner().deepClone(originalField);
            char[] traceMask = prepareTraceMask(i, field.getLinks().size());

            logger.debug("Applied trace mask: {}", new String(traceMask));
            List<Trace> traces = new ArrayList<>();
            for (int j = 0; j < field.getLinks().size(); j++) {
                Link link = field.getLinks().get(j);
                traces.add(field.traceLink(link, traceMask[j] == '0'));
            }
            field.setTraces(traces);

            if (!isChannelsOverloaded(field.getConnectors())) {
                logger.debug("Mask {} is feasible solution", new String(traceMask));
                if (requireBestSolution) {
                    logger.debug("Require best solution, continue with next trace mask");
                    solutions.add(new Cloner().deepClone(field));
                } else {
                    logger.debug("Best solution doesn't required, field traced by {} mask is solution", new String(traceMask));
                    return Arrays.asList(field);
                }
            } else {
                logger.debug("Mask {} is unacceptable solution", new String(traceMask));
            }
        }

        if (requireBestSolution && !solutions.isEmpty()) {
            solutions.sort((Field firstField, Field secondField) -> secondField.getRating() - firstField.getRating());
            int bestSolutionRating = solutions.get(0).getRating();
            solutions = solutions.stream()
                    .filter((solution) -> solution.getRating() == bestSolutionRating).collect(Collectors.toList());
            logger.debug("Find {} best solutions, it's rating: {}", solutions.size(), bestSolutionRating);
        } else {
            logger.debug("Field doesn't have solutions");
            solutions = Arrays.asList(originalField);
        }

        return solutions;
    }

    private char[] prepareTraceMask(long number, int maskSize) {
        char[] traceMask = new char[maskSize];
        Arrays.fill(traceMask, '0');

        char[] parsedNumber = Long.toBinaryString(number).toCharArray();
        System.arraycopy(parsedNumber, 0, traceMask, maskSize - parsedNumber.length, parsedNumber.length);
        return traceMask;
    }

    private boolean isChannelsOverloaded(List<Connector> connectors) {
        //TODO To Field.isAcceptable()
        for (Connector connector : connectors) {
            if (connector.getTopChannel().isOverloaded() || connector.getBottomChannel().isOverloaded())
                return true;
        }
        return false;
    }
}
