package com.example.hackathon2025;

import static java.time.LocalDate.now;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackathon2025.models.Badges;
import com.example.hackathon2025.models.BudgetLimitCategory;
import com.example.hackathon2025.models.Transaction;
import com.example.hackathon2025.models.UserItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;



public class BudgetFragment extends Fragment {



    public BudgetFragment() {
        // Required empty public constructor
    }


    UserItem user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (UserItem) getArguments().getSerializable("USER_DATA");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        return view;
    }

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        housingProgress = view.findViewById(R.id.housingProgress);
        transportationProgress = view.findViewById(R.id.transportationProgress);
        foodProgress = view.findViewById(R.id.foodProgress);
        medicalProgress = view.findViewById(R.id.medicalProgress);
        personalProgress = view.findViewById(R.id.personalProgress);
        entertainmentProgress = view.findViewById(R.id.entertainmentProgress);
        editCategoryLimits = view.findViewById(R.id.editLimitsButton);
        transactionListView = view.findViewById(R.id.transactionListView);
        transactionButton = view.findViewById(R.id.transactionButton);
        bottomNavigationView = view.findViewById(R.id.bottomNavigation);

        if(user != null) {
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

            adapter = new TransactionAdapter(getContext(), R.layout.transaction_item, user.getBudget().getTransactionList());
            transactionListView.setAdapter(adapter);

            Badges budgetBadge = user.getBadgesList().get(0);
            for (BudgetLimitCategory c : user.getBudget().getCategoryList()) {
                if (c.getAmountSpent() < c.getLimitAmount()) {
                    budgetBadge.addXp(25);
                }
            }
            transactionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_transaction, null);
                    new MaterialAlertDialogBuilder(getContext()).setTitle("Add Transaction").setView(dialogView).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
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
                                Toast.makeText(getContext(),"Please select a category",Toast.LENGTH_SHORT).show();
                            }

                            if(!amount.getText().toString().isEmpty() && !description.getText().toString().isEmpty()){
                                user.getBudget().addTransaction(new Transaction(user.getBudget().getSpecificCategory(categorySelected),Double.parseDouble(amount.getText().toString()), now(),description.getText().toString(),false));
                                user.getBudget().getSpecificCategory(categorySelected).addToAmountSpent(Integer.parseInt(amount.getText().toString()));
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(),"Successfully added expense transaction",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(),"Please fill in all fields",Toast.LENGTH_SHORT).show();

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
        else {
            Toast.makeText(getContext(),"No data available",Toast.LENGTH_SHORT).show();
            housingProgress.setMax(0);
            housingProgress.setProgress(0);
            transportationProgress.setMax(0);
            transportationProgress.setProgress(0);
            foodProgress.setMax(0);
            foodProgress.setProgress(0);
            medicalProgress.setMax(0);
            medicalProgress.setProgress(0);
            personalProgress.setMax(0);
            personalProgress.setProgress(0);
            entertainmentProgress.setMax(0);
            entertainmentProgress.setProgress(0);

            transactionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"No User Available",Toast.LENGTH_SHORT).show();
                }
            });

        }

        editCategoryLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Under Construction",Toast.LENGTH_SHORT).show();
            }
        });

    }

    }
class TransactionAdapter extends ArrayAdapter<Transaction> {
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