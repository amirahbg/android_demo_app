package com.example.boltalp1.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.boltalp1.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity implements OnStartMainActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(AuthActivity.this,
                MainActivity.class);

        startActivity(intent);
        finish();
    }
}
