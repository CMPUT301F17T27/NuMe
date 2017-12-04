package com.example.cmput301f17t27.nume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Creates the Habit class
 * @author
 * @version 0.1
 * @see HabitListActivity
 * @see AddHabitActivity
 * @see HabitEvent
 * @since 0.1
 */
public class Habit implements Serializable {
    private String title;
    private String reason;
    private Date dateToStart;
    private ArrayList<String> frequency;
    private ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();


    public Habit( String title, String reason, Date dateToStart, ArrayList frequency) {
        this.title = title;
        this.reason = reason;
        this.dateToStart = dateToStart;
        this.frequency = frequency;
    }



    /**
     * Returns Habits title
     * @return title
     */
    public String getTitle() {
        return title;
    }



    /**
     * sets habit's title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }



    /**
     * Returns Habit's reason
     * @return reason
     */
    public String getReason() {
        return reason;
    }



    /**
     * sets Habit's reason
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }



    /**
     * gets Habit's Start Date
     * @return dateTo Start
     */
    public Date getDateToStart() {
        return dateToStart;
    }



    /**
     *  sets Habit's Start Date
     * @param dateToStart
     */
    public void setDateToStart(Date dateToStart) {
        this.dateToStart = dateToStart;
    }



    /**
     * ArrayList of Freq. dates
     * @return freq. days
     */
    public ArrayList<String> getFrequency() {
        return this.frequency;
    }



    /**
     * Sets the new freq. dates
     * @param frequency
     */
    public void setFrequency(ArrayList frequency) {
        this.frequency = frequency;
    }



    /**
     * Gets Habit's past events
     * @return events
     */
    public ArrayList<HabitEvent> getEvents() {
        return habitEvents;
    }



    /**
     *  Checks to see if Habit has events
     * @param event
     * @return bool
     */
    public Boolean hasEvent(HabitEvent event) {
        if (habitEvents.contains(event)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }



    /**
     * Adds a new event to the Habit
     * @param event
     */
    public void addEvent(HabitEvent event) {
        habitEvents.add(event);
    }



    /**
     * Returns the specific event selected
     * @param index
     * @return event
     */
    public HabitEvent getEvent(int index) {
        return habitEvents.get(index);
    }



    /**
     *
     * @param index
     * @param habitEvent
     */
    public void setEvent(int index, HabitEvent habitEvent) {
        habitEvents.set(index, habitEvent);
    }



    /**
     *
     * @param habitEvents
     */
    public void setEvents(ArrayList<HabitEvent> habitEvents) {
        this.habitEvents = habitEvents;
    }



    /**
     * Deletes the selected event
     * @param index
     */
    public void deleteEvent(int index) {
        habitEvents.remove(index);
    }



    /**
     * Deletes the specific event
     * @param event
     */
    public void deleteEvent(HabitEvent event) {
        habitEvents.remove(event);
    }



    /**
     * Returns some arb stat info
     * todo implementation
     * @param statsType
     * @return stats
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
     * Returns the display string for the habit in list views
     */
    public String toString(){
        //toDo add next occurance of this habit
        return "Habit: "+this.title+"\nReason:"+this.reason;
    }



    public static Comparator<Habit> habitComparator = new Comparator<Habit>() {
        public int compare(Habit h1, Habit h2) {
            String title1 = h1.getTitle();
            String title2 = h2.getTitle();

            //ascending order
            return title1.compareTo(title2);
        }
    };
}