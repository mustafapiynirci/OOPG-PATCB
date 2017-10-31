package nl.han.ica.world.player;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.world.BeanWorld;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.player.LookingSide;
import nl.han.ica.world.player.Pajaro;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.util.List;

/**
 * @author Jesse Oukes & Mustafa Piynirci
 * Tongue of Pájaro
 */

public class Tongue extends GameObject implements ICollidableWithGameObjects {
	
	private BeanWorld world;
	private Pajaro pajaro;
	private LookingSide side;
	private float startX, startY;
	private boolean isRetracting;
	
	public Tongue(BeanWorld world, Pajaro pajaro, LookingSide side, int spitSize, float x, float y) {
		this.world = world;
		this.pajaro = pajaro;
		setX(x);
		setY(y);
		startX = x;
		startY = y;
		setHeight(spitSize);
		setWidth(spitSize);
		this.side = side;
		
		float speed = 15;
		setySpeed(-speed);
		setxSpeed(side == LookingSide.LEFT ? -speed : speed);
	}
	
	public void retract() {
		if (isRetracting)
			return;
		
		setSpeed(getSpeed() * -4);
		isRetracting = true;
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
		if (getySpeed() > 0)
			return;
		
		for (GameObject g : collidedGameObjects) {
			if (g instanceof Bean) {
				((Bean) g).pop();
				retract();
			}
		}
	}
	
	@Override
	public void update() {
		if (getySpeed() > 0 && getY() > pajaro.getY()) {
			pajaro.retracted();
			world.deleteGameObject(this);
		} else if (getY() <= (0 - getHeight()) || getX() <= (0 - getWidth()) || getX() >= (world.width + getWidth())) {
			retract();
		}
	}
}
