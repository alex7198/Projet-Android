package com.allabrosse1.bomberman.modele;

import android.util.Log;

import com.allabrosse1.bomberman.Utils.BluetoothManager.BluetoothSocketManager;

import java.util.List;

/**
 * Created by diberger on 28/02/18.
 */

public class SaveGameState {
    private Integer isAvailable = 0;
    private Player joueur1;
    private Player joueur2;
    private ObjetGraphique bombe;
    private BluetoothSocketManager socket;
    private static SaveGameState uniqueInstance;
    List<ObjetGraphique> objetGraphiqueList;
    private SaveGameState() {

    }

    public static SaveGameState getInstance() {
        if (SaveGameState.uniqueInstance == null) {
            SaveGameState.uniqueInstance = new SaveGameState();
        }
        return SaveGameState.uniqueInstance;
    }

    public Player getJoueur1() {
        return this.joueur1;
    }

    public Player getJoueur2() {
        return this.joueur2;
    }

    public void setJoueur1(Player joueur) {
        this.joueur1 = joueur;
    }

    public void setJoueur2(Player joueur) {
        this.joueur2 = joueur;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        Log.i("bomberman", "jeu reset");
        this.isAvailable = isAvailable;
    }

    public ObjetGraphique getBombe() {
        return bombe;
    } //sauve pas toute les bombes a modifier !

    public void setBombe(ObjetGraphique bombe) {
        this.bombe = bombe;
    }

    public List<ObjetGraphique> getObjetGraphiqueList() {
        return objetGraphiqueList;
    }

    public void setObjetGraphiqueList(List<ObjetGraphique> l) {
        this.objetGraphiqueList = l;
    }

    public BluetoothSocketManager getSocketManager() {
        return socket;
    }

    public void setSocketManager(BluetoothSocketManager socket) {
        this.socket = socket;
    }
}
