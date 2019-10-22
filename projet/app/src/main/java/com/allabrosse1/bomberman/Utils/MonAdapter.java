package com.allabrosse1.bomberman.Utils;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.allabrosse1.bomberman.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hylow on 07/03/2018.
 */

public class MonAdapter extends ArrayAdapter<BluetoothDevice> {

    private List<BluetoothDevice> deviceList = new ArrayList<>();

    public MonAdapter(@NonNull Context context, ArrayList<BluetoothDevice> list) {
        super(context, 0, list);
        this.deviceList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_item_layout, parent, false);
        TextView item_nom = convertView.findViewById(R.id.item_nom);
        BluetoothDevice device = this.deviceList.get(position);
        item_nom.setText(device.getName());
        return convertView;
    }
}
