package com.example.boltalp1.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;

import com.example.boltalp1.R;
import com.example.boltalp1.databinding.FragmentLoginBinding;
import com.example.boltalp1.view.OnStartMainActivity;
import com.example.boltalp1.viewmodel.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private FragmentLoginBinding mBinding;
    private String mCurrentRole;
    private NavController mNavController;
    private LoginViewModel mLoginViewModel;
    private OnStartMainActivity mOnStartMainActivity;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,
                    container, false);
            mLoginViewModel = ViewModelProviders.of(this)
                    .get(LoginViewModel.class);
            mBinding.setViewModel(mLoginViewModel);
            mBinding.setLifecycleOwner(this);
        }

        setupSpinner();

        mLoginViewModel.requestIsLoggedIn();
        mLoginViewModel.setWaitingProgress(true);

        mLoginViewModel.getIsLoggedIn().observe(this,
                b -> {
                    if (b != null && b) {
                        mOnStartMainActivity.startMainActivity();
                    } else {
                        mLoginViewModel.setWaitingProgress(false);
                    }
                });

        mLoginViewModel.getIsSuccessfulLogin().observe(this,
                b -> {
                    if (b != null && b) {
                        mOnStartMainActivity.startMainActivity();
                    }
                });

        mBinding.tvRegister.setOnClickListener(v -> {
            if (mNavController.getCurrentDestination() != null
                    && mNavController.getCurrentDestination().getId() == R.id.loginFragment) {
                mNavController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        mBinding.btnLogin.setOnClickListener(v -> {
            if (validateInput()) {
                mLoginViewModel.requestLogin(mBinding.etUsername.getText().toString(),
                        mBinding.etPassword.getText().toString(),
                        mCurrentRole);
            }
        });

        return mBinding.getRoot();
    }

    private boolean validateInput() {
        boolean valid = true;
        if (mBinding.etUsername.getText().toString().equals("")) {
            mBinding.etUsername.setError(getString(R.string.err_empty_field));
            valid = false;
        }
        if (mBinding.etPassword.getText().toString().equals("")) {
            mBinding.etPassword.setError(getString(R.string.err_empty_field));
            valid = false;
        }
        return valid;
    }

    private void setupSpinner() {
        if (isAdded()) {
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.roles, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            mBinding.spRoles.setAdapter(adapter);
            mBinding.spRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mCurrentRole = (String) parent.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mNavController = NavHostFragment.findNavController(this);

        mOnStartMainActivity = (OnStartMainActivity) context;
    }
}
