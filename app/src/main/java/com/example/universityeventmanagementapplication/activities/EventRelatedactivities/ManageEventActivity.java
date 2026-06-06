package com.example.universityeventmanagementapplication.activities.EventRelatedactivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityeventmanagementapplication.DataBase.DatabaseHelper;
import com.example.universityeventmanagementapplication.Models.Event;
import com.example.universityeventmanagementapplication.R;

public class ManageEventActivity extends AppCompatActivity {

    EditText title, date, time, location, description;
    Button saveBtn;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_event);

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        saveBtn = findViewById(R.id.saveBtn);

        dbHelper = new DatabaseHelper(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String t = title.getText().toString().trim();
                String d = date.getText().toString().trim();
                String ti = time.getText().toString().trim();
                String l = location.getText().toString().trim();
                String desc = description.getText().toString().trim();

                if (t.isEmpty() || d.isEmpty()  || ti.isEmpty() || l.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(ManageEventActivity.this,
                            "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Event event = new Event();
                event.setTitle(t);
                event.setDate(d);
                event.setTime(ti);
                event.setLocation(l);
                event.setDescription(desc);

                boolean inserted = dbHelper.insertEvent(event);

                if (inserted) {
                    Toast.makeText(ManageEventActivity.this,
                            "Event Created Successfully",
                            Toast.LENGTH_SHORT).show();

                    finish(); // go back to dashboard
                } else {
                    Toast.makeText(ManageEventActivity.this,
                            "Failed to create event",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}