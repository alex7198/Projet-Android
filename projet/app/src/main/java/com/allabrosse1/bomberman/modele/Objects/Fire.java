package com.allabrosse1.bomberman.modele.Objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.allabrosse1.bomberman.modele.Drawer;
import com.allabrosse1.bomberman.modele.ObjetGraphique;
import com.allabrosse1.bomberman.modele.Player;

/**
 * Created by MinecraftYolo on 10/03/2018.
 */

public class Fire extends ObjetGraphique {

    public Fire(int positionX, int positionY, Bitmap sprite) {
        super(positionX, positionY, sprite);
    }

    @Override
    public boolean Do(Player player) {
        return false;
    }

    @Override
    public void drawSelf(Canvas c) {
        Drawer.drawFire(this, c);

    }

}
