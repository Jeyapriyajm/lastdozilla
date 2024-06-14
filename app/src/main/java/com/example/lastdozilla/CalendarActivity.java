package com.example.lastdozilla;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_calendar);

        bottomNavigationView.setOnItemReselectedListener(this::onNavigationItemSelected);
    }



    private boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.navigation_index) {

            Intent intent = new Intent(CalendarActivity.this, AddtaskActivity.class);
            startActivity(intent);
            // Handle index navigation
            Toast.makeText(CalendarActivity.this,  "Index", Toast.LENGTH_SHORT).show();
            return true;
        } else if (menuItem.getItemId() == R.id.navigation_calendar) {

            // Open CalendarActivity when calendar item is clicked

            return true;
        } else if (menuItem.getItemId() == R.id.navigation_focus) {
            // Open FocusActivity when focus item is clicked
            Intent intent = new Intent(CalendarActivity.this, FocusActivity.class);
            startActivity(intent);
            // Handle focus navigation
            Toast.makeText(CalendarActivity.this,  "Focus", Toast.LENGTH_SHORT).show();
            return true;
        } else if (menuItem.getItemId() == R.id.navigation_profile) {
            // Open ProfileActivity when profile item is clicked
            Intent intent = new Intent(CalendarActivity.this, ProfileActivity.class);
            startActivity(intent);
            // Handle profile navigation
            Toast.makeText(CalendarActivity.this, "Profile", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    };
    }

