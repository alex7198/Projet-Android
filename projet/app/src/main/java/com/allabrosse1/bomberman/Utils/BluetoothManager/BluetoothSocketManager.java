package com.allabrosse1.bomberman.Utils.BluetoothManager;

import android.bluetooth.BluetoothSocket;
import android.graphics.Point;
import android.util.Log;

import com.allabrosse1.bomberman.modele.Game.GameEngine;
import com.allabrosse1.bomberman.modele.Grid;
import com.allabrosse1.bomberman.modele.Objects.Bombe;
import com.allabrosse1.bomberman.modele.Objects.Obstacle;
import com.allabrosse1.bomberman.modele.ObjetGraphique;
import com.allabrosse1.bomberman.modele.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hylow on 11/03/2018.
 */

public class BluetoothSocketManager extends Thread {

    private BluetoothSocket bluetoothSocket;
    private OutputStream out;
    private InputStream in;
    byte[] buffer;
    private Point enemyPosition;
    private List<Bombe> enemyBombs = new ArrayList<>();
    private List<ObjetGraphique> listObstacle;

    private int role; // 0 = server, 1 = client

    public BluetoothSocketManager(BluetoothSocket socket) {
        this.bluetoothSocket = socket;
        listObstacle = new ArrayList<>();
        try {
            out = bluetoothSocket.getOutputStream();
            in = bluetoothSocket.getInputStream();
        } catch (IOException e) {
            Log.i("bombermanBT", "IOEXCEPTION");
        }
    }

    @Override
    public void run() {
        buffer = new byte[512];
        enemyPosition = new Point();
        int numBytes; // bytes returned from read()


        while (true) {
            try {
                numBytes = in.read(buffer);
                handleRequest(new String(buffer, 0, numBytes));
                sleep(150);
            } catch (Exception e) {
                //Log.i("bombermanBT", "IOEXCEPTION while reading");
            }
        }

    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        if (role < 0 || role > 1) {
            Log.i("bombermanBT", "Tentative de set un role non valide a la socket manager");
            return;
        }
        this.role = role;
    }

    public void handleRequest(String request) throws Exception {
        Integer x, y;
        String splitRequest[] = request.split(" ");
        String type = splitRequest[0];
        if (!type.equals("POSITION")) {
            Log.i("bombermanMAP", "recu " + request);
        }
        switch (type) {
            case "POSITION":
                x = Integer.valueOf(splitRequest[1]);
                y = Integer.valueOf(splitRequest[2]);
                if (x > Grid.NB_BLOCK_HORIZONTALE || y > Grid.NB_BLOCK_VERTICAL || x < 0 || y < 0) {
                    throw new Exception("Données recu corrompu !");
                }
                enemyPosition = new Point(x, y);
                //Log.i("bombermanBT", "Recu " + enemyPosition);
                break;

            case "BOMBE":
                x = Integer.valueOf(splitRequest[1]);
                y = Integer.valueOf(splitRequest[2]);
                if (x > Grid.NB_BLOCK_HORIZONTALE || y > Grid.NB_BLOCK_VERTICAL || x < 0 || y < 0) {
                    throw new Exception("Données recu corrompu !");
                }
                enemyPosition = new Point(x, y);
                this.addEnemyBomb(x, y);
                Log.i("bombermanBT", "Recu bombe" + enemyPosition);
                break;
            case "OBSTACLES":
                Log.i("bombermanMAP", "case objstacles ducou" + request);
                Integer s = splitRequest.length;
                Log.i("bombermanMAP", s.toString());
                for (int i = 1; i < splitRequest.length; i += 2) {
                    try {
                        x = Integer.valueOf(splitRequest[i]);
                        y = Integer.valueOf(splitRequest[i + 1]);
                        if (x > Grid.NB_BLOCK_HORIZONTALE || y > Grid.NB_BLOCK_VERTICAL || x < 0 || y < 0) {
                            throw new Exception("Données recu corrompu !");
                        }
                        Log.i("bombermanMAP", x.toString() + " " + y.toString());
                        this.listObstacle.add(new Obstacle(x, y, null));
                    } catch (Exception e) {
                        Log.i("bombermanMAP", e.getMessage());
                    }
                }
                break;
        }
    }


    public void sendPlayerPosition(Player player) {
        String content = "POSITION " + player.getPositionX() + " " + player.getPositionY();
        try {
            out.write(content.getBytes());
            sleep(150);
        } catch (Exception e) {
            Log.i("bombermanBT", "Probleme ecriture au serveur bt !");
        }
    }

    public void publishBomb(Bombe bombe) {
        String content = "BOMBE " + bombe.getPositionX() + " " + bombe.getPositionY();
        try {
            out.write(content.getBytes());
        } catch (IOException e) {
            Log.i("bombermanBT", "Probleme ecriture au serveur bt d'une bombe !");
        }
    }

    public Point getEnemyPosition() {
        return enemyPosition;
    }

    private void addEnemyBomb(int x, int y) {
        this.enemyBombs.add(new Bombe(x, y, GameEngine.bombImg, 1));
    }

    public List<Bombe> getEnemyBombs() {
        return enemyBombs;
    }

    public void publishAllObstacles(List<ObjetGraphique> liObject) {
        String content = "OBSTACLES ";
        for (ObjetGraphique obj : liObject) {
            if (obj instanceof Obstacle) {
                content += obj.getPositionX() + " " + obj.getPositionY() + " ";
            }
        }
        try {
            out.write(content.getBytes());
            Log.i("bombermanMAP", content);
        } catch (IOException e) {
            Log.i("bombermanBT", "Probleme ecriture au serveur bt des obstacles !");
        }
    }

    public List<ObjetGraphique> getListObstacle() {
        return listObstacle;
    }

}
