package com.allabrosse1.bomberman.modele;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by allabrosse1 on 14/02/18.
 */

public class Player extends ObjetGraphique {

    private String player;
    private int score;
    private int nbBombeAPose = 3;
    private int nbBombePose = 0;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    private boolean isAlive = true;

    public void setNbBombePose(int nb) {
        this.nbBombePose = nb;
    }

    public Player(int positionX, int positionY, Bitmap sprite, String player, int score) {
        super(positionX, positionY, sprite);
        this.player = player;
        this.score = score;
    }


    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public void setPositionX(int x) {
        this.position.x = x;
    }

    public void setPositionY(int y) {
        this.position.y = y;
    }


    @Override
    public boolean Do(Player player) {
        return false;
    }

    public void drawSelf(Canvas c) {
        Drawer.drawPlayer(this, c);
    }

    public int getNbBombeAPose() {
        return nbBombeAPose;
    }

    public int getNbBombePose() {
        return nbBombePose;
    }

}
