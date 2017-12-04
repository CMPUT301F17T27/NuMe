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
    private ArrayList<String> followingList = new ArrayList<>();
    private ArrayList<String> followerList = new ArrayList<>();
    private ArrayList<String> requestList = new ArrayList<>();


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



    public void setUsername(String username) {
        this.username = username;
    }



    public String getName(){
        return this.fullName;
    }



    public void setName(String newName){
        fullName = newName;
    }



    public ArrayList<Habit> getHabitList() {
        return habitList;
    }



    public Habit getHabit(int index) {
        return habitList.get(index);
    }



    public void setHabitList(ArrayList<Habit> habitList) {
        this.habitList = habitList;
    }



    public void setHabit(int index, Habit habit) {
        habitList.set(index, habit);
    }



    public void addHabit(Habit habit) {
        if (!habitList.contains(habit)) {
            habitList.add(habit);
        }
    }


    /**
     * Converts index from today's habits to the corresponding index
     * for all habits
     *
     * @param index Index of habit in today's habits
     * @return The index of that same habit in all habits
     */
    public int convertIndex(int index) {
        Habit habit = getTodaysHabitList().get(index);

        if(habitList.contains(habit)) {
            return habitList.indexOf(habit);
        }

        else {
            return -1;
        }
    }



    public void deleteHabit(int index) {
        if (index < habitList.size()) {
            habitList.remove(index);
        }
    }



    public ArrayList<String> getFollowing() {
        return followingList;
    }



    public String getFollowing(int index) {
        return followingList.get(index);
    }



    public void addFollowing(String userName) {
        followingList.add(userName);
    }



    public ArrayList<String> getFollowerList() {
        return followerList;
    }



    public String getFollower(int index) {
        return followerList.get(index);
    }



    public void addFollower(String userName) {
        followerList.add(userName);
    }


    public ArrayList<String> getRequestList() {
        return requestList;
    }



    public String getRequest(int index) {
        return requestList.get(index);
    }



    public void addRequest(String userName) {
        /*
        if(requestList==null){
            this.requestList = new ArrayList<>();
            Log.d("Blank", "addRequest: TEst");
        }
        */

        requestList.add(userName);
    }



    public void deleteRequest(int index) {
        requestList.remove(index);
    }



    /**
     * Gets habits out of the habit list that have today as
     * one of their days in their frequency list
     *
     * @return The list of today's habits
     */
    public ArrayList<Habit> getTodaysHabitList(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        String sDay =  new SimpleDateFormat("EEEE", Locale.ENGLISH).format(today.getTime());

        ArrayList<Habit> todaysHabits = new ArrayList<>();
        for( Habit habit : habitList){
            if(habit.getFrequency().contains(sDay) && habit.getDateToStart().before(today)){
                todaysHabits.add(habit);
            }
        }

        return todaysHabits;
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
     * Gets all the profile objects from ElasticSearch that match the
     * usernames in the followingList and sorts them based on username.
     * Then for each profile, sorts the habits by their title.
     *
     * @return The sorted list of profiles.
     */
    public ArrayList<Profile> getFollowingProfiles() {
        ArrayList<Profile> followingProfiles = new ArrayList<>();

        for (String uName : followingList) {
            followingProfiles.add(SaveLoadController.getProfile(uName));
        }

        Collections.sort(followingProfiles, profileComparator);

        for (int i = 0; i < followingProfiles.size(); i++) {
            Profile prof = followingProfiles.get(i);
            ArrayList<Habit> hList = prof.getHabitList();
            Collections.sort(hList, Habit.habitComparator);
            prof.setHabitList(hList);
            followingProfiles.set(i, prof);
        }

        return followingProfiles;
    }



    private static Comparator<Profile> profileComparator = new Comparator<Profile>() {

        public int compare(Profile p1, Profile p2) {
            String userName1 = p1.getUserName();
            String userName2 = p2.getUserName();

            //ascending order
            return userName1.compareTo(userName2);
        }
    };
}