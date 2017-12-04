package com.example.cmput301f17t27.nume;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.Assert.*;

/**
 * Created by Matt on 2017-10-20.
 */
// extends ActivityInstrumentationTestCase2
public class ProfileTest {

    /* public ProfileTest(){
        super(com.example.cmput301f17t27.nume.MainActivity.class);
    }
    */

    /**
     * Created by xcao2
     * testing the getUserName() method in Profile class
     */
    @Test
    public void testGetUserName(){
        String username = "Test";
        Profile testProfile = new Profile(username, "Test Name");
        assertEquals(testProfile.getUserName(), username);
    }

    /**
     * Created by xcao2
     * testing the getName() method in Profile class
     * differ from getUsername() method?
     */
    @Test
    public void testGetName(){
        String fullName = "Test";
        Profile testProfile = new Profile("Test", fullName);
        assertEquals(testProfile.getName(), fullName);
    }

    /**
     * Created by xcao2
     * testing the setName() method in Profile class
     * set the name "Ginger", then reset the name via method setName();
     */
    @Test
    public void testSetName(){
        Profile testProfile = new Profile("Ginger", "Ginger Rogers");
        String newUserName = "Fred";
        testProfile.setName(newUserName);
        assertEquals(testProfile.getName(), newUserName);

    }

    /**
     * Created by xcao2
     * testing the Habit arraylist Profile class
     * assertSame to test whether the arraylist contain the correct object
     */
    @Test
    public void testHabitsList(){
        ArrayList<Habit> habitList = new ArrayList<>();
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit testHabit = new Habit("testTitle","testReason",dateToStart, frequency);
        habitList.add(testHabit);
        assertSame(habitList.get(0),testHabit);

    }

    /**
     * Created by xcao2
     * testing the following arraylist Profile class
     * assertSame to test whether the arraylist contain the correct object
     */
    /*
    @Test
    public void testFollowing(){
        Profile testToFollow = new Profile("Test 1", "Test One");
        Profile testFollowing = new Profile("Test 2", "Test Two");
        testFollowing.followSomeone(testToFollow);
        assertSame(testFollowing.getFollowingList().get(0), testToFollow);

    }
    */

    /**
     * Created by xcao2
     * testing the follower arraylist Profile class
     * assertSame to test whether the arraylist contain the correct object
     */
    /*
    @Test
    public void testFollower(){
        Profile testFollowed = new Profile("Test 1", "Test 1");
        Profile testFollower = new Profile("Test 2", "Test 2");
        testFollowed.addFollower(testFollower);
        assertSame(testFollowed.getFollowerList().get(0), testFollower);

    }
    */

    /**
     * Created by mkluk
     * testing the habitHistory method
     * to see if it returns the HabitEvents in order
     */
    @Test
    public void testHabitHistory() {

        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Profile test = new Profile("Test", "Test");
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit1 = new Habit("Test 1", "Test 1", dateToStart, frequency);
        Habit habit2 = new Habit("Test 2", "Test 2", dateToStart, frequency);
        Habit habit3 = new Habit("Test 3", "Test 3", dateToStart, frequency);
        test.addHabit(habit1);
        test.addHabit(habit2);
        test.addHabit(habit3);
        HabitEvent event1 = new HabitEvent(null, "comment", location);
        HabitEvent event2 = new HabitEvent(null, "comment", location);
        HabitEvent event3 = new HabitEvent(null, "comment", location);
        HabitEvent event4 = new HabitEvent(null, "comment", location);
        HabitEvent event5 = new HabitEvent(null, "comment", location);
        HabitEvent event6 = new HabitEvent(null, "comment", location);
        habit1.addEvent(event1);
        habit1.addEvent(event2);
        habit2.addEvent(event3);
        habit2.addEvent(event4);
        habit3.addEvent(event5);
        habit3.addEvent(event6);
        ArrayList<HabitEvent> testEvents = new ArrayList<>();
        testEvents.add(event6);
        testEvents.add(event5);
        testEvents.add(event4);
        testEvents.add(event3);
        testEvents.add(event2);
        testEvents.add(event1);
        assertEquals(test.habitHistory(), testEvents);

    }

    /**
     * Created by mkluk
     * testing the habitHistory method
     * to see if it returns all events of a certain habit in order.
     */
    @Test
    public void testHabitHistoryFiltered() {

        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Profile test = new Profile("Test", "Test");
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit1 = new Habit("Test 1", "Test 1", dateToStart, frequency);
        Habit habit2 = new Habit("Test 2", "Test 2", dateToStart, frequency);
        Habit habit3 = new Habit("Test 3", "Test 3", dateToStart, frequency);
        test.addHabit(habit1);
        test.addHabit(habit2);
        test.addHabit(habit3);
        HabitEvent event1 = new HabitEvent(null, "comment", location);
        HabitEvent event2 = new HabitEvent(null, "comment", location);
        HabitEvent event3 = new HabitEvent(null, "comment", location);
        HabitEvent event4 = new HabitEvent(null, "comment", location);
        HabitEvent event5 = new HabitEvent(null, "comment", location);
        HabitEvent event6 = new HabitEvent(null, "comment", location);
        habit1.addEvent(event1);
        habit1.addEvent(event2);
        habit2.addEvent(event3);
        habit2.addEvent(event4);
        habit3.addEvent(event5);
        habit3.addEvent(event6);
        ArrayList<HabitEvent> testEvents = new ArrayList<>();
        testEvents.add(event4);
        testEvents.add(event3);
        assertEquals(test.habitHistory(habit2), testEvents);

    }

    /**
     * Created by mkluk
     * testint the habitHistory method
     * to see if it returns all events with a certain search term
     * in the comment in order.
     */
    @Test
    public void testHabitHistorySearch() {
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Profile test = new Profile("Test", "Test");
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit1 = new Habit("Test 1", "Test 1", dateToStart, frequency);
        Habit habit2 = new Habit("Test 2", "Test 2", dateToStart, frequency);
        Habit habit3 = new Habit("Test 3", "Test 3", dateToStart, frequency);
        test.addHabit(habit1);
        test.addHabit(habit2);
        test.addHabit(habit3);
        HabitEvent event1 = new HabitEvent(null, "search term", location);
        HabitEvent event2 = new HabitEvent(null, "not", location);
        HabitEvent event3 = new HabitEvent(null, "not", location);
        HabitEvent event4 = new HabitEvent(null, "not", location);
        HabitEvent event5 = new HabitEvent(null, "search term", location);
        HabitEvent event6 = new HabitEvent(null, "search term", location);
        habit1.addEvent(event1);
        habit1.addEvent(event2);
        habit2.addEvent(event3);
        habit2.addEvent(event4);
        habit3.addEvent(event5);
        habit3.addEvent(event6);
        ArrayList<HabitEvent> testEvents = new ArrayList<>();
        testEvents.add(event6);
        testEvents.add(event5);
        testEvents.add(event1);
        assertEquals(test.habitHistory("search term"), testEvents);
    }

    /**
     * created by mkluk
     * Tests the habitHistory method
     * to see if it returns all events of a certain habit
     * accessed from its index in order.
     */
    @Test
    public void testHabitHistoryIndex() {
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Profile test = new Profile("Test", "Test");
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit1 = new Habit("Test 1", "Test 1", dateToStart, frequency);
        Habit habit2 = new Habit("Test 2", "Test 2", dateToStart, frequency);
        Habit habit3 = new Habit("Test 3", "Test 3", dateToStart, frequency);
        test.addHabit(habit1);
        test.addHabit(habit2);
        test.addHabit(habit3);
        HabitEvent event1 = new HabitEvent(null, "comment", location);
        HabitEvent event2 = new HabitEvent(null, "comment", location);
        HabitEvent event3 = new HabitEvent(null, "comment", location);
        HabitEvent event4 = new HabitEvent(null, "comment", location);
        HabitEvent event5 = new HabitEvent(null, "comment", location);
        HabitEvent event6 = new HabitEvent(null, "comment", location);
        habit1.addEvent(event1);
        habit1.addEvent(event2);
        habit2.addEvent(event3);
        habit2.addEvent(event4);
        habit3.addEvent(event5);
        habit3.addEvent(event6);
        ArrayList<HabitEvent> testEvents = new ArrayList<>();
        testEvents.add(event4);
        testEvents.add(event3);
        assertEquals(test.habitHistory(1), testEvents);
    }

    /**
     * Created by mkluk
     * Testing the habitHistory method
     * to see if it returns, in order, the events
     * from a certain habit and with a certain search term.
     */
    @Test
    public void testHabitHistoryIndexSearch() {
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Profile test = new Profile("Test", "Test");
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit1 = new Habit("Test 1", "Test 1", dateToStart, frequency);
        Habit habit2 = new Habit("Test 2", "Test 2", dateToStart, frequency);
        Habit habit3 = new Habit("Test 3", "Test 3", dateToStart, frequency);
        test.addHabit(habit1);
        test.addHabit(habit2);
        test.addHabit(habit3);
        HabitEvent event1 = new HabitEvent(null, "search term", location);
        HabitEvent event2 = new HabitEvent(null, "not", location);
        HabitEvent event3 = new HabitEvent(null,"not", location);
        HabitEvent event4 = new HabitEvent(null, "search term", location);
        HabitEvent event5 = new HabitEvent(null, "search term", location);
        HabitEvent event6 = new HabitEvent(null, "not", location);
        habit1.addEvent(event1);
        habit1.addEvent(event2);
        habit2.addEvent(event3);
        habit2.addEvent(event4);
        habit3.addEvent(event5);
        habit3.addEvent(event6);
        ArrayList<HabitEvent> testEvents = new ArrayList<>();
        testEvents.add(event4);
        assertEquals(test.habitHistory(1, "search term"), testEvents);
    }

    /**
     * Created by mkluk
     * Testing the habitHistory method
     * that returns, in order, the events
     * of one habit by direct reference.
     */
    @Test
    public void testHabitHistoryHabit() {
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Profile test = new Profile("Test", "Test");
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit1 = new Habit("Test 1", "Test 1", dateToStart, frequency);
        Habit habit2 = new Habit("Test 2", "Test 2", dateToStart, frequency);
        Habit habit3 = new Habit("Test 3", "Test 3", dateToStart, frequency);
        test.addHabit(habit1);
        test.addHabit(habit2);
        test.addHabit(habit3);
        HabitEvent event1 = new HabitEvent(null, "comment", location);
        HabitEvent event2 = new HabitEvent(null, "comment", location);
        HabitEvent event3 = new HabitEvent(null, "comment", location);
        HabitEvent event4 = new HabitEvent(null, "comment", location);
        HabitEvent event5 = new HabitEvent(null, "comment", location);
        HabitEvent event6 = new HabitEvent(null, "comment", location);
        habit1.addEvent(event1);
        habit1.addEvent(event2);
        habit2.addEvent(event3);
        habit2.addEvent(event4);
        habit3.addEvent(event5);
        habit3.addEvent(event6);
        ArrayList<HabitEvent> testEvents = new ArrayList<>();
        testEvents.add(event4);
        testEvents.add(event3);
        assertEquals(test.habitHistory(habit2), testEvents);
    }

    /**
     * Created by mkluk
     * Testing the habitHistory method
     * that returns, in order, the events
     * from a directly referenced habit
     * and with a certain search term in the comment.
     */
    @Test
    public void testHabitHistoryHabitSearch() {
        double location[] = new double[2];
        location[0] = 0.0;
        location[1] = 0.0;
        Profile test = new Profile("Test", "Test");
        Date dateToStart = new Date();
        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Monday");
        Habit habit1 = new Habit("Test 1", "Test 1", dateToStart, frequency);
        Habit habit2 = new Habit("Test 2", "Test 2", dateToStart, frequency);
        Habit habit3 = new Habit("Test 3", "Test 3", dateToStart, frequency);
        test.addHabit(habit1);
        test.addHabit(habit2);
        test.addHabit(habit3);
        HabitEvent event1 = new HabitEvent(null,"search term", location);
        HabitEvent event2 = new HabitEvent(null, "not", location);
        HabitEvent event3 = new HabitEvent(null, "not", location);
        HabitEvent event4 = new HabitEvent(null, "search term", location);
        HabitEvent event5 = new HabitEvent(null, "search term", location);
        HabitEvent event6 = new HabitEvent(null, "not", location);
        habit1.addEvent(event1);
        habit1.addEvent(event2);
        habit2.addEvent(event3);
        habit2.addEvent(event4);
        habit3.addEvent(event5);
        habit3.addEvent(event6);
        ArrayList<HabitEvent> testEvents = new ArrayList<>();
        testEvents.add(event4);
        assertEquals(test.habitHistory(habit2, "search term"), testEvents);
    }




}