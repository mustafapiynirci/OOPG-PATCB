package nl.han.ica.world.Beans;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Exceptions.TileNotFoundException;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.world.BeanWorld;
import nl.han.ica.world.effects.Poof;
import nl.han.ica.world.tiles.BoardsTile;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

import java.util.Iterator;
import java.util.List;

/**
 * @author Jesse Oukes & Mustafa Piynirci
 * Base of all beans
 */

public class Bean extends AnimatedSpriteObject implements ICollidableWithTiles {

	private final int[][] scoreModel = { { 10, 1000 }, { 30, 300 }, { 60, 100 }, { 90, 50 }, { 100, 10 } };
	private int spriteFrame = 0;
	
	protected final BeanWorld world;
	
	/**
	 * Constructor
	 * @param world
	 *            World parameter
	 * @param spriteUrl
	 *            Sprite url path
	 * @param spriteSlices
	 *            Amount of slices the sprite should be cut in
	 */
	public Bean(BeanWorld world, String spriteUrl, int spriteSlices) {
		super(new Sprite(spriteUrl), spriteSlices);
		this.world = world;
		int beanSize = 32;
		setySpeed(+beanSize / 15f);
		setHeight(beanSize);
		setWidth(beanSize);
		world.getBeans().add(this);
	}
	
	/**
	 * Sprite positie aanpassen zodat het niet begint op 0,0 van het object
	 * @param g
	 *        Processing object
	 */
	@Override
	public void draw(PGraphics g) {
		PImage img = getImage().get(getCurrentFrame().x, getCurrentFrame().y, getCurrentFrame().width,
				getCurrentFrame().height);
		g.image(img, x - (int) Math.ceil((getHeight() / 2)), y - (int) Math.ceil((getHeight() / 3)));
	}
	
	/**
	 * This method gets called constantly and has a basic functionality like removing
	 * the bean and updating the sprite
	 */
	@Override
	public void update() {
		if (getY() >= world.getHeight()) {
			world.deleteGameObject(this);
		}
		getScore();
		spriteFrame++;
		if (spriteFrame == 40) spriteFrame = 0;
		setCurrentFrameIndex(spriteFrame / 10);
	}
	
	/**
	 * This method adds a score to the current score
	 */
	public void pop() {
		world.addToScore(getScore());
		delete();
	}
	
	/**
	 * This method removes the bean
	 */
	public void delete() {
		world.deleteBean(this);
	}
	
	/**
	 * This method deletes the bean
	 * @param iter
	 */
	public void delete(Iterator<Bean> iter) {
		iter.remove();
		world.deleteGameObject(this);
	}
	
	/**
	 * This method creates a Poof object that animates on the current position
	 */
	private void createPoof() {
		world.addGameObject(new Poof(world), getX() - 32, getY() - 32);
	}
	
	/**
	 * This method calls all methods that needs to run when a bean gets removed from
	 * the world but not when the tongue hits the bean
	 */
	public void poof() {
		delete();
		createPoof();
	}

	/**
	 * This method calls all methods that needs to run when a bean gets removed from
	 * the world but not when the tongue hits the bean
	 * @param iter
	 * 			iterator value
	 */
	public void poof(Iterator<Bean> iter) {
		createPoof();
		delete(iter);
	}
	
	/**
	 * Returns the score score when a bean gets eaten
	 * @return score
	 *         This value contains the score
	 */
	public int getScore() {
		for (int[] i : scoreModel) {
			if (getY() <= ((world.getHeight() - world.getTileSize()) / 100 * i[0])) {
				return i[1];
			}
		}
		return getLowScore();
	}
	
	/**
	 * This method returns the lowest score
	 * @return scoreModel
	 *         Lowest score what a player can get when a bean gets eaten
	 */
	public int getLowScore() {
		return scoreModel[scoreModel.length - 1][1];
	}
	
	/**
	 * This method gets called when Pájaro is in collision with a tile
	 * @param collidedTiles
	 *            List with all collided tiles
	 */
	@Override
	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
		PVector vector;
		
		for (CollidedTile ct : collidedTiles) {
			if (ct.theTile instanceof BoardsTile) {
				try {
					vector = world.getTileMap().getTilePixelLocation(ct.theTile);
					world.getTileMap().setTile((int) vector.x / 32, (int) vector.y / 32, -1);
					poof();
				} catch (TileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
