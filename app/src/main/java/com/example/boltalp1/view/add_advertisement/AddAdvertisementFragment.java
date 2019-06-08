package com.example.boltalp1.view.add_advertisement;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import pub.devrel.easypermissions.EasyPermissions;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.boltalp1.R;
import com.example.boltalp1.databinding.FragmentAddAdvertismentBinding;
import com.example.boltalp1.viewmodel.AddAdvertisementViewModel;

import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAdvertisementFragment extends Fragment
        implements EasyPermissions.PermissionCallbacks {
    public static final int PICK_IMAGE_RESULT_CODE = 1;
    public static final int PICK_VIDEO_RESULT_CODE = 2;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 100;

    private boolean mReadExStorage;
    private FragmentAddAdvertismentBinding mBinding;
    private AddAdvertisementViewModel mAddAdvertisementViewModel;

    private Uri mUri;
    private String mVideoUrl;
    private NavController mNavController;

    public AddAdvertisementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater,
                    R.layout.fragment_add_advertisment, container, false);

            mAddAdvertisementViewModel = ViewModelProviders.of(this)
                    .get(AddAdvertisementViewModel.class);
        }

        mBinding.btnSaved.setOnClickListener(v -> {
            if (validateData()) {
                mAddAdvertisementViewModel.requestAddAdvertisement(
                        mBinding.etTitle.getText().toString().trim(),
                        mBinding.etBody.getText().toString().trim(),
                        mBinding.etPrice.getText().toString().trim(),
                        mUri,
                        mVideoUrl);
            }
        });

        mBinding.ivAddImg.setOnClickListener(v -> {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);

            chooseFile.setType("image/*");

            chooseFile = Intent.createChooser(chooseFile, "Choose a file");

            startActivityForResult(chooseFile, PICK_IMAGE_RESULT_CODE);
        });

        mBinding.ivAddVideo.setOnClickListener(v -> {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);

            chooseFile.setType("video/*");

            chooseFile = Intent.createChooser(chooseFile, "Choose a file");

            startActivityForResult(chooseFile, PICK_VIDEO_RESULT_CODE);
        });

        if (isAdded()) {
            if (!EasyPermissions.hasPermissions(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                EasyPermissions.requestPermissions(this,
                        getString(R.string.permission_read_external_storage)
                        , EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

            }
        }

        mAddAdvertisementViewModel.getIsSuccessFullyAdded().observe(this,
                r -> {
                    if (r != null && r) {
                        mNavController.navigateUp();
                    }
                });

        return mBinding.getRoot();
    }

    private boolean validateData() {
        boolean valid = true;
        if (mBinding.etTitle.getText().toString().trim().equals("")) {
            mBinding.etTitle.setError(getString(R.string.err_empty_field));
            valid = false;
        }
        if (mBinding.etBody.getText().toString().trim().equals("")) {
            mBinding.etBody.setError(getString(R.string.err_empty_field));
            valid = false;
        }
        if (mBinding.etPrice.getText().toString().trim().equals("")) {
            mBinding.etPrice.setError(getString(R.string.err_empty_field));
            valid = false;
        }
        if (mUri == null) {
            Toast.makeText(getContext(), R.string.err_image_url, Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri fileUri;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_RESULT_CODE:
                    fileUri = data.getData();
                    if (fileUri != null) {
                        mBinding.tvImagePath.setText(fileUri.getPath());
                        mUri = fileUri;
                        // FIXME: 6/8/19 result of method is null
                        String s = getRealPathFromUri(getContext(), fileUri);
                    }
                    break;
                case PICK_VIDEO_RESULT_CODE:
                    fileUri = data.getData();
                    if (fileUri != null) {
                        mBinding.tvVideoPath.setText(fileUri.getPath());
                        mVideoUrl = fileUri.getPath();
                    }
                    break;

            }
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mNavController = NavHostFragment.findNavController(this);
    }
}
