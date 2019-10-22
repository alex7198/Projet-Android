package com.allabrosse1.bomberman.modele.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.allabrosse1.bomberman.Utils.CollisionManager;
import com.allabrosse1.bomberman.modele.Drawer;
import com.allabrosse1.bomberman.modele.Game.GameEngine;
import com.allabrosse1.bomberman.modele.ObjetGraphique;
import com.allabrosse1.bomberman.modele.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by allabrosse1 on 07/02/18.
 */

public class Bombe extends ObjetGraphique {

    public static final int COMPTEAREBOURS = 2;
    private int tailleExplosion;


    public Bombe(int positionX, int positionY, Bitmap sprite, int tailleExplosion) {
        super(positionX, positionY, sprite);
        this.tailleExplosion = tailleExplosion;

    }


    @Override
    public boolean Do(Player player) {

        return true;
    }


    public void explode(final List<ObjetGraphique> l) {
        final Fire fire = new Fire(this.getPositionX(), this.getPositionY(), GameEngine.fire);
        final Fire fireRight = new Fire(this.getPositionX() + 1, this.getPositionY(), GameEngine.fire);
        final Fire fireLeft = new Fire(this.getPositionX() - 1, this.getPositionY(), GameEngine.fire);
        final Fire fireUp = new Fire(this.getPositionX(), this.getPositionY() - 1, GameEngine.fire);
        final Fire fireDown = new Fire(this.getPositionX(), this.getPositionY() + 1, GameEngine.fire);
        final List<ObjetGraphique> toAdd = new LinkedList<>();

        toAdd.add(fire);

        ObjetGraphique object = null;

        // Add a fire image if there is a wall
        if (CollisionManager.canExploseRight(this)) {
            toAdd.add(fireRight);
        }
        if (CollisionManager.canExploseLeft(this)) {
            toAdd.add(fireLeft);
        }
        if (CollisionManager.canExploseUp(this)) {
            toAdd.add(fireUp);
        }
        if (CollisionManager.canExploseDown(this)) {
            toAdd.add(fireDown);
        }

        for (int i = 0; i < l.size(); i++) {

            object = l.get(i);
            // Remove the obstacle because it'll explode
            if (object instanceof Obstacle) {
                if ((object.getPositionX() == this.getPositionX() && object.getPositionY() == this.getPositionY() - 1)
                        || (object.getPositionX() == this.getPositionX() + 1 && object.getPositionY() == this.getPositionY())
                        || (object.getPositionY() == this.getPositionY() + 1 && object.getPositionX() == this.getPositionX())
                        || (object.getPositionX() == this.getPositionX() - 1 && object.getPositionY() == this.getPositionY())) {
                    l.remove(object);
                }
            }
        }

        // Add the fire
        l.addAll(toAdd);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            public void run() {
                l.removeAll(toAdd);
            }
        }, 1500);
    }

    public void drawSelf(Canvas c) {
        Drawer.drawBombe(this, c);
    }

}
