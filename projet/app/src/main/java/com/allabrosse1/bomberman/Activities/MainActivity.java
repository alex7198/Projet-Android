package com.allabrosse1.bomberman.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.allabrosse1.bomberman.R;
import com.allabrosse1.bomberman.vue.InstructionsDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play = findViewById(R.id.buttonPlay);
        //Button classement = findViewById(R.id.buttonClassement);
        Button btTest = findViewById(R.id.buttonTestBT);
        Button instuctions = findViewById(R.id.buttonIns);
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchGame();
            }
        });

        /*classement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchClassment();
            }
        });*/

        btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBT();
            }
        });

        instuctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstructionsDialog dialog = new InstructionsDialog();
                dialog.show(getSupportFragmentManager(), "InstructionDialog");
            }
        });

    }

    @Override
    protected void onResume() {
        Log.i("onResume", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("onPause", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("onStop", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("onDestroy", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.i("onStart", "onStart");
        super.onStart();

    }

    public void launchGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void launchClassment() {
        Intent intent = new Intent(this, ClassementActivity.class);
        startActivity(intent);
    }

    public void launchBT() {
        Intent intent = new Intent(this, BluetoothActivity.class);
        startActivity(intent);
    }


}
