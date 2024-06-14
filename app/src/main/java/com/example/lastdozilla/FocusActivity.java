package com.example.lastdozilla;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FocusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_focus);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation1);
        bottomNavigationView.setSelectedItemId(R.id.navigation_focus);

        bottomNavigationView.setOnItemReselectedListener(this::onNavigationItemSelected);
    }

    private void onNavigationItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.navigation_index) {

            Intent intent = new Intent(FocusActivity.this, AddtaskActivity.class);
            startActivity(intent);
            // Handle index navigation
            Toast.makeText(FocusActivity.this, "Index", Toast.LENGTH_SHORT).show();

        } else if (menuItem.getItemId() == R.id.navigation_calendar) {
            // Handle calendar navigation
            Toast.makeText(FocusActivity.this, "Calendar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FocusActivity.this, CalendarActivity.class);
            startActivity(intent);


        } else if (menuItem.getItemId() == R.id.navigation_focus) {
            // Handle focus navigation
            Toast.makeText(FocusActivity.this, "Focus", Toast.LENGTH_SHORT).show();


        } else if (menuItem.getItemId() == R.id.navigation_profile) {
            // Handle profile navigation
            Toast.makeText(FocusActivity.this, "Profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FocusActivity.this, ProfileActivity.class);
            startActivity(intent);


        }
    }}