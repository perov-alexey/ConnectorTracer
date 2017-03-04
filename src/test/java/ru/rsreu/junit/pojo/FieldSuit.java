package ru.rsreu.junit.pojo;

import org.junit.Test;
import ru.rsreu.junit.utils.FixtureProvider;
import ru.rsreu.tracer.pojo.Field;

import static org.junit.Assert.assertEquals;

/**
 * Test suit for Field class
 */
public class FieldSuit {

    /**
     * Test rating calculation field function
     */
    @Test
    public void testRatingCalculation() {
        Field fixture = FixtureProvider.getTracedField();
        assertEquals("Field rating was calculated wrong", 1610, fixture.getRating());
    }

}
