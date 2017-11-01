package nl.han.ica.world.player;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.EmptyTile;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.Tile;
import nl.han.ica.world.BeanWorld;
import nl.han.ica.world.Beans.Bean;
import processing.core.PConstants;

import java.util.List;

/**
 * @author Jesse Oukes & Mustafa Piynirci
 * The player Pájaro
 */

public class Pajaro extends AnimatedSpriteObject implements ICollidableWithGameObjects {
	
	private final int rightFramesOffset = 2;

	private final BeanWorld world;
	private LookingSide side;
	private PajaroState state;
	private boolean leftPress, rightPress, upPress, spacePress, shootPress;
	private Tongue tongue;
	
	/**
	 * Constructor
	 * @param world
	 *          Reference to the world
	 */
	public Pajaro(BeanWorld world) {
		super(new Sprite("src/main/java/nl/han/ica/world/media/pajaro.png"), 4);
		this.world = world;
		setCurrentFrameIndex(rightFramesOffset);
		side = LookingSide.RIGHT;
		state = PajaroState.IDLE;
	}

	/**
	 * This method runs constant and has the functionality of Pájaro's tongue
	 */
	@Override
	public void update() {
		if (state == PajaroState.IDLE) {
			int speed = 6;
			if (shootPress) {
				int tongueOffset = 20;
				tongue = new Tongue(world, this, side, world.getTileSize(),
						getX() + getWidth() / 4 + (side == LookingSide.LEFT ? -tongueOffset : tongueOffset),
						getY() + getHeight() / 4);
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
				int size = 32;
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

	/**
	 * This method gets called when the toungue is back at Pájaro and some values get resetted
	 */
	public void retracted() {
		state = PajaroState.IDLE;
		tongue = null;
		setCurrentFrameIndex(side == LookingSide.RIGHT ? rightFramesOffset : 0);
	}

	private void keyPressedValue(int keyCode, char key, boolean state) {
		if (keyCode == PConstants.LEFT) {
			leftPress = state;
		} else if (keyCode == PConstants.UP) {
			upPress = false;
			shootPress = upPress || spacePress;
		} else if (key == ' ') {
			spacePress = state;
			shootPress = upPress || spacePress;
		} else if (keyCode == PConstants.RIGHT) {
			rightPress = state;
		}
	}

	/**
	 * This method detects if a key is pressed and shoots the tongue
	 * @param keyCode
	 * @param key
	 */
	@Override
	public void keyPressed(int keyCode, char key) {
		keyPressedValue(keyCode, key, true);
	}
	
	@Override
	public void keyReleased(int keyCode, char key) {
		keyPressedValue(keyCode, key, false);
	}
	
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g : collidedGameObjects) {
			if (g instanceof Bean) {
				world.gameOver();
			}
		}
	}
}
