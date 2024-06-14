package com.example.lastdozilla;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardActivity extends AppCompatActivity {

    private FloatingActionButton fabAddTask;
    private ImageView emptyImage;
    private TextView emptyText, emptySubtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fabAddTask = findViewById(R.id.fab_add_task);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_index);

        bottomNavigationView.setOnItemReselectedListener(this::onNavigationItemReselected);



        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open activity to add a new task
                Intent intent = new Intent(DashboardActivity.this, AddtaskActivity.class);
                startActivity(intent);
            }
        });



        }

    private boolean onNavigationItemReselected(MenuItem item) {
        if (item.getItemId() == R.id.navigation_index) {
            // Handle index navigation
            Toast.makeText(DashboardActivity.this,  "Index", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.navigation_calendar) {

            // Open CalendarActivity when calendar item is clicked
            Intent intent = new Intent(DashboardActivity.this, CalendarActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.navigation_focus) {
            // Open FocusActivity when focus item is clicked
            Intent intent = new Intent(DashboardActivity.this, FocusActivity.class);
            startActivity(intent);
            // Handle focus navigation
            Toast.makeText(DashboardActivity.this,  "Focus", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.navigation_profile) {
            // Open ProfileActivity when profile item is clicked
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
            // Handle profile navigation
            Toast.makeText(DashboardActivity.this, "Profile", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    };




}
