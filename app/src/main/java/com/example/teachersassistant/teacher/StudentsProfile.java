package com.example.teachersassistant.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.teachersassistant.R;

public class StudentsProfile extends AppCompatActivity {

    TextView tv6;
    String visit_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_profile);

        //tv6 = findViewById(R.id.textView6);
        //visit_id = getIntent().getExtras().get("visit_user_id").toString();
        //tv6.setText(visit_id);
    }
}