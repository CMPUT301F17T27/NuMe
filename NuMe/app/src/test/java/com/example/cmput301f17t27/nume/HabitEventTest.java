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
        double[] location = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        HabitEvent test = new HabitEvent(null, tooLong, location);
        assertEquals(test.getComment(), tooLong);
    }

    @Test
    public void testSetComment() {
        double[] location = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        HabitEvent habitEvent = new HabitEvent(null, "Comment", location);
        String comment = "I'm a comment";
        habitEvent.setComment(comment);
        assertEquals(habitEvent.getComment(), comment);
    }
}