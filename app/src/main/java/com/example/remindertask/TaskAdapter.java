package com.example.remindertask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final Context context;
    private final List<Task> taskList;

    // Construtor que aceita Context e List<Task>
    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each task item
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // Bind the task data to the views
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // Classe interna static TaskViewHolder
    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTaskTitle;
        private final TextView tvTaskCategory;
        private final TextView tvTaskDueDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskCategory = itemView.findViewById(R.id.tvTaskCategory);
            tvTaskDueDate = itemView.findViewById(R.id.tvTaskDueDate);
        }

        public void bind(Task task) {
            // Set task data to the views
            tvTaskTitle.setText(task.getTitle());
            tvTaskCategory.setText(task.getCategory());
            tvTaskDueDate.setText(task.getDueDate());
        }
    }

}
