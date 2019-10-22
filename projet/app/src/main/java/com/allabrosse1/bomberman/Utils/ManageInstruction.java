package com.allabrosse1.bomberman.Utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.MotionEvent;

import com.allabrosse1.bomberman.modele.ObjetGraphique;
import com.allabrosse1.bomberman.modele.Player;

import java.util.List;

/**
 * Created by MinecraftYolo on 04/03/2018.
 */

public class ManageInstruction {

    private static Point oldPosition;

    public static void MoovePlayer(MotionEvent e1, MotionEvent e2, Player p, List<ObjetGraphique> objetGraphiqueList, Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // Si mode pause on empeche le joueur de bouger
            return;
        }

        oldPosition = new Point(p.getPositionX(), p.getPositionY());
        Direction direction;

        if (Instruction.upInstruction(e1, e2)) {
            direction = new UpDirection();
            direction.move(p);
            commitMove(p, objetGraphiqueList);
            return;
        }

        if (Instruction.downInstruction(e1, e2)) {
            direction = new DownDirection();
            direction.move(p);
            commitMove(p, objetGraphiqueList);
            return;
        }

        if (Instruction.leftInstruction(e1, e2)) {
            direction = new LeftDirection();
            direction.move(p);
            commitMove(p, objetGraphiqueList);
            return;
        }

        if (Instruction.rightInstruction(e1, e2)) {
            direction = new RightDirection();
            direction.move(p);
            commitMove(p, objetGraphiqueList);
        }
    }

    private static void commitMove(Player p, List<ObjetGraphique> objetGraphiqueList) {
        if (CollisionManager.checkCollision(p, objetGraphiqueList)) { // Si une collision est détecté on "undo" le move
            p.setPositionX(oldPosition.x);
            p.setPositionY(oldPosition.y);
        }
    }

}
