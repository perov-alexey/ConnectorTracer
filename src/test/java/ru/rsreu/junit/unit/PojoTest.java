package ru.rsreu.junit.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.rsreu.tracer.pojo.Channel;
import ru.rsreu.tracer.pojo.Connector;
import ru.rsreu.tracer.pojo.Pin;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:unit-test-context.xml")
public class PojoTest {

    @Autowired
    private Pin firstPin;

    @Autowired
    private Pin secondPin;

    @Autowired
    private Connector firstConnector;

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

    @Test
    public void testConnectorCreation() throws Exception{
        Exception exception = null;
        try {
            Constructor<Connector> connectorConstructor = Connector.class.getDeclaredConstructor(Integer.class, Integer.class, Integer.class, Integer.class, List.class);
            connectorConstructor.newInstance(30, 30, 30, 30, Arrays.asList(firstPin, secondPin));
        } catch (Exception e) {
            exception = e;
        }
        assertEquals("Connector creation exception", null, exception);
    }

    @Test
    public void testChannelCreation() throws Exception{
        Exception exception = null;
        try {
            Constructor<Channel> channelConstructor = Channel.class.getDeclaredConstructor(Connector.class, Integer.class, Boolean.class);
            channelConstructor.newInstance(firstConnector, 3, true);
            channelConstructor.newInstance(firstConnector, 3, false);
        } catch (Exception e) {
            exception = e;
        }
        assertEquals("Channel creation exception", null, exception);
    }

}
