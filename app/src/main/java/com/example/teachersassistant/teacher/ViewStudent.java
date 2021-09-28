package com.example.teachersassistant.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teachersassistant.R;
import com.example.teachersassistant.auth.TeachersLogin;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ViewStudent extends AppCompatActivity {

    String teacherID,classStd;
    ImageView viewStudent, viewStudentRqst;
    TextView viewStudentTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ViewStudent.this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_students);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        teacherID = getIntent().getExtras().get("teacherID").toString();
        classStd = getIntent().getExtras().get("classStd").toString();

        viewStudentTV = bottomSheetDialog.findViewById(R.id.viewStudentTextView);
        viewStudent = findViewById(R.id.viewStudentImage);
        viewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStudentTV.setText("View Student");
                bottomSheetDialog.show();
            }
        });

        viewStudentRqst = findViewById(R.id.viewStudentRequestImage);
    }

    public void goToViewRequest(View view) {
        Intent intent = new Intent(this,ViewStudentsRequest.class);
        intent.putExtra("teacherID",teacherID);
        intent.putExtra("classStd",classStd);
        startActivity(intent);
    }
}