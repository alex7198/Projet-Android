package com.allabrosse1.bomberman.modele.Game;

import com.allabrosse1.bomberman.vue.GameView;

/**
 * Created by diberger on 31/01/18.
 */

public class GameLoop extends Thread {
    private int fps = 60;
    // Si  on veut X image en une seconde (1000ms)
    private int ticks = 1000 / this.fps;
    private GameView view;
    private boolean running = true;

    public GameLoop(GameView view) {
        this.view = view;
    }

    @Override
    public void run() {
        long startTime;
        long sleepTime;
        while (running) {
            startTime = System.currentTimeMillis();
            view.update();
            sleepTime = ticks - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime >= 0) {
                    sleep(sleepTime);
                }
            } catch (Exception e) {
                //Boucle interrompu
            }
        }
    }

    public void setRunning(boolean run) {
        this.running = run;
    }
}
