package com.example.teachersassistant.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.example.teachersassistant.WelcomeScreen;
import com.example.teachersassistant.prevalent.SessionManager;
import com.google.android.material.navigation.NavigationView;

public class StudentMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView timeTableImage;
    public static FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        this.setTitle("Home");
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.containerFrame) != null) {
            if (savedInstanceState != null) {
                return;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            StudentMainFragment studentMainFragment = new StudentMainFragment();
            transaction.add(R.id.containerFrame, studentMainFragment, null);
            transaction.commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.home:
                StudentMainFragment mainFragment = new StudentMainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.containerFrame, mainFragment).commit();
                navigationView.setCheckedItem(R.id.home);
                this.setTitle("Home");
                break;
            case R.id.assignment:
                AssignmenttFragment assignmentFragment = new AssignmenttFragment();
                fragmentTransaction.replace(R.id.containerFrame, assignmentFragment).commit();
                navigationView.setCheckedItem(R.id.assignment);
                this.setTitle("Assignment");
                break;
            case R.id.time_table:
                TimeTableFragment timetableFragment = new TimeTableFragment();
                fragmentTransaction.replace(R.id.containerFrame, timetableFragment).commit();
                navigationView.setCheckedItem(R.id.time_table);
                this.setTitle("Time Table");
                break;
            case R.id.result:
                ResultFragment resultFragment = new ResultFragment();
                fragmentTransaction.replace(R.id.containerFrame, resultFragment).commit();
                navigationView.setCheckedItem(R.id.result);
                this.setTitle("Result");
                break;
            case R.id.about_us:
                AboutUsFragment aboutusFragment = new AboutUsFragment();
                fragmentTransaction.replace(R.id.containerFrame, aboutusFragment).commit();
                navigationView.setCheckedItem(R.id.about_us);
                this.setTitle("About Us");
                break;
            case R.id.logout:
                SessionManager sessionManager = new SessionManager(getApplicationContext(), SessionManager.SESSION_REMEMBERME);
                sessionManager.logoutUserFromSession();
                Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /*@Override
    public void onItemSelected(int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (position == POS_HOME){
            StudentMainFragment mainFragment = new StudentMainFragment();
            fragmentTransaction.replace(R.id.containerFrame,mainFragment);
        } else if (position == POS_ASSIGNMENTS){
           AssignmenttFragment mainFragment = new AssignmenttFragment();
            fragmentTransaction.replace(R.id.containerFrame,mainFragment);
        }else if (position == POS_TIME_TABLE){
            TimeTableFragment mainFragment = new TimeTableFragment();
            fragmentTransaction.replace(R.id.containerFrame,mainFragment);
        }else if (position == POS_RESULT){
            ResultFragment mainFragment = new ResultFragment();
            fragmentTransaction.replace(R.id.containerFrame,mainFragment);
        }else if (position == POS_ABOUT_US){
            AboutUsFragment mainFragment = new AboutUsFragment();
            fragmentTransaction.replace(R.id.containerFrame,mainFragment);
        }else if (position == POS_LOGOUT){
            finish();
        }
    }*/
}