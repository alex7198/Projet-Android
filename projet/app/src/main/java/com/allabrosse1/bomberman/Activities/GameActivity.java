package com.allabrosse1.bomberman.Activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.allabrosse1.bomberman.R;
import com.allabrosse1.bomberman.modele.Drawer;
import com.allabrosse1.bomberman.modele.Game.GameEngine;
import com.allabrosse1.bomberman.modele.Grid;
import com.allabrosse1.bomberman.modele.SaveGameState;
import com.allabrosse1.bomberman.vue.GameView;


/**
 * Created by diberger on 31/01/18.
 */

public class GameActivity extends AppCompatActivity {
    public GameView gameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        Drawer.setScreenSize(size);
        gameView = new GameView(getApplicationContext(), size);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Drawer.populateGrid(gameView.grid, Grid.NB_BLOCK_HORIZONTALE, Grid.NB_BLOCK_VERTICAL);
        GameEngine.bombermanImg = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bomberman_face);
        GameEngine.wallImg = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.wall);
        GameEngine.bombImg = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bombe);
        GameEngine.herbeBG = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.herbebg);
        GameEngine.obstacle = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.obstacle);
        GameEngine.fire = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.explosion);
        setContentView(gameView);
        isPlayerDead();

    }


    public void isPlayerDead() {
        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    while (true) {
                        sleep(1000);
                        if (!gameView.getEngine().checkPlayersAlive()) {
                            launchGameOver();
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }


    public void launchGameOver() {
        SaveGameState.getInstance().setIsAvailable(0); // Si le joueur relance une autre partie tout sera regenéré
        Intent intent = new Intent(this, GameOverActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        Log.i("com.allabrosse1.bomberman", "GameActivityOnStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i("com.allabrosse1.bomberman", "GameActivityOnStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("com.allabrosse1.bomberman", "GameActivityDestroy");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.i("com.allabrosse1.bomberman", "GameActivityOnResume");
        super.onResume();
    }
}
