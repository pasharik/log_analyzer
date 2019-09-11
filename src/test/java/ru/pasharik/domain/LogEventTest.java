package ru.pasharik.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LogEventTest {
    private LogEvent event;

    @Before
    public void init() {
        event = new LogEvent();
    }

    @Test
    public void testIsFinished() {
        Assert.assertFalse(event.isFinished());
        event.setState(LogEventState.FINISHED);
        Assert.assertTrue(event.isFinished());
        event.setState(LogEventState.STARTED);
        Assert.assertFalse(event.isFinished());
    }

    @Test
    public void testIsAlert() {
        event.setDuration(3L);
        Assert.assertFalse(event.isAlert());
        event.setDuration(4L);
        Assert.assertFalse(event.isAlert());
        event.setDuration(5L);
        Assert.assertTrue(event.isAlert());
    }
}
