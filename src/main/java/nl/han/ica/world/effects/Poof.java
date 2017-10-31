package nl.han.ica.world.effects;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.world.BeanWorld;

/**
 * @author Jesse Oukes & Mustafa Piynirci
 * Poof animation
 */

public class Poof extends AnimatedSpriteObject {
	
	private int life = 0;
	private BeanWorld world;

	/**
	 * Constructor
	 * @param world
	 * 			Contains the BeanWorld
	 */
	public Poof(BeanWorld world) {
		super(new Sprite("src/main/java/nl/han/ica/world/media/poof.png"), 10);
		setCurrentFrameIndex(life);
		this.world = world;
	}

	/**
	 * Updates the sprite frame
	 */
	@Override
	public void update() {
		life++;
		if (life < 20) {
			setCurrentFrameIndex(life / 2);
		} else {
			world.deleteGameObject(this);
		}
	}
	
}
