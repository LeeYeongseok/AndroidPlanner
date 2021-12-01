package com.example.plannerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.plannerproject.databinding.ActivityNormaltodolistBinding;
import com.example.plannerproject.databinding.ActivityTodolistBinding;

public class NormaltodolistActivity extends AppCompatActivity {

    private ActivityNormaltodolistBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNormaltodolistBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initializeDB(view);
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
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            onCreate(db);
        }
    }

    public void initializeDB(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        myHelper.onUpgrade(sqlDB,1,2);;
        sqlDB.close();
    }

    public void submitAssignment(View view){
        try {
            myDBHelper myHelper = new myDBHelper(this);
            SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
            String year = binding.deadYear.getText().toString();
            String month = binding.deadMonth.getText().toString();
            String day = binding.deadDay.getText().toString();
            if(year.length()!=4){
                Toast.makeText(this, "올바르지 않은 년도입니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(month.length()==1){
                month = "0"+month;
            }
            if(day.length()==1){
                day = "0"+day;
            }

            String subjectName = binding.addSubjectName.getText().toString();
            if(subjectName.length()<1){
                Toast.makeText(this, "등록할 일정 이름을 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }

            String datetime = year+"-"+month+"-"+day;
            sqlDB.execSQL("insert into todolist values ( '-' , '" + subjectName + "', '" + datetime + "');");
            sqlDB.close();
            Toast.makeText(getApplicationContext(), "new Time Inserted", Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e)
        {
            Toast.makeText(this, "잘못되었습니다..", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void deleteAssignment(View view){
        try {
            myDBHelper myHelper = new myDBHelper(this);
            SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
            String deleteAssignmentName = binding.deleteSubjectName.getText().toString();
            if(deleteAssignmentName.length()<1){
                Toast.makeText(this, "삭제할 일정 이름을 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }
            sqlDB.execSQL("delete from todolist where subject = '-' and todo = '"+ deleteAssignmentName + "';");
            sqlDB.close();
            Toast.makeText(getApplicationContext(), "delete " + deleteAssignmentName, Toast.LENGTH_LONG).show();
        }
        catch(SQLiteException e)
        {
            Toast.makeText(this, "잘못되었습니다..", Toast.LENGTH_SHORT).show();
            return;
        }
    }


}