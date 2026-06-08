package com.example.universityeventmanagementapplication.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.universityeventmanagementapplication.Models.Event;
import com.example.universityeventmanagementapplication.Models.User;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EventDB.db";
    private static final int DATABASE_VERSION = 2; // Version updated to apply new schema

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // USERS TABLE
        db.execSQL("CREATE TABLE users (" +
                "userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT," +
                "studentId TEXT," +
                "password TEXT," +
                "role TEXT)");

        // ✅ DEFAULT ADMIN ADDED (NO LOGIC CHANGED)
        ContentValues admin = new ContentValues();
        admin.put("name", "Admin");
        admin.put("email", "admin@gmail.com");
        admin.put("studentId", "ADMIN001");
        admin.put("password", "admin123");
        admin.put("role", "ADMIN");
        db.insert("users", null, admin);

        // EVENTS TABLE
        db.execSQL("CREATE TABLE events (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "date TEXT," +
                "time TEXT," +
                "location TEXT," +
                "description TEXT)");

        // REGISTRATIONS TABLE
        db.execSQL("CREATE TABLE registrations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "eventId INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS events");
        db.execSQL("DROP TABLE IF EXISTS registrations");
        onCreate(db);
    }

    // =========================
    // USER AUTHENTICATION
    // =========================

    // REGISTER USER
    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("studentId", user.getStudentId());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        long result = db.insert("users", null, values);
        return result != -1;
    }

    // LOGIN USER
    public User loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (cursor.moveToFirst()) {
            user = new User();
            user.setUserId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setStudentId(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            user.setRole(cursor.getString(5));
        }

        cursor.close();
        return user;
    }

    // =========================
    // EVENTS (ADMIN DASHBOARD)
    // =========================

    // ADD EVENT
    public boolean addEvent(Event event) {
        return insertEvent(event);
    }

    // INSERT EVENT
    public boolean insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", event.getTitle());
        values.put("date", event.getDate());
        values.put("time", event.getTime());
        values.put("location", event.getLocation());
        values.put("description", event.getDescription());

        long result = db.insert("events", null, values);
        return result != -1;
    }

    // GET ALL EVENTS
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM events", null);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setTitle(cursor.getString(1));
                event.setDate(cursor.getString(2));
                event.setTime(cursor.getString(3));
                event.setLocation(cursor.getString(4));
                event.setDescription(cursor.getString(5));
                list.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    // DELETE EVENT
    public void deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("events", "id=?", new String[]{String.valueOf(id)});
    }

    // =========================
    // PARTICIPANTS (ADMIN)
    // =========================

    public ArrayList<User> getParticipants(int eventId) {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT u.* FROM users u " +
                "INNER JOIN registrations r ON u.userId = r.userId " +
                "WHERE r.eventId=?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventId)});

        if (cursor.moveToFirst()) {
            do {
                User u = new User();
                u.setUserId(cursor.getInt(0));
                u.setName(cursor.getString(1));
                u.setEmail(cursor.getString(2));
                u.setStudentId(cursor.getString(3));
                u.setPassword(cursor.getString(4));
                u.setRole(cursor.getString(5));
                list.add(u);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
}