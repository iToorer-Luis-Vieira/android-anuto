package ch.bfh.anuto.game.objects;
import android.content.res.Resources;

import ch.bfh.anuto.game.Enemy;
import ch.bfh.anuto.game.Shot;
import ch.bfh.anuto.game.Tower;

public class ShockWave extends Shot{
    // To be implemented ...

    public ShockWave(Tower owner, Enemy target) {
        super(owner, target);
    }

    @Override
    public void initResources(Resources res) {

    }
}
