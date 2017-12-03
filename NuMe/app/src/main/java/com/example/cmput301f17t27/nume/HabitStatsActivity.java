package com.example.cmput301f17t27.nume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HabitStatsActivity extends AppCompatActivity {

    private Habit habit;

    private Date todaysDate;
    private long daysSince;
    private String eventNum;
    private String weeklyUpdate;
    private ArrayList<HabitEvent> allEvents = new ArrayList<>();
    private int[] weeklyEvents;

    private TextView eventNumber;
    private TextView eventsThisWeek;
    private GraphView lineGraph;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_stats);

        todaysDate = new Date();

        weeklyEvents = new int[7];
        weeklyEvents[0] = 0;
        weeklyEvents[1] = 0;
        weeklyEvents[2] = 0;
        weeklyEvents[3] = 0;
        weeklyEvents[4] = 0;
        weeklyEvents[5] = 0;
        weeklyEvents[6] = 0;

        Bundle bundle = getIntent().getExtras();
        habit = (Habit) bundle.getSerializable("HABIT");

        daysSince = todaysDate.getTime() - habit.getDateToStart().getTime();
        daysSince = TimeUnit.DAYS.convert(daysSince, TimeUnit.MILLISECONDS);

        allEvents = habit.getEvents();

        eventNumber = (TextView) findViewById(R.id.eventnumber);
        eventsThisWeek = (TextView) findViewById(R.id.eventsThisWeek);
        lineGraph = (GraphView) findViewById(R.id.graph);
        backButton = (Button) findViewById(R.id.backbutton);

        if (todaysDate.compareTo(habit.getDateToStart()) < 0) {
            eventNum = "Your habit hasn't started yet!";
        }
        else {
            ArrayList<HabitEvent> eventsSinceStarting = new ArrayList<>();
            for(HabitEvent event : allEvents) {
                if(event.getDateCompleted().compareTo(habit.getDateToStart()) >= 0 && event.getDateCompleted().compareTo(todaysDate) <= 0) {
                    eventsSinceStarting.add(event);
                }
            }
            eventNum = "You have completed " + Integer.toString(eventsSinceStarting.size()) + " events in " +
                    Long.toString(daysSince) + " days.";
        }
        eventNumber.setText(eventNum);

        int backCount = 1;
        boolean keepCounting = true;
        long withinTheWeek;
        HabitEvent latestEvent;

        while (keepCounting) {
            if (allEvents.size() - backCount >= 0) {
                latestEvent = allEvents.get(allEvents.size() - backCount);
                withinTheWeek = todaysDate.getTime() - latestEvent.getDateCompleted().getTime();
                withinTheWeek = TimeUnit.DAYS.convert(withinTheWeek, TimeUnit.MILLISECONDS);
                if(withinTheWeek > 7) {
                    keepCounting = false;
                }
                else {
                    backCount += 1;
                    weeklyEvents[(int) withinTheWeek] += 1;
                }
            }
            else {
                keepCounting = false;
            }
        }

        int weeklyNum = 0;

        for(int events : weeklyEvents) {
            weeklyNum += events;
        }

        if(weeklyNum == 1) {
            weeklyUpdate = "You completed 1 event this week";
        } else {
            weeklyUpdate = "You completed " + Integer.toString(weeklyNum) + " events this week";
        }
        eventsThisWeek.setText(weeklyUpdate);

        int[] reverseWeeklyEvents = new int[7];
        reverseWeeklyEvents[0] = weeklyEvents[6];
        reverseWeeklyEvents[1] = weeklyEvents[5];
        reverseWeeklyEvents[2] = weeklyEvents[4];
        reverseWeeklyEvents[3] = weeklyEvents[3];
        reverseWeeklyEvents[4] = weeklyEvents[2];
        reverseWeeklyEvents[5] = weeklyEvents[1];
        reverseWeeklyEvents[6] = weeklyEvents[0];


        int i;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, reverseWeeklyEvents[0]),
                new DataPoint(1, reverseWeeklyEvents[1]),
                new DataPoint(2, reverseWeeklyEvents[2]),
                new DataPoint(3, reverseWeeklyEvents[3]),
                new DataPoint(4, reverseWeeklyEvents[4]),
                new DataPoint(5, reverseWeeklyEvents[5]),
                new DataPoint(6, reverseWeeklyEvents[6])
        });
        lineGraph.addSeries(series);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
