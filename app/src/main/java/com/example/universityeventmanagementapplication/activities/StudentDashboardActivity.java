package com.example.universityeventmanagementapplication.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityeventmanagementapplication.R;
import com.example.universityeventmanagementapplication.activities.EventRelatedactivities.EventListenActivity;
import com.example.universityeventmanagementapplication.activities.EventRelatedactivities   .MyRegisteredEventActivity;

public class StudentDashboardActivity extends AppCompatActivity {

    Button viewEvents, myEvents, logout;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdashboard);

        // Receive userId from LoginActivity
        userId = getIntent().getIntExtra("userId", -1);

        viewEvents = findViewById(R.id.viewEventsBtn);
        myEvents = findViewById(R.id.myEventsBtn);
        logout = findViewById(R.id.logoutBtn);

        // View Events
        viewEvents.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            StudentDashboardActivity.this,
                            EventListenActivity.class
                    );

            intent.putExtra("userId", userId);

            startActivity(intent);
        });

        // My Registered Events
        myEvents.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            StudentDashboardActivity.this,
                            MyRegisteredEventActivity.class
                    );

            intent.putExtra("userId", userId);

            startActivity(intent);
        });

        // Logout
        logout.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            StudentDashboardActivity.this,
                            LoginActivity.class
                    );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });
    }
}