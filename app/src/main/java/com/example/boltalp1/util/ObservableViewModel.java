package com.example.boltalp1.util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class ObservableViewModel extends AndroidViewModel
        implements Observable {
    private transient PropertyChangeRegistry mCallbacks;
    private CompositeDisposable mCompositeDisposable;

    public ObservableViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }
}
