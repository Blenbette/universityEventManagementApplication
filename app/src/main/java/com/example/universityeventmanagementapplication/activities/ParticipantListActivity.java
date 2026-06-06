package com.example.universityeventmanagementapplication.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityeventmanagementapplication.R;
import com.example.universityeventmanagementapplication.adapters.ParticipantAdapter;
import com.example.universityeventmanagementapplication.DataBase.DatabaseHelper;
import com.example.universityeventmanagementapplication.Models.User;

import java.util.ArrayList;

public class ParticipantListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    ParticipantAdapter adapter;

    ArrayList<User> participants;

    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);

        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);

        // Get event ID from intent
        eventId = getIntent().getIntExtra("eventId", -1);

        // Load participants from DB
        participants = dbHelper.getParticipants(eventId);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ParticipantAdapter(this, participants);
        recyclerView.setAdapter(adapter);
    }
}