package com.example.cmput301f17t27.nume.habit;

import com.example.cmput301f17t27.nume.habitEvent.HabitEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * The instantiation of this class represents a single
 * habit of a user
 * @author CMPUT301F17T27
 * @version 1.0
 * @see
 */
public class Habit implements Serializable {
    private String title; //The title of the habit
    private String reason; //Reason for the habit
    private Date dateToStart; //Date to start the habit
    private ArrayList<String> frequency; //Days of the week for the habit to occur
    private ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>(); //A list of all the habit events
                                                                    //for this habit


    public Habit( String title, String reason, Date dateToStart, ArrayList frequency) {
        this.title = title;
        this.reason = reason;
        this.dateToStart = dateToStart;
        this.frequency = frequency;
    }



    /**
     * Gets the title of the habit
     * @return Title of the habit
     */
    public String getTitle() {
        return title;
    }



    /**
     * Set the title of the habit
     * @param title Title to set to
     */
    public void setTitle(String title) {
        this.title = title;
    }



    /**
     * Get the reason for the habit
     * @return The reason for the habit
     */
    public String getReason() {
        return reason;
    }



    /**
     * Set the reason for the habit
     * @param reason Reason to set to
     */
    public void setReason(String reason) {
        this.reason = reason;
    }



    /**
     * Get the date when the habit should start
     * @return The date to start
     */
    public Date getDateToStart() {
        return dateToStart;
    }



    /**
     * Set the date to start a habit
     * @param dateToStart The date to set to
     */
    public void setDateToStart(Date dateToStart) {
        this.dateToStart = dateToStart;
    }



    /**
     * Get the days of the week this habit should occur
     * @return The days of the week that it should occur
     */
    public ArrayList<String> getFrequency() {
        return this.frequency;
    }



    /**
     * Sets the days of the week the habit should occur
     * @param frequency The days to set to
     */
    public void setFrequency(ArrayList frequency) {
        this.frequency = frequency;
    }



    /**
     * Get all the events for the habit
     * @return The events
     */
    public ArrayList<HabitEvent> getEvents() {
        return habitEvents;
    }



    /**
     * Checks to see if the habit a particular event
     * @param event The event to check for
     * @return True if it has the event, false otherwise
     */
    public Boolean hasEvent(HabitEvent event) {
        if (habitEvents.contains(event)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }



    /**
     * Append an event to the existing list of events
     * @param event Event to append
     */
    public void addEvent(HabitEvent event) {
        habitEvents.add(event);
    }



    /**
     * Gets an event at a specific index in the event list
     * @param index Index to get the event at
     * @return The event at that index
     */
    public HabitEvent getEvent(int index) {
        return habitEvents.get(index);
    }



    /**
     * Set an event at a specific index
     * @param index Index to set
     * @param habitEvent Event to set to
     */
    public void setEvent(int index, HabitEvent habitEvent) {
        habitEvents.set(index, habitEvent);
    }



    /**
     * Sets the entire list of events at once
     * @param habitEvents The list of events to use
     */
    public void setEvents(ArrayList<HabitEvent> habitEvents) {
        this.habitEvents = habitEvents;
    }



    /**
     * Deletes an event at a specific index
     * @param index The index to delete at
     */
    public void deleteEvent(int index) {
        habitEvents.remove(index);
    }



    /**
     * Deletes an event in the event list
     * @param event Event to delete
     */
    public void deleteEvent(HabitEvent event) {
        habitEvents.remove(event);
    }



    /**
     * Returns the amount of habit events or -1. (Used for stats)
     * @param statsType 0 if you want the amount of events, anything else for -1
     * @return Returns the amount of habit events or -1
     */
    public int viewStats( int statsType ) {
        if (statsType == 0) {
            int numOfEvents = habitEvents.size();
            return numOfEvents;
        } else {
            return -1;
        }
    }



    @Override
    /**
     * Converts the habit to an easy to read string
     */
    public String toString(){
        return "Habit: "+this.title+"\nReason:"+this.reason;
    }


    /**
     * A comparator used for sorting habits. This one is for
     * sorting by title in ascending order
     */
    public static Comparator<Habit> habitComparator = new Comparator<Habit>() {
        public int compare(Habit h1, Habit h2) {
            String title1 = h1.getTitle();
            String title2 = h2.getTitle();

            return title1.compareTo(title2);
        }
    };
}