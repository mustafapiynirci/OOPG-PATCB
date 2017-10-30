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
	
	private final static int rightFramesOffset = 2;
	private final static int size = 32;
	
	private final BeanWorld world;
	private LookingSide side;
	private PajaroState state;
	private boolean leftPress, rightPress, upPress, spacePress, shootPress;
	private Tongue tongue;
	
	/**
	 * Constructor
	 * 
	 * @param world
	 *            Referentie naar de wereld
	 */
	public Pajaro(BeanWorld world) {
		super(new Sprite("src/main/java/nl/han/ica/world/media/pajaro.png"), 4);
		this.world = world;
		setCurrentFrameIndex(rightFramesOffset);
		side = LookingSide.RIGHT;
		state = PajaroState.IDLE;
	}
	
	@Override
	public void update() {
		final int speed = 4;
		
		if (state == PajaroState.IDLE) {
			if (shootPress) {
				tongue = new Tongue(world, this, side, world.getTileSize(),
						getX() + getWidth() / 4 + (side == LookingSide.LEFT ? -20 : 20), getY() + getHeight() / 4);
				world.addGameObject(tongue, -1);
				state = PajaroState.EXTENDING;
				setCurrentFrameIndex((side == LookingSide.RIGHT ? rightFramesOffset : 0) + 1);
			} else if (leftPress) {
				if (getX() - speed >= 0) {
					Tile tile = world.getTileMap().getTileOnIndex((int) getX() / world.getTileSize(),
							world.getWorldHeight() / world.getTileSize() - 1);
					if (!(tile instanceof EmptyTile)) {
						side = LookingSide.LEFT;
						setX(getX() - speed);
						setCurrentFrameIndex(0);
					}
				}
			} else if (rightPress) {
				if (getX() + size * 2 < world.getWorldWidth()) {
					Tile tile = world.getTileMap().getTileOnIndex((int) getX() / world.getTileSize() + 2,
							world.getWorldHeight() / world.getTileSize() - 1);
					if (!(tile instanceof EmptyTile)) {
						side = LookingSide.RIGHT;
						setX(getX() + speed);
						setCurrentFrameIndex(rightFramesOffset);
					}
				}
			}
		} else if (state == PajaroState.EXTENDING) {
			if (!shootPress) {
				state = PajaroState.RETRACTING;
				tongue.retract();
			}
		}
	}
	
	public void retracted() {
		state = PajaroState.IDLE;
		tongue = null;
		setCurrentFrameIndex(side == LookingSide.RIGHT ? rightFramesOffset : 0);
	}
	
	@Override
	public void keyPressed(int keyCode, char key) {
		// To improve the performance and input consistency, we only toggle the button's
		// states here.
		if (keyCode == PConstants.LEFT) {
			leftPress = true;
		} else if (keyCode == PConstants.UP) {
			upPress = true;
			shootPress = upPress || spacePress;
		} else if (key == ' ') {
			spacePress = true;
			shootPress = upPress || spacePress;
		} else if (keyCode == PConstants.RIGHT) {
			rightPress = true;
		}
	}
	
	@Override
	public void keyReleased(int keyCode, char key) {
		if (keyCode == PConstants.LEFT) {
			leftPress = false;
		} else if (keyCode == PConstants.UP) {
			upPress = false;
			shootPress = upPress || spacePress;
		} else if (key == ' ') {
			spacePress = false;
			shootPress = upPress || spacePress;
		} else if (keyCode == PConstants.RIGHT) {
			rightPress = false;
		}
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
