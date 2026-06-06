package com.example.universityeventmanagementapplication.activities;

import static com.example.universityeventmanagementapplication.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityeventmanagementapplication.R;
import com.example.universityeventmanagementapplication.activities.EventRelatedactivities.EventListenActivity;
import com.example.universityeventmanagementapplication.activities.EventRelatedactivities.MyRegisteredEventActivity;

public class StudentDashboardActivity extends AppCompatActivity {

    Button viewEvents, myEvents, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdashboard);

        viewEvents = findViewById(R.id.viewEventsBtn);
        myEvents = findViewById(R.id.myEventsBtn);
        logout = findViewById(R.id.logoutBtn);

        // View Events
        viewEvents.setOnClickListener(v -> {
            startActivity(new Intent(this, EventListenActivity.class));
        });

        // My Registered Events
        myEvents.setOnClickListener(v -> {
            startActivity(new Intent(this, MyRegisteredEventActivity.class));
        });

        // Logout
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}