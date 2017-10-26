package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.EmptyTile;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.Tile;
import nl.han.ica.world.Beans.Bean;
import processing.core.PConstants;

import java.util.List;

/**
 * @author Ralph Niels
 *         De spelerklasse (het paarse visje)
 */
public class Pajaro extends AnimatedSpriteObject implements ICollidableWithGameObjects {
	
	final int size = 32;
	private final BeanWorld world;
	private LookingSide side;
	
	/**
	 * Constructor
	 * 
	 * @param world
	 *            Referentie naar de wereld
	 */
	public Pajaro(BeanWorld world) {
		super(new Sprite("src/main/java/nl/han/ica/world/media/pajaro.png"), 4);
		this.world = world;
		setCurrentFrameIndex(2);
		side = LookingSide.RIGHT;
	}
	
	@Override
	public void update() {
		if (getX() <= 0) {
			setxSpeed(0);
			setX(0);
		} else if (getX() >= world.getWidth() - size) {
			setxSpeed(0);
			setX(world.getWidth() - size);
		} else if (getY() >= (world.getHeight() + getHeight())) {
			world.deleteGameObject(this);
			System.out.println("Pajaro died");
		}
		
		if (getxSpeed() < 0) {
			Tile tile = world.getTileMap().getTileOnIndex((int) getX() / world.getTileSize(),
					world.getWorldHeight() / world.getTileSize() - 1);
			if (tile instanceof EmptyTile) {
				setSpeed(0);
				setX((float) Math.ceil(getX() / world.getTileSize()) * world.getTileSize());
			}
		} else if (getxSpeed() > 0 && (getX() > (world.getWidth() - getWidth()))) {
			setSpeed(0);
			setX((float) Math.floor(getX() / world.getTileSize()) * world.getTileSize());
		} else if (getxSpeed() > 0) {
			Tile tile = world.getTileMap().getTileOnIndex((int) getX() / world.getTileSize() + 2,
					world.getWorldHeight() / world.getTileSize() - 1);
			if (tile instanceof EmptyTile) {
				setSpeed(0);
				setX((float) Math.floor(getX() / world.getTileSize()) * world.getTileSize());
			}
		}
	}
	
	@Override
	public void keyPressed(int keyCode, char key) {
		final int speed = 4;
		if (keyCode == PConstants.LEFT) {
			setDirectionSpeed(270, speed);
			setCurrentFrameIndex(0);
			side = LookingSide.LEFT;
		} else if (keyCode == PConstants.UP || key == ' ') {
			Spit s = new Spit(world, side, world.getTileSize(), getX() + getWidth() / 4, getY() + getHeight() / 4);
			world.addGameObject(s, getX() + getWidth() / 4, getY() + getHeight() / 4);
		} else if (keyCode == PConstants.RIGHT) {
			setDirectionSpeed(90, speed);
			setCurrentFrameIndex(2);
			side = LookingSide.RIGHT;
		}
	}
	
	@Override
	public void keyReleased(int keyCode, char key) {
		setSpeed(0);
	}
	
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g : collidedGameObjects) {
			if (g instanceof Bean) {
				System.out.println("Game Over");
				world.gameOver();
			}
		}
	}
}
