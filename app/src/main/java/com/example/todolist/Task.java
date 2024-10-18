package com.example.todolist;

public class Task {
    private String id;
    private int isChecked;
    private String taskName;

    public String getId() {
        return this.id;
    }
    public int getIsChecked() {
        return this.isChecked;
    }
    public String getTaskName() {
        return this.taskName;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }
    public void setTaskName(java.lang.String taskName) {
        this.taskName = taskName;
    }

    public Task(String id, int isChecked, String taskName) {
        this.id = id;
        this.isChecked = isChecked;
        this.taskName = taskName;
    }
}
