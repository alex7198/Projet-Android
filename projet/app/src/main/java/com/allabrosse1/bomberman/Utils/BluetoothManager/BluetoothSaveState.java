package com.allabrosse1.bomberman.Utils.BluetoothManager;


/**
 * Created by diberger on 14/03/18.
 */

public class BluetoothSaveState {

    private static BluetoothSaveState uniqueInstance;


    private BluetoothServer bluetoothServer;
    private BluetoothClient bluetoothClient;

    private BluetoothSaveState() {

    }

    public static BluetoothSaveState getInstance() {
        if (BluetoothSaveState.uniqueInstance == null) {
            BluetoothSaveState.uniqueInstance = new BluetoothSaveState();
        }
        return BluetoothSaveState.uniqueInstance;
    }

    public BluetoothServer getBluetoothServer() {
        return bluetoothServer;
    }

    public void setBluetoothServer(BluetoothServer bluetoothServer) {
        this.bluetoothServer = bluetoothServer;
    }

    public BluetoothClient getBluetoothClient() {
        return bluetoothClient;
    }

    public void setBluetoothClient(BluetoothClient bluetoothClient) {
        this.bluetoothClient = bluetoothClient;
    }
}
