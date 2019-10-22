package com.allabrosse1.bomberman.Utils;

import com.allabrosse1.bomberman.modele.Player;

/**
 * Created by allabrosse1 on 28/02/18.
 */

public class DownDirection extends Direction {
    @Override
    public void move(Player player) {
        player.setPositionY(player.getPositionY() + 1);
    }
}

