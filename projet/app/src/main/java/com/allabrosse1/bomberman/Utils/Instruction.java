package com.allabrosse1.bomberman.Utils;

import android.view.MotionEvent;

/**
 * Created by allabrosse1 on 27/02/18.
 */

public class Instruction {

    public static boolean upInstruction(MotionEvent e1, MotionEvent e2) {
        return e1.getY() - e2.getY() > 80;
    }

    public static boolean downInstruction(MotionEvent e1, MotionEvent e2) {
        return e2.getY() - e1.getY() >= 80;
    }


    public static boolean rightInstruction(MotionEvent e1, MotionEvent e2) {
        return e2.getX() - e1.getX() >= 80;
    }


    public static boolean leftInstruction(MotionEvent e1, MotionEvent e2) {
        return e1.getX() - e2.getX() >= 80;
    }


}
