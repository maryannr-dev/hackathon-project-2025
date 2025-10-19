package com.example.hackathon2025;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hackathon2025.models.Budget;
import com.example.hackathon2025.models.BudgetLimitCategory;
import com.example.hackathon2025.models.Transaction;
import com.example.hackathon2025.models.UserItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.Serializable;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment dashboardFragment;
    private final Fragment budgetFragment = new BudgetFragment();
    private final Fragment profileFragment = new FragmentProfile();
    private Fragment active;



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
        dashboardFragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        UserItem user = (UserItem) getIntent().getSerializableExtra("USER_DATA");
        if (user != null) {
            bundle.putSerializable("USER_DATA", user);
            dashboardFragment.setArguments(bundle);
        }
        dashboardFragment.setArguments(bundle);
        budgetFragment.setArguments(bundle);
        profileFragment.setArguments(bundle);

        active = dashboardFragment;

        fm.beginTransaction().add(R.id.fragment_container, profileFragment, "3").hide(profileFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, budgetFragment, "2").hide(budgetFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, dashboardFragment, "1").commit();


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                fm.beginTransaction().hide(active).show(dashboardFragment).commit();
                dashboardFragment.setArguments(bundle);
                active = dashboardFragment;
                return true;
            } else if (itemId == R.id.budgetNav) {
                fm.beginTransaction().hide(active).show(budgetFragment).commit();
                budgetFragment.setArguments(bundle);
                active = budgetFragment;
                return true;
            } else if (itemId == R.id.profile) {
                fm.beginTransaction().hide(active).show(profileFragment).commit();
                profileFragment.setArguments(bundle);
                active = profileFragment;
                return true;
            }
            return false;

        });
    }
}


