package com.mdomeck.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public ArrayList<Task> task;

    public TaskAdapter(ArrayList<Task> task) {
        this.task = task;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public Task task;
        public View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }


    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_task, parent, false);

            TaskViewHolder viewHolder = new TaskViewHolder(view);


            // TODO onClick Override for clicking


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        holder.task = task.get(position);

        TextView titleTextView = holder.itemView.findViewById(R.id.titleView);
        TextView bodyTextView = holder.itemView.findViewById(R.id.bodyView);
        TextView stateTextView = holder.itemView.findViewById(R.id.stateView);
        titleTextView.setText(holder.task.title);
        bodyTextView.setText(holder.task.body);
        stateTextView.setText(holder.task.state);


    }

    @Override
    public int getItemCount() {
        return task.size();
    }




}
