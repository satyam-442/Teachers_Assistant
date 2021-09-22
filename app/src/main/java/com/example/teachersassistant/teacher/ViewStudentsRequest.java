package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teachersassistant.R;
import com.example.teachersassistant.modal.ClassTeacher;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewStudentsRequest extends AppCompatActivity {

    RecyclerView viewRequestRecList;
    DatabaseReference teacherRef;
    TextInputLayout requestViewStudent;
    String teacherID, classStdGrade;
    ProgressDialog loadingBar;
    DatabaseReference reqRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students_request);

        loadingBar = new ProgressDialog(this);

        teacherID = getIntent().getExtras().get("teacherID").toString();
        classStdGrade = getIntent().getExtras().get("classStd").toString();

        reqRef = FirebaseDatabase.getInstance().getReference().child("StudRequest");

        viewRequestRecList = findViewById(R.id.viewStudentRequest);
        viewRequestRecList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        viewRequestRecList.setLayoutManager(linearLayoutManager);

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
        String stdgrade = requestViewStudent.getEditText().getText().toString();
        Query query = FirebaseDatabase.getInstance().getReference().child("StudRequest").child(classStdGrade).limitToLast(50);
        //Query query = reqRef.child(stdgrade).limitToLast(50);
        FirebaseRecyclerOptions<ClassTeacher> options = new FirebaseRecyclerOptions.Builder<ClassTeacher>().setQuery(query, ClassTeacher.class).build();
        FirebaseRecyclerAdapter<ClassTeacher,DonorsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ClassTeacher, DonorsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(DonorsViewHolder holder, int position, ClassTeacher model) {
                //final String PostKey = getRef(position).getKey();
                holder.setFirstName(model.getFirstName());
                holder.setLastName(model.getLastName());
                holder.setClass(model.getClassStd());
                holder.setGrade(model.getGrade());
                holder.setStudentID(model.getStudentID());
                holder.setTeacherName(model.getTeacher());
                loadingBar.dismiss();
                /*holder.mView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String visit_user_id = getRef(position).getKey();
                        Intent teacherProfileIntent = new Intent(ViewStudentsRequest.this,StudentsProfile.class);
                        //teacherProfileIntent.putExtra("visit_user_id",visit_user_id);
                        startActivity(teacherProfileIntent);
                    }
                });*/
            }

            @NonNull
            @Override
            public DonorsViewHolder onCreateViewHolder(ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_student_request, parent, false);
                return new DonorsViewHolder(view);
            }

        };
        viewRequestRecList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    /*And this is the static class*/
    public static class DonorsViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public DonorsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setFirstName(String fname) {
            TextView firstname = (TextView) mView.findViewById(R.id.studentFirstName);
            firstname.setText("FirstName: "+fname);
        }

        public void setLastName(String lname) {
            TextView lastname = (TextView) mView.findViewById(R.id.studentLastName);
            lastname.setText("LastName: "+lname);
        }

        /*public void setImagee(Context ctx, String image)
        {
            CircleImageView donorimage = (CircleImageView) mView.findViewById(R.id.donor_profile_image);
            Picasso.with(ctx).load(image).into(donorimage);
        }*/

        public void setClass(String classStd) {
            TextView std = (TextView) mView.findViewById(R.id.studentClass);
            std.setText("Class: "+classStd);
        }

        public void setGrade(String grade) {
            TextView studGrade = (TextView) mView.findViewById(R.id.studentGrade);
            studGrade.setText("Grade: "+grade);
        }

        public void setStudentID(String studID) {
            TextView stdID = (TextView) mView.findViewById(R.id.studentStudID);
            stdID.setText("StudentID: "+studID);
        }

        public void setTeacherName(String teacherName) {
            TextView teachName = (TextView) mView.findViewById(R.id.studentTeacherName);
            teachName.setText("Teacher: "+teacherName);
        }
    }
}