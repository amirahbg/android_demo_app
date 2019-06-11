package com.example.boltalp1.viewmodel;

import android.app.Application;

import com.example.boltalp1.util.Event;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SharedAdvertisementViewModel extends AndroidViewModel {
    private MutableLiveData<Event<AdvertisementItemViewModel>> mSelectedItem;

    public SharedAdvertisementViewModel(@NonNull Application application) {
        super(application);

        mSelectedItem = new MutableLiveData<>();
    }

    public void selectItem(AdvertisementItemViewModel viewModel) {
        mSelectedItem.setValue(new Event<>(viewModel));
    }

    public LiveData<Event<AdvertisementItemViewModel>> getSelectedItem() {
        return mSelectedItem;
    }
}
