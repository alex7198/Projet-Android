package com.allabrosse1.bomberman.Utils.BluetoothManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by diberger on 07/03/18.
 */

public class BluetoothServer extends Thread {
    private UUID DEFAULT_UUID = UUID.fromString("cf1e9e44-a3fe-407e-bf22-83bc6132aad6");
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothServerSocket mmServerSocket;

    private BluetoothSocket socket = null;

    private boolean isActive = false;


    public BluetoothServer(BluetoothAdapter mBluetoothAdapter) {
        this.mBluetoothAdapter = mBluetoothAdapter;
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = this.mBluetoothAdapter.listenUsingRfcommWithServiceRecord("BomberManBT", DEFAULT_UUID);
        } catch (IOException e) {
            Log.i("bombermanBT", "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;

    }

    public void run() {
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
                Log.i("bombermanBT", "waiting for connection");
            } catch (IOException e) {
                Log.i("bombermanBT", "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                //manageMyConnectedSocket(socket);
                isActive = true;
                mBluetoothAdapter.cancelDiscovery();
                Log.i("bombermanBT", "connection accepté !");
                while (isActive) {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        isActive = false;
                    }
                }
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    Log.i("bombermanBT", e.getMessage());
                }
                break;
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.i("bombermanBT", "Could not close the connect socket", e);
        }
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public boolean isActive() {
        return isActive;
    }
}
