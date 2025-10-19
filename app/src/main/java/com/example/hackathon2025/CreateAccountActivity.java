package com.example.hackathon2025;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.example.hackathon2025.models.Badges;
import com.example.hackathon2025.models.Budget;
import com.example.hackathon2025.models.UserItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CreateAccountActivity extends AppCompatActivity {
    MaterialDatePicker<Long> datePicker;
    MaterialDatePicker.Builder<Long> builder;
    MaterialButton dateOfBirthButton;
    MaterialButton createAccountSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Date Of Birth");
        datePicker = builder.build();
        dateOfBirthButton = findViewById(R.id.btnDateOfBirth);
        dateOfBirthButton.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "DATE_PICKER"));

        createAccountSubmitButton = findViewById(R.id.btnCreateAccountSubmit);
        createAccountSubmitButton.setOnClickListener(v -> {
            // data validation
            Long selectedDateInMillis = datePicker.getSelection();
            TextInputEditText inputEmailCheck = findViewById(R.id.textInputEmail);
            String email = Objects.requireNonNull(inputEmailCheck.getText()).toString().trim();
            TextInputEditText inputPasswordCheck = findViewById(R.id.textInputPassword);
            String password = Objects.requireNonNull(inputPasswordCheck.getText()).toString().trim();

            if (email.isEmpty() || password.isEmpty() || selectedDateInMillis == null) {
                Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar dateOfBirth = Calendar.getInstance();
            dateOfBirth.setTimeInMillis(selectedDateInMillis);
            Calendar eighteenYearsAgo = Calendar.getInstance();
            eighteenYearsAgo.add(Calendar.YEAR, -18);
            if (dateOfBirth.after(eighteenYearsAgo)) {
                Toast.makeText(getApplicationContext(), "You must be at least 18 years old.", Toast.LENGTH_LONG).show();
                return;
            }

            //password hashing
            String[] hashedPasswordAndSalt;
            try {
                hashedPasswordAndSalt = hashPassword(password);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error securing password.", Toast.LENGTH_SHORT).show();
                return;
            }
            String hashedPassword = hashedPasswordAndSalt[0];
            String salt = hashedPasswordAndSalt[1];

            Budget startingBudget = new Budget();


            UserItem userToPass = new UserItem(email, hashedPassword, salt, new Date(selectedDateInMillis), startingBudget);

            try {
                Gson gson = new Gson();
                String userJsonString = gson.toJson(userToPass);

                String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                        "secure_account_storage", masterKeyAlias, getApplicationContext(),
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(userToPass.getEmail(), userJsonString);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error creating account", Toast.LENGTH_SHORT).show();
                return; // Do not proceed if saving failed
            }

            // 5. Start the Next Activity
            Intent intent = new Intent(CreateAccountActivity.this, DashboardActivity.class);
            intent.putExtra("USER_DATA", userToPass);
            startActivity(intent);
            finish();
        });
    }

    private String[] hashPassword(String password) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        String base64Hash = Base64.encodeToString(hash, Base64.DEFAULT);
        String base64Salt = Base64.encodeToString(salt, Base64.DEFAULT);

        return new String[]{base64Hash, base64Salt};
    }
}
