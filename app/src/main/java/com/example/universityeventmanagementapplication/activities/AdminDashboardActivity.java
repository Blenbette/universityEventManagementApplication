package com.example.universityeventmanagementapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityeventmanagementapplication.R;
import com.example.universityeventmanagementapplication.activities.EventRelatedactivities.EventListenActivity;
import com.example.universityeventmanagementapplication.activities.EventRelatedactivities.ManageEventActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    Button createEvent, manageEvents, participants, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        createEvent = findViewById(R.id.btnCreateEvent);
        manageEvents = findViewById(R.id.btnManageEvents);
        participants = findViewById(R.id.btnViewParticipants);
        logout = findViewById(R.id.btnLogout);

        // Create Event
        createEvent.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageEventActivity.class));
        });

        // Manage Events (list + edit/delete)
        manageEvents.setOnClickListener(v -> {
            startActivity(new Intent(this, EventListenActivity.class));
        });

        // View Participants
        participants.setOnClickListener(v -> {
            startActivity(new Intent(this, ParticipantListActivity.class));
        });

        // Logout
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}