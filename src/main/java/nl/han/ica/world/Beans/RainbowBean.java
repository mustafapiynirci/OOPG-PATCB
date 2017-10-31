package nl.han.ica.world.Beans;

import java.util.Iterator;

import nl.han.ica.world.BeanWorld;

public class RainbowBean extends Bean {

	/**
	 * Constructor
	 * @param world
	 * 			World has the world object
	 */
	public RainbowBean(BeanWorld world) {
		super(world, "nl/han/ica/world/media/rainbowbean.png", 4);
	}

	/**
	 * This method calls the pop from it's super and
	 * removes all beans in the air
	 */
	@Override
	public void pop() {
		super.pop();
		Iterator<Bean> iter = world.getBeans().iterator();
		while (iter.hasNext()) {
			Bean b = iter.next();
			if (b == this) continue;
			world.addToScore(getLowScore());
			b.poof(iter);
		}
		world.resetTileMap(0);
	}
}
