package com.example.teachersassistant.student;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teachersassistant.R;

public class StudentMain extends AppCompatActivity {

    ImageView timeTableImage;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.containerFrame) != null){
            if (savedInstanceState != null){
                return;
            }
            StudentMainFragment mainFragment = new StudentMainFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.containerFrame,mainFragment,null);
            fragmentTransaction.commit();
        }
    }
    /*@Override
    public void onBackPressed() {
        if (isPressed){
            finishAffinity();
            System.exit(0);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please click back again to exit", Toast.LENGTH_SHORT).show();
            isPressed = true;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                isPressed = false;
            }
        };
        new Handler().postDelayed(runnable, 2000);
    }*/
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).create().show();
    }
}