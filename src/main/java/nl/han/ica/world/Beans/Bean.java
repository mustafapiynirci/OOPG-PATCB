package nl.han.ica.world.Beans;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Exceptions.TileNotFoundException;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.world.BeanWorld;
import nl.han.ica.world.tiles.BoardsTile;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import java.util.List;

public class Bean extends AnimatedSpriteObject implements ICollidableWithTiles {

    protected int beanSize;
    protected int spriteFrame = 0;
    protected final BeanWorld world;

    public Bean(BeanWorld world, int beanSize, String spriteUrl, int spriteSlices) {
        super(new Sprite(spriteUrl), spriteSlices);
        this.beanSize = beanSize;
        this.world=world;
        setySpeed(+beanSize/15f);
        /* De volgende regels zijn in een zelfgekend object nodig
            om collisiondetectie mogelijk te maken.
         */
        setHeight(beanSize);
        setWidth(beanSize);

        world.getBeans().add(this);
    }

    /**
     * Sprite positie aanpassen zodat het niet begint op 0,0 van het object
     * @param g
     *      Processing object
     */
    @Override
    public void draw(PGraphics g) {
        PImage img = getImage().get(getCurrentFrame().x, getCurrentFrame().y, getCurrentFrame().width, getCurrentFrame().height);
        g.image(img, x - (int) Math.ceil((getHeight() / 2)), y - (int) Math.ceil((getHeight() / 3)));
    }

    @Override
    public void update() {
        if (getY() >= world.getHeight()) {
            world.deleteGameObject(this);
        }
        getScore();
        spriteFrame++;
        if (spriteFrame == 40)
        	spriteFrame = 0;
        setCurrentFrameIndex(spriteFrame/10);
    }

    public int getScore() {
        if(getY() <= (world.height / 100 * 15)) {
            return 1000;
        } else if (getY() <= (world.height / 100 * 30)) {
            return 600;
        } else if (getY() <= (world.height / 100 * 50)) {
            return 300;
        } else if (getY() <= (world.height / 100 * 70)) {
            return 100;
        } else {
            return 50;
        }
    }

    @Override
    public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
        PVector vector;

        for (CollidedTile ct : collidedTiles) {
            if (ct.theTile instanceof BoardsTile) {
                try {
                    vector = world.getTileMap().getTilePixelLocation(ct.theTile);
                    world.getTileMap().setTile((int) vector.x / 32, (int) vector.y / 32, -1);
                } catch (TileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
