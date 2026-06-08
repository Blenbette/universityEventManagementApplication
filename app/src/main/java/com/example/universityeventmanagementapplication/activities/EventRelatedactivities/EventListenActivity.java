package com.example.universityeventmanagementapplication.activities.EventRelatedactivities;
import com.example.universityeventmanagementapplication.DataBase.DatabaseHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityeventmanagementapplication.Models.Event;
import com.example.universityeventmanagementapplication.R;
import com.example.universityeventmanagementapplication.adapters.EventAdapter;

import java.util.ArrayList;

public class EventListenActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DatabaseHelper dbHelper;

    ArrayList<Event> eventList;

    EventAdapter adapter;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_listen);

        // Receive userId from Dashboard
        userId = getIntent().getIntExtra("userId", -1);

        recyclerView = findViewById(R.id.recyclerView);

        dbHelper = new DatabaseHelper(this);

        // Load all events
        eventList = dbHelper.getAllEvents();

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // Pass userId to adapter
        adapter =   new EventAdapter(
                this,
                eventList,
                userId
        );

        recyclerView.setAdapter(adapter);
    }
}