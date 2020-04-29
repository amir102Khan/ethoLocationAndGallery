package com.amir.ethoimage.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amir.ethoimage.roomDatabase.CameraData;
import com.amir.ethoimage.roomDatabase.CameraDatabase;

import java.util.List;

public class GalleryViewModel extends ViewModel {

    private Context context;

    public void getContext(Context context){
        this.context = context;
    }
    private MutableLiveData<List<CameraDatabase>> cameraDatabase;

    public LiveData<List<CameraDatabase>> getData() {
        //if the list is null
        if (cameraDatabase == null) {
            cameraDatabase = new MutableLiveData<List<CameraDatabase>>();
            //we will load it asynchronously from server in this method
            loadData();
        }

        //finally we will return the list
        return cameraDatabase;
    }


    private void loadData(){
        CameraData cameraData = new CameraData(context);
        cameraDatabase.setValue(cameraData.getCart());
    }
}
