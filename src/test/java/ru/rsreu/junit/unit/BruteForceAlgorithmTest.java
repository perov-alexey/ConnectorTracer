package ru.rsreu.junit.unit;

import org.junit.Test;
import ru.rsreu.junit.utils.FixtureProvider;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.BruteForceAlgorithm;
import ru.rsreu.tracer.pojo.Field;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test suit for brute force algorithm
 */
public class BruteForceAlgorithmTest {

    /**
     * Test top path override behavior. In this test algorithm must trace last link through the bottom channel.
     */
    @Test
    public void testTopChannelOverloadedField() {
        Field fixture = FixtureProvider.getTopChannelOverloadedField();
        Algorithm algorithm = new BruteForceAlgorithm();
        List<Field> solutions = algorithm.execute(fixture, false, true);

        assertEquals("Wrong number of best solutions", 1, solutions.size());
        assertEquals("Was find not best solution", 2090, solutions.get(0).getRating());
    }

}
