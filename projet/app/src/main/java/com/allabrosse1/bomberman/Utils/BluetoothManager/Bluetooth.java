package com.allabrosse1.bomberman.Utils.BluetoothManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by diberger on 06/03/18.
 */

public class Bluetooth {


    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothDevice> deviceFound = new ArrayList<>();

    public BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public Bluetooth() {
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Log.i("bombermanBT", "Pas de bluetooth sur ce telephone alors qu'on est en 2018 ...");
        }
    }

    public ArrayList<BluetoothDevice> getDeviceFound() {
        return deviceFound;
    }
}
