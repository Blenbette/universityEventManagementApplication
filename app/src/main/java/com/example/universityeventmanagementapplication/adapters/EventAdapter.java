package com.example.universityeventmanagementapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityeventmanagementapplication.DataBase.DatabaseHelper;
import com.example.universityeventmanagementapplication.Models.Event;
import com.example.universityeventmanagementapplication.R;
import com.example.universityeventmanagementapplication.activities.EventRelatedactivities.EventListenActivity;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    Context context;
    ArrayList<Event> eventList;
    int userId;

    public EventAdapter(Context context, ArrayList<Event> eventList, int userId) {
        this.context = context;
        this.eventList = eventList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.title.setText(event.getTitle());
        holder.date.setText("Date: " + event.getDate());
        holder.location.setText("Location: " + event.getLocation());

        holder.itemView.setOnClickListener(v -> {
            // Updated to route to your actual EventListenActivity class
            Intent intent = new Intent(context, EventListenActivity.class);
            intent.putExtra("eventId", event.getId());
            intent.putExtra("userId", userId);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        TextView location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            location = itemView.findViewById(R.id.location);
        }
    }
}
