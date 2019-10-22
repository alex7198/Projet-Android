package com.allabrosse1.bomberman.modele;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;

/**
 * Created by MinecraftYolo on 03/03/2018.
 */

public class Grid

{
    public static final Integer NB_BLOCK_HORIZONTALE = 25;
    public static final Integer NB_BLOCK_VERTICAL = 14;
    public static Point gridtab[][];

    public Grid(Context c, Point size) {
        if (c.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // Si mode portrait on inverse les X et les Y
            Drawer.setTailleBlocX(size.y / NB_BLOCK_HORIZONTALE); // On divise l'ecran en 25 blocs horizontalement
            Drawer.setTailleBlocY(size.x / NB_BLOCK_VERTICAL); // On divise l'ecran en 15 blocs horizontalement
        } else {
            Drawer.setTailleBlocX(size.x / NB_BLOCK_HORIZONTALE); // On divise l'ecran en 25 blocs horizontalement
            Drawer.setTailleBlocY(size.y / NB_BLOCK_VERTICAL); // On divise l'ecran en 15 blocs horizontalement
        }
    }
}

