package com.allabrosse1.bomberman.modele;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by allabrosse1 on 07/02/18.
 */

abstract public class ObjetGraphique {

    protected Point position;
    public Bitmap Sprite;

    public int getPositionX() {
        return this.position.x;
    }

    public int getPositionY() {
        return this.position.y;
    }

    public ObjetGraphique(int positionX, int positionY, Bitmap sprite) {
        position = new Point(positionX, positionY);
        if (sprite != null) {
            this.Sprite = Bitmap.createScaledBitmap(sprite, Drawer.getTailleBlocX(), Drawer.getTailleBlocY(), false);
        }
    }


    public abstract boolean Do(Player player);

    abstract public void drawSelf(Canvas c);
}
