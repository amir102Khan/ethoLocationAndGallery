package com.amir.ethoimage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amir.ethoimage.R;
import com.amir.ethoimage.interfaces.OnItemClick;
import com.amir.ethoimage.model.BluetoothObject;

import java.util.ArrayList;

public class BluetoothFoundDevicesAdapter extends RecyclerView.Adapter<BluetoothFoundDevicesAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<BluetoothObject> devices ;
    private OnItemClick onItemClick;

    public BluetoothFoundDevicesAdapter(Context context,ArrayList<BluetoothObject> devices,OnItemClick onItemClick){
        this.context = context;
        this.devices =devices;
        this.onItemClick = onItemClick;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_devices,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvDevices.setText(devices.get(position).getBluetoothName());

        holder.tvAddress.setText(devices.get(position).getBluetoothAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(position,devices.get(position).getBluetoothAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDevices;
        private TextView tvAddress;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDevices = itemView.findViewById(R.id.tvDevices);
            tvAddress = itemView.findViewById(R.id.tvDeviceAddress);
        }
    }
}
