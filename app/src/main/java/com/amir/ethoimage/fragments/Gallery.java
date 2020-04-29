package com.amir.ethoimage.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amir.ethoimage.R;
import com.amir.ethoimage.adapter.GalleryAdapter;
import com.amir.ethoimage.core.BaseFragment;
import com.amir.ethoimage.databinding.FragmentGalleryBinding;
import com.amir.ethoimage.roomDatabase.CameraData;
import com.amir.ethoimage.roomDatabase.CameraDatabase;
import com.amir.ethoimage.viewModel.GalleryViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Gallery extends BaseFragment {


    private static Gallery gallery;
    private FragmentGalleryBinding binding;
    private ArrayList<CameraDatabase> sharedData = new ArrayList<>();
    private GalleryAdapter galleryAdapter;
    private GalleryViewModel galleryViewModel;

    public static Gallery getInstance(){
        if (gallery == null){
            gallery = new Gallery();
        }
        return gallery;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_gallery,container,false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyAdapter();
        setViewModel();
        getData();
    }

    private void setEmptyAdapter(){
        binding.rv.setLayoutManager(new LinearLayoutManager(mContext));
        galleryAdapter = new GalleryAdapter(mContext, sharedData);
        binding.rv.setAdapter(galleryAdapter);
    }

    private void setViewModel(){
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        binding.setGalleryViewModel(galleryViewModel);
        binding.setLifecycleOwner(this);
        galleryViewModel.getContext(mContext);
    }

    private void getData(){
        CameraData cameraData = new CameraData(mContext);
        cameraData.getCart();


        galleryViewModel.getData().observe(this, new Observer<List<CameraDatabase>>() {
            @Override
            public void onChanged(List<CameraDatabase> cameraDatabases) {
                sharedData.clear();
                sharedData.addAll(cameraDatabases);
                Collections.reverse(sharedData);
                galleryAdapter.notifyDataSetChanged();
            }
        });
    }
}
