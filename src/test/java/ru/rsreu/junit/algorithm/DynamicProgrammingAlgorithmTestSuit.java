package ru.rsreu.junit.algorithm;

import org.junit.Test;
import ru.rsreu.junit.utils.FixtureProvider;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.DynamicProgrammingAlgorithm;
import ru.rsreu.tracer.pojo.Field;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test suit for dynamic programming algorithm
 */
public class DynamicProgrammingAlgorithmTestSuit implements CommonAlgorithmTestSuit{

    @Override
    @Test
    public void testWithOverloadedChannel() {
        Field field = FixtureProvider.getTopChannelOverloadedField();
        Algorithm algorithm = new DynamicProgrammingAlgorithm();
        List<Field> solutions = algorithm.execute(field, false, true);

        for (Field solution : solutions) {
            assertTrue("Solution must be acceptable field", solution.isAcceptableField());
        }
        assertEquals("Found not best solution", 1430, solutions.get(0).getRating());
    }

    @Override
    @Test
    public void testUnresolvableField() {
        Field field = FixtureProvider.getDefectiveField();
        Algorithm algorithm = new DynamicProgrammingAlgorithm();
        List<Field> solutions = algorithm.execute(field, false, true);

        assertTrue("Algorithm must return empty solutions list if field is defective", solutions.isEmpty());
    }

}
