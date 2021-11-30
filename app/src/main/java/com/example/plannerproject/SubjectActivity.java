package com.example.plannerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.plannerproject.databinding.ActivityMainBinding;
import com.example.plannerproject.databinding.ActivitySubjectBinding;

public class SubjectActivity extends AppCompatActivity {

    private ActivitySubjectBinding binding;
    String subject = "";
    String professor = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubjectBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent subIntent = getIntent();
        String day = subIntent.getStringExtra("day");
        String time = subIntent.getStringExtra("time");
        initializeDB(view);

        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        Cursor cursor;
        Cursor cursor2;
        cursor = sqlDB.rawQuery("select * from timeTable where day = '"+ day + "' and time = " + time  + ";" , null);
        while(cursor.moveToNext()){
            subject = cursor.getString(0);
        }
        cursor2 = sqlDB.rawQuery("select * from SubjectTable where subject = '"+ subject + "';" , null);
        while(cursor2.moveToNext()){
            professor = cursor2.getString(1)+ System.lineSeparator();
        }

        cursor.close();
        cursor2.close();
        sqlDB.close();

        binding.subjectName.setText(subject);
        binding.professorName.setText(professor);
        searchToDoList(view);

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

    public void addToDoList(View view){
        try {
            myDBHelper myHelper = new myDBHelper(this);
            SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
            String year = binding.deadYear.getText().toString();
            String month = binding.deadMonth.getText().toString();
            String day = binding.deadDay.getText().toString();
            String toDoName = binding.todolistName.getText().toString();

            if(toDoName.length()<1){
                Toast.makeText(this, "등록할 과제 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            if(year.length()!=4 || month.length()==0 || day.length()==0 ){
                Toast.makeText(this, "올바르지 않은 날짜입니다", Toast.LENGTH_SHORT).show();
                return;
            }
            if(month.length()==1){
                month = "0"+month;
            }
            if(day.length()==1){
                day = "0"+day;
            }

            String datetime = year+"-"+month+"-"+day;
            sqlDB.execSQL("insert into todolist values ('" + subject + "', '" + toDoName + "', '" + datetime + "');");
            sqlDB.close();
            searchToDoList(view);
            Toast.makeText(getApplicationContext(), "new Time Inserted", Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e)
        {
            Toast.makeText(this, "잘못되었습니다..", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void deleteToDoList(View view){
        try {
            myDBHelper myHelper = new myDBHelper(this);
            SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
            String toDoListName = binding.deletetodolistName.getText().toString();
            if(toDoListName.length()<1){
                Toast.makeText(this, "삭제할 과제 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            sqlDB.execSQL("delete from todolist where subject = '"+ subject + "' and todo = '" + toDoListName + "';");
            sqlDB.close();
            searchToDoList(view);
            Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e)
        {
            Toast.makeText(this, "잘못되었습니다..", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public void searchToDoList(View view){
        try {
            myDBHelper myHelper = new myDBHelper(this);
            SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
            Cursor cursor;
            cursor = sqlDB.rawQuery("select * from todolist where subject = '"+ subject +"';", null);
            String string1 = "해야 할 것" + System.lineSeparator();
            String string2 = "데드라인" + System.lineSeparator();
            while(cursor.moveToNext()){
                string1 += cursor.getString(1)+ System.lineSeparator();
                string2 += cursor.getString(2)+ System.lineSeparator();
            }
            sqlDB.close();
            binding.deadline.setText(string2);
            binding.todolist.setText(string1);
            //Toast.makeText(getApplicationContext(), "new Time Inserted", Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e)
        {
            Toast.makeText(this, "뭔가잘못되었습니다..", Toast.LENGTH_SHORT).show();
            return;
        }
    }



}