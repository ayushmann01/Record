package com.example.record.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.record.R;




public class MainActivity extends AppCompatActivity {

   private Button button_add;
   private Button button_update;
   private Button button_delete;
   private Button button_cancel;
   private Button button_Last;
   private Button button_next;
   private Button button_prev;
   private Button button_first;
   private RecordController record;
   //Database database;


   private EditText text_id;
   private EditText text_name;
   private EditText text_semester;


    // SQLiteDatabase myDatabase;
     static long total_student;
     long i= 1;
     int lock = 0;
     int update = 0;

    public void setDetails() {
         try {
             i=1;
             Cursor c = record.getAllData();
             c.moveToFirst();

             int idIndex = c.getColumnIndex("id");
             int nameIndex = c.getColumnIndex("name");
             int semesterIndex = c.getColumnIndex("semester");

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

        button_add.setEnabled(true);
        button_delete.setEnabled(true);
        button_first.setEnabled(true);
        button_prev.setEnabled(true);
        button_next.setEnabled(true);
        button_Last.setEnabled(true);
        button_update.setText("Edit");

    }

    public void onAdd(View view){


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

    }

    public void onUpdate(View view){


        EditText text_id = (EditText) findViewById(R.id.text_id);
        EditText text_name = (EditText) findViewById((R.id.text_name));
        EditText text_semester = (EditText) findViewById(R.id.text_semester);

        if(lock == 0) {

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

                //major bugs in this function(does not work in desired way)

      if(text_name.getText().toString().trim().length() == 0) {
                //text_name.setError("Invalid Field");
                lock = 1;
                update = 1;
      }
      else if(update == 1){
          int id = Integer.parseInt(text_id.getText().toString());
          String name = text_name.getText().toString();
          int semester = Integer.parseInt(text_semester.getText().toString());
          record.updateData(id,name,semester);
          Toast.makeText(this,"Updated Successfully",Toast.LENGTH_SHORT);
          update = 0;
          lock = 0;
      }
      else {
          int id = Integer.parseInt(text_id.getText().toString());
          String name = text_name.getText().toString();
          int semester = Integer.parseInt(text_semester.getText().toString());
          record.addData(id,name,semester);
          Toast.makeText(this, "Added Succesfully", Toast.LENGTH_SHORT).show();
          setDetails();
          lock = 0;
      }
    }

    public void onDelete(View view){

        if(total_student != 0) {
            text_id = (EditText) findViewById(R.id.text_id);
            int id = Integer.parseInt(text_id.getText().toString());
            boolean result = record.delete(id);
            if(result){
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                setDetails();
            }else Toast.makeText(this,"Deletion failed",Toast.LENGTH_SHORT).show();

        }else Toast.makeText(this,"No record found",Toast.LENGTH_SHORT).show();
    }

    public void onCancel(View view)
    {
        setDetails();
    }



    public void onNext(View view) {

        if (i < total_student) {
            i = i + 1;
             text_id = (EditText) findViewById(R.id.text_id);
             text_name = (EditText) findViewById(R.id.text_name);
             text_semester = (EditText) findViewById(R.id.text_semester);

            Cursor c = RecordController.showStudent(i);
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

            Cursor c = RecordController.showStudent(i);
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

            Cursor c = RecordController.showStudent(i);
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

           // Initializing Buttons
        button_add    =  findViewById(R.id.button_add);
        button_update =  findViewById(R.id.button_update);
        button_delete =  findViewById(R.id.button_delete);
        button_cancel =  findViewById(R.id.button_cancel);
        button_Last   =  findViewById(R.id.button_last);
        button_next   =  findViewById(R.id.button_next);
        button_prev   =  findViewById(R.id.button_prev);
        button_first  =  findViewById(R.id.button_first);

            //Initializing Texts
        text_id       =  findViewById(R.id.text_id);
        text_name     =  findViewById((R.id.text_name));
        text_semester =  findViewById(R.id.text_semester);

        record = new RecordController(this);


        setDetails();

    }
}
