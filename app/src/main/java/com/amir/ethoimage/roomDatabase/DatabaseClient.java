package com.amir.ethoimage.roomDatabase;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient mInstance;


    // app database
    private AppDatabase appDatabase;

    private DatabaseClient(Context mContext) {
        this.context = mContext;

        //creating the app database with Room database builder
        //MyCamera is the name of the database

        appDatabase = Room.databaseBuilder(mContext, AppDatabase.class, "MyCamera").build();

    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(context);
        }

        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

}
