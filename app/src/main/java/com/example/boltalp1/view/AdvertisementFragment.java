package com.example.boltalp1.view;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.boltalp1.R;
import com.example.boltalp1.adapter.AdvertisementAdapter;
import com.example.boltalp1.databinding.FragmentAdvertismentBinding;
import com.example.boltalp1.viewmodel.AdvertisementsViewModel;
import com.example.boltalp1.viewmodel.SharedAdvertisementViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertisementFragment extends Fragment {
    private FragmentAdvertismentBinding mBinding;
    private AdvertisementsViewModel mAdvertisementsViewModel;
    private SharedAdvertisementViewModel mSharedAdvertisementViewModel;
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

            if (isAdded()) {
                mSharedAdvertisementViewModel = ViewModelProviders.of(getActivity())
                        .get(SharedAdvertisementViewModel.class);
            }

            mBinding.setViewModel(mAdvertisementsViewModel);
            mBinding.setLifecycleOwner(this);

            mBinding.rvAdvertises.setLayoutManager(new LinearLayoutManager(getContext()));

            // TODO: 6/10/19 consider better option for clicking item
            mAdapter = new AdvertisementAdapter(mAdvertisementsViewModel.getApplication(),
                    mAdvertisementsViewModel.isAdmin(),
                    viewModel -> {
                        if (mNavController.getCurrentDestination() != null &&
                                mNavController.getCurrentDestination().getId() == R.id.advertismentFragment) {
                            mSharedAdvertisementViewModel.selectItem(viewModel);
                            mNavController.navigate(R.id.action_advertismentFragment_to_advertismentDetailFragment);
                        }
                    });

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
