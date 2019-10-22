package com.allabrosse1.bomberman.Utils;


import android.util.Log;

import com.allabrosse1.bomberman.modele.Game.GameEngine;
import com.allabrosse1.bomberman.modele.Grid;
import com.allabrosse1.bomberman.modele.Objects.Bombe;
import com.allabrosse1.bomberman.modele.Objects.Fire;
import com.allabrosse1.bomberman.modele.Objects.Wall;
import com.allabrosse1.bomberman.modele.ObjetGraphique;
import com.allabrosse1.bomberman.modele.Player;

import java.util.List;

/**
 * Created by diberger on 14/02/18.
 */

public class CollisionManager {

    public static ObjetGraphique[][] wallTab = new ObjetGraphique[Grid.NB_BLOCK_HORIZONTALE + 1][Grid.NB_BLOCK_VERTICAL + 1];

    public static boolean checkCollision(Player player, List<ObjetGraphique> g) {
        for (int i = 0; i < g.size(); i++) {
            ObjetGraphique objetGraphique = g.get(i);
            if (player.getPositionX() == objetGraphique.getPositionX() && player.getPositionY() == objetGraphique.getPositionY()) {
                return objetGraphique.Do(player);
            }
        }
        return false;
    }

    public static boolean canExploseRight(Bombe b) {
        return CollisionManager.wallTab[b.getPositionX() + 1][b.getPositionY()] == null;
    }

    public static boolean canExploseLeft(Bombe b) {
        return CollisionManager.wallTab[b.getPositionX() - 1][b.getPositionY()] == null;
    }

    public static boolean canExploseUp(Bombe b) {
        return CollisionManager.wallTab[b.getPositionX()][b.getPositionY() - 1] == null;
    }

    public static boolean canExploseDown(Bombe b) {
        return CollisionManager.wallTab[b.getPositionX()][b.getPositionY() + 1] == null;
    }

    public static boolean isInCollisionWithFire(List<ObjetGraphique> objectsList, Player player1, Player player2) {
        ObjetGraphique object = null;
        for (int i = 0; i < objectsList.size(); i++) {
            object = objectsList.get(i);
            if (object instanceof Fire) {
                if ((player1.getPositionX() == object.getPositionX() && player1.getPositionY() == object.getPositionY())) {
                    player1.setAlive(false);
                    return true;
                }
                if (player2 != null) {
                    if ((player2.getPositionX() == object.getPositionX() && player2.getPositionY() == object.getPositionY())) {
                        player2.setAlive(false);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
