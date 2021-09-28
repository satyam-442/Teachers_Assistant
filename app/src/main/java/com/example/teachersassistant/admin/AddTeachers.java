package com.example.teachersassistant.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.example.teachersassistant.auth.StudentLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class AddTeachers extends AppCompatActivity {

    TextInputLayout teacherFirstName, teacherLastName, teacherPhone, teacherEmail, teacherGender,teacherClass,teacherGrade;
    EditText classTeacher, subjTeacher;
    Button addTeacherBtn;
    DatabaseReference teacherRef, classTeacherRef, allTeacherList;
    ProgressDialog loadingBar;
    LinearLayout classUpdate;
    Spinner classSpinner, gradeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);

        teacherRef = FirebaseDatabase.getInstance().getReference();
        //classTeacherRef = FirebaseDatabase.getInstance().getReference().child("ClassTeacher");
        allTeacherList = FirebaseDatabase.getInstance().getReference().child("AllTeachers");

        teacherFirstName = findViewById(R.id.teacherFirstNameLay);
        teacherLastName = findViewById(R.id.teacherLastNameLay);
        teacherPhone = findViewById(R.id.teacherPhoneLay);
        teacherEmail = findViewById(R.id.teacherEmailLay);
        teacherGender = findViewById(R.id.teacherGenderLay);
        teacherClass = findViewById(R.id.add_teacher_ClassLay);
        teacherGrade = findViewById(R.id.add_teacher_GradeLay);

        classSpinner = findViewById(R.id.add_teacher_class_spinner);
        gradeSpinner = findViewById(R.id.add_teacher_grade_spinner);

        classTeacher = findViewById(R.id.classTeacher);
        subjTeacher = findViewById(R.id.subjTeacher);
        classUpdate = findViewById(R.id.addTeacherClassUpdateLay);

        loadingBar = new ProgressDialog(this);

        addTeacherBtn = findViewById(R.id.addTeacherBtn);
        addTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classTeacher.getText().toString().equals("T") && subjTeacher.getText().toString().equals("T")){
                    String fname = teacherFirstName.getEditText().getText().toString();
                    String lname = teacherLastName.getEditText().getText().toString();
                    String fullname = fname+" "+lname;
                    String phone = teacherPhone.getEditText().getText().toString();
                    String email = teacherEmail.getEditText().getText().toString();
                    String gender = teacherGender.getEditText().getText().toString();
                    String classTeach = classTeacher.getText().toString();
                    String subjTeach = subjTeacher.getText().toString();
                    String classStd = teacherClass.getEditText().getText().toString();
                    String grade = teacherGrade.getEditText().getText().toString();
                    String password = "dummy";
                    String fnameStr = fname.substring(0,3).toUpperCase();
                    String lnameStr = lname.substring(0,3).toUpperCase();
                    String phoneStr = phone.substring(0,4);
                    String teacherId = fnameStr+lnameStr+phoneStr;
                    if (TextUtils.isEmpty(fname)||TextUtils.isEmpty(lname)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(email)||TextUtils.isEmpty(gender)){
                        Toast.makeText(getApplicationContext(), "Some field's are empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        loadingBar.setMessage("please wait...");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();

                        //CLASS TEACHER AND SUBJECT TEACHER
                        HashMap<String, Object> teacherMap = new HashMap<String, Object>();
                        teacherMap.put("FirstName",fname);
                        teacherMap.put("LastName",lname);
                        teacherMap.put("Email",email);
                        teacherMap.put("Phone",phone);
                        teacherMap.put("Gender",gender);
                        teacherMap.put("Class",classStd);
                        teacherMap.put("Grade",grade);
                        teacherMap.put("Password",password);
                        teacherMap.put("classTeacher",classTeach);
                        teacherMap.put("subjectTeacher",subjTeach);
                        teacherMap.put("image","default");
                        teacherMap.put("teacherID",teacherId);
                        teacherRef.child("Teachers").child("ClassTeacher").child(teacherId).updateChildren(teacherMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            /*if (classTeach.equals("T")){

                            }*/
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), SuccessAddPage.class);
                                    intent.putExtra("ID", teacherId);
                                    intent.putExtra("email",email);
                                    intent.putExtra("name",fname);
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
                        teacherRef.child("Teachers").child("SubjectTeacher").child(teacherId).updateChildren(teacherMap);

                        //ALL TEACHER LIST
                        HashMap<String, Object> allTeacherMap = new HashMap<String, Object>();
                        allTeacherMap.put("Name",fullname);
                        allTeacherMap.put("teacherID",teacherId);
                        allTeacherList.child(teacherId).updateChildren(allTeacherMap);
                    }
                }
                else if(subjTeacher.getText().toString().equals("T")){
                    String fname = teacherFirstName.getEditText().getText().toString();
                    String lname = teacherLastName.getEditText().getText().toString();
                    String fullname = fname+" "+lname;
                    String phone = teacherPhone.getEditText().getText().toString();
                    String email = teacherEmail.getEditText().getText().toString();
                    String gender = teacherGender.getEditText().getText().toString();
                    String classTeach = classTeacher.getText().toString();
                    String subjTeach = subjTeacher.getText().toString();
                    String classStd = teacherClass.getEditText().getText().toString();
                    String grade = teacherGrade.getEditText().getText().toString();
                    String fnameStr = fname.substring(0,3).toUpperCase();
                    String lnameStr = lname.substring(0,3).toUpperCase();
                    String phoneStr = phone.substring(0,4);
                    String teacherId = fnameStr+lnameStr+phoneStr;
                    String password = "dummy";
                    if (TextUtils.isEmpty(fname)||TextUtils.isEmpty(lname)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(email)||TextUtils.isEmpty(gender)){
                        Toast.makeText(getApplicationContext(), "Some field's are empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        loadingBar.setMessage("please wait...");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        HashMap<String, Object> teacherMap = new HashMap<String, Object>();
                        teacherMap.put("FirstName",fname);
                        teacherMap.put("LastName",lname);
                        teacherMap.put("Email",email);
                        teacherMap.put("Phone",phone);
                        teacherMap.put("Gender",gender);
                        teacherMap.put("Class",classStd);
                        teacherMap.put("Grade",grade);
                        teacherMap.put("Password",password);
                        teacherMap.put("classTeacher",classTeach);
                        teacherMap.put("subjectTeacher",subjTeach);
                        teacherMap.put("image","default");
                        teacherMap.put("teacherID",teacherId);
                        teacherRef.child("Teachers").child("SubjectTeacher").child(teacherId).updateChildren(teacherMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            /*if (classTeach.equals("T")){

                            }*/
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), SuccessAddPage.class);
                                    intent.putExtra("ID", teacherId);
                                    intent.putExtra("email",email);
                                    intent.putExtra("name",fname);
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
                        //ALL TEACHER LIST
                        HashMap<String, Object> allTeacherMap = new HashMap<String, Object>();
                        allTeacherMap.put("Name",fullname);
                        allTeacherMap.put("teacherID",teacherId);
                        allTeacherList.child(teacherId).updateChildren(allTeacherMap);
                    }
                }
                else {
                    String fname = teacherFirstName.getEditText().getText().toString();
                    String lname = teacherLastName.getEditText().getText().toString();
                    String fullname = fname+" "+lname;
                    String phone = teacherPhone.getEditText().getText().toString();
                    String email = teacherEmail.getEditText().getText().toString();
                    String gender = teacherGender.getEditText().getText().toString();
                    String classTeach = classTeacher.getText().toString();
                    String subjTeach = subjTeacher.getText().toString();
                    String classStd = teacherClass.getEditText().getText().toString();
                    String grade = teacherGrade.getEditText().getText().toString();
                    String fnameStr = fname.substring(0,3).toUpperCase();
                    String lnameStr = lname.substring(0,3).toUpperCase();
                    String phoneStr = phone.substring(0,4);
                    String teacherId = fnameStr+lnameStr+phoneStr;
                    String password = "dummy";
                    if (TextUtils.isEmpty(fname)||TextUtils.isEmpty(lname)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(email)||TextUtils.isEmpty(gender)){
                        Toast.makeText(getApplicationContext(), "Some field's are empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        loadingBar.setMessage("please wait...");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        HashMap<String, Object> teacherMap = new HashMap<String, Object>();
                        teacherMap.put("FirstName",fname);
                        teacherMap.put("LastName",lname);
                        teacherMap.put("Email",email);
                        teacherMap.put("Phone",phone);
                        teacherMap.put("Gender",gender);
                        teacherMap.put("Class",classStd);
                        teacherMap.put("Grade",grade);
                        teacherMap.put("Password",password);
                        teacherMap.put("classTeacher",classTeach);
                        teacherMap.put("subjectTeacher",subjTeach);
                        teacherMap.put("image","default");
                        teacherMap.put("teacherID",teacherId);
                        teacherRef.child("Teachers").child("SubjectTeacher").child(teacherId).updateChildren(teacherMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            /*if (classTeach.equals("T")){

                            }*/
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), SuccessAddPage.class);
                                    intent.putExtra("ID", teacherId);
                                    intent.putExtra("email",email);
                                    intent.putExtra("name",fname);
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

                        //ALL TEACHER LIST
                        HashMap<String, Object> allTeacherMap = new HashMap<String, Object>();
                        allTeacherMap.put("Name",fullname);
                        allTeacherMap.put("teacherID",teacherId);
                        allTeacherList.child(teacherId).updateChildren(allTeacherMap);
                    }
                }
            }
        });

        //SPINNER CODE FOR CLASS AND GRADE
        ArrayAdapter<CharSequence> adapter4Class = ArrayAdapter.createFromResource(this, R.array.class_array, android.R.layout.simple_spinner_item);
        adapter4Class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter4Class);
        classSpinner.setOnItemSelectedListener(new ClassSpinner());

        ArrayAdapter<CharSequence> adapter4Grade = ArrayAdapter.createFromResource(this, R.array.grade_array, android.R.layout.simple_spinner_item);
        adapter4Grade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter4Grade);
        gradeSpinner.setOnItemSelectedListener(new GradeSpinner());
    }

    //CLEAR FORM
    public void clearForm(View view) {
        teacherFirstName.getEditText().getText().clear();
        teacherLastName.getEditText().getText().clear();
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
                    subjTeacher.setText("T");
                    classUpdate.setVisibility(View.VISIBLE);
                } else{
                    classTeacher.setText("F");
                    subjTeacher.setText("F");
                    classUpdate.setVisibility(View.GONE);
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

    private class ClassSpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            teacherClass.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    }

    private class GradeSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            teacherGrade.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}