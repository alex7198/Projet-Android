package com.allabrosse1.bomberman.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.allabrosse1.bomberman.R;


public class GameOverActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
    }

    public void onClickBtn(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
