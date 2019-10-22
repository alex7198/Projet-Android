package com.allabrosse1.bomberman.modele.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.allabrosse1.bomberman.modele.ObjetGraphique;
import com.allabrosse1.bomberman.modele.Player;

/**
 * Created by allabrosse1 on 14/03/18.
 */

public class Obstacle extends ObjetGraphique {
    public Obstacle(int positionX, int positionY, Bitmap sprite) {
        super(positionX, positionY, sprite);
    }

    @Override
    public boolean Do(Player player) {
        return true;
    }

    @Override
    public void drawSelf(Canvas c) {

    }
}
