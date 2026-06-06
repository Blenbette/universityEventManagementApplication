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

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);

        dbHelper = new DatabaseHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();

                // Validation
                if (e.isEmpty() || p.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Please enter email and password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check user in database
                User user = dbHelper.loginUser(e, p);

                if (user != null) {

                    Toast.makeText(LoginActivity.this,
                            "Login Successful",
                            Toast.LENGTH_SHORT).show();

                    // ROLE BASED NAVIGATION
                    if (user.getRole().equalsIgnoreCase("admin")) {

                        Intent intent = new Intent(LoginActivity.this,
                                AdminDashboardActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        Intent intent = new Intent(LoginActivity.this,
                                StudentDashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}