package com.amir.ethoimage.viewModel;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amir.ethoimage.interfaces.DatabaseListener;
import com.amir.ethoimage.interfaces.GetLocationListener;
import com.amir.ethoimage.roomDatabase.CameraDatabase;
import com.amir.ethoimage.roomDatabase.DatabaseClient;
import com.amir.ethoimage.util.LocationHelper;
import com.amir.ethoimage.util.PermissionHelper;

import java.io.File;

import pl.aprilapps.easyphotopicker.EasyImage;

public class CameraViewModel extends ViewModel {
    public MutableLiveData<String> location = new MutableLiveData<>();

    private Activity context;
    private String latitue;
    private String longitude;
    private String currentAddress;
    private File imageFile;
    private DatabaseListener databaseListener;

    public void setContext(Activity context) {
        this.context = context;
    }

    public void getListener(DatabaseListener databaseListener){
        this.databaseListener = databaseListener;
    }
    public void setImageFile(File file){
        this.imageFile = file;
    }


    public void onClick() {
        if (imageFile != null){
            addCameraData();
        }
        else {
            databaseListener.onError("Please click image");
        }
    }

    private void addCameraData(){
        //  showLoader();
        class SaveData extends AsyncTask<Void,Void,Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                // creating database
                CameraDatabase cameraDatabase = new CameraDatabase();
                cameraDatabase.setImageFilePath(imageFile.getAbsolutePath());
                cameraDatabase.setLatitude(latitue);
                cameraDatabase.setLongitude(longitude);
                cameraDatabase.setAddress(currentAddress);
                // adding in database

                DatabaseClient.getInstance(context)
                        .getAppDatabase()
                        .cameraDao()
                        .insert(cameraDatabase);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //  hideLoader();
                databaseListener.onSuccess("Image saved");
                imageFile = null;

            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);



            }
        }


        SaveData saveData = new SaveData();
        saveData.execute();
    }

    public void onCameraClick(){
        if (PermissionHelper.checkPermissionCG(context)){
            if (PermissionHelper.checkLocationPermission(context)){
                getCurrentLocation();
                openCamera();
            }
            else {
             PermissionHelper.requestLocationPermission(context);
            }
        }
        else {
            PermissionHelper.requestPermissionCG(context);
        }
    }




    public void openCamera() {
        EasyImage.openCamera(context, 1);
    }

    private void getCurrentLocation() {
        LocationHelper locationHelper = new LocationHelper(context, new GetLocationListener() {
            @Override
            public void onLocationFound(String lat, String lng, String address) {
                Log.e("location: ", lat + " : " + lng + " : " + address);
                latitue = lat;
                longitude = lng;
                currentAddress = address;
                location.setValue(address);
            }
        });

        locationHelper.getLocation();
    }

}
