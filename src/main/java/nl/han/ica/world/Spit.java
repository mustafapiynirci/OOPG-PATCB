package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.Beans.RainbowBean;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.util.List;

<<<<<<< HEAD
public class Spit extends AnimatedSpriteObject implements ICollidableWithGameObjects {

    private int spitSize;
    private BeanWorld world;

    private int thisX;
    private int thisY;
    private LookingSide side;

    public Spit(BeanWorld world, int spitSize, LookingSide side) {
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
        this.side = side;
    }


    @Override
    public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
        for(GameObject g : collidedGameObjects) {
            if(g instanceof Bean) {
                world.setCurrentScore(world.getCurrentScore() + ((Bean) g).getScore());
                if(g instanceof RainbowBean) {
                    ((RainbowBean) g).popBean();
                } else {
                    world.deleteGameObject(g);
                }
                world.deleteGameObject(this);
                world.refreshDasboardText();
            }
        }
    }

    @Override
    public void update() {
        if (getY() <= (0 - getHeight()) || getX() <= (0 - getWidth()) || getX() >= (world.width + getWidth())) {
            world.deleteGameObject(this);
        }
        if(side == LookingSide.LEFT) {
            setX(getX() - getSpeed());
        } else if (side == LookingSide.RIGHT) {
            setX(getX() + getSpeed());
        }
    }
=======
public class Spit extends GameObject implements ICollidableWithGameObjects {
	
	private BeanWorld world;
	private LookingSide side;
	private float startX, startY;
	
	public Spit(BeanWorld world, LookingSide side, int spitSize, float x, float y) {
		this.world = world;
		setX(x);
		setY(y);
		startX = x;
		startY = y;
		setHeight(spitSize);
		setWidth(spitSize);
		this.side = side;
		setySpeed(-4);
		setxSpeed(side == LookingSide.LEFT ? -4 : 4);
	}
	
	@Override
	public void draw(PGraphics g) {
		// tongue outline
		g.strokeWeight(16);
		g.stroke(0);
		g.line(getX() + getWidth() / 2, getY() + getHeight() / 2, startX + getWidth() / 2, startY + getHeight() / 2);
		
		// tongue end
		g.fill(0xFFFF73AD);
		g.strokeWeight(4);
		g.stroke(0);
		g.ellipseMode(PConstants.CORNER);
		g.ellipse(getX(), getY(), getWidth(), getHeight());
		
		// tongue inline
		g.strokeWeight(8);
		g.stroke(0xFFFF73AD);
		g.line(getX() + getWidth() / 2, getY() + getHeight() / 2, startX + getWidth() / 2, startY + getHeight() / 2);
	}
	
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g : collidedGameObjects) {
			if (g instanceof Bean) {
				System.out.println(((Bean) g).getScore());
				if (g instanceof RainbowBean) {
					((RainbowBean) g).popBean();
				} else {
					System.out.println("No rainbow");
					world.deleteGameObject(g);
				}
				world.deleteGameObject(this);
			}
		}
	}
	
	@Override
	public void update() {
		if (getY() <= (0 - getHeight()) || getX() <= (0 - getWidth()) || getX() >= (world.width + getWidth())) {
			world.deleteGameObject(this);
		}
	}
>>>>>>> master
}
