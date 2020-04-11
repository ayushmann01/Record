package com.example.record.ui;

import android.database.Cursor;

import com.example.record.data.Database;

public class RecordController {

    private static Database database;

    RecordController(MainActivity main){
        database = new Database(main);
    }



    public static Cursor showStudent(long id){
        return database.showStudent(id);
    }

    public Cursor getAllData(){
        return database.getAllData();
    }

    public void updateData(long id,String name,int semester){
        database.updateData(id,name,semester);
    }

    public void addData(long id, String name, int semester){
        database.addData(id,name,semester);
    }

    public boolean delete(int id){
        return database.delete(id);
    }

    public long totalStudents(){
        return database.totalStudents();
    }
}
