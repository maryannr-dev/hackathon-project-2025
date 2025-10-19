package com.example.hackathon2025;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hackathon2025.models.BudgetLimitCategory;
import com.example.hackathon2025.models.UserItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;



public class DashboardFragment extends Fragment {
    private UserItem currentUser;
    public DashboardFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            currentUser = (UserItem) getArguments().getSerializable("USER_DATA");
        }

    }
    UserItem user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        assert getArguments() != null;
        user = (UserItem) getArguments().getSerializable("USER_DATA");


        return view;
    }


    LinearProgressIndicator weeklyTotalProgress;
    LinearProgressIndicator weeklyWantsProgress;
    LinearProgressIndicator weeklyCategoryProgress1;
    LinearProgressIndicator weeklyCategoryProgress2;
    TextView spendingCategoryLabel;
    TextView spendingCategoryLabel2;
    TextView amountSpent;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        weeklyTotalProgress = view.findViewById(R.id.housingProgress);
        weeklyWantsProgress = view.findViewById(R.id.transportationProgress);
        weeklyCategoryProgress1 = view.findViewById(R.id.weeklyCategoryProgress1);
        weeklyCategoryProgress2 = view.findViewById(R.id.weeklyCategoryProgress2);
        spendingCategoryLabel = view.findViewById(R.id.textViewSpending1);
        spendingCategoryLabel2 = view.findViewById(R.id.textViewSpending2);
        amountSpent = view.findViewById(R.id.textViewAmountSpent);

        if(user != null){
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
        else{
            weeklyTotalProgress.setMax(0);
            weeklyTotalProgress.setProgress(0,false);
            weeklyWantsProgress.setMax(0);
            weeklyWantsProgress.setProgress(0,false);
            spendingCategoryLabel.setText("No data available");
            spendingCategoryLabel2.setText("No data available");
            weeklyCategoryProgress1.setMax(0);
            weeklyCategoryProgress1.setProgress(0,false);
            weeklyCategoryProgress2.setMax(0);
            weeklyCategoryProgress2.setProgress(0,false);
            amountSpent.setText("No data available");
        }









    }
    }
