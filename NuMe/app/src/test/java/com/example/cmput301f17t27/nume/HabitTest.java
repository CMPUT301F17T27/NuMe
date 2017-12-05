package com.example.cmput301f17t27.nume;

import android.test.ActivityInstrumentationTestCase2;

import com.example.cmput301f17t27.nume.habit.Habit;
import com.example.cmput301f17t27.nume.habitEvent.HabitEvent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.Assert.*;

/**
 * Created by Matt on 2017-10-20.
 */

// extends ActivityInstrumentationTestCase2
public class HabitTest  {

    /*public HabitTest(){
        super(com.example.cmput301f17t27.nume.MainActivity.class);
    } */

    /**
     * Test to add an event to a Habit
     * Also tests hasEvent(HabitEvent event)
     * @throws Exception
     */
    @Test
    public void TestAddEvent() throws Exception{
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit = new Habit("Test", "Test", dateToStart, frequency);
        HabitEvent event = new HabitEvent(null, "comment", location);
        habit.addEvent(event);
        assertTrue(habit.hasEvent(event));
    }

    @Test
    public void testDeleteEvent() throws Exception{
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit = new Habit("Test", "Test", dateToStart, frequency);
        HabitEvent event = new HabitEvent(null, "comment", location);
        habit.addEvent(event);
        habit.deleteEvent(event);
        assertFalse(habit.hasEvent(event));
    }

    @Test
    public void testDeleteByIndex() throws Exception {
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit = new Habit("Test", "Test", dateToStart, frequency);
        HabitEvent event1 = new HabitEvent(null, "comment", location);
        HabitEvent event2 = new HabitEvent(null, "comment", location);
        habit.addEvent(event1);
        habit.addEvent(event2);
        habit.deleteEvent(0);
        assertFalse(habit.hasEvent(event1));
    }

    @Test
    public void testToString() throws Exception {
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit = new Habit("Test", "Test", dateToStart, frequency);
        String habitString = habit.toString();
        String habitExpected = "Habit: Test\nReason:Test";
        assertEquals(habitString, habitExpected);
    }

    @Test
    public void testViewStats() throws Exception{
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit = new Habit("Test", "Test", dateToStart, frequency);
        HabitEvent event1 = new HabitEvent(null, "comment", location);
        HabitEvent event2 = new HabitEvent(null, "comment", location);
        HabitEvent event3 = new HabitEvent(null, "comment", location);
        habit.addEvent(event1);
        habit.addEvent(event2);
        habit.addEvent(event3);
        int stats;
        stats = habit.viewStats(0);
        assertEquals(stats, 3);
    }

}