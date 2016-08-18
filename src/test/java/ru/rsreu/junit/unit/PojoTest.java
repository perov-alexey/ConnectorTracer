package ru.rsreu.junit.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.rsreu.tracer.pojo.Pin;

import java.lang.reflect.Constructor;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:unit-test-context.xml")
public class PojoTest {

    @Test
    public void testPinCreation() throws Exception{
        Exception exception = null;
        try {
            Constructor<Pin> pinConstructor = Pin.class.getDeclaredConstructor(Integer.class, Integer.class);
            pinConstructor.newInstance(30, 30);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals("Pin creation exception", null, exception);
    }

}
