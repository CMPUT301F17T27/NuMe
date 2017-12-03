package com.example.cmput301f17t27.nume;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Creates the profile class
 *
 * @author CMPUT301F17T27
 * @version 0.1
 * @see Habit
 */
public class Profile implements Serializable {
    private String id;
    private String username;
    private String fullName;
    private ArrayList<Habit> habitList = new ArrayList<>();
    private ArrayList<Profile> followingList = new ArrayList<>();
    private ArrayList<Profile> followerList = new ArrayList<>();


    public Profile(String username, String fullName){
        this.username = username;
        this.fullName = fullName;
    }



    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }



    public String getUserName(){
        return username;
    }



    public String getName(){
        return this.fullName;
    }



    public ArrayList<Profile> getFollowingList() {
        return followingList;
    }



    public ArrayList<Profile> getFollowerList() {
        return followerList;
    }



    public ArrayList<Habit> getHabitList() {
        return habitList;
    }

    public  ArrayList<Habit> getTodaysHabitList(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String sDay=  new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        ArrayList<Habit> todaysHabits = new ArrayList<>();
        Log.d("Date", "getTodaysHabitList: "+sDay);
        for( Habit habit : habitList){
            if(habit.getFrequency().contains(sDay)){
                todaysHabits.add(habit);
            }
        }

        return todaysHabits;
    }


    public void setName(String newName){
        fullName = newName;
    }



    public void setUsername(String username) {
        this.username = username;
    }


    //The remaining functions are an extension to the ArrayAdapter functions
    public void addHabit(Habit habit) {
        if (!habitList.contains(habit)) {
            habitList.add(habit);
        }
    }

    public void deleteHabit(int index) {
        if (index < habitList.size()) {
            habitList.remove(index);
        }
    }

    public void deleteHabit(Habit habit) {
        if (habitList.contains(habit)) {
            habitList.remove(habit);
        }
    }

    public Habit getHabit(int index) {
        return habitList.get(index);
    }

    public void setHabit(int index, Habit habit) {
        habitList.set(index, habit);
    }

    /**
     * Takes a habit, checks if it is in the habit list, and gives the position.
     */
    public int getPosition(Habit habit) {
        if(habitList.contains(habit)) {
            return habitList.indexOf(habit);
        } else { return -1; }
    }

    /**
     * Returns a sorted list of all events of a profile
     *
     * @return Reverse chronological list of all events from a profile
     */
    public ArrayList<HabitEvent> habitHistory() {

        ArrayList<HabitEvent> history = new ArrayList<HabitEvent>();

        for (Habit habit : habitList) {
            history.addAll(habit.getEvents());
        }

        if (history.size() > 1) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;

    }

    /**
     * Return a sorted list of all events from one habit by directly referencing the habit
     *
     * @param habit : habit to get the events from
     * @return Reverse chronological list of all events of a certain habit
     */
    public ArrayList<HabitEvent> habitHistory(Habit habit) {

        ArrayList<HabitEvent> history = habit.getEvents();

        if (history.size() > 1) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;
    }

    /**
     * Return a sorted list of all events from a habit based on where it is indexed in a profile.
     *
     * @param index : index of habit to get the events from
     * @return Reverse chronological list of all events of a certain habit
     */
    public ArrayList<HabitEvent> habitHistory(int index) {
        ArrayList<HabitEvent> history = this.getHabit(index).getEvents();

        if (history.size() > 1) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;
    }

    /**
     * Returns a sorted list of all events in a profile
     * that contain a certain search term in the comment.
     *
     * @param searchFor : Term to search for in the comments of the habit events
     * @return Reverse chronological list of all events from a profile with that term in the comment.
     */
    public ArrayList<HabitEvent> habitHistory(String searchFor) {

        ArrayList<HabitEvent> history = new ArrayList<HabitEvent>();

        for (Habit habit : habitList) {
            history.addAll(habit.getEvents());
        }

        /* for (HabitEvent event : history) {
            if (!event.getComment().toLowerCase().contains(searchFor.toLowerCase())) {
                history.remove(event);
            }
        }
        */

        for (int i = history.size()-1; i > -1; i--) {
            if (!history.get(i).getComment().toLowerCase().contains(searchFor.toLowerCase())) {
                history.remove(i);
            }
        }

        if (history.size() > 1) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;
    }

    /**
     * Returns a sorted list of events from a habit
     * with a certain search term in the comment of the habit
     * and with the habit directly referenced.
     *
     * @param habit : Habit to get events from
     * @param searchFor : Term to search for in the comments.
     * @return Reverse chronological list of all events from that habit with that term in the comment.
     */
    public ArrayList<HabitEvent> habitHistory(Habit habit, String searchFor) {

        ArrayList<HabitEvent> history = habit.getEvents();

        for (HabitEvent event : history) {
            if (!event.getComment().toLowerCase().contains(searchFor.toLowerCase())) {
                history.remove(event);
            }
        }

        if (history.size() > 1) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;
    }

    /**
     * Returns a sorted list of all events from a certain habit
     * and with a certain term in the comment
     * and with the habit accessed on where it is indexed in the profile
     *
     * @param index : Where the habit is indexed in a profile
     * @param searchFor : What term we are searching for in a HabitEvent comment
     * @return Reverse chronological list of all events from a habit with a certain term in the comment.
     */
    public ArrayList<HabitEvent> habitHistory(int index, String searchFor) {
        ArrayList<HabitEvent> history = this.getHabit(index).getEvents();

        for (HabitEvent event : history) {
            if (!event.getComment().toLowerCase().contains(searchFor.toLowerCase())) {
                history.remove(event);
            }
        }

        if (history.size() > 1) {
            Collections.sort(history, new Comparator<HabitEvent>() {
                @Override
                public int compare(HabitEvent event1, HabitEvent event2) {
                    return event1.getDateCompleted().compareTo(event2.getDateCompleted());
                }
            });

            Collections.reverse(history);
        }

        return history;
    }

    /**
     * Accept someone's request to follow you
     *
     * @param follower : Profile that follows you
     */
    public void addFollower(Profile follower) {
        if (!this.followerList.contains(follower)) {
            this.followerList.add(follower);
        }
    }

    /**
     * When someone accepts your follow request, follow them.
     *
     * @param following : Profile that you follow
     */
    public void followSomeone(Profile following) {
        if (!this.followingList.contains(following)) {
            this.followingList.add(following);
        }
    }

}