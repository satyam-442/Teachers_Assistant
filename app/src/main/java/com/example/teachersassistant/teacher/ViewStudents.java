package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.teachersassistant.R;
import com.example.teachersassistant.modal.ClassTeacher;
import com.example.teachersassistant.modal.Students;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ViewStudents extends AppCompatActivity {

    RecyclerView viewStudentRecList;
    DatabaseReference teacherRef;
    TextInputLayout requestViewStudent;
    String teacherID, classStdGrade;
    ProgressDialog loadingBar;
    DatabaseReference reqRef, studentsRef, teacherStudentRef, removeStudRef, rejectedStudRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        loadingBar = new ProgressDialog(this);

        teacherID = getIntent().getExtras().get("teacherID").toString();
        classStdGrade = getIntent().getExtras().get("classStd").toString();

        reqRef = FirebaseDatabase.getInstance().getReference();
        teacherStudentRef = FirebaseDatabase.getInstance().getReference();
        studentsRef = FirebaseDatabase.getInstance().getReference();
        removeStudRef = FirebaseDatabase.getInstance().getReference();

        rejectedStudRef = FirebaseDatabase.getInstance().getReference();

        viewStudentRecList = findViewById(R.id.viewStudent);
        viewStudentRecList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewStudentRecList.setLayoutManager(linearLayoutManager);

        requestViewStudent = findViewById(R.id.requestViewText);

        teacherRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("ClassTeacher").child(teacherID);
        startListen();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadingBar.setMessage("please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        startListen();
    }

    private void startListen() {
        Query query = FirebaseDatabase.getInstance().getReference().child("TeachersClass").child(teacherID).child("Students").limitToLast(50);
        FirebaseRecyclerOptions<Students> options = new FirebaseRecyclerOptions.Builder<Students>().setQuery(query, Students.class).build();
        FirebaseRecyclerAdapter<Students, viewStudentsViewHolder> adapter = new FirebaseRecyclerAdapter<Students, viewStudentsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewStudentsViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Students model) {
                //final String PostKey = getRef(position).getKey();
                holder.setFirstName(model.getFirstName());
                holder.setLastName(model.getLastName());
                holder.setClass(model.getClassStd());
                holder.setGrade(model.getGrade());
                holder.setStudentID(model.getStudentID());
                //holder.setTeacherName(model.getTeacher());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String studentID = model.getStudentID();
                        Intent intent = new Intent(ViewStudents.this,StudentsProfile.class);
                        intent.putExtra("studID",studentID);
                        intent.putExtra("teacherID",teacherID);
                        startActivity(intent);
                    }
                });
                loadingBar.dismiss();
            }

            @NonNull
            @Override
            public viewStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_student_view, parent, false);
                return new viewStudentsViewHolder(view);
            }
        };
        viewStudentRecList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class viewStudentsViewHolder extends RecyclerView.ViewHolder {
        Button acceptBtn, rejectBtn;
        public viewStudentsViewHolder(@NonNull View itemView) {
            super(itemView);
            //mView = itemView;
            /*acceptBtn = itemView.findViewById(R.id.studRqstAcceptBtn);
            rejectBtn = itemView.findViewById(R.id.studRqstRejectBtn);*/
        }

        public void setFirstName(String fname) {
            TextView firstname = (TextView) itemView.findViewById(R.id.studentFirstName);
            firstname.setText("FirstName: " + fname);
        }

        public void setLastName(String lname) {
            TextView lastname = (TextView) itemView.findViewById(R.id.studentLastName);
            lastname.setText("LastName: " + lname);
        }

        public void setClass(String classStd) {
            TextView std = (TextView) itemView.findViewById(R.id.studentClass);
            std.setText("Class: " + classStd);
        }

        public void setGrade(String grade) {
            TextView studGrade = (TextView) itemView.findViewById(R.id.studentGrade);
            studGrade.setText("Grade: " + grade);
        }

        public void setStudentID(String studID) {
            TextView stdID = (TextView) itemView.findViewById(R.id.studentStudID);
            stdID.setText("StudentID: " + studID);
        }

        /*public void setImagee(Context ctx, String image)
        {
            CircleImageView donorimage = (CircleImageView) mView.findViewById(R.id.donor_profile_image);
            Picasso.with(ctx).load(image).into(donorimage);
        }*/
    }
}