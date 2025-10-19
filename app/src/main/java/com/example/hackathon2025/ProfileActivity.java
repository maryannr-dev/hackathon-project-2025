package com.example.hackathon2025;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hackathon2025.models.Badges;
import com.example.hackathon2025.models.UserItem;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    UserItem user;
    TextView textViewLevel;
    TextView textViewXP1;
    LinearProgressIndicator budgetLevelProgress;
    TextView textViewLevel2;
    TextView textViewXP2;
    LinearProgressIndicator litLevelProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        user = (UserItem) getIntent().getSerializableExtra("USER_DATA");
        textViewLevel = findViewById(R.id.textViewLevel);
        textViewXP1 = findViewById(R.id.textViewDesc2);
        budgetLevelProgress = findViewById(R.id.budgetLevelProgress);
        textViewLevel2 = findViewById(R.id.textViewLevel2);
        textViewXP2 = findViewById(R.id.textViewDesc22);
        litLevelProgress = findViewById(R.id.litLevelProgress);

        List<Badges> userBadges = user.getBadgesList();
        Badges budgetBadge = userBadges.get(0);
        Badges litBadge = userBadges.get(1);



        textViewLevel.setText("Level " + budgetBadge.getLvl());
        textViewXP1.setText("XP Until Next Level: " + ((budgetBadge.getLvl()+1)*100 - budgetBadge.getXp()));
        budgetLevelProgress.setMax(budgetBadge.getLvl()*100);
        budgetLevelProgress.setProgress(budgetBadge.getXp());

        textViewLevel2.setText("Level " + litBadge.getLvl());
        textViewXP2.setText("XP Until Next Level: " + ((litBadge.getLvl()+1)*100 - budgetBadge.getXp()));
        litLevelProgress.setMax(litBadge.getLvl()*100);
        litLevelProgress.setProgress(litBadge.getXp());








    }
}