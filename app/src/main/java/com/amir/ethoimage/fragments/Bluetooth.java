package com.amir.ethoimage.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.amir.ethoimage.R;
import com.amir.ethoimage.core.BaseFragment;
import com.amir.ethoimage.databinding.FragmentBluetoothBinding;
import com.amir.ethoimage.model.BluetoothService;

import java.io.IOException;


public class Bluetooth extends BaseFragment implements View.OnClickListener {
    private static Bluetooth bluetooth;
    private FragmentBluetoothBinding binding;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothService bluetoothService;


    public static Bluetooth newInstance() {
        if (bluetooth != null) {
            bluetooth = new Bluetooth();
        }
        return bluetooth;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bluetooth, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        checkBluetooth();
        implementListener();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnListen) {
            bluetoothService.listen();
        }
    }

    private void checkBluetooth(){
        if (!bluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,REQUEST_ENABLE_BLUETOOTH);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BLUETOOTH){
            checkBluetooth();
        }
    }

    private void implementListener() {
        binding.btnListen.setOnClickListener(this);
    }

    private void init() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothService = new BluetoothService(handler);
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
                    byte[] readBuff = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(readBuff, 0, msg.arg1);
                    binding.imgBluetooth.setImageBitmap(bitmap);
                    break;
            }
            return false;
        }
    });
}
