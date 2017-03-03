package ru.rsreu.junit.unit;

import org.junit.Test;
import ru.rsreu.junit.utils.FixtureProvider;
import ru.rsreu.tracer.algorithms.Algorithm;
import ru.rsreu.tracer.algorithms.FirstAlgorithm;
import ru.rsreu.tracer.pojo.Field;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test for branch and bound algorithm
 */
public class BranchAndBoundAlgorithmTest {

    @Test
    public void testTopOverride() {
        Field fixture = FixtureProvider.getTopChannelOverloadedField();
        Algorithm algorithm = new FirstAlgorithm();
        List<Field> steps = algorithm.execute(fixture, false);
        Field result = steps.get(steps.size() - 1);

        assertEquals("This field must contain four traces", result.getTraces().size(), 4);

        assertEquals("First link was traced wrong", result.getTraces().get(0).getPath(),
                Arrays.asList(fixture.getConnectors().get(1).getTopChannel()));
        assertEquals("Second link was traced wrong", result.getTraces().get(1).getPath(),
                Arrays.asList(fixture.getConnectors().get(2).getTopChannel()));
        assertEquals("Third link was traced wrong", result.getTraces().get(2).getPath(),
                Arrays.asList(fixture.getConnectors().get(2).getTopChannel()));
        assertEquals("Fourth link was traced wrong", result.getTraces().get(3).getPath(),
                Arrays.asList(fixture.getConnectors().get(2).getBottomChannel()));
    }

}
