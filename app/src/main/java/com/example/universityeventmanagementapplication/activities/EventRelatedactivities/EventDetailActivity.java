package com.example.universityeventmanagementapplication.activities.EventRelatedactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityeventmanagementapplication.DataBase.DatabaseHelper;
import com.example.universityeventmanagementapplication.Models.Event;
import com.example.universityeventmanagementapplication.R;

public class EventDetailActivity extends AppCompatActivity {

    TextView title, date, time, location, description;
    Button registerBtn;

    DatabaseHelper dbHelper;

    int eventId;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        registerBtn = findViewById(R.id.registerBtn);

        dbHelper = new DatabaseHelper(this);

        // Get data from Intent
        eventId = getIntent().getIntExtra("eventId", -1);
        userId = getIntent().getIntExtra("userId", -1);

        // Load event details
        Event event = dbHelper.getEventById(eventId);

        if (event != null) {
            title.setText(event.getTitle());
            date.setText("Date: " + event.getDate());
            time.setText("Time: " + event.getTime());
            location.setText("Location: " + event.getLocation());
            description.setText(event.getDescription());
        }

        // REGISTER BUTTON
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userId == -1 || eventId == -1) {
                    Toast.makeText(EventDetailActivity.this,
                            "Error: missing user or event",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = dbHelper.registerForEvent(userId, eventId);

                if (success) {
                    Toast.makeText(EventDetailActivity.this,
                            "Registered Successfully!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EventDetailActivity.this,
                            "Already registered or failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}