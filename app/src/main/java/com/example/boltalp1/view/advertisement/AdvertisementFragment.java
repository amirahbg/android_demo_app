package com.example.boltalp1.view.advertisement;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.boltalp1.R;
import com.example.boltalp1.adapter.AdvertisementAdapter;
import com.example.boltalp1.databinding.FragmentAdvertismentBinding;
import com.example.boltalp1.viewmodel.AdvertisementItemViewModel;
import com.example.boltalp1.viewmodel.AdvertisementsViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertisementFragment extends Fragment {
    private FragmentAdvertismentBinding mBinding;
    private AdvertisementsViewModel mAdvertisementsViewModel;
    private AdvertisementAdapter mAdapter;
    private NavController mNavController;

    public AdvertisementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_advertisment, container, false);

            mAdvertisementsViewModel = ViewModelProviders.of(this)
                    .get(AdvertisementsViewModel.class);

            mBinding.setViewModel(mAdvertisementsViewModel);
            mBinding.setLifecycleOwner(this);

            mBinding.rvAdvertises.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new AdvertisementAdapter();
            mBinding.rvAdvertises.setAdapter(mAdapter);
        }

        mAdvertisementsViewModel.requestAdvertisements();

        mBinding.fabAddAdvertisement.setOnClickListener(v -> {
            if (mNavController.getCurrentDestination() != null &&
                    mNavController.getCurrentDestination().getId() == R.id.advertismentFragment) {
                mNavController.navigate(R.id.action_advertismentFragment_to_addAdvertismentFragment);
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mNavController = NavHostFragment.findNavController(this);
    }
}
