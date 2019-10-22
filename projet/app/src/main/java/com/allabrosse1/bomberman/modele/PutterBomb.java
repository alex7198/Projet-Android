package com.allabrosse1.bomberman.modele;

import android.os.Handler;
import android.os.Looper;

import com.allabrosse1.bomberman.modele.Objects.Bombe;

import java.util.List;

/**
 * Created by MinecraftYolo on 10/03/2018.
 */

public class PutterBomb {

    public static void putBomb(final Bombe bombe, final Player p, final List<ObjetGraphique> objetGraphiqueList) {
        if (p.getNbBombePose() < p.getNbBombeAPose()) {
            p.setNbBombePose(p.getNbBombePose() + 1);
            objetGraphiqueList.add(bombe);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                public void run() {
                    bombe.explode(objetGraphiqueList);
                    objetGraphiqueList.remove(bombe);
                    p.setNbBombePose(p.getNbBombePose() - 1);
                }
            }, Bombe.COMPTEAREBOURS * 1000);

        }
    }
}
