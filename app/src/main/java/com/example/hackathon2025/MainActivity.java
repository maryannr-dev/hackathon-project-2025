package com.example.hackathon2025;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hackathon2025.models.OnboardingItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        TabLayout tabIndicator = findViewById(R.id.tabIndicator);
        List<OnboardingItem> items = new ArrayList<>();
        items.add(new OnboardingItem(R.drawable.budget,"Track Your Budget"));
        items.add(new OnboardingItem(R.drawable.money, "Set Financial Goals"));
        items.add(new OnboardingItem(R.drawable.piggy,"Earn Badges for Saving"));

        onboardingViewPager.setAdapter(new OnboardingAdapter(items));

        new TabLayoutMediator(tabIndicator, onboardingViewPager, (tab, position) -> {}).attach();
        MaterialButton btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            if (onboardingViewPager.getCurrentItem() + 1 < items.size()) {
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
            } else {

            }
        });
    }
}