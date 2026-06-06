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
    private static final int DATABASE_VERSION = 1;

    // TABLES
    private static final String TABLE_USERS = "users";
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_REG = "registrations";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // USERS TABLE
        String createUsers = "CREATE TABLE " + TABLE_USERS + "(" +
                "userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT UNIQUE," +
                "studentId TEXT," +
                "password TEXT," +
                "role TEXT" +
                ")";

        // EVENTS TABLE
        String createEvents = "CREATE TABLE " + TABLE_EVENTS + "(" +
                "eventId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "date TEXT," +
                "time TEXT," +
                "location TEXT," +
                "description TEXT" +
                ")";

        // REGISTRATION TABLE
        String createReg = "CREATE TABLE " + TABLE_REG + "(" +
                "registrationId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "eventId INTEGER" +
                ")";

        db.execSQL(createUsers);
        db.execSQL(createEvents);
        db.execSQL(createReg);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REG);
        onCreate(db);
    }

    // =========================
    // USER METHODS
    // =========================

    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("studentId", user.getStudentId());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public User loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password}
        );

        if (cursor.moveToFirst()) {
            User user = new User();
            user.setUserId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setStudentId(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            user.setRole(cursor.getString(5));
            cursor.close();
            return user;
        }

        cursor.close();
        return null;
    }

    // =========================
    // EVENT METHODS
    // =========================

    public boolean insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", event.getTitle());
        values.put("date", event.getDate());
        values.put("time", event.getTime());
        values.put("location", event.getLocation());
        values.put("description", event.getDescription());
        long result = db.insert(TABLE_EVENTS, null, values);
        return result != -1;
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM events", null);

        if (cursor.moveToFirst()) {
            do {
                Event e = new Event();
                e.setEventId(cursor.getInt(0));
                e.setTitle(cursor.getString(1));
                e.setDate(cursor.getString(2));
                e.setTime(cursor.getString(3));
                e.setLocation(cursor.getString(4));
                e.setDescription(cursor.getString(5));

                list.add(e);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public Event getEventById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM events WHERE eventId=?",
                new String[]{String.valueOf(id)}
        );

        if (cursor.moveToFirst()) {
            Event e = new Event();
            e.setEventId(cursor.getInt(0));
            e.setTitle(cursor.getString(1));
            e.setDate(cursor.getString(2));
            e.setTime(cursor.getString(3));
            e.setLocation(cursor.getString(4));
            e.setDescription(cursor.getString(5));
            cursor.close();
            return e;
        }

        cursor.close();
        return null;
    }

    public boolean updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", event.getTitle());
        values.put("date", event.getDate());
        values.put("time", event.getTime());
        values.put("location", event.getLocation());
        values.put("description", event.getDescription());

        int result = db.update(TABLE_EVENTS, values,
                "eventId=?",
                new String[]{String.valueOf(event.getEventId())});

        return result > 0;
    }

    public boolean deleteEvent(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_EVENTS,
                "eventId=?",
                new String[]{String.valueOf(eventId)});

        return result > 0;
    }

    // =========================
    // REGISTRATION METHODS
    // =========================

    public boolean registerForEvent(int userId, int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("eventId", eventId);

        long result = db.insert(TABLE_REG, null, values);
        return result != -1;
    }

    public ArrayList<Event> getRegisteredEvents(int userId) {
        ArrayList<Event> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT e.* FROM events e " +
                "INNER JOIN registrations r ON e.eventId = r.eventId " +
                "WHERE r.userId=?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Event e = new Event();
                e.setEventId(cursor.getInt(0));
                e.setTitle(cursor.getString(1));
                e.setDate(cursor.getString(2));
                e.setTime(cursor.getString(3));
                e.setLocation(cursor.getString(4));
                e.setDescription(cursor.getString(5));

                list.add(e);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
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