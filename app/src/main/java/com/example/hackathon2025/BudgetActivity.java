package com.example.hackathon2025;

import static java.time.LocalDate.now;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hackathon2025.models.Badges;
import com.example.hackathon2025.models.Budget;
import com.example.hackathon2025.models.BudgetLimitCategory;
import com.example.hackathon2025.models.Transaction;
import com.example.hackathon2025.models.UserItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class BudgetActivity extends AppCompatActivity {
    UserItem user;
    LinearProgressIndicator housingProgress;
    LinearProgressIndicator transportationProgress;
    LinearProgressIndicator foodProgress;
    LinearProgressIndicator medicalProgress;
    LinearProgressIndicator personalProgress;
    LinearProgressIndicator entertainmentProgress;
    MaterialButton editCategoryLimits;
    ListView transactionListView;
    FloatingActionButton transactionButton;
    BottomNavigationView bottomNavigationView;
    TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        user = (UserItem) getIntent().getSerializableExtra("USER_DATA");
        housingProgress = findViewById(R.id.housingProgress);
        transportationProgress = findViewById(R.id.transportationProgress);
        foodProgress = findViewById(R.id.foodProgress);
        medicalProgress = findViewById(R.id.medicalProgress);
        personalProgress = findViewById(R.id.personalProgress);
        entertainmentProgress = findViewById(R.id.entertainmentProgress);
        editCategoryLimits = findViewById(R.id.editLimitsButton);
        transactionListView = findViewById(R.id.transactionListView);
        transactionButton = findViewById(R.id.transactionButton);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == R.id.home) {
                intent = new Intent(BudgetActivity.this, DashboardActivity.class);
                intent.putExtra("USER_DATA", (Serializable) user);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.learnNav) {
                Toast.makeText(BudgetActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.profile) {
                intent = new Intent(BudgetActivity.this, ProfileActivity.class);
                intent.putExtra("USER_DATA", (Serializable) user);
                startActivity(intent);
                return true;

            }
            return false;

        });

        housingProgress.setMax((int) user.getBudget().getSpecificCategory("Housing").getLimitAmount());
        housingProgress.setProgress((int) user.getBudget().getSpecificCategory("Housing").getAmountSpent(), false);
        transportationProgress.setMax((int) user.getBudget().getSpecificCategory("Transportation").getLimitAmount());
        transportationProgress.setProgress((int) user.getBudget().getSpecificCategory("Transportation").getAmountSpent(), false);
        foodProgress.setMax((int) user.getBudget().getSpecificCategory("Food").getLimitAmount());
        foodProgress.setProgress((int) user.getBudget().getSpecificCategory("Food").getAmountSpent(), false);
        medicalProgress.setMax((int) user.getBudget().getSpecificCategory("Medical").getLimitAmount());
        medicalProgress.setProgress((int) user.getBudget().getSpecificCategory("Medical").getAmountSpent(), false);
        personalProgress.setMax((int) user.getBudget().getSpecificCategory("Personal Spending").getLimitAmount());
        personalProgress.setProgress((int) user.getBudget().getSpecificCategory("Personal Spending").getAmountSpent(), false);
        entertainmentProgress.setMax((int) user.getBudget().getSpecificCategory("Entertainment").getLimitAmount());
        entertainmentProgress.setProgress((int) user.getBudget().getSpecificCategory("Entertainment").getAmountSpent(), false);

        adapter = new TransactionAdapter(BudgetActivity.this, R.layout.transaction_item, user.getBudget().getTransactionList());
        transactionListView.setAdapter(adapter);

        Badges budgetBadge = user.getBadgesList().get(0);
        for(BudgetLimitCategory c : user.getBudget().getCategoryList()){
            if(c.getAmountSpent() < c.getLimitAmount()){
                budgetBadge.addXp(25);
            }
        }

        editCategoryLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(BudgetActivity.this,"Under Construction",Toast.LENGTH_SHORT).show();
            }
        });
        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_transaction, null);
                new MaterialAlertDialogBuilder(BudgetActivity.this).setTitle("Add Transaction").setView(dialogView).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextInputEditText amount = dialogView.findViewById(R.id.textInputAmount);
                        TextInputEditText description = dialogView.findViewById(R.id.textInputDesc);
                        RadioGroup categorySelections = dialogView.findViewById(R.id.categorySelections);
                        int selectedId = categorySelections.getCheckedRadioButtonId();
                        String categorySelected = "";
                        if(selectedId == R.id.housingRadioBtn){
                            categorySelected = "Housing";
                        }
                        else if(selectedId == R.id.transportationRadioBtn){
                            categorySelected = "Transportation";
                        }
                        else if(selectedId == R.id.foodRadioBtn){
                            categorySelected = "Food";
                        }
                        else if(selectedId == R.id.medicalRadioBtn){
                            categorySelected = "Medical";
                        }
                        else if(selectedId == R.id.personalRadioBtn){
                            categorySelected = "Personal Spending";
                        }
                        else if(selectedId == R.id.entertainmentRadioBtn){
                            categorySelected = "Entertainment";
                        }
                        else{
                            Toast.makeText(BudgetActivity.this,"Please select a category",Toast.LENGTH_SHORT).show();
                        }

                        if(!amount.getText().toString().isEmpty() && !description.getText().toString().isEmpty()){
                            user.getBudget().addTransaction(new Transaction(user.getBudget().getSpecificCategory(categorySelected),Double.parseDouble(amount.getText().toString()), now(),description.getText().toString(),false));
                            user.getBudget().getSpecificCategory(categorySelected).addToAmountSpent(Integer.parseInt(amount.getText().toString()));
                            adapter.notifyDataSetChanged();
                            Toast.makeText(BudgetActivity.this,"Successfully added expense transaction",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(BudgetActivity.this,"Please fill in all fields",Toast.LENGTH_SHORT).show();

                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }


        });
    }

    static class TransactionAdapter extends ArrayAdapter<Transaction> {
        private final List<Transaction> transactions;

        public TransactionAdapter(@NonNull Context context, int resource, @NonNull List<Transaction> objects) {
            super(context, resource, objects);
            this.transactions = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_item, parent, false);

            }
            Transaction transaction = transactions.get(position);
            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewAmount = convertView.findViewById(R.id.textViewAmount);
            TextView textViewDate = convertView.findViewById(R.id.textViewDate);
            TextView textViewDesc = convertView.findViewById(R.id.textViewDesc);
            textViewName.setText(transaction.getCategory().getCategoryName());
            textViewAmount.setText("$" + transaction.getAmount());
            textViewDate.setText(transaction.getDate().toString());
            textViewDesc.setText(transaction.getDescription());

            return convertView;
        }
    }
}