package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private final ArrayList<Task> list;
    private final DBHandler db;
    private final Dialog dialog;
    public TaskAdapter(ArrayList<Task> list, DBHandler db, Dialog dialog) {
        this.list = list;
        this.db = db;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_each_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task item = list.get(position);
        holder.tvTaskName.setText(item.getTaskName());

        if (item.getIsChecked() == 1) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                db.updateTaskStatus(item.getId(), 1);
            } else if (!holder.checkBox.isChecked()) {
                db.updateTaskStatus(item.getId(), 0);
            }

        });
        holder.ivDelete.setOnClickListener(v -> {
            db.deleteTask(item.getId());
            notifyItemRemoved(holder.getAdapterPosition());
        });
        TextView etTextEditTaskName = dialog.findViewById(R.id.etTextEditTaskName);
        Button btnSave = dialog.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String taskName = etTextEditTaskName.getText().toString();

            db.updateTask(item.getId(), taskName);
            notifyItemChanged(holder.getAdapterPosition());
            dialog.dismiss();

        });
        holder.ivEdit.setOnClickListener(v -> {
            etTextEditTaskName.setText(item.getTaskName());
            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvTaskName;
        ImageView ivEdit;
        ImageView ivDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cbCheckbox);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);

        }
    }
}
