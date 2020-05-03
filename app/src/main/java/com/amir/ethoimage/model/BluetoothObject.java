package com.amir.ethoimage.model;

import android.os.ParcelUuid;

public class BluetoothObject {

    private String bluetoothName;
    private String bluetoothAddress;
    private int bluetoothState;
    private int bluetoothType;
    private ParcelUuid[] bluetoothUUID;
    private int bluetoothRSSI;

    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public int getBluetoothState() {
        return bluetoothState;
    }

    public void setBluetoothState(int bluetoothState) {
        this.bluetoothState = bluetoothState;
    }

    public int getBluetoothType() {
        return bluetoothType;
    }

    public void setBluetoothType(int bluetoothType) {
        this.bluetoothType = bluetoothType;
    }

    public ParcelUuid[] getBluetoothUUID() {
        return bluetoothUUID;
    }

    public void setBluetoothUUID(ParcelUuid[] bluetoothUUID) {
        this.bluetoothUUID = bluetoothUUID;
    }

    public int getBluetoothRSSI() {
        return bluetoothRSSI;
    }

    public void setBluetoothRSSI(int bluetoothRSSI) {
        this.bluetoothRSSI = bluetoothRSSI;
    }


}
