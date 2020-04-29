package com.amir.ethoimage.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.amir.ethoimage.R;
import com.amir.ethoimage.core.BaseFragment;
import com.amir.ethoimage.databinding.FragmentCameraBinding;
import com.amir.ethoimage.interfaces.DatabaseListener;
import com.amir.ethoimage.util.Dialogs;
import com.amir.ethoimage.util.PermissionHelper;
import com.amir.ethoimage.viewModel.CameraViewModel;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;


public class Camera extends BaseFragment implements DatabaseListener {

    private static Camera camera;
    private FragmentCameraBinding binding;
    private CameraViewModel cameraViewModel;

    public static Camera getInstance() {
        if (camera == null) {
            camera = new Camera();
        }
        return camera;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewModel();
    }

    private void setViewModel() {
        cameraViewModel = ViewModelProviders.of(this).get(CameraViewModel.class);
        binding.setCameraViewModel(cameraViewModel);
        binding.setLifecycleOwner(this);
        cameraViewModel.setContext(mContext);
        cameraViewModel.getListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, mContext, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                File file = imageFiles.get(0);
                binding.imgCamera.setImageURI(Uri.fromFile(file));
                cameraViewModel.setImageFile(file);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE_CG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraViewModel.onCameraClick();
            }
            else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.CAMERA) ||
                        !ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        !ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Dialogs.alertDialogDeny(getString(R.string.camera), mContext);
                } else {
                    final AlertDialog alert = Dialogs.alertDialogWithTwoButtons(
                            getString(R.string.camera),
                            getString(R.string.proceed),
                            getString(R.string.exit),
                            mContext
                    );
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            PermissionHelper.requestPermissionCG(mContext);
                        }
                    });
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });
                }
            }
        }
        else if (requestCode == PERMISSION_REQUEST_CODE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                cameraViewModel.onCameraClick();
            }
            else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                        !ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Dialogs.alertDialogDeny(getString(R.string.location_permission_error), mContext);
                }
                else {
                    final AlertDialog alert = Dialogs.alertDialogWithTwoButtons(
                            getString(R.string.location),
                            getString(R.string.proceed),
                            getString(R.string.exit),
                            mContext
                    );
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            PermissionHelper.requestLocationPermission(mContext);
                        }
                    });
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            mContext.finish();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onSuccess(String message) {
        showToast(message);
        setDefault();
    }

    @Override
    public void onError(String message) {
        showToast(message);
    }

    private void setDefault(){
        binding.imgCamera.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_camera));
        binding.location.setText("");
        binding.location.setHint("Location");

    }
}
