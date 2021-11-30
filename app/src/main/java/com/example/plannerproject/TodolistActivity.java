package com.example.plannerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.plannerproject.databinding.ActivitySettingBinding;
import com.example.plannerproject.databinding.ActivityTodolistBinding;

public class TodolistActivity extends AppCompatActivity {

    private ActivityTodolistBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodolistBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initializeDB(view);
        refreshDB(view);
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "Subject_DB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.setForeignKeyConstraintsEnabled(true);
            db.execSQL("CREATE table if not exists SubjectTable (subject VARCHAR(45) primary key, professor VARCHAR(45));");
            db.execSQL("CREATE table if not exists timeTable (subject VARCHAR(45), day VARCHAR(45), time INT, constraint pk primary key (day, time));");
            db.execSQL("CREATE table if not exists todolist (subject VARCHAR(45), todo VARCHAR(200), deadline date, constraint pk primary key (subject, todo, deadline));");

            //Toast.makeText(getApplicationContext(),"Initialized a",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            //db.execSQL("drop table if exists SubjectTable");
            //db.execSQL("drop table if exists timeTable");
            onCreate(db);
            //Toast.makeText(getApplicationContext(),"Initialized b",Toast.LENGTH_LONG).show();
        }
    }

    public void initializeDB(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        myHelper.onUpgrade(sqlDB,1,2);;
        sqlDB.close();
        //Toast.makeText(getApplicationContext(),"Initialized",Toast.LENGTH_LONG).show();
    }

    public void refreshDB(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from todolist Order by deadline ASC", null);
        String string1 = "과목" + System.lineSeparator();
        String string2 = "과제" + System.lineSeparator();
        String string3 = "기한" + System.lineSeparator();
        while(cursor.moveToNext()){
            String subject =cursor.getString(0);
            string1 += subject + System.lineSeparator();
            string2 += cursor.getString(1)+ System.lineSeparator();
            string3 += cursor.getString(2)+ System.lineSeparator();
        }
        binding.subjectName.setText(string1);
        binding.deadline.setText(string3);
        binding.todolist.setText(string2);

        cursor.close();
        sqlDB.close();
    }

    public void deleteOvertime(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL("delete from todolist where deadline < date('now');" );
        sqlDB.close();
        refreshDB(view);
        //Toast.makeText(getApplicationContext(),"delete "+binding.deleteSubjectName.getText().toString(), Toast.LENGTH_LONG).show();
    }

    public void normalAssignment(View view){
        Intent intent = new Intent(this, NormaltodolistActivity.class);
        startActivity(intent);
    }

}