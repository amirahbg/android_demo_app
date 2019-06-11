package com.example.boltalp1.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.boltalp1.R;
import com.example.boltalp1.data.advertisement.AdvertisementWithImage;
import com.example.boltalp1.databinding.AdvertiseItemBinding;
import com.example.boltalp1.util.OnItemClickListener;
import com.example.boltalp1.util.SetDataInterface;
import com.example.boltalp1.viewmodel.AdvertisementItemViewModel;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder>
        implements SetDataInterface<AdvertisementWithImage> {
    private final Application mApplication;
    private OnItemClickListener<AdvertisementItemViewModel> mListener;
    private List<AdvertisementWithImage> mData;
    private boolean mIsAdmin;

    public AdvertisementAdapter(Application application, boolean isAdmin,
                                OnItemClickListener<AdvertisementItemViewModel> listener) {
        mIsAdmin = isAdmin;
        mListener = listener;
        mApplication = application;
    }

    @Override
    public void setData(List<AdvertisementWithImage> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void setAdmin(boolean admin) {
        mIsAdmin = admin;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdvertisementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AdvertiseItemBinding itemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.advertise_item, parent, false);

        return new AdvertisementViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementViewHolder holder, int position) {
        if (mData != null) {
            holder.bind(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class AdvertisementViewHolder extends RecyclerView.ViewHolder {

        private final AdvertiseItemBinding mItemBinding;

        public AdvertisementViewHolder(@NonNull AdvertiseItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mItemBinding = itemBinding;
            mItemBinding.setViewModel(new AdvertisementItemViewModel(mApplication));
            mItemBinding.cvParent.setOnClickListener(v -> {
                mListener.onItemClicked(mItemBinding.getViewModel());
            });
        }

        void bind(AdvertisementWithImage advertisement) {
            mItemBinding.getViewModel().setAdvertisement(advertisement.mAdvertisement);
            mItemBinding.getViewModel().setBody(advertisement.mAdvertisement.getBody());
            mItemBinding.getViewModel().setPrice(advertisement.mAdvertisement.getPrice());
            mItemBinding.getViewModel().setConfDate(advertisement.mAdvertisement.getConfDate());
            mItemBinding.getViewModel().setTitle(advertisement.mAdvertisement.getTitle());
            mItemBinding.getViewModel().setAdmin(mIsAdmin);
            mItemBinding.executePendingBindings();
        }
    }
}
