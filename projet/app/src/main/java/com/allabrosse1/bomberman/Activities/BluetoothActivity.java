package com.allabrosse1.bomberman.Activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.allabrosse1.bomberman.R;
import com.allabrosse1.bomberman.Utils.BluetoothManager.Bluetooth;
import com.allabrosse1.bomberman.Utils.BluetoothManager.BluetoothClient;
import com.allabrosse1.bomberman.Utils.BluetoothManager.BluetoothSaveState;
import com.allabrosse1.bomberman.Utils.BluetoothManager.BluetoothServer;
import com.allabrosse1.bomberman.Utils.BluetoothManager.BluetoothSocketManager;
import com.allabrosse1.bomberman.Utils.MonAdapter;
import com.allabrosse1.bomberman.modele.SaveGameState;

import static java.lang.Thread.sleep;

/**
 * Created by diberger on 07/03/18.
 */

public class BluetoothActivity extends AppCompatActivity {
    private Bluetooth bt;
    private final int REQUEST_ENABLE_BT = 10; //The REQUEST_ENABLE_BT constant passed to startActivityForResult() is a locally defined integer that must be greater than 0
    private MonAdapter adapter;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothServer bluetoothServer;
    private BluetoothClient bluetoothClient;
    private ListView lvBT;
    private BluetoothSaveState save;


    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.i("bombermanBT", "onreceive");
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName() != null) {
                    adapter.add(device);
                }
                Log.i("bombermanBT", "Found : " + device.getName() + " Mac : " + device.getAddress());
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        save = BluetoothSaveState.getInstance();
        loadState();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // On enregistre le broadcastReceiver qui sera appelé quand un appareil sera trouvé lors du startDiscovery()
        setContentView(R.layout.bt_select_layout);
        this.lvBT = findViewById(R.id.listViewBT);
        Button btnDialog = findViewById(R.id.btnBTdialog);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBluetoothDialog();
            }
        });
        Button btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGame();
            }
        });
        bt = new Bluetooth();
        this.mBluetoothAdapter = bt.getmBluetoothAdapter();
        adapter = new MonAdapter(getApplicationContext(), bt.getDeviceFound());
        lvBT.setAdapter(adapter);
        checkBluetooth();
        lvBT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = (BluetoothDevice) lvBT.getItemAtPosition(position);
                connectToDevice(device);
            }
        });
        Log.i("bombermanBT", "onCREATEBTACTIVITY");
        super.onCreate(savedInstanceState);
    }

    private void launchGame() {
        SaveGameState save = SaveGameState.getInstance();
        int role = 0;
        BluetoothSocket socket = null;
        //check qui est server et qui est client avant de lancer, si null launch 1player
        if (this.bluetoothServer.isActive()) { // Le joueur est serveur et a accepté une connexion
            //mode2joueurs joueur = joueur 1
            socket = bluetoothServer.getSocket();
        } else {
            if (this.bluetoothClient != null && this.bluetoothClient.isActive()) { // le joueur est client et connecté
                this.bluetoothServer.cancel(); // On stop le serveur du client
                // mode 2 joueurs joueur = joueur2
                socket = bluetoothClient.getSocket();
                role = 1;
            } else {
                //npplayer = mode 1 joueur

            }
        }
        if (socket != null) {
            BluetoothSocketManager socketManager = new BluetoothSocketManager(socket);
            socketManager.setRole(role);
            socketManager.start();
            save.setSocketManager(socketManager);
            //save.setIsAvailable(1);
        }
        if (role == 1) // Si client
        {
            try {
                sleep(1000); // On attend que la map soit syncro
            } catch (Exception e) {
                Log.i("bombermanMap", "pb sleep");
            }
        }
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void connectToDevice(BluetoothDevice device) {
        if (bluetoothClient == null) {
            bluetoothClient = new BluetoothClient(device, mBluetoothAdapter);
            bluetoothClient.start();
        }
        Log.i("bombermanBT", "coucou");
    }

    private void openBluetoothDialog() {
        startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
    }

    private void checkBluetooth() {
        try {
            if (!bt.getmBluetoothAdapter().isEnabled()) { // Si le bluetooth n'est pas activé sur le telephone on l'active

                // Ce bout de code doit etre dans une activité !

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                //throw new Exception("Le bluetooth est desativé !");
            } else {
                activateBluetoothDiscoverable();
            }
        } catch (Exception e) {
            Log.i("bombermanBT", e.getMessage());
        }
    }

    private void activateBluetoothDiscoverable() {
        if (mBluetoothAdapter.isDiscovering()) {
            Log.i("bombermanBT", "Deja entrain de discovering");
            // Bluetooth is already in modo discovery mode, we cancel to restart it again
            mBluetoothAdapter.cancelDiscovery();
        } else {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
            int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            Log.i("bombermanBT", "Start discovering");
            // ne pas oublier
            // bt.getmBluetoothAdapter().cancelDiscovery();
            startHostingBluetoothServer();
        }
        mBluetoothAdapter.startDiscovery();
    }

    private void startHostingBluetoothServer() {
        if (bluetoothServer == null) {
            bluetoothServer = new BluetoothServer(mBluetoothAdapter);
            bluetoothServer.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.i("bombermanBT", "Bluetooth activé !!");
            activateBluetoothDiscoverable();
        } else {
            Log.i("bombermanBT", "Bluetooth non activé");
        }

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        save.setBluetoothClient(bluetoothClient);
        save.setBluetoothServer(bluetoothServer);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.i("bombermanBT", "onResumeBTACTIVITY");
        super.onResume();
    }

    private void loadState() {
        this.bluetoothClient = save.getBluetoothClient();
        this.bluetoothServer = save.getBluetoothServer();
    }
}
