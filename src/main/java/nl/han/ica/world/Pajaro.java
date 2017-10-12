package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Exceptions.TileNotFoundException;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.world.tiles.BoardsTile;
import processing.core.PVector;

import java.util.List;

/**
 * @author Ralph Niels
 *         De spelerklasse (het paarse visje)
 */
public class Pajaro extends AnimatedSpriteObject implements ICollidableWithTiles {
	
	final int size = 64;
	private final BeanWorld world;
	
	/**
	 * Constructor
	 * 
	 * @param world
	 *            Referentie naar de wereld
	 */
	public Pajaro(BeanWorld world) {
		super(new Sprite("src/main/java/nl/han/ica/world/media/pajaro.png"), 4);
		this.world = world;
		setCurrentFrameIndex(1);
		//setFriction(0.05f);
	}
	
	@Override
	public void update() {
		if (getX() <= 0) {
			setxSpeed(0);
			setX(0);
		} else if (getX() >= world.getWidth() - size) {
			setxSpeed(0);
			setX(world.getWidth() - size);
		}
	}
	
	@Override
	public void keyPressed(int keyCode, char key) {
		final int speed = 5;
		if (keyCode == world.LEFT) {
			setDirectionSpeed(270, speed);
			setCurrentFrameIndex(0);
		} else if (keyCode == world.UP || key == ' ') {
			// setDirectionSpeed(0, speed);
		} else if (keyCode == world.RIGHT) {
			setDirectionSpeed(90, speed);
			setCurrentFrameIndex(2);
		}
	}
	
	@Override
	public void keyReleased(int keyCode, char key) {
		setSpeed(0);    
	}
	
	@Override
	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
		PVector vector;
		
		for (CollidedTile ct : collidedTiles) {
			if (ct.theTile instanceof BoardsTile) {
				if (ct.collisionSide == ct.TOP) {
					try {
						vector = world.getTileMap().getTilePixelLocation(ct.theTile);
						setY(vector.y - getHeight());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (ct.collisionSide == ct.RIGHT) {
					try {
						vector = world.getTileMap().getTilePixelLocation(ct.theTile);
						world.getTileMap().setTile((int) vector.x / 50, (int) vector.y / 50, -1);
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
