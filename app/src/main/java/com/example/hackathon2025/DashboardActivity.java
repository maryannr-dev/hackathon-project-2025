package com.example.hackathon2025;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    LinearProgressIndicator weeklyTotalProgress;
    LinearProgressIndicator weeklyWantsProgress;
    LinearProgressIndicator weeklyCategoryProgress1;
    LinearProgressIndicator weeklyCategoryProgress2;
    TextView spendingCategoryLabel;
    TextView spendingCategoryLabel2;
    TextView amountSpent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            if(item.getItemId() == R.id.budgetNav){
                intent = new Intent(DashboardActivity.this, BudgetActivity.class);
                startActivity(intent);
                return true;
            }
            else if(item.getItemId() == R.id.learnNav){
                intent = new Intent(DashboardActivity.this,LearnActivity.class);
                startActivity(intent);
                return true;
            }
            else if(item.getItemId() == R.id.profile){
                intent = new Intent(DashboardActivity.this,ProfileActivity.class);
                startActivity(intent);
                return true;

            }
            return false;

        });

    }
}