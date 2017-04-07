package ru.rsreu.factory;

import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.BranchAndBoundAlgorithm;
import ru.rsreu.tracer.algorithms.BruteForceAlgorithm;
import ru.rsreu.tracer.algorithms.DynamicProgrammingAlgorithm;

/**
 * Create algorithm instance based on input parameters
 */
public class AlgorithmFactory {

    /**
     * Create factory based on it type
     * @param type Algorithm type
     * @return Appropriate factory
     */
    public static Algorithm createAlgorithm(AlgorithmTypes type) {
        Algorithm algorithm;
        switch (type) {
            case BRUTE_FORCE:
                algorithm = new BruteForceAlgorithm();
                break;
            case BRANCH_AND_BOUND:
                algorithm = new BranchAndBoundAlgorithm();
                break;
            case DYNAMIC_PROGRAMMING:
                algorithm = new DynamicProgrammingAlgorithm();
                break;
            default:
                throw new IllegalArgumentException("Algorithm type not allowed");
        }
        return algorithm;
    }

}
