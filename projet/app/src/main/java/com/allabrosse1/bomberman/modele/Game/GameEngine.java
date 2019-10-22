package com.allabrosse1.bomberman.modele.Game;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

import com.allabrosse1.bomberman.Utils.BluetoothManager.BluetoothSocketManager;
import com.allabrosse1.bomberman.Utils.CollisionManager;
import com.allabrosse1.bomberman.modele.Drawer;
import com.allabrosse1.bomberman.modele.Grid;
import com.allabrosse1.bomberman.modele.Objects.Bombe;
import com.allabrosse1.bomberman.modele.Objects.Obstacle;
import com.allabrosse1.bomberman.modele.ObjetGraphique;
import com.allabrosse1.bomberman.modele.PutterBomb;
import com.allabrosse1.bomberman.modele.SaveGameState;
import com.allabrosse1.bomberman.modele.Objects.Wall;
import com.allabrosse1.bomberman.modele.Player;
import com.allabrosse1.bomberman.vue.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by diberger on 31/01/18.
 */

public class GameEngine {


    public static Bitmap bombermanImg;
    public static Bitmap wallImg;
    public static Bitmap bombImg;
    public static Bitmap herbeBG;
    public static Bitmap obstacle;
    public static Bitmap fire;

    public List<ObjetGraphique> objetGraphiqueList;

    private Player player1 = null;

    private Player player2 = null;

    private SaveGameState saveGameState;
    private Point CurrentPos;

    private BluetoothSocketManager socketManager = null;

    private GameView view;

    public GameEngine(GameView view) {
        objetGraphiqueList = new ArrayList<>();
        this.saveGameState = SaveGameState.getInstance();
        this.socketManager = saveGameState.getSocketManager();
        this.view = view;
    }

    public void updateGame() {
        if (socketManager != null) {
            player2.setPositionX(socketManager.getEnemyPosition().x);
            player2.setPositionY(socketManager.getEnemyPosition().y);
            socketManager.sendPlayerPosition(player1);
            addEnemyBombsToObjectList();
        }
        this.setCurrentPos(new Point(player1.getPositionX(), player1.getPositionY()));
        checkPlayersAlive();

        view.drawGame();
    }

    public void saveState() {
        if (player1.isAlive()) {
            if (player2 == null || player2.isAlive()) {
                saveGameState.setJoueur1(player1);
                saveGameState.setJoueur2(player2);
                saveGameState.setObjetGraphiqueList(objetGraphiqueList);
                saveGameState.setSocketManager(socketManager);
                saveGameState.setIsAvailable(1);
            }
        }
    }

    public Point getCurrentPos() {
        return CurrentPos;
    }


    public void setCurrentPos(Point currentPos) {
        CurrentPos = currentPos;
    }

    public void loadState() {
        Log.i("bombermanBT", "loadstate" + saveGameState.getIsAvailable());
        if (saveGameState.getIsAvailable() == 1) {
            this.player1 = saveGameState.getJoueur1();
            this.player2 = saveGameState.getJoueur2();
            this.objetGraphiqueList = saveGameState.getObjetGraphiqueList();
        } else {
            initializePlayers();
            //On genere les obstacles
            for (int l = 1; l < Grid.NB_BLOCK_VERTICAL - 2; l++) {
                Random r = new Random();
                int i = 0;
                while (i < 10) {
                    int random_column = r.nextInt(Grid.NB_BLOCK_HORIZONTALE - 4) + 2;
                    while (random_column == 1 && l == 1) {
                        random_column = r.nextInt(Grid.NB_BLOCK_HORIZONTALE - 4) + 2;
                    }
                    this.objetGraphiqueList.add(new Obstacle(random_column, l, obstacle));
                    i++;
                }
                l++;
            }
            // Le code ci-dessous pour avoir la meme map sur les 2 appareils
            if (this.socketManager != null) {
                if (socketManager.getListObstacle().size() != 0) {
                    this.objetGraphiqueList.clear();
                    getObstaclesFromServer();
                } else {
                    publishObstaclesToServer();
                }
            }
            // On genere les murs durs
            for (int i = 0; i < Grid.gridtab.length; i++) {
                ObjetGraphique firstWall = new Wall(i, 0, wallImg);
                ObjetGraphique secondWall = new Wall(i, Grid.NB_BLOCK_VERTICAL - 1, wallImg);
                this.objetGraphiqueList.add(firstWall);
                this.objetGraphiqueList.add(secondWall);
                CollisionManager.wallTab[i][0] = firstWall;
                CollisionManager.wallTab[i][Grid.NB_BLOCK_VERTICAL - 1] = secondWall;
                if (i == 0 || i == Grid.NB_BLOCK_HORIZONTALE - 1 || i == Grid.NB_BLOCK_HORIZONTALE) {
                    for (int j = 0; j < Grid.NB_BLOCK_VERTICAL + 1; j++) {
                        ObjetGraphique wall = new Wall(i, j, wallImg);
                        this.objetGraphiqueList.add(wall);
                        CollisionManager.wallTab[i][j] = wall;
                    }
                }
            }
            for (int l = 2; l < Grid.NB_BLOCK_VERTICAL; l++) {
                for (int i = 1; i < Grid.gridtab.length - 1; i++) {
                    ObjetGraphique wall = new Wall(i + 1, l, wallImg);
                    CollisionManager.wallTab[i + 1][l] = wall;
                    this.objetGraphiqueList.add(wall);
                    i++;
                }
                l++;
            }
        }
    }

    private void initializePlayers() {
        if (this.socketManager != null) {
            if (this.socketManager.getRole() == 0) {
                player2 = new Player(Grid.NB_BLOCK_HORIZONTALE,
                        Grid.NB_BLOCK_VERTICAL - 2,
                        bombermanImg, "ElPlayor2", 0);
            } else {
                player1 = new Player(Grid.NB_BLOCK_HORIZONTALE - 2,
                        Grid.NB_BLOCK_VERTICAL - 2,
                        bombermanImg, "ElPlayor", 0);
                player2 = new Player(1, 1, bombermanImg, "ElPlayor2", 0);
                Log.i("bombermanBT", "player 2 fait !!!!!!!!!!!!!!!");
                return;
            }
        }
        player1 = new Player(1, 1, bombermanImg, "ElPlayor", 0);
    }


    public List<ObjetGraphique> getObjetGraphiqueList() {
        return objetGraphiqueList;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private void addEnemyBombsToObjectList() {
        for (Bombe bombe : this.socketManager.getEnemyBombs()) {
            PutterBomb.putBomb(bombe, player2, objetGraphiqueList);
        }
        this.socketManager.getEnemyBombs().clear();
    }

    private void getObstaclesFromServer() {
        for (ObjetGraphique obj : this.socketManager.getListObstacle()) {
            Obstacle obstacle = new Obstacle(obj.getPositionX(), obj.getPositionY(), GameEngine.obstacle);
            this.objetGraphiqueList.add(obstacle);
        }
    }

    private void publishObstaclesToServer() {
        this.socketManager.publishAllObstacles(objetGraphiqueList);
    }

    public void addBomb(Bombe bombe) {
        PutterBomb.putBomb(bombe, player1, objetGraphiqueList);
        if (socketManager != null) { // Si mode 2 joueurs on communique le addBombe au serveur bluetooth
            socketManager.publishBomb(bombe);
        }
    }

    public boolean checkPlayersAlive() {
        if (CollisionManager.isInCollisionWithFire(objetGraphiqueList, player1, player2)) {
            return false;
        }
        return true;
    }
}
