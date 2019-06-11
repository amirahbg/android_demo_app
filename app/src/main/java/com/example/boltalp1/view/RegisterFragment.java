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

import com.example.boltalp1.R;
import com.example.boltalp1.databinding.FragmentRegisterBinding;
import com.example.boltalp1.view.OnStartMainActivity;
import com.example.boltalp1.viewmodel.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding mBinding;
    private RegisterViewModel mRegisterViewModel;
    private NavController mNavController;
    private OnStartMainActivity mOnStartMainActivity;
    private String mCurrentRole;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register,
                    container, false);
            mBinding.setLifecycleOwner(this);
        }

        mBinding.tvLogin.setOnClickListener(v -> {
            if (mNavController.getCurrentDestination() != null &&
                    mNavController.getCurrentDestination().getId() == R.id.registerFragment) {
                mNavController.navigateUp();
            }
        });

        mRegisterViewModel = ViewModelProviders.of(this)
                .get(RegisterViewModel.class);
        mBinding.setViewmodel(mRegisterViewModel);

        mRegisterViewModel.getIsSuccessfulRegister().observe(this,
                isSuccessful -> {
                    if (isSuccessful != null && isSuccessful) {
                        mOnStartMainActivity.startMainActivity();
                    }
                });

        setupSpinner();

        mBinding.btnRegister.setOnClickListener(v -> {
            if (validateInput()) {
                mRegisterViewModel.requestRegisterUser(mBinding.etUsername.getText().toString(),
                        mBinding.etPassword.getText().toString(),
                        mBinding.etEmail.getText().toString(),
                        mBinding.etPhoneNumber.getText().toString(),
                        mCurrentRole);
                mRegisterViewModel.setWaitingProgress(true);
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
        if (mCurrentRole.equals("")) {
            mBinding.etPassword.setError(getString(R.string.err_empty_field));
            valid = false;
        }

        return valid;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mNavController = NavHostFragment.findNavController(this);


        // host activity must implement @{OnStartMainActivity}
        mOnStartMainActivity = (OnStartMainActivity) context;
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
}
