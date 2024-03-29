package com.allabrosse1.bomberman.Utils.BluetoothManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by diberger on 07/03/18.
 */

public class BluetoothClient extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter;
    private UUID DEFAULT_UUID = UUID.fromString("cf1e9e44-a3fe-407e-bf22-83bc6132aad6");

    private boolean isActive = true;

    public BluetoothClient(BluetoothDevice device, BluetoothAdapter mBluetoothAdapter) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        mmDevice = device;
        this.mBluetoothAdapter = mBluetoothAdapter;
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(DEFAULT_UUID);
        } catch (IOException e) {
            Log.i("bombermanBT", "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
            Log.i("bombermanBT", "Connected");
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.i("bombermanBT", "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        //manageMyConnectedSocket(mmSocket);
        while (isActive) {
            try {
                sleep(2000);
                Log.i("bombermanBT", "Connection active");
            } catch (InterruptedException e) {
                isActive = false;
            }
        }
        this.cancel();
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.i("bombermanBT", "Could not close the client socket", e);
        }
    }

    public BluetoothSocket getSocket() {
        return mmSocket;
    }

    public boolean isActive() {
        return isActive;
    }
}
