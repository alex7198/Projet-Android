package com.allabrosse1.bomberman.modele;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.allabrosse1.bomberman.R;
import com.allabrosse1.bomberman.modele.Objects.Bombe;
import com.allabrosse1.bomberman.modele.Objects.Fire;
import com.allabrosse1.bomberman.modele.Objects.Wall;

import java.util.List;

/**
 * Created by diberger on 07/02/18.
 */

public class Drawer {


    private static Point screenSize;
    private static int tailleBlocX;
    private static int tailleBlocY;


    public static Point getScreenSize() {
        return screenSize;
    }

    public static void setScreenSize(Point screenSize) {
        Drawer.screenSize = screenSize;
    }

    public static int getTailleBlocX() {
        return tailleBlocX;
    }

    public static void setTailleBlocX(int tailleBlocX) {
        Drawer.tailleBlocX = tailleBlocX;
    }

    public static int getTailleBlocY() {
        return tailleBlocY;
    }

    public static void setTailleBlocY(int tailleBlocY) {
        Drawer.tailleBlocY = tailleBlocY;
    }


    public static void drawInterface(Canvas c, Context context) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(tailleBlocX);
        c.drawColor(Color.argb(255, 120, 197, 87)); //Background drawing
        c.drawText(context.getResources().getText(R.string.app_name).toString(), (float) (screenSize.x / 2.72), (float) (screenSize.y / 20), paint);
    }

    public static void drawPause(Canvas c, Context context) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize((int) (tailleBlocX / 2));
        c.drawColor(Color.argb(255, 120, 197, 87)); //Background drawing
        c.drawText(context.getResources().getText(R.string.pauseActivate).toString(), (float) (screenSize.x / 8.5), (float) (screenSize.y / 2.2), paint);
        c.drawText(context.getResources().getText(R.string.howResume).toString(), (float) (screenSize.x / 8.3), (float) (screenSize.y / 2.1), paint);
    }

    /*public static void drawObject(ObjetGraphique obj, Canvas c){
        Paint paint = new Paint();
        paint.setColor(Color.argb(255,0,0,0));
        c.drawBitmap(bombImg,obj.positionX, obj.positionY, paint);
    }*/

    public static void drawPlayer(Player player, Canvas c) {
        Paint a = new Paint();
        a.setAntiAlias(true);
        c.drawBitmap(player.Sprite, Grid.gridtab[player.getPositionX()][player.getPositionY()].x,
                Grid.gridtab[player.getPositionX()][player.getPositionY()].y, a);
    }

    public static void drawBombe(Bombe bombe, Canvas c) {
        Paint a = new Paint();
        a.setAntiAlias(true);
        c.drawBitmap(bombe.Sprite, Grid.gridtab[bombe.getPositionX()][bombe.getPositionY()].x,
                Grid.gridtab[bombe.getPositionX()][bombe.getPositionY()].y, a);
    }

    public static void drawWall(Wall wall, Canvas c) {
        Paint a = new Paint();
        a.setAntiAlias(true);
        c.drawBitmap(wall.Sprite, Grid.gridtab[wall.getPositionX()][wall.getPositionY()].x,
                Grid.gridtab[wall.getPositionX()][wall.getPositionY()].y, a);
    }


    public static void drawFire(Fire fire, Canvas c) {
        Paint a = new Paint();
        a.setAntiAlias(true);
        c.drawBitmap(fire.Sprite, Grid.gridtab[fire.getPositionX()][fire.getPositionY()].x,
                Grid.gridtab[fire.getPositionX()][fire.getPositionY()].y, a);
    }

    public static void drawObjetGraphique(List<ObjetGraphique> objetGraphiqueList, Canvas c) {
        Paint a = new Paint();
        a.setAntiAlias(true);
        try {
            for (int i = 0; i < objetGraphiqueList.size(); i++) {
                if (objetGraphiqueList.get(i) != null) {
                    c.drawBitmap(objetGraphiqueList.get(i).Sprite,
                            Grid.gridtab[objetGraphiqueList.get(i).getPositionX()][objetGraphiqueList.get(i).getPositionY()].x,
                            Grid.gridtab[objetGraphiqueList.get(i).getPositionX()][objetGraphiqueList.get(i).getPositionY()].y, a);
                }
            }
        } catch (Exception e) {
            Log.i("bomberman", "drawObjetGraphique: Can't draw that one");
        }
    }


    public static void populateGrid(Grid g, int NB_BLOCK_HORIZONTALE, int NB_BLOCK_VERTICAL) {
        g.gridtab = new Point[NB_BLOCK_HORIZONTALE + 1][NB_BLOCK_VERTICAL + 1];
        for (int i = 0; i < NB_BLOCK_HORIZONTALE + 1; i++) {
            for (int j = 0; j < NB_BLOCK_VERTICAL + 1; j++) {
                g.gridtab[i][j] = new Point(i * Drawer.getTailleBlocX(), j * Drawer.getTailleBlocY());
            }
        }
        //g.gridtab[5][2]=new  Point(1 * Drawer.getTailleBlocX(), 2 * Drawer.getTailleBlocY());
    }


}
