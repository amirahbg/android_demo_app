package com.example.boltalp1.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.boltalp1.R;
import com.example.boltalp1.data.advertisement.AdvertisementWithImage;
import com.example.boltalp1.data.image.Image;
import com.example.boltalp1.databinding.AdvertiseItemBinding;
import com.example.boltalp1.util.SetDataInterface;
import com.example.boltalp1.viewmodel.AdvertisementItemViewModel;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder>
        implements SetDataInterface<AdvertisementWithImage> {
    private List<AdvertisementWithImage> mData;

    @Override
    public void setData(List<AdvertisementWithImage> data) {
        mData = data;
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
            mItemBinding.setViewModel(new AdvertisementItemViewModel());
        }

        void bind(AdvertisementWithImage advertisement) {
            mItemBinding.getViewModel().setBody(advertisement.mAdvertisement.getTitle());
            mItemBinding.getViewModel().setPrice(advertisement.mAdvertisement.getPrice());
            mItemBinding.getViewModel().setConfDate(advertisement.mAdvertisement.getConfDate());
            mItemBinding.getViewModel().setTitle(advertisement.mAdvertisement.getTitle());

//            Bitmap bitmap = loadBitmap(advertisement.mImages.get(0));
//            if (bitmap != null) {
//                mItemBinding.ivAdvertise.setImageBitmap(bitmap);
//            }

            Image image = advertisement.mImages.get(0);
            Uri uri = Uri.fromParts(image.getScheme(),
                    image.getSsp(),
                    image.getFragment());

            mItemBinding.ivAdvertise.setImageURI(uri);
            mItemBinding.executePendingBindings();
        }
    }
}
