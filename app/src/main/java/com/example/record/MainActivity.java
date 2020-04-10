package com.example.record;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     SQLiteDatabase myDatabase;
     static long total_student;
     long i= 1;
     int lock = 0;
     int update = 0;

    public void setDetails() {
         try {
             i=1;
             myDatabase = this.openOrCreateDatabase("Students", MODE_PRIVATE, null);
             myDatabase.execSQL("CREATE TABLE IF NOT EXISTS students (id INT NOT NULL, name VARCHAR, semester INT(1), PRIMARY KEY (id) )");
             //myDatabase.execSQL("DELETE FROM students WHERE id=1");

             total_student = DatabaseUtils.queryNumEntries(myDatabase,"students");
             Cursor c = myDatabase.rawQuery("SELECT * FROM students", null);


             int idIndex = c.getColumnIndex("id");
             int nameIndex = c.getColumnIndex("name");
             int semesterIndex = c.getColumnIndex("semester");

             c.moveToFirst();

             EditText text_id = (EditText) findViewById(R.id.text_id);
             EditText text_name = (EditText) findViewById((R.id.text_name));
             EditText text_semester = (EditText) findViewById(R.id.text_semester);

             text_id.setInputType(InputType.TYPE_NULL);
             text_name.setInputType(InputType.TYPE_NULL);
             text_semester.setInputType(InputType.TYPE_NULL);


             if (total_student > 0) {
                 text_id.setText(Integer.toString(c.getInt(idIndex)));
                 text_name.setText(c.getString(nameIndex));
                 text_semester.setText(Integer.toString(c.getInt(semesterIndex)));
             }
             else{
                 text_id.setText(null);
                 text_name.setText(null);
                 text_semester.setText(null);
             }
          c.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
        Button button_add = (Button) findViewById(R.id.button_add);
        Button button_update = (Button) findViewById(R.id.button_update);
        Button button_delete = (Button) findViewById(R.id.button_delete);
        Button button_cancel = (Button) findViewById(R.id.button_cancel);
        Button button_Last = (Button) findViewById(R.id.button_last);
        Button button_next = (Button) findViewById(R.id.button_next);
        Button button_prev = (Button) findViewById(R.id.button_prev);
        Button button_first = (Button) findViewById(R.id.button_first);

        button_add.setEnabled(true);
        button_delete.setEnabled(true);
        button_first.setEnabled(true);
        button_prev.setEnabled(true);
        button_next.setEnabled(true);
        button_Last.setEnabled(true);
        button_update.setText("Edit");

    }

    public void onAdd(View view){

        Button button_add = (Button) findViewById(R.id.button_add);
        Button button_update = (Button) findViewById(R.id.button_update);
        Button button_delete = (Button) findViewById(R.id.button_delete);
        Button button_cancel = (Button) findViewById(R.id.button_cancel);
        Button button_Last = (Button) findViewById(R.id.button_last);
        Button button_next = (Button) findViewById(R.id.button_next);
        Button button_prev = (Button) findViewById(R.id.button_prev);
        Button button_first = (Button) findViewById(R.id.button_first);

        button_add.setEnabled(false);
        button_delete.setEnabled(false);
        button_first.setEnabled(false);
        button_prev.setEnabled(false);
        button_next.setEnabled(false);
        button_Last.setEnabled(false);

        button_update.setText("Save");

        EditText text_id = (EditText) findViewById(R.id.text_id);
        EditText text_name = (EditText) findViewById((R.id.text_name));
        EditText text_semester = (EditText) findViewById(R.id.text_semester);

        text_id.setInputType(InputType.TYPE_NULL);
        text_name.setInputType(InputType.TYPE_CLASS_TEXT);
        text_semester.setInputType(InputType.TYPE_CLASS_NUMBER);
        text_id.setText((total_student+1)+ "");
        text_name.setText(null);
        text_semester.setText(null);
        lock = 1;

      //  Toast.makeText(this,text_name.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    public void onUpdate(View view){


        EditText text_id = (EditText) findViewById(R.id.text_id);
        EditText text_name = (EditText) findViewById((R.id.text_name));
        EditText text_semester = (EditText) findViewById(R.id.text_semester);

        if(lock == 0) {
            Button button_add = (Button) findViewById(R.id.button_add);
            Button button_update = (Button) findViewById(R.id.button_update);
            Button button_delete = (Button) findViewById(R.id.button_delete);
            Button button_cancel = (Button) findViewById(R.id.button_cancel);
            Button button_Last = (Button) findViewById(R.id.button_last);
            Button button_next = (Button) findViewById(R.id.button_next);
            Button button_prev = (Button) findViewById(R.id.button_prev);
            Button button_first = (Button) findViewById(R.id.button_first);

            button_add.setEnabled(false);
            button_delete.setEnabled(false);
            button_first.setEnabled(false);
            button_prev.setEnabled(false);
            button_next.setEnabled(false);
            button_Last.setEnabled(false);

            button_update.setText("Save");

            text_name.setInputType(InputType.TYPE_CLASS_TEXT);
            text_semester.setInputType(InputType.TYPE_CLASS_NUMBER);
            text_id.setText(i+"");
            text_name.setText(null);
            text_semester.setText(null);
            update = 1;
        }



      if(text_name.getText().toString().trim().length() == 0) {
                //text_name.setError("Invalid Field");
                lock = 1;
                update = 1;
      }
      else if(update == 1){
          int id = Integer.parseInt(text_id.getText().toString());
          String name = text_name.getText().toString();
          int semester = Integer.parseInt(text_semester.getText().toString());

          String query = "UPDATE students " +
                  "SET name = '"+name+"', " +
                  "semester = "+semester+
                  " WHERE " +
                  "id = "+i+";";
           myDatabase.execSQL(query);
           Toast.makeText(this,"Updated Successfully",Toast.LENGTH_SHORT);
           update = 0;
           lock = 0;
      }
      else {
          int id = Integer.parseInt(text_id.getText().toString());
          String name = text_name.getText().toString();
          int semester = Integer.parseInt(text_semester.getText().toString());

          myDatabase.execSQL("INSERT INTO students VALUES (" + id + "," + "'" + name + "'" + "," + semester + ")");
          Toast.makeText(this, "Added Succesfully", Toast.LENGTH_SHORT).show();
          setDetails();
          lock = 0;
      }
    }

    public void onDelete(View view){

        if(total_student != 0) {
            myDatabase = this.openOrCreateDatabase("Students", MODE_PRIVATE, null);
            EditText text_id = (EditText) findViewById(R.id.text_id);
            EditText text_name = (EditText) findViewById(R.id.text_name);
            int id = Integer.parseInt(text_id.getText().toString());
            //Toast.makeText(this, id + "", Toast.LENGTH_SHORT).show();


            try {
                myDatabase.execSQL("DELETE FROM students WHERE id=" + id);
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                setDetails();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onCancel(View view)
    {
        setDetails();
    }



    public void onNext(View view) {

        if (i < total_student) {
            i = i + 1;
            EditText text_id = (EditText) findViewById(R.id.text_id);
            EditText text_name = (EditText) findViewById(R.id.text_name);
            EditText text_semester = (EditText) findViewById(R.id.text_semester);

            myDatabase = this.openOrCreateDatabase("Students", MODE_PRIVATE, null);

            Cursor c = myDatabase.rawQuery("SELECT * FROM students WHERE id=" + i, null);
            c.moveToFirst();

            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int semesterIndex = c.getColumnIndex("semester");

            text_id.setText(Integer.toString(c.getInt(idIndex)));
            text_name.setText(c.getString(nameIndex));
            text_semester.setText(c.getInt(semesterIndex) + "");

        }
    }

    public void onPrev(View view) {

        if (i > 1) {
            i = i - 1;
            EditText text_id = (EditText) findViewById(R.id.text_id);
            EditText text_name = (EditText) findViewById(R.id.text_name);
            EditText text_semester = (EditText) findViewById(R.id.text_semester);

            myDatabase = this.openOrCreateDatabase("Students", MODE_PRIVATE, null);

            Cursor c = myDatabase.rawQuery("SELECT * FROM students WHERE id=" + i, null);
            c.moveToFirst();

            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int semesterIndex = c.getColumnIndex("semester");

            text_id.setText(Integer.toString(c.getInt(idIndex)));
            text_name.setText(c.getString(nameIndex));
            text_semester.setText(c.getInt(semesterIndex) + "");

        }
    }

    public void onFirst(View view){
        setDetails();
    }

    public void onLast(View view){

        i = total_student;

        if(i != 0) {
            EditText text_id = (EditText) findViewById(R.id.text_id);
            EditText text_name = (EditText) findViewById(R.id.text_name);
            EditText text_semester = (EditText) findViewById(R.id.text_semester);

            myDatabase = this.openOrCreateDatabase("Students", MODE_PRIVATE, null);

            Cursor c = myDatabase.rawQuery("SELECT * FROM students WHERE id=" + i, null);
            c.moveToFirst();

            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int semesterIndex = c.getColumnIndex("semester");

            text_id.setText(Integer.toString(c.getInt(idIndex)));
            text_name.setText(c.getString(nameIndex));
            text_semester.setText(c.getInt(semesterIndex) + "");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDetails();
    }
}
