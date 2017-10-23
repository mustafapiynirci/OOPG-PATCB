package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Exceptions.TileNotFoundException;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.tiles.BoardsTile;
import processing.core.PVector;

import java.util.List;

/**
 * @author Ralph Niels
 *         De spelerklasse (het paarse visje)
 */
public class Pajaro extends AnimatedSpriteObject implements ICollidableWithGameObjects, ICollidableWithTiles {
	
//	final int size = 64;
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
		setCurrentFrameIndex(1);
		setGravity(1);
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
//		walkable();
	}
	
	@Override
	public void keyPressed(int keyCode, char key) {
		final int speed = 5;
		if (keyCode == world.LEFT) {
			setDirectionSpeed(270, speed);
			setCurrentFrameIndex(0);
			side = LookingSide.LEFT;
		} else if (keyCode == world.UP && key == ' ' || key == ' ') {
			System.out.println(side);
			Spit s = new Spit(world, 20, side);
			world.addGameObject(s, getX(), getY() + ((getHeight() / 2) / 2));
		} else if (keyCode == world.RIGHT) {
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
	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
		PVector vector;

		for (CollidedTile ct : collidedTiles) {
			if (ct.theTile instanceof BoardsTile) {
				if (ct.collisionSide == ct.TOP) {
					try {
						vector = world.getTileMap().getTilePixelLocation(ct.theTile);
						setY(vector.y - getHeight());
//						System.out.println(world.getTileMap().getTileOnIndex(0,0) + " 0");
//						System.out.println(world.getTileMap().getTileOnIndex(0,1) + " 1");
//						System.out.println(world.getTileMap().getTileOnIndex(0,(world.getHeight() / world.getTileSize()) - 1) + " 2");
//						System.out.println((world.getHeight() / world.getTileSize()) - 1 + " tesst");
//						System.out.println(world.getTileMap().getTileOnIndex((int) ((world.getWidth() / getX())), 21) + " 5");
//						System.out.println(world.getTileMap().getTileOnIndex(0, 21) + " 5");
//						System.out.println((getX() + getHeight()) / world.getTileSize() + " test");

						// 22 - 30
						System.out.println(world.getHeight() / world.getTileSize() + " " + world.getWidth() / world.getTileSize());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for(GameObject g : collidedGameObjects) {
			if(g instanceof Bean) {
				System.out.println("Game Over");
				world.gameOver();
			}
		}
	}
}
