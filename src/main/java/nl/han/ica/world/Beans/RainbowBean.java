package nl.han.ica.world.Beans;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.world.BeanWorld;

public class RainbowBean extends Bean {

    public RainbowBean(BeanWorld world, int beanSize) {
        super(world, beanSize, "nl/han/ica/world/media/rainbowbean.png", 4);
        setCurrentFrameIndex(0);
    }

    public void popBean() {
        for(GameObject g : world.getBeans()) {
            world.setCurrentScore(world.getCurrentScore() + getScore());
            world.deleteGameObject(g);
        }
        world.resetTileMap();
    }
}
