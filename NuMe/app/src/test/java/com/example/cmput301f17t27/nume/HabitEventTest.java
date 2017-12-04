package com.example.cmput301f17t27.nume;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Matt on 2017-10-20.
 */

public class HabitEventTest {

    /* public HabitEventTest(){
        super(com.example.cmput301f17t27.nume.MainActivity.class);
    }
    */

    @Test
    public void testTooLongComment() throws Exception {
        String tooLong = "WAYYYYYYYYYY TOOOOOOO LOOOOONNNGG COMMMMEENNNT";
        HabitEvent test = new HabitEvent(tooLong, 0);
        assertTrue(test.getComment() == null || test.getComment().isEmpty());
    }

    @Test
    public void testSetComment() {
        HabitEvent habitEvent = new HabitEvent();
        String comment = "I'm a comment";
        habitEvent.setComment(comment);
        assertEquals(habitEvent.getComment(), comment);
    }
}