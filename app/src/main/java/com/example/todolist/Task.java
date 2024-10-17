package com.example.todolist;

public class Task {
    private int id;
    private int isChecked;
    private String taskName;

    public int getId() {
        return this.id;
    }
    public int getIsChecked() {
        return this.isChecked;
    }
    public String getTaskName() {
        return this.taskName;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }
    public void setTaskName(java.lang.String taskName) {
        this.taskName = taskName;
    }

    public Task(int isChecked, String taskName) {
        this.isChecked = isChecked;
        this.taskName = taskName;
    }
}
