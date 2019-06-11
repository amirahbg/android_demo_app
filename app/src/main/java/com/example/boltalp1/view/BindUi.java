package com.example.boltalp1.view;

import android.view.View;
import android.widget.TextView;

import com.example.boltalp1.R;
import com.example.boltalp1.util.SetDataInterface;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BindUi {
    @BindingAdapter("android:visibility")
    public static void progressDialogVisibility(View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
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

//    @InverseBindingAdapter(attribute = "android:visibility")
//    public static boolean isChecked(CheckBox checkBox) {
//        return checkBox.isChecked();
//    }

    @BindingAdapter("android:text")
    public static void setDate(TextView textView, Date date) {
        if (date != null && !date.equals(new Date(0))) {
            SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
            textView.setText(format.format(date));
        } else {
            textView.setText(textView.getContext().getString(R.string.err_not_conf));
        }
    }
}
