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

import com.example.hackathon2025.models.UserItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;
import java.util.Objects;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText textInputEmail;
    private TextInputEditText textInputPassword;
    private MaterialButton btnLoginSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupWindowInsets();
        setupViews();

        btnLoginSubmit.setOnClickListener(v -> {
            String email = Objects.requireNonNull(textInputEmail.getText()).toString().trim();
            String password = Objects.requireNonNull(textInputPassword.getText()).toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // 1. Access the EncryptedSharedPreferences
                String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                        "secure_account_storage",
                        masterKeyAlias,
                        getApplicationContext(),
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );

                //Retrieve the user's data using their email as the key
                String userJsonString = sharedPreferences.getString(email, null);

                if (userJsonString == null) {
                    showLoginError();
                    return;
                }

                //Deserialize the JSON string back into a UserItem object
                Gson gson = new Gson();
                UserItem storedUser = gson.fromJson(userJsonString, UserItem.class);

                //Hash the entered password with the user's stored salt
                String enteredPasswordHash = hashPasswordWithSalt(password, storedUser.getSalt());

                // 5. Compare the hashes
                if (enteredPasswordHash.equals(storedUser.getHashedPassword())) {
                    // Message shown if succesful
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("USER_DATA", storedUser);
                    startActivity(intent);
                    finish();
                } else {
                    // Message shown if a failure
                    showLoginError();
                }

            } catch (Exception e) {
                e.printStackTrace();
                showLoginError();
            }
        });
    }

    private void setupViews() {
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showLoginError() {
        Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
    }

    private String hashPasswordWithSalt(String password, String salt) throws Exception {
        byte[] saltBytes = Base64.decode(salt, Base64.DEFAULT);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.encodeToString(hash, Base64.DEFAULT);
    }
}
