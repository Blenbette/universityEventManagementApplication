package com.example.universityeventmanagementapplication.activities.EventRelatedactivities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityeventmanagementapplication.R;
import com.example.universityeventmanagementapplication.adapters.EventAdapter;
import com.example.universityeventmanagementapplication.DataBase.DatabaseHelper;
import com.example.universityeventmanagementapplication.Models.Event;

import java.util.ArrayList;

public class EventListenActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ArrayList<Event> eventList;
    EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_listen);

        // 1. Connect RecyclerView from XML
        recyclerView = findViewById(R.id.recyclerView);

        // 2. Database helper
        dbHelper = new DatabaseHelper(this);

        // 3. Get data from SQLite
        eventList = dbHelper.getAllEvents();

        // 4. Set Layout Manager (VERY IMPORTANT)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 5. Create Adapter
        adapter = new EventAdapter(this, eventList);

        // 6. Attach Adapter to RecyclerView
        recyclerView.setAdapter(adapter);
    }
}