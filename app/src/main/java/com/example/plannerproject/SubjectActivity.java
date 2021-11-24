package com.example.plannerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.plannerproject.databinding.ActivityMainBinding;
import com.example.plannerproject.databinding.ActivitySubjectBinding;

public class SubjectActivity extends AppCompatActivity {

    private ActivitySubjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubjectBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Bundle extras =getIntent().getExtras();
        if(extras==null){
            return;
        }


    }


}