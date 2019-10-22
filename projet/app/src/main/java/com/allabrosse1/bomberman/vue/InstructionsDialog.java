package com.allabrosse1.bomberman.vue;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.allabrosse1.bomberman.R;

/**
 * Created by hylow on 25/03/2018.
 */

public class InstructionsDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.instruc_dialog, null));
        builder.setMessage(R.string.btn_param)
                .setPositiveButton(R.string.btn_play, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // nothing :)
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}