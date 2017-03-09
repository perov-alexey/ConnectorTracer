package ru.rsreu.tracer.algorithms;

import com.rits.cloning.Cloner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rsreu.tracer.pojo.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Algorithm based on branch and bound method.
 */
public class BranchAndBoundAlgorithm implements Algorithm {

    private Logger logger = LogManager.getLogger(BranchAndBoundAlgorithm.class);
    private List<Field> bound = new LinkedList<>();

    @Override
    public List<Field> execute(Field field, boolean debugEnabled, boolean requireBestSolution) {
        List<Field> solutions = new ArrayList<>();
        logger.debug("Start execute branch and bound algorithm");
        logger.debug("Start time: {}", new Date());
        findBestSolution(field);
        bound.add(new Cloner().deepClone(field));
        while (!bound.isEmpty() && !bound.get(0).isAcceptableField()) {
            logger.debug("Solution was not find");
            evolveBranch(bound.get(0));
        }
        if (!bound.isEmpty()) {
            logger.debug("Solution was find. Solution rating: {}", bound.get(0).getRating());
            solutions.add(bound.get(0));
        }
        return solutions;
    }

    private void evolveBranch(Field peak) {
        try {
            Field topFixedField = new Cloner().deepClone(peak);
            Trace topCandidate = getCandidateToFix(topFixedField);

            topCandidate = topFixedField.retrace(topCandidate, true);
            logger.debug("Top candidate was retraced to top");
            topCandidate.setFixed(true);
            logger.debug("Top candidate trace was fixed");

            bound.add(topFixedField);
            logger.debug("Field with top fixed candidate trace was add to bound");
        } catch (NoSuchElementException e) {
            logger.debug("Field created to trace link to top haven't not fixed traces. This field will not be added to bound.");
        }

        try {
            Field bottomFixedField = new Cloner().deepClone(peak);
            Trace bottomCandidate = getCandidateToFix(bottomFixedField);

            bottomCandidate = bottomFixedField.retrace(bottomCandidate, false);
            logger.debug("Bottom candidate was retraced to bottom");
            bottomCandidate.setFixed(true);
            logger.debug("Bottom candidate trace was fixed");

            bound.add(bottomFixedField);
            logger.debug("Field with bottom fixed candidate trace was add to bound");
        } catch (NoSuchElementException e) {
            logger.debug("Field created to trace link to bottom haven't not fixed traces. This field will not be added to bound.");
        }

        bound.remove(bound.indexOf(peak));
        logger.debug("Parent peak was removed from bound");

        bound.stream()
                .sorted(Comparator.comparingLong(Field::getRating))
                .collect(Collectors.toList());
    }

    private void findBestSolution(Field field) {
        for (Link link : field.getLinks()) {
            field.traceLink(link, link.getLength(true) < link.getLength(false));
        }
    }

    private Trace getCandidateToFix(Field field) {
        logger.debug("Try to find candidate to fix. Amount of traces: {}", field.getTraces().size());
        List<Trace> candidates = field.getTraces().stream()
                .filter(trace -> !trace.isFixed())
                .sorted(Comparator.comparingLong(this::getOverloadedChannelsCount))
                .collect(Collectors.toList());
        if (candidates.isEmpty()) {
            logger.debug("Field haven't candidate traces, it will removed from bound");
            throw new NoSuchElementException("Field haven't not fixed traces");
        }
        logger.debug("Found {} not fixed traces", candidates.size());
        return candidates.get(0);
    }

    private long getOverloadedChannelsCount(Trace trace) {
        return trace.getPath().stream()
                .filter((channel) -> !channel.isOverloaded())
                .count();
    }
}
