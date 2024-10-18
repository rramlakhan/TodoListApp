package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "TaskDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Tasks";
    private static final String COL_ID = "id";
    private static final String COL_CHECKED = "checked";
    private static final String COL_TASK_NAME = "taskName";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " TEXT PRIMARY KEY, " +
                COL_CHECKED + " INTEGER, " +
                COL_TASK_NAME + " TEXT)";
        db.execSQL(query);
    }

    public void addNewTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CHECKED, task.getIsChecked());
        contentValues.put(COL_TASK_NAME, task.getTaskName());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public ArrayList<Task> readTask() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Task> taskList = new ArrayList<>();

        while (cursor.moveToNext()) {
            taskList.add(new Task(cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getString(2)));
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public void updateTaskStatus(String id, int checked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CHECKED, checked);
        db.update(TABLE_NAME, contentValues, COL_ID + " = ? ", new String[]{id});
        db.close();
    }

    public void updateTask(String id, String taskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TASK_NAME, taskName);
        db.update(TABLE_NAME, contentValues, COL_ID + " = ? ", new String[]{id});
        db.close();
    }

    public void deleteTask(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + " = ? ", new String[]{id});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
