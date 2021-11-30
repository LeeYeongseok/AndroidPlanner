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

import com.example.plannerproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        refreshTimeTable(view);
        setContentView(view);

    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "Subject_DB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE table if not exists SubjectTable (subject VARCHAR(45), professor VARCHAR(45));");
            db.execSQL("CREATE table if not exists timeTable (subject VARCHAR(45), day VARCHAR(45), time INT);");
//            Toast.makeText(getApplicationContext(),"Initialized a",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//            db.execSQL("drop table if exists "+ Subject_Table);
            onCreate(db);
//            Toast.makeText(getApplicationContext(),"Initialized b",Toast.LENGTH_LONG).show();
        }
    }

    public void openSubject(View view){
        String i = view.getResources().getResourceEntryName(view.getId());
        String day ="";
        if (i.substring(0,1).equals("m")){
            day = "월요일";
        }
        else if (i.substring(0,1).equals("t")){
            day = "화요일";
        }
        else if (i.substring(0,1).equals("w")){
            day = "수요일";
        }
        else if (i.substring(0,1).equals("h")){
            day = "목요일";
        }
        else{
            day = "금요일";
        }
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from timeTable where day = '" + day +"' and time = " + i.substring(1) + ";", null);
        int check = 0;
        while(cursor.moveToNext()) {
            check += 1;
        }
        cursor.close();
        sqlDB.close();

        if(check != 0) {
            subjectIntent(view, day, i.substring(1));
        }
        else{
            Toast.makeText(this, "일정이 등록되지 않은 시간입니다.", Toast.LENGTH_SHORT).show();
        }
    }
    public void subjectIntent(View view, String day, String time){
        Intent intent = new Intent(this, SubjectActivity.class);
        intent.putExtra("day", day);
        intent.putExtra("time", time);
        startActivity(intent);
    }

    public void settingSubject(View view){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
    public void todolist(View view){
        Intent intent = new Intent(this, TodolistActivity.class);
        startActivity(intent);
    }

    public void refreshTimeTable(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from timeTable", null);
        String string1 = "";
        String string2 = "";
        String string3 = "";
        binding.m1.setText("");
        binding.m2.setText("");
        binding.m3.setText("");
        binding.m4.setText("");
        binding.m5.setText("");
        binding.m6.setText("");
        binding.m7.setText("");
        binding.m8.setText("");
        binding.m9.setText("");
        binding.m10.setText("");
        binding.t1.setText("");
        binding.t2.setText("");
        binding.t3.setText("");
        binding.t4.setText("");
        binding.t5.setText("");
        binding.t6.setText("");
        binding.t7.setText("");
        binding.t8.setText("");
        binding.t9.setText("");
        binding.t10.setText("");
        binding.w1.setText("");
        binding.w2.setText("");
        binding.w3.setText("");
        binding.w4.setText("");
        binding.w5.setText("");
        binding.w6.setText("");
        binding.w7.setText("");
        binding.w8.setText("");
        binding.w9.setText("");
        binding.w10.setText("");
        binding.h1.setText("");
        binding.h2.setText("");
        binding.h3.setText("");
        binding.h4.setText("");
        binding.h5.setText("");
        binding.h6.setText("");
        binding.h7.setText("");
        binding.h8.setText("");
        binding.h9.setText("");
        binding.h10.setText("");
        binding.f1.setText("");
        binding.f2.setText("");
        binding.f3.setText("");
        binding.f4.setText("");
        binding.f5.setText("");
        binding.f6.setText("");
        binding.f7.setText("");
        binding.f8.setText("");
        binding.f9.setText("");
        binding.f10.setText("");
        while(cursor.moveToNext()){
            string1 = cursor.getString(0);
            string2 = cursor.getString(1);
            string3 = cursor.getString(2);
            if(string2.equals("월요일")){
                if(string3.equals("1")){
                    binding.m1.setText(string1);
                }
                if(string3.equals("2")){
                    binding.m2.setText(string1);
                }
                if(string3.equals("3")){
                    binding.m3.setText(string1);
                }
                if(string3.equals("4")){
                    binding.m4.setText(string1);
                }
                if(string3.equals("5")){
                    binding.m5.setText(string1);
                }
                if(string3.equals("6")){
                    binding.m6.setText(string1);
                }
                if(string3.equals("7")){
                    binding.m7.setText(string1);
                }
                if(string3.equals("8")){
                    binding.m8.setText(string1);
                }
                if(string3.equals("9")){
                    binding.m9.setText(string1);
                }
                if(string3.equals("10")){
                    binding.m10.setText(string1);
                }
            }
            if(string2.equals("화요일")){
                if(string3.equals("1")){
                    binding.t1.setText(string1);
                }
                if(string3.equals("2")){
                    binding.t2.setText(string1);
                }
                if(string3.equals("3")){
                    binding.t3.setText(string1);
                }
                if(string3.equals("4")){
                    binding.t4.setText(string1);
                }
                if(string3.equals("5")){
                    binding.t5.setText(string1);
                }
                if(string3.equals("6")){
                    binding.t6.setText(string1);
                }
                if(string3.equals("7")){
                    binding.t7.setText(string1);
                }
                if(string3.equals("8")){
                    binding.t8.setText(string1);
                }
                if(string3.equals("9")){
                    binding.t9.setText(string1);
                }
                if(string3.equals("10")){
                    binding.t10.setText(string1);
                }
            }
            if(string2.equals("수요일")){
                if(string3.equals("1")){
                    binding.w1.setText(string1);
                }
                if(string3.equals("2")){
                    binding.w2.setText(string1);
                }
                if(string3.equals("3")){
                    binding.w3.setText(string1);
                }
                if(string3.equals("4")){
                    binding.w4.setText(string1);
                }
                if(string3.equals("5")){
                    binding.w5.setText(string1);
                }
                if(string3.equals("6")){
                    binding.w6.setText(string1);
                }
                if(string3.equals("7")){
                    binding.w7.setText(string1);
                }
                if(string3.equals("8")){
                    binding.w8.setText(string1);
                }
                if(string3.equals("9")){
                    binding.w9.setText(string1);
                }
                if(string3.equals("10")){
                    binding.w10.setText(string1);
                }
            }
            if(string2.equals("목요일")){
                if(string3.equals("1")){
                    binding.h1.setText(string1);
                }
                if(string3.equals("2")){
                    binding.h2.setText(string1);
                }
                if(string3.equals("3")){
                    binding.h3.setText(string1);
                }
                if(string3.equals("4")){
                    binding.h4.setText(string1);
                }
                if(string3.equals("5")){
                    binding.h5.setText(string1);
                }
                if(string3.equals("6")){
                    binding.h6.setText(string1);
                }
                if(string3.equals("7")){
                    binding.h7.setText(string1);
                }
                if(string3.equals("8")){
                    binding.h8.setText(string1);
                }
                if(string3.equals("9")){
                    binding.h9.setText(string1);
                }
                if(string3.equals("10")){
                    binding.h10.setText(string1);
                }
            }
            if(string2.equals("금요일")){
                if(string3.equals("1")){
                    binding.f1.setText(string1);
                }
                if(string3.equals("2")){
                    binding.f2.setText(string1);
                }
                if(string3.equals("3")){
                    binding.f3.setText(string1);
                }
                if(string3.equals("4")){
                    binding.f4.setText(string1);
                }
                if(string3.equals("5")){
                    binding.f5.setText(string1);
                }
                if(string3.equals("6")){
                    binding.f6.setText(string1);
                }
                if(string3.equals("7")){
                    binding.f7.setText(string1);
                }
                if(string3.equals("8")){
                    binding.f8.setText(string1);
                }
                if(string3.equals("9")){
                    binding.f9.setText(string1);
                }
                if(string3.equals("10")){
                    binding.f10.setText(string1);
                }
            }
        }

        cursor.close();
        sqlDB.close();
    }

}