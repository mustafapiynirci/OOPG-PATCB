package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.world.Beans.Bean;

import java.util.List;

public class Spit extends AnimatedSpriteObject implements ICollidableWithGameObjects {

    private int spitSize;
    private BeanWorld world;


    private int thisX;
    private int thisY;

    public Spit(BeanWorld world, int spitSize) {
        super(new Sprite("nl/han/ica/world/media/whiteCircle.png"), 1);
        this.world = world;

        for(GameObject g : world.getGameObjectItems()) {
            if(g instanceof Pajaro) {
                thisX = (int) g.getX();
                thisY = (int) g.getY();
            }
        }

        setHeight(spitSize);
        setWidth(spitSize);

        setSpeed(5);

        this.spitSize = spitSize;
    }


    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for(GameObject g : collidedGameObjects) {
            if(g instanceof Bean) {
                System.out.println("COLLIDED WITH BEAN");
                world.deleteGameObject(g);
                world.deleteGameObject(this);
            }
        }
    }

    @Override
    public void update() {
        setX(getX() - getSpeed());
        setY(getY() - getSpeed());
    }

//    @Override
//    public void draw(PGraphics g) {
//        g.ellipseMode(g.CORNER); // Omdat cirkel anders vanuit midden wordt getekend en dat problemen geeft bij collisiondetectie
//        g.stroke(0, 50, 200, 100);
//        g.fill(255);
//        g.ellipse(thisX, thisY, spitSize, spitSize);

        // eigenlijk speed van spit
//        thisX -= 5;
//        thisY -= 5;
//    }
}
