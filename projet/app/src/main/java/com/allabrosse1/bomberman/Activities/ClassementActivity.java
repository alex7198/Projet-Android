package com.allabrosse1.bomberman.Activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import com.allabrosse1.bomberman.modele.Drawer;
import com.allabrosse1.bomberman.vue.GameView;

/**
 * Created by allabrosse1 on 14/02/18.
 */

public class ClassementActivity extends AppCompatActivity {

    public GameView gameView;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //gameView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gameView = new GameView(getApplicationContext());
        setContentView(gameView);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Drawer.setScreenSize(size);
        //TEST
        /*List<Player> playerList=new ArrayList<Player>();
        playerList.add(new Player("Jean Claude",50));
        playerList.add(new Player("Jean Didier",120));
        playerList.add(new Player("Jean Luc",250));
        playerList.add(new Player("Jean Dimitri",0));
        playerList.add(new Player("Jean Dorian",3));*/

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
