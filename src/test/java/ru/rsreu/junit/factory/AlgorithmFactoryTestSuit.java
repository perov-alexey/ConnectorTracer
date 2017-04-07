package ru.rsreu.junit.factory;

import org.junit.Test;
import ru.rsreu.factory.AlgorithmFactory;
import ru.rsreu.factory.AlgorithmTypes;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.BranchAndBoundAlgorithm;
import ru.rsreu.tracer.algorithms.BruteForceAlgorithm;
import ru.rsreu.tracer.algorithms.DynamicProgrammingAlgorithm;

import static org.junit.Assert.assertTrue;

/**
 * Tests for AlgorithmFactory
 */
public class AlgorithmFactoryTestSuit {

    /**
     * Test ability of brute force algorithm creation
     */
    @Test
    public void testBruteForceAlgorithmCreation() {
        Algorithm algorithm = AlgorithmFactory.createAlgorithm(AlgorithmTypes.BRUTE_FORCE);
        assertTrue("Factory doesn't create brute force algorithm", algorithm instanceof BruteForceAlgorithm);
    }

    /**
     * Test ability of branch and bound algorithm creation
     */
    @Test
    public void testBranchAndBoundAlgorithmCreation() {
        Algorithm algorithm = AlgorithmFactory.createAlgorithm(AlgorithmTypes.BRANCH_AND_BOUND);
        assertTrue("Factory doesn't create branch and bound algorithm", algorithm instanceof BranchAndBoundAlgorithm);
    }

    /**
     * Test ability of dynamic programming algorithm creation
     */
    @Test
    public void testDynamicProgrammingAlgorithmCreation() {
        Algorithm algorithm = AlgorithmFactory.createAlgorithm(AlgorithmTypes.DYNAMIC_PROGRAMMING);
        assertTrue("Factory doesn't create dynamic programming algorithm", algorithm instanceof DynamicProgrammingAlgorithm);
    }

}
