package com.example.teachersassistant.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.example.teachersassistant.teacher.TeacherMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StudentLogin extends AppCompatActivity{

    LinearLayout loginLO, registerLO;
    TextInputLayout studentGender,studentClass, studentGrade, studentTeacherList;
    Spinner classSpinner, gradeSpinner, teacherSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        loginLO = findViewById(R.id.loginLayout);
        registerLO = findViewById(R.id.registerLayout);
        studentGender = findViewById(R.id.teacherGenderLay);
        studentClass = findViewById(R.id.studentClassLay);
        studentGrade = findViewById(R.id.studentGradeLay);
        studentTeacherList = findViewById(R.id.studentTeacherLay);
        classSpinner = findViewById(R.id.class_spinner);
        gradeSpinner = findViewById(R.id.grade_spinner);
        teacherSpinner = findViewById(R.id.teacher_spinner);

        ArrayAdapter<CharSequence> adapter4Class = ArrayAdapter.createFromResource(this, R.array.class_array, android.R.layout.simple_spinner_item);
        adapter4Class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter4Class);
        classSpinner.setOnItemSelectedListener(new ClassSpinner());

        ArrayAdapter<CharSequence> adapter4Grade = ArrayAdapter.createFromResource(this, R.array.grade_array, android.R.layout.simple_spinner_item);
        adapter4Grade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter4Grade);
        gradeSpinner.setOnItemSelectedListener(new GradeSpinner());

        ArrayAdapter<CharSequence> adapter4Teacher = ArrayAdapter.createFromResource(this, R.array.teacher_array, android.R.layout.simple_spinner_item);
        adapter4Teacher.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(adapter4Teacher);
        teacherSpinner.setOnItemSelectedListener(new TeacherStudentList());
    }

    public void loginLayoutVisibility(View view) {
        loginLO.setVisibility(View.VISIBLE);
        registerLO.setVisibility(View.GONE);
    }

    public void registerLayoutVisibility(View view) {
        registerLO.setVisibility(View.VISIBLE);
        loginLO.setVisibility(View.GONE);
    }

    public void onRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioMale:
                if (checked)
                    studentGender.getEditText().setText("Male");
                break;
            case R.id.radioFemale:
                if (checked)
                    studentGender.getEditText().setText("Female");
                break;
        }
    }

    public void openBottomSheet(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_table);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }

    private class ClassSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            studentClass.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class GradeSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            studentGrade.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class TeacherStudentList implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            studentTeacherList.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}