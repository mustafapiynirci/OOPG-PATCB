package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.Beans.RainbowBean;

import java.util.List;

public class Spit extends AnimatedSpriteObject implements ICollidableWithGameObjects {
	
	private BeanWorld world;
	private LookingSide side;
	
	public Spit(BeanWorld world, LookingSide side, int spitSize, float x, float y) {
		super(new Sprite("nl/han/ica/world/media/whiteCircle.png"), 1);
		this.world = world;
		setX(x);
		setY(y);
		setHeight(spitSize);
		setWidth(spitSize);
		setSpeed(5);
		this.side = side;
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
		if (side == LookingSide.LEFT) {
			setX(getX() - getSpeed());
		} else if (side == LookingSide.RIGHT) {
			setX(getX() + getSpeed());
		}
	}
}
