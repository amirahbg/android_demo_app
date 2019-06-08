package com.example.boltalp1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.boltalp1.R;
import com.example.boltalp1.databinding.ActivityMainBinding;
import com.example.boltalp1.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mBinding.toolbar);
        mMainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        mMainViewModel.getIsLogoutSuccessful().observe(this,
                b -> {
                    if (b != null && b) {
                        Intent intent = new Intent(
                                MainActivity.this, AuthActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            mMainViewModel.requestLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
