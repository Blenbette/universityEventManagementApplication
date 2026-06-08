package com.example.universityeventmanagementapplication.activities.EventRelatedactivities;


import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.universityeventmanagementapplication.DataBase.DatabaseHelper;
import com.example.universityeventmanagementapplication.Models.Event;
import com.example.universityeventmanagementapplication.R;

public class AddEventActivity extends AppCompatActivity {

    EditText etTitle, etDate;
    Button btnSave;

    DatabaseHelper db;

    int eventId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        btnSave = findViewById(R.id.btnSave);

        db = new DatabaseHelper(this);

        // CHECK IF EDIT MODE
        if (getIntent().hasExtra("eventId")) {
            eventId = getIntent().getIntExtra("eventId", -1);
            etTitle.setText(getIntent().getStringExtra("title"));
            etDate.setText(getIntent().getStringExtra("date"));
        }

        btnSave.setOnClickListener(v -> {

            String title = etTitle.getText().toString();
            String date = etDate.getText().toString();

            if (title.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Event event = new Event();
            event.setTitle(title);
            event.setDate(date);

            if (eventId == -1) {
                // ADD
                db.addEvent(event);
                Toast.makeText(this, "Event Added", Toast.LENGTH_SHORT).show();
            } else {
                // UPDATE (simple way: delete + add)
                db.deleteEvent(eventId);
                db.addEvent(event);
                Toast.makeText(this, "Event Updated", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}
