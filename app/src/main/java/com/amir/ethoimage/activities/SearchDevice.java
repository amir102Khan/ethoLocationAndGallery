package com.amir.ethoimage.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amir.ethoimage.R;
import com.amir.ethoimage.adapter.BluetoothFoundDevicesAdapter;
import com.amir.ethoimage.core.BaseActivity;
import com.amir.ethoimage.databinding.ActivitySearchDeviceBinding;
import com.amir.ethoimage.interfaces.Constants;
import com.amir.ethoimage.interfaces.OnItemClick;
import com.amir.ethoimage.model.BluetoothObject;
import com.amir.ethoimage.model.BluetoothService;
import com.amir.ethoimage.util.Common;

import java.util.ArrayList;

import proto.BtMessageProtos;


public class SearchDevice extends BaseActivity implements OnItemClick, Constants {

    private BluetoothAdapter bluetoothAdapter;
    private ActivitySearchDeviceBinding binding;
    private ArrayList<BluetoothObject> arrayOfFoundBTDevices = new ArrayList<>();
    private BluetoothFoundDevicesAdapter adapter;
    private BluetoothService bluetoothService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_device);
        Common.setToolbarWithBackAndTitle(mContext, "Bluetooth Devices", true, R.drawable.ic_chevron_left_black_24dp);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        init();
        getData();
        setEMptyAdapter();
        checkBluetooth();

    }

    private void init() {
        bluetoothService = new BluetoothService(handler);
    }

    private void getData() {
       String filePath = getIntent().getStringExtra(FILE_PATH);
        bluetoothService.getImagePath(filePath);
    }

    private void setEMptyAdapter() {
        binding.rv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BluetoothFoundDevicesAdapter(mContext, arrayOfFoundBTDevices, this);
        binding.rv.setAdapter(adapter);
    }

    private void checkBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH);
        } else {
            displayListOfFoundDevices();
        }
    }

    private void displayListOfFoundDevices() {
        arrayOfFoundBTDevices.addAll(Common.getArrayOfAlreadyPairedBluetoothDevices(bluetoothAdapter));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            checkBluetooth();
        }
    }

    @Override
    public void onClick(int position, String data) {
        bluetoothService.connect(bluetoothAdapter.getRemoteDevice(data));
        showToast("connecting");
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case STATE_lISTENING:
                    showToast("listening");
                    break;
                case STATE_CONNECTING:
                    showToast("connecting");
                    break;
                case STATE_CONNECTED:
                    showToast("connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    showToast("connection failed");
                    break;
                case MESSAGE_RECIEVED:
                    showToast("message recieved");
                    break;
            }
            return false;
        }
    });
}
