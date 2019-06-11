package com.example.boltalp1.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.boltalp1.R;
import com.example.boltalp1.databinding.FragmentAdvertismentDetailBinding;
import com.example.boltalp1.viewmodel.AdvertisementItemViewModel;
import com.example.boltalp1.viewmodel.SharedAdvertisementViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertisementDetailFragment extends Fragment {
    private FragmentAdvertismentDetailBinding mBinding;
    private SharedAdvertisementViewModel mSharedAdvertisementViewModel;

    public AdvertisementDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_advertisment_detail,
                    container, false);

            if (isAdded()) {
                mSharedAdvertisementViewModel = ViewModelProviders.of(getActivity())
                        .get(SharedAdvertisementViewModel.class);
            }
        }

        mSharedAdvertisementViewModel.getSelectedItem().observe(this,
                sharedVMEvent -> {
                    AdvertisementItemViewModel viewModel = sharedVMEvent.getContentIfNotHandled();
                    if (viewModel != null) {
                        mBinding.setViewModel(viewModel);
                    }
                });

        return mBinding.getRoot();
    }

}
