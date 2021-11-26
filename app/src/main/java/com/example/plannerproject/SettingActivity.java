package com.example.plannerproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    String[] day = {"월요일", "화요일", "수요일", "목요일", "금요일"};
    String[] times = {"1교시", "2교시", "3교시", "4교시", "5교시", "6교시", "7교시", "8교시", "9교시", "10교시"};

    public static final String Subject_DB = "Subject_DB";
    public static final String Subject_Table = "SubjectTable";
    public static ArrayList<String> attention = new ArrayList<String>();

    
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
            db.execSQL("CREATE table if not exists SubjectTable (subject VARCHAR(45), professor VARCHAR(45));");
            db.execSQL("CREATE table if not exists timeTable (subject VARCHAR(45), day VARCHAR(45), time INT);");
            Toast.makeText(getApplicationContext(),"Initialized a",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//            db.execSQL("drop table if exists "+ Subject_Table);
            onCreate(db);
            Toast.makeText(getApplicationContext(),"Initialized b",Toast.LENGTH_LONG).show();
        }
    }

    public void initializeDB(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        myHelper.onUpgrade(sqlDB,1,2);;
        sqlDB.close();
        Toast.makeText(getApplicationContext(),"Initialized",Toast.LENGTH_LONG).show();
    }

    public void addNewSubject(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL("insert into "+Subject_Table+ " values ('"+ binding.addSubjectName.getText().toString() + "', '" + binding.addProfessorName.getText().toString()+"');" );
        sqlDB.close();
        Toast.makeText(getApplicationContext(),"new subject Inserted", Toast.LENGTH_LONG).show();
    }

    public void addTimeSubject(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL("insert into timeTable values ('"+ binding.addTimeSubjectName.getText().toString() + "', '" + binding.subjectDaySpinner.getSelectedItem().toString()+ "', " + binding.subjectTimeSpinner.getSelectedItem().toString()+");" );
        sqlDB.close();
        Toast.makeText(getApplicationContext(),"new Time Inserted", Toast.LENGTH_LONG).show();
    }

    public void searchDB(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        Cursor cursor;
        Cursor cursor2;
        cursor = sqlDB.rawQuery("select * from "+ Subject_Table, null);
        String string1 = "subject" + System.lineSeparator();
        String string2 = "professor" + System.lineSeparator();
        while(cursor.moveToNext()){
            string1 += cursor.getString(0)+ System.lineSeparator();
            string2 += cursor.getString(1)+ System.lineSeparator();
        }
        cursor2 = sqlDB.rawQuery("select * from timeTable", null);
        while(cursor2.moveToNext()){
            string1 += cursor2.getString(0)+ System.lineSeparator();
            string2 += cursor2.getString(1);
            string2 += cursor2.getString(2)+ System.lineSeparator();
        }
        binding.temp1.setText(string1);
        binding.temp2.setText(string2);

        cursor.close();
        sqlDB.close();

    }

    public void deleteSubject(View view){
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB = myHelper.getWritableDatabase();
        sqlDB.execSQL("delete from "+Subject_Table+ " where subject = '" + binding.deleteSubjectName.getText().toString() +"'; " );
        sqlDB.execSQL("delete from timeTable where subject = '" + binding.deleteSubjectName.getText().toString() +"'; " );
        sqlDB.close();
        Toast.makeText(getApplicationContext(),"delete "+binding.deleteSubjectName.getText().toString(), Toast.LENGTH_LONG).show();
    }



}