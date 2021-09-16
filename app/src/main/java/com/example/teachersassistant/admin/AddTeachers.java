package com.example.teachersassistant.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class AddTeachers extends AppCompatActivity {

    TextInputLayout teacherName, teacherPhone, teacherEmail, teacherGender;
    EditText classTeacher, subjTeacher;
    Button addTeacherBtn;
    DatabaseReference teacherRef;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);

        teacherRef = FirebaseDatabase.getInstance().getReference().child("Teachers");

        teacherName = findViewById(R.id.teacherNameLay);
        teacherPhone = findViewById(R.id.teacherPhoneLay);
        teacherEmail = findViewById(R.id.teacherEmailLay);
        teacherGender = findViewById(R.id.teacherGenderLay);

        classTeacher = findViewById(R.id.classTeacher);
        subjTeacher = findViewById(R.id.subjTeacher);

        loadingBar = new ProgressDialog(this);

        addTeacherBtn = findViewById(R.id.addTeacherBtn);
        addTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacherId = generateString(7);
                String name = teacherName.getEditText().getText().toString();
                String phone = teacherPhone.getEditText().getText().toString();
                String email = teacherEmail.getEditText().getText().toString();
                String gender = teacherGender.getEditText().getText().toString();
                String classTeach = classTeacher.getText().toString();
                String subjTeach = subjTeacher.getText().toString();
                String password = "dummy";
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(email)||TextUtils.isEmpty(gender)){
                    Toast.makeText(getApplicationContext(), "Some field's are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    HashMap<String, Object> teacherMap = new HashMap<String, Object>();
                    teacherMap.put("Name",name);
                    teacherMap.put("Email",email);
                    teacherMap.put("Phone",phone);
                    teacherMap.put("Gender",gender);
                    teacherMap.put("Password",password);
                    teacherMap.put("classTeacher",classTeach);
                    teacherMap.put("subjectTeacher",subjTeach);
                    teacherMap.put("image","default");
                    teacherMap.put("teacherID",teacherId);

                    teacherRef.child(teacherId).updateChildren(teacherMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), SuccessAddPage.class);
                                intent.putExtra("ID", teacherId);
                                intent.putExtra("email",email);
                                intent.putExtra("name",name);
                                startActivity(intent);
                                loadingBar.dismiss();
                            }
                            else {
                                loadingBar.dismiss();
                                String msg = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    //CLEAR FORM
    public void clearForm(View view) {
        teacherName.getEditText().getText().clear();
        teacherPhone.getEditText().getText().clear();
        teacherEmail.getEditText().getText().clear();
        teacherGender.getEditText().getText().clear();
        classTeacher.getText().clear();
        subjTeacher.getText().clear();
    }

    //FOR GENDER
    public void onRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioMale:
                if (checked)
                    teacherGender.getEditText().setText("Male");
                break;
            case R.id.radioFemale:
                if (checked)
                    teacherGender.getEditText().setText("Female");
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_class_teacher:
                if (checked) {
                    classTeacher.setText("T");
                } else{
                    classTeacher.setText("F");
                }
                    break;
            case R.id.checkbox_subject_teacher:
                if (checked) {
                    subjTeacher.setText("T");
                } else{
                    subjTeacher.setText("F");
                }
                    break;
        }
    }

    private String generateString(int lenght) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < lenght; i++) {
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}














