package ru.rsreu.junit.performance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.BranchAndBoundAlgorithm;
import ru.rsreu.tracer.algorithms.BruteForceAlgorithm;
import ru.rsreu.tracer.algorithms.DynamicProgrammingAlgorithm;
import ru.rsreu.tracer.pojo.Field;
import ru.rsreu.tracer.utils.FieldGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

/**
 * This class contains tests for checking algorithms performance after changes.
 * Each test includes bunch of algorithm runs for different configuration. There is two kind of tests: strong and
 * weak. So for strong tests configuration changed to generate fields which has solution in ~ 50% chance.
 * For weak tests configuration same as for strong tests, but each channel capacity multiply in 10 times.
 * <table>
 *     <tr>
 *         <th colspan="3">Strong Tests</th>
 *     </tr>
 *     <tr>
 *         <th>Amount of connectors</th>
 *         <th>Amount of links</th>
 *         <th>Channel capacity</th>
 *     </tr>
 *     <tr>
 *         <td>5</td><td>10</td><td>3 - 4</td>
 *     </tr>
 *     <tr>
 *         <td>6</td><td>12</td><td>4</td>
 *     </tr>
 *     <tr>
 *         <td>7</td><td>14</td><td>4 - 5</td>
 *     </tr>
 *     <tr>
 *         <td>8</td><td>16</td><td>5</td>
 *     </tr>
 *     <tr>
 *         <td>9</td><td>18</td><td>5 - 6</td>
 *     </tr>
 *     <tr>
 *         <td>10</td><td>20</td><td>6</td>
 *     </tr>
 *     <tr>
 *         <th colspan="3">Weak Tests</th>
 *     </tr>
 *     <tr>
 *         <th>Amount of connectors</th>
 *         <th>Amount of links</th>
 *         <th>Channel capacity</th>
 *     </tr>
 *     <tr>
 *         <td>5</td><td>10</td><td>30 - 40</td>
 *     </tr>
 *     <tr>
 *         <td>6</td><td>12</td><td>40</td>
 *     </tr>
 *     <tr>
 *         <td>7</td><td>14</td><td>40 - 50</td>
 *     </tr>
 *     <tr>
 *         <td>8</td><td>16</td><td>50</td>
 *     </tr>
 *     <tr>
 *         <td>9</td><td>18</td><td>50 - 60</td>
 *     </tr>
 *     <tr>
 *         <td>10</td><td>20</td><td>60</td>
 *     </tr>
 * </table>
 */
public class PerformanceTestSuit {

    private Logger logger = LogManager.getLogger(PerformanceTestSuit.class);

    private Map<Integer, List<Integer>> strongConfigs = new HashMap<Integer, List<Integer>>() {{
        put(5, Arrays.asList(3, 4));
        put(6, Arrays.asList(4, 4));
        put(7, Arrays.asList(4, 5));
        put(8, Arrays.asList(5, 5));
        put(9, Arrays.asList(5, 6));
        put(10, Arrays.asList(6, 6));
    }};

    private Map<Integer, List<Integer>> weakConfigs = new HashMap<Integer, List<Integer>>() {{
        put(5, Arrays.asList(30, 40));
        put(6, Arrays.asList(40, 40));
        put(7, Arrays.asList(40, 50));
        put(8, Arrays.asList(50, 50));
        put(9, Arrays.asList(50, 60));
        put(10, Arrays.asList(60, 60));
    }};

    /**
     * Performance tests for brute force algorithm
     */
    @Test
    public void estimateBruteForceAlgorithmPerformance() throws InterruptedException {
        logger.info("Start unsolved performance test for brute force algorithm");
        for (Integer key : strongConfigs.keySet()) {
            executeAlgorithmTest(new BruteForceAlgorithm(), key, 3, 2, key * 2,
                    strongConfigs.get(key).get(0), strongConfigs.get(key).get(1), false);
        }

        logger.info("Start strong performance test for brute force algorithm");
        for (Integer key : strongConfigs.keySet()) {
            executeAlgorithmTest(new BruteForceAlgorithm(), key, 3, 2, key * 2,
                    strongConfigs.get(key).get(0), strongConfigs.get(key).get(1), true);
        }

        logger.info("Start weak performance test for brute force algorithm");
        for (Integer key : weakConfigs.keySet()) {
            executeAlgorithmTest(new BruteForceAlgorithm(), key, 3, 2, key * 2,
                    strongConfigs.get(key).get(0), strongConfigs.get(key).get(1), true);
        }
    }

    /**
     * Performance tests for branch and bound algorithm
     */
    @Test
    public void estimateBranchAndBoundAlgorithmPerformance() throws InterruptedException {
        logger.info("Start unsolved performance test for branch and bound algorithm");
        for (Integer key : strongConfigs.keySet()) {
            executeAlgorithmTest(new BranchAndBoundAlgorithm(), key, 3, 2, key * 2,
                    strongConfigs.get(key).get(0), strongConfigs.get(key).get(1), false);
        }

        logger.info("Start strong performance test for branch and bound algorithm");
        for (Integer key : strongConfigs.keySet()) {
            executeAlgorithmTest(new BranchAndBoundAlgorithm(), key, 3, 2, key * 2,
                    strongConfigs.get(key).get(0), strongConfigs.get(key).get(1), true);
        }

        logger.info("Start weak performance test for branch and bound algorithm");
        for (Integer key : weakConfigs.keySet()) {
            executeAlgorithmTest(new BranchAndBoundAlgorithm(), key, 3, 2, key * 2,
                    strongConfigs.get(key).get(0), strongConfigs.get(key).get(1), true);
        }
    }

    /**
     * Performance tests for dynamic programming algorithm
     */
    @Test
    public void estimateDynamicProgrammingPerformance() throws InterruptedException {
        logger.info("Start unsolved performance test for dynamic programming algorithm");
        for (Integer key : strongConfigs.keySet()) {
            executeAlgorithmTest(new DynamicProgrammingAlgorithm(), key, 3, 2, key * 2,
                    strongConfigs.get(key).get(0), strongConfigs.get(key).get(1), false);
        }

        logger.info("Start strong performance test for dynamic programming algorithm");
        for (Integer key : strongConfigs.keySet()) {
            executeAlgorithmTest(new DynamicProgrammingAlgorithm(), key, 3, 2, key * 2,
                    strongConfigs.get(key).get(0), strongConfigs.get(key).get(1), true);
        }

        logger.info("Start weak performance test for dynamic programming algorithm");
        for (Integer key : weakConfigs.keySet()) {
            executeAlgorithmTest(new DynamicProgrammingAlgorithm(), key, 3, 2, key * 2,
                    strongConfigs.get(key).get(0), strongConfigs.get(key).get(1), true);
        }
    }

    private void executeAlgorithmTest(Algorithm algorithm, int connectorsAmount, int pinsAmount, int columnPinsAmount,
                                      int linksAmount, int minChannelCapacity, int maxChannelCapacity, boolean whileAcceptableFound) throws InterruptedException {
        FieldGenerator generator = new FieldGenerator();
        boolean solutionIsFinded = false;
        logger.info("Connectors amount: {}", connectorsAmount);
        logger.info("Links amount: {}", linksAmount);
        LocalDateTime startDate = LocalDateTime.now();
        while (!solutionIsFinded) {
            Field field = generator.generateField(connectorsAmount, pinsAmount, columnPinsAmount, linksAmount,
                    minChannelCapacity, maxChannelCapacity);
            startDate = LocalDateTime.now();
            List<Field> solutions = algorithm.execute(field, false, true);
            if (solutions.size() > 0 && whileAcceptableFound) {
                solutionIsFinded = true;
            } else if (solutions.size() == 0 && !whileAcceptableFound) {
                solutionIsFinded = true;
            } else {
                logger.info("Tracing failed!");
            }
        }
        LocalDateTime finishDate = LocalDateTime.now();
        logger.info("Start time: {}", startDate);
        logger.info("Finish time: {}", finishDate);
        logger.info("Time spent: {}", ChronoUnit.SECONDS.between(startDate, finishDate));
    }

}
