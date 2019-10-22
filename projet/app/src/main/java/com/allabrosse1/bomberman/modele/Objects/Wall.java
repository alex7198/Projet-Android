package com.allabrosse1.bomberman.modele.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.allabrosse1.bomberman.modele.Drawer;
import com.allabrosse1.bomberman.modele.ObjetGraphique;
import com.allabrosse1.bomberman.modele.Player;

/**
 * Created by diberger on 14/02/18.
 */

public class Wall extends ObjetGraphique {
    public Wall(int positionX, int positionY, Bitmap sprite) {
        super(positionX, positionY, sprite);
    }

    @Override
    public boolean Do(Player player) {

        return true;
    }

    public void drawSelf(Canvas c) {
        Drawer.drawWall(this, c);
    }


}
