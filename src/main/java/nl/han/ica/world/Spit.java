package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.Beans.RainbowBean;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.util.List;

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
}
