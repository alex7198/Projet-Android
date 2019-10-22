package com.allabrosse1.bomberman.vue;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.allabrosse1.bomberman.Utils.ManageInstruction;
import com.allabrosse1.bomberman.modele.Drawer;
import com.allabrosse1.bomberman.modele.Game.GameEngine;
import com.allabrosse1.bomberman.modele.Game.GameLoop;

import com.allabrosse1.bomberman.modele.Grid;
import com.allabrosse1.bomberman.modele.Objects.Bombe;

/**
 * Created by diberger on 31/01/18.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private SurfaceHolder holder;
    public Grid grid;

    public GameEngine getEngine() {
        return engine;
    }

    private GameEngine engine;
    private GameLoop loop;

    private GestureDetectorCompat mDetector;

    public GameView(Context context, Point size) {
        super(context);

        this.getHolder().addCallback(this);
        grid = new Grid(context, size);
        this.holder = this.getHolder();
        engine = new GameEngine(this);
        loop = new GameLoop(this);

        mDetector = new GestureDetectorCompat(getContext(), this);

    }


    public void pause() {
        Context context = getContext();
        loop.setRunning(false);
        Canvas c = holder.lockCanvas();
        if (c != null) {
            Drawer.drawPause(c, context);
            holder.unlockCanvasAndPost(c);
        }
    }

    public void update() {
        if (checkOrientation()) {
            engine.updateGame();
        }
    }

    public boolean checkOrientation() {
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            pause();
            return false;
        }
        return true;
    }

    public void drawGame() {
        Canvas c = holder.lockCanvas();
        //engine.loadState();
        if (c != null) {
            Context context = getContext();
            Drawer.drawInterface(c, context);
            if (engine.getObjetGraphiqueList() != null) {
                Drawer.drawObjetGraphique(engine.getObjetGraphiqueList(), c);
            }
            Drawer.drawPlayer(engine.getPlayer1(), c);
            if (engine.getPlayer2() != null) {
                Drawer.drawPlayer(engine.getPlayer2(), c);
            }
            holder.unlockCanvasAndPost(c);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        ManageInstruction.MoovePlayer(e1, e2, engine.getPlayer1(), engine.getObjetGraphiqueList(), getContext());
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.engine.loadState();
        if (checkOrientation()) {
            this.drawGame();
            try {
                if (!loop.isAlive()) {
                    loop.start();
                }
            } catch (IllegalThreadStateException e) {
                loop.interrupt();
                loop = new GameLoop(this);
                loop.start();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        engine.saveState();
        loop.interrupt();
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Bombe bombe = new Bombe(engine.getCurrentPos().x, engine.getCurrentPos().y, GameEngine.bombImg, 1);
        engine.addBomb(bombe);
        Log.i("bombermanBOMBE", "bombe posé");
        return false;
    }


    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

}
