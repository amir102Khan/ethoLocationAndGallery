package com.amir.ethoimage.roomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class CameraData {
    private Context context;

    public CameraData(Context context){
        this.context = context;
    }

    public List<CameraDatabase> getCart(){
        try {
            return new GetCart().execute().get();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    class GetCart extends AsyncTask<Void,Void,List<CameraDatabase>> {
        @Override
        protected List<CameraDatabase> doInBackground(Void... voids) {
            return DatabaseClient
                    .getInstance(context)
                    .getAppDatabase()
                    .cameraDao()
                    .getAll();

        }
    }
}
