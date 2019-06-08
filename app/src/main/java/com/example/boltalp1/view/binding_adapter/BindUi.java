package com.example.boltalp1.view.binding_adapter;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.boltalp1.util.SetDataInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BindUi {
    @BindingAdapter("android:visibility")
    public static void progressDialogVisibility(ProgressBar progressBar, boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("app:message")
    public static void showMessage(View view, String msg) {
        if (msg == null || msg.trim().equals("")) {
            return;
        }
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @BindingAdapter("app:data")
    public static <T> void setRecyclerViewData(RecyclerView recyclerView, List<T> data) {
        if (recyclerView.getAdapter() == null)
            return;

        if (recyclerView.getAdapter() instanceof SetDataInterface) {
            ((SetDataInterface) recyclerView.getAdapter()).setData(data);
        }
    }

    @BindingAdapter("android:text")
    public static void setDate(TextView textView, Date date) {
        if (date != null) {
            textView.setText(date.toString());
        }
    }
}
