package ru.rsreu.junit.algorithm;

/**
 * This class contain test for all algorithms. This test suit is main algorithm comparative benchmark.
 */
public interface CommonAlgorithmTestSuit {

    /**
     * This test verify algorithm on channel override behavior and rating of the best solution.
     */
    void testWithOverloadedChannel();

    /**
     * This test verify algorithm behavior with defective field.
     */
    void testUnresolvableField();

    /**
     * This test verify amount of traces after field tracing. By design, amount of traces must be equals amount of links.
     */
    void testAmountOfTraces();

    /**
     * This test verify algorithm behavior on repeatable algorithm execution. Algorithm must be stateless.
     */
    void testAlgorithmStateClearance();
}
