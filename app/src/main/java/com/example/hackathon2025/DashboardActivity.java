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
    LinearProgressIndicator weeklyTotalProgress;
    LinearProgressIndicator weeklyWantsProgress;
    LinearProgressIndicator weeklyCategoryProgress1;
    LinearProgressIndicator weeklyCategoryProgress2;
    TextView spendingCategoryLabel;
    TextView spendingCategoryLabel2;
    TextView amountSpent;
    UserItem user;


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
                intent.putExtra("USER_DATA", (Serializable) user);
                startActivity(intent);
                return true;
            }
            else if(item.getItemId() == R.id.learnNav){
                Toast.makeText(DashboardActivity.this,"Under Construction",Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(item.getItemId() == R.id.profile){
                intent = new Intent(DashboardActivity.this,ProfileActivity.class);
                intent.putExtra("USER_DATA", (Serializable) user);
                startActivity(intent);
                return true;

            }
            return false;

        });

        Intent intent = getIntent();
        user = (UserItem) intent.getSerializableExtra("USER_DATA");


        weeklyTotalProgress = findViewById(R.id.housingProgress);
        weeklyWantsProgress = findViewById(R.id.transportationProgress);
        weeklyCategoryProgress1 = findViewById(R.id.weeklyCategoryProgress1);
        weeklyCategoryProgress2 = findViewById(R.id.weeklyCategoryProgress2);
        spendingCategoryLabel = findViewById(R.id.textViewSpending1);
        spendingCategoryLabel2 = findViewById(R.id.textViewSpending2);
        amountSpent = findViewById(R.id.textViewAmountSpent);

        weeklyTotalProgress.setMax(user.getBudget().getTotalBudget());
        weeklyTotalProgress.setProgress(user.getBudget().getTotalSpent(),false);
        weeklyWantsProgress.setMax(user.getBudget().getWantsBudget());
        weeklyWantsProgress.setProgress(user.getBudget().getWantsSpent(),false);

        List<BudgetLimitCategory> categories = user.getBudget().getCategoryList();

        BudgetLimitCategory highestSpendCategory = categories.get(0);
        BudgetLimitCategory secondHighestSpendCategory = categories.get(1);
        for(BudgetLimitCategory cat : categories){
            if(cat.getAmountSpent() > highestSpendCategory.getAmountSpent()){
                secondHighestSpendCategory = highestSpendCategory;
                highestSpendCategory = cat;
            }
            else if(cat.getAmountSpent() > secondHighestSpendCategory.getAmountSpent()){
                secondHighestSpendCategory = cat;
            }
        }

        spendingCategoryLabel.setText(highestSpendCategory.getCategoryName());
        spendingCategoryLabel2.setText(secondHighestSpendCategory.getCategoryName());

        weeklyCategoryProgress1.setMax((int) highestSpendCategory.getLimitAmount());
        weeklyCategoryProgress1.setProgress((int) highestSpendCategory.getAmountSpent(),false);
        weeklyCategoryProgress2.setMax((int) secondHighestSpendCategory.getLimitAmount());
        weeklyCategoryProgress2.setProgress((int) secondHighestSpendCategory.getAmountSpent(),false);
        amountSpent.setText("You've spent $" + user.getBudget().getTotalSpent() + " overall");


    }


}