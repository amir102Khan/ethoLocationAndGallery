package com.amir.ethoimage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.ethoimage.R;
import com.amir.ethoimage.fragments.Gallery;
import com.amir.ethoimage.roomDatabase.CameraDatabase;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder>{

    private ArrayList<CameraDatabase> cameraDatabases;
    private Context context;

    public GalleryAdapter(Context context,ArrayList<CameraDatabase> cameraDatabases){
        this.context =context;
        this.cameraDatabases = cameraDatabases;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bitmap myBitmap = BitmapFactory.decodeFile(cameraDatabases.get(position).getImageFilePath());
        holder.image.setImageBitmap(myBitmap);
        holder.tvLocation.setText(cameraDatabases.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return cameraDatabases.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView tvLocation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgGallery);
            tvLocation = itemView.findViewById(R.id.location);
        }
    }
}
