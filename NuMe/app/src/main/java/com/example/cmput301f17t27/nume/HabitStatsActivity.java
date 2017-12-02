package com.example.cmput301f17t27.nume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public class HabitStatsActivity extends AppCompatActivity {

    private Habit habit;

    private BarChart chart;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_stats);

        Bundle bundle = getIntent().getExtras();
        habit = (Habit) bundle.getSerializable("HABIT");

        chart = (BarChart) findViewById(R.id.chart);
        backButton = (Button) findViewById(R.id.backbutton);

        ArrayList<Integer> array = new ArrayList<>();
        array.add(1);
        array.add(2);
        array.add(2);
        array.add(3);
        array.add(3);
        array.add(3);
    }
}
