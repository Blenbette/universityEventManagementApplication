package com.example.universityeventmanagementapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityeventmanagementapplication.DataBase.DatabaseHelper;
import com.example.universityeventmanagementapplication.Models.User;
import com.example.universityeventmanagementapplication.R;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, studentId, password;
    Button registerBtn;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        studentId = findViewById(R.id.studentId);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);

        dbHelper = new DatabaseHelper(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString().trim();
                String e = email.getText().toString().trim();
                String s = studentId.getText().toString().trim();
                String p = password.getText().toString().trim();

                if (n.isEmpty() ||  e.isEmpty() ||  s.isEmpty() || p.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,
                            "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create User object
                User user = new User();
                user.setName(n);
                user.setEmail(e);
                user.setStudentId(s);
                user.setPassword(p);
                user.setRole("student"); // default role

                boolean inserted = dbHelper.registerUser(user);

                if (inserted) {
                    Toast.makeText(RegisterActivity.this,
                            "Registration Successful", Toast.LENGTH_SHORT).show();

                    // Go to login screen
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this,
                            "Registration Failed (Email may already exist)",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}