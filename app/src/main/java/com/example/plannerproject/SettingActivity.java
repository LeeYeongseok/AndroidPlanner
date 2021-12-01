package com.example.plannerproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plannerproject.databinding.ActivityMainBinding;
import com.example.plannerproject.databinding.ActivitySettingBinding;
import com.example.plannerproject.databinding.ActivitySubjectBinding;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding binding;

    public static final String Subject_Table = "SubjectTable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initializeDB(view);
    }

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context){
            super(context, "Subject_DB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.setForeignKeyConstraintsEnabled(true);
            db.execSQL("CREATE table if not exists SubjectTable (subject VARCHAR(45) primary key, professor VARCHAR(45));");
            db.execSQL("CREATE table if not exists timeTable (subject VARCHAR(45), day VARCHAR(45), time INT, constraint pk primary key (day, time));");

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

    public void addNewSubject(View view){
        try {
            myDBHelper myHelper = new myDBHelper(this);
            SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
            String subjectName = binding.addSubjectName.getText().toString();
            String professorName = binding.addProfessorName.getText().toString();
            if(subjectName.length()<1 || subjectName.equals("-")){
                Toast.makeText(getApplicationContext(), "과목 이름을 입력하세요", Toast.LENGTH_LONG).show();
                return;
            }
            if(professorName.length()<1){
                Toast.makeText(getApplicationContext(), "교수님 이름을 입력하세요", Toast.LENGTH_LONG).show();
                return;
            }
            sqlDB.execSQL("insert into SubjectTable values ('" + subjectName + "', '" + professorName + "');");
            sqlDB.close();
            Toast.makeText(getApplicationContext(), "new subject Inserted", Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e)
        {
            Toast.makeText(this, "이미 있는 과목 이름입니다", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void addTimeSubject(View view){
        try {
            myDBHelper myHelper = new myDBHelper(this);
            SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
            String subjectName = binding.addTimeSubjectName.getText().toString();
            Cursor cursor;
            cursor = sqlDB.rawQuery("select * from "+ Subject_Table + " where subject = '" + subjectName + "';", null);
            int check = 0;
            while(cursor.moveToNext()){
                check += 1;
            }
            if(check ==0 ){
                Toast.makeText(getApplicationContext(), "해당 이름의 과목이 등록되어 있지 않습니다", Toast.LENGTH_LONG).show();
                return;
            }
            if(subjectName.length()<1){
                Toast.makeText(getApplicationContext(), "과목 이름을 입력하세요", Toast.LENGTH_LONG).show();
                return;
            }
            sqlDB.execSQL("insert into timeTable values ('" + subjectName + "', '" + binding.subjectDaySpinner.getSelectedItem().toString() + "', " + binding.subjectTimeSpinner.getSelectedItem().toString() + ");");
            sqlDB.close();
            Toast.makeText(getApplicationContext(), "시간표에 등록되었습니다", Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e)
        {
            Toast.makeText(this, "해당 시간에 이미 과목이 있습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

//    public void searchDB(View view){
//        myDBHelper myHelper = new myDBHelper(this);
//        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
//        Cursor cursor;
//        Cursor cursor2;
//        cursor = sqlDB.rawQuery("select * from "+ Subject_Table, null);
//        String string1 = "subject" + System.lineSeparator();
//        String string2 = "professor" + System.lineSeparator();
//        while(cursor.moveToNext()){
//            string1 += cursor.getString(0)+ System.lineSeparator();
//            string2 += cursor.getString(1)+ System.lineSeparator();
//        }
//        cursor2 = sqlDB.rawQuery("select * from timeTable", null);
//        while(cursor2.moveToNext()){
//            string1 += cursor2.getString(0)+ System.lineSeparator();
//            string2 += cursor2.getString(1)+ " - ";
//            string2 += cursor2.getString(2)+ System.lineSeparator();
//        }
//        binding.temp1.setText(string1);
//        binding.temp2.setText(string2);
//
//        cursor.close();
//        sqlDB.close();
//
//    }

    public void deleteSubject(View view){
        try {
            myDBHelper myHelper = new myDBHelper(this);
            SQLiteDatabase sqlDB = myHelper.getWritableDatabase();

            String deleteName = binding.deleteSubjectName.getText().toString();

            if(deleteName.length()<1 || deleteName.equals("-")){
                Toast.makeText(getApplicationContext(), "삭제할 과목 이름을 입력해주세요", Toast.LENGTH_LONG).show();
                return;
            }
            
            sqlDB.execSQL("delete from SubjectTable where subject = '" + binding.deleteSubjectName.getText().toString() + "'; ");
            sqlDB.execSQL("delete from timeTable where subject = '" + binding.deleteSubjectName.getText().toString() + "'; ");
            sqlDB.execSQL("delete from todolist where subject = '" + binding.deleteSubjectName.getText().toString() + "'; ");
            sqlDB.close();
            Toast.makeText(getApplicationContext(), "삭제: " + binding.deleteSubjectName.getText().toString(), Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e)
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void deleteTimeTable(View view){
        try {
            myDBHelper myHelper = new myDBHelper(this);
            SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
            String day = binding.deleteSubjectDaySpinner.getSelectedItem().toString();
            String time = binding.deleteSubjectTimeSpinner.getSelectedItem().toString();
            sqlDB.execSQL("delete from timeTable where day = '" + day + "' and time = " + time + ";");
            sqlDB.close();
            //Toast.makeText(getApplicationContext(), "삭제: " + binding.deleteSubjectName.getText().toString(), Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e)
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }
    }




}