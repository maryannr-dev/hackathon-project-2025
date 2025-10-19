package com.example.hackathon2025;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hackathon2025.models.Badges;
import com.example.hackathon2025.models.UserItem;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;



public class FragmentProfile extends Fragment {



    public FragmentProfile() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    UserItem user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        if (getArguments() != null) {
           user = (UserItem) getArguments().getSerializable("USER_DATA");
            // Now you can use the user object to populate the UI
        }

        return view;
    }

    TextView textViewLevel;
    TextView textViewXP1;
    LinearProgressIndicator budgetLevelProgress;
    TextView textViewLevel2;
    TextView textViewXP2;
    LinearProgressIndicator litLevelProgress;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewLevel = view.findViewById(R.id.textViewLevel);
        textViewXP1 = view.findViewById(R.id.textViewDesc2);
        budgetLevelProgress = view.findViewById(R.id.budgetLevelProgress);
        textViewLevel2 = view.findViewById(R.id.textViewLevel2);
        textViewXP2 = view.findViewById(R.id.textViewDesc22);
        litLevelProgress = view.findViewById(R.id.litLevelProgress);

        if(user != null) {
            List<Badges> userBadges = user.getBadgesList();
            Badges budgetBadge = userBadges.get(0);
            Badges litBadge = userBadges.get(1);


            textViewLevel.setText("Level " + budgetBadge.getLvl());
            textViewXP1.setText("XP Until Next Level: " + ((budgetBadge.getLvl() + 1) * 100 - budgetBadge.getXp()));
            budgetLevelProgress.setMax(budgetBadge.getLvl() * 100);
            budgetLevelProgress.setProgress(budgetBadge.getXp());
            textViewLevel2.setText("Level " + litBadge.getLvl());
            textViewXP2.setText("XP Until Next Level: " + ((litBadge.getLvl() + 1) * 100 - budgetBadge.getXp()));
            litLevelProgress.setMax(litBadge.getLvl() * 100);
            litLevelProgress.setProgress(litBadge.getXp());
        }
        else{
            textViewLevel.setText("No data available");
            textViewXP1.setText("No data available");
            budgetLevelProgress.setMax(0);
            budgetLevelProgress.setProgress(0);
            textViewLevel2.setText("No data available");
            textViewXP2.setText("No data available");

        }

    }
}