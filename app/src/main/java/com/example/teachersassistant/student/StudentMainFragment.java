package com.example.teachersassistant.student;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.teachersassistant.R;
import com.example.teachersassistant.WelcomeScreen;
import com.example.teachersassistant.prevalent.SessionManager;
import com.example.teachersassistant.teacher.TeacherMain;

public class StudentMainFragment extends Fragment {

    ImageView timeTableImage, logout;

    StudentMainFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_main, container, false);


        timeTableImage = view.findViewById(R.id.timeTableImage);
        timeTableImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*StudentMain.fragmentManager.beginTransaction().replace(R.id.containerFrame,new TimeTableFragment()).addToBackStack(null).commit();*/
                TimeTableFragment tableFragment = new TimeTableFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.containerFrame,tableFragment,null);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(getContext(), SessionManager.SESSION_REMEMBERME);
                sessionManager.logoutUserFromSession();
                Intent intent = new Intent(getContext(), WelcomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;
    }
}