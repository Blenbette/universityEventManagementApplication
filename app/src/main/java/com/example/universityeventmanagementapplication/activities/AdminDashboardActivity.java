
package com.example.universityeventmanagementapplication.activities;
import com.example.universityeventmanagementapplication.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.universityeventmanagementapplication.DataBase.DatabaseHelper;
import com.example.universityeventmanagementapplication.Models.Event;
import com.example.universityeventmanagementapplication.activities.EventRelatedactivities.AddEventActivity;

import java.util.ArrayList;

public class AdminDashboardActivity extends AppCompatActivity {

    Button btnAddEvent;
    ListView eventListView;

    DatabaseHelper db;
    ArrayList<Event> eventList;
    ArrayAdapter<String> adapter;
    ArrayList<String> eventTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R            .layout.activity_admin_dashboard);

        btnAddEvent = findViewById(R.id.btnAddEvent);
        eventListView = findViewById(R.id.eventListView);

        db = new DatabaseHelper(this);

        loadEvents();

        btnAddEvent.setOnClickListener(v ->
                startActivity(new Intent(this, AddEventActivity.class))
        );

        eventListView.setOnItemClickListener((parent, view, position, id) -> {
            Event selectedEvent = eventList.get(position);
            showOptionsDialog(selectedEvent);
        });
    }

    private void loadEvents() {
        eventList = db.getAllEvents();
        eventTitles = new ArrayList<>();

        for (Event e : eventList) {
            eventTitles.add(e.getTitle() + " - " + e.getDate());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventTitles);
        eventListView.setAdapter(adapter);
    }

    private void showOptionsDialog(Event event) {
        String[] options = {"Edit", "Delete"};

        new AlertDialog.Builder(this)
                .setTitle("Choose Option")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        Intent intent = new Intent(this, AddEventActivity.class);
                        intent.putExtra("eventId", event.getId());
                        intent.putExtra("title", event.getTitle());
                        intent.putExtra("date", event.getDate());
                        startActivity(intent);
                    } else {
                        db.deleteEvent(event.getId());
                        Toast.makeText(this, "Event Deleted", Toast.LENGTH_SHORT).show();
                        loadEvents();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }
}