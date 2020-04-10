package com.example.record.data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.record.ui.MainActivity;

public class Database {
    private static SQLiteDatabase myDatabase;

    public  Database(MainActivity main){
        myDatabase = main.openOrCreateDatabase("Students", Context.MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS students (id INT NOT NULL, name VARCHAR, semester INT(1), PRIMARY KEY (id) )");
    }

    public void updateData(long id, String name, int semester){
        String query = "UPDATE students " +
                "SET name = '"+name+"', " +
                "semester = "+semester+
                " WHERE " +
                "id = "+id+";";
         myDatabase.execSQL(query);
    }

    public void addData(long id, String name, int semester){
        myDatabase.execSQL("INSERT INTO students VALUES (" + id + "," + "'" + name + "'" + "," + semester + ")");
    }

    public Cursor getAllData(){
       long total_student = DatabaseUtils.queryNumEntries(myDatabase,"students");
       return myDatabase.rawQuery("SELECT * FROM students", null);
    }

    public boolean delete(long id){
        try {
            myDatabase.execSQL("DELETE FROM students WHERE id=" + id);

        } catch (Exception e) {
            return false;
        }
     return true;
    }

    public Cursor showStudent(long id){
        return myDatabase.rawQuery("SELECT * FROM students WHERE id=" + id, null);
    }

}
