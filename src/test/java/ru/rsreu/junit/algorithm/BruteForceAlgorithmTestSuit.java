package ru.rsreu.junit.algorithm;

import org.junit.Test;
import ru.rsreu.junit.utils.FixtureProvider;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.BruteForceAlgorithm;
import ru.rsreu.tracer.pojo.Field;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test suit for brute force algorithm
 */
public class BruteForceAlgorithmTestSuit implements CommonAlgorithmTestSuit {

    @Override
    @Test
    public void testWithOverloadedChannel() {
        Field fixture = FixtureProvider.getTopChannelOverloadedField();
        Algorithm algorithm = new BruteForceAlgorithm();
        List<Field> solutions = algorithm.execute(fixture, false, true);

        assertEquals("Wrong number of best solutions", 1, solutions.size());
        assertEquals("Found not best solution", 1430, solutions.get(0).getRating());
    }

    @Override
    @Test
    public void testUnresolvableField() {
        Field field = FixtureProvider.getDefectiveField();
        Algorithm algorithm = new BruteForceAlgorithm();
        List<Field> solutions = algorithm.execute(field, false, true);

        assertTrue("Algorithm must return empty solutions list if field is defective", solutions.isEmpty());
    }

    @Override
    @Test
    public void testAmountOfTraces() {
        Field field = FixtureProvider.getTopChannelOverloadedField();
        Algorithm algorithm = new BruteForceAlgorithm();
        Field result = algorithm.execute(field, false, true).get(0);
        assertEquals("Wrong amount of traces after tracing", result.getLinks().size(), result.getTraces().size());
    }
}
