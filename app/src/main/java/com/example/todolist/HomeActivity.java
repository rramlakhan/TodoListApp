package com.example.todolist;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    ArrayList<Task> taskList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tvCurrentTime = findViewById(R.id.tvCurrentTime);
        Button btnAddNew = findViewById(R.id.btnAddNew);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        DBHandler db = new DBHandler(HomeActivity.this);
        Dialog dialog = new Dialog(HomeActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime current = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedDate = current.format(formatter);
            tvCurrentTime.setText(formattedDate);
        }

        taskList = db.readTask();

        TaskAdapter taskAdapter = new TaskAdapter(taskList, db, dialog);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(taskAdapter);



        dialog.setContentView(R.layout.layout_add_new_task);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        EditText etTextTaskName = dialog.findViewById(R.id.etTextTaskName);
        Button btnAddTask = dialog.findViewById(R.id.btnAddTask);

        btnAddTask.setOnClickListener(v -> {
            String taskName = etTextTaskName.getText().toString();
            if (!taskName.isEmpty()) {
                Task task = new Task(0, taskName);
                db.addNewTask(task);
                taskList.add(task);
                taskAdapter.notifyItemInserted(taskList.size());
            }
            etTextTaskName.setText("");
            dialog.dismiss();

        });

        btnAddNew.setOnClickListener(v -> {
            dialog.show();
        });
    }
}