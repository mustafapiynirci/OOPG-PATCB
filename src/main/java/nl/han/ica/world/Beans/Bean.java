package nl.han.ica.world.Beans;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;
import nl.han.ica.world.BeanWorld;
import processing.core.PGraphics;

import java.util.List;

public class Bean extends AnimatedSpriteObject implements ICollidableWithGameObjects, ICollidableWithTiles {

    protected int beanSize;
    protected final BeanWorld world;

    public Bean(BeanWorld world, int beanSize) {
        super(new Sprite("src/main/java/nl/han/ica/world/media/pajaro.png"),2);
        this.beanSize = beanSize;
        this.world=world;
        setySpeed(+beanSize/20f);
        /* De volgende regels zijn in een zelfgekend object nodig
            om collisiondetectie mogelijk te maken.
         */
        setHeight(beanSize);
        setWidth(beanSize);
    }

    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {

    }

    @Override
    public void update() {
        if (getY() >= world.getHeight()) {
            world.deleteGameObject(this);
            System.out.println("Deleted");
        }
    }

    @Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {



    }
}
