package nl.han.ica.world.Beans;

import nl.han.ica.world.BeanWorld;

public class TimeSlowingBean extends Bean {

	/**
	 * Constructor
	 * @param world
	 * 			World has the world object
	 */
	public TimeSlowingBean(BeanWorld world) {
		super(world, "nl/han/ica/world/media/whitebean.png", 4);
	}

	/**
	 * This method calls the pop from it's super
	 */
	@Override
	public void pop() {
		super.pop();
	}
}
