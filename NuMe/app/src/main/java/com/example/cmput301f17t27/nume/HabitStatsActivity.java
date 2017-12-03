package com.example.cmput301f17t27.nume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HabitStatsActivity extends AppCompatActivity {

    private Habit habit;

    private Date todaysDate;
    private long daysSince;
    private long weekNumber;
    private long remainder;
    private long freqNumber;
    private String eventNum;
    private String weeklyUpdate;
    private ArrayList<HabitEvent> allEvents = new ArrayList<>();
    private int[] weeklyEvents;

    private TextView eventNumber;
    private TextView eventsThisWeek;
    private TextView status;
    private GraphView lineGraph;
    private ImageView statusIndicator;
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
        status = (TextView) findViewById(R.id.status);
        statusIndicator = (ImageView) findViewById(R.id.statusIndicator);
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



        int i;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, weeklyEvents[6]),
                new DataPoint(1, weeklyEvents[5]),
                new DataPoint(2, weeklyEvents[4]),
                new DataPoint(3, weeklyEvents[3]),
                new DataPoint(4, weeklyEvents[2]),
                new DataPoint(5, weeklyEvents[1]),
                new DataPoint(6, weeklyEvents[0])
        });
        lineGraph.addSeries(series);

        weekNumber = daysSince/7;
        remainder = daysSince%7;
        freqNumber = 0;
        if (habit.getFrequency().contains("Sunday")) {
            freqNumber += 1;
        }
        if (habit.getFrequency().contains("Monday")) {
            freqNumber += 1;
        }
        if (habit.getFrequency().contains("Tuesday")) {
            freqNumber += 1;
        }
        if (habit.getFrequency().contains("Wednesday")) {
            freqNumber += 1;
        }
        if (habit.getFrequency().contains("Thursday")) {
            freqNumber += 1;
        }
        if (habit.getFrequency().contains("Friday")) {
            freqNumber += 1;
        }
        if (habit.getFrequency().contains("Saturday")) {
            freqNumber += 1;
        }

        Log.d("Week", Long.toString(weekNumber));
        Log.d("Freq", Long.toString(freqNumber));
        Log.d("Events", Integer.toString(weeklyNum));
        Log.d("All", Integer.toString(allEvents.size()));
        if(weekNumber == 0) {
            status.setText("Welcome to this new Habit!");
            statusIndicator.setImageResource(R.drawable.face1);
        }
        else if ((double) allEvents.size() / ((double) freqNumber * (double) weekNumber) >= 0.66 &&
                (double) weeklyNum / (double) freqNumber >= 0.5) {
            status.setText("Doing excellent!");
            statusIndicator.setImageResource(R.drawable.face1);
        }
        else if ((double) allEvents.size() / ((double) freqNumber * (double) weekNumber) >= 0.66 &&
                (double) weeklyNum / (double) freqNumber < 0.5 &&
                (double) weeklyNum / (double) freqNumber > 0) {
            status.setText("Add some more events!");
            statusIndicator.setImageResource(R.drawable.face2);
        }
        else if ((double) allEvents.size() / ((double) freqNumber * (double) weekNumber) >= 0.66 &&
                weeklyNum == 0) {
            status.setText("Add an event this week!");
            statusIndicator.setImageResource(R.drawable.face3);
        }
        else if ((double) allEvents.size() / ((double) freqNumber * (double) weekNumber) < 0.66 &&
                (double) allEvents.size() / ((double) freqNumber * (double) weekNumber) >= 0.33 &&
                weeklyNum >= freqNumber) {
            status.setText("Great week, but build up your habit over time!");
            statusIndicator.setImageResource(R.drawable.face2);
        }
        else if ((double) allEvents.size() / ((double) freqNumber * (double) weekNumber) < 0.66 &&
                (double) allEvents.size() / ((double) freqNumber * (double) weekNumber) >= 0.33 &&
                weeklyNum < freqNumber) {
            status.setText("Add events to reach your weekly goal!");
            statusIndicator.setImageResource(R.drawable.face3);
        }
        else {
            status.setText("Need to build up your habit!");
            statusIndicator.setImageResource(R.drawable.face3);
        }


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
