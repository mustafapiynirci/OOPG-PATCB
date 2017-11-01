package nl.han.ica.world.Beans;

import nl.han.ica.world.BeanWorld;

/**
 * @author Jesse Oukes & Mustafa Piynirci
 *         Time slowing bean
 */

public class TimeSlowingBean extends Bean {
	
	/**
	 * Constructor
	 * 
	 * @param world
	 *            World has the world object
	 */
	public TimeSlowingBean(BeanWorld world) {
		super(world, "nl/han/ica/world/media/timebean.png", 4);
	}
	
	/**
	 * This method calls the pop from it's super, and makes the world slow down the
	 * beans
	 */
	@Override
	public void pop() {
		super.pop();
		world.slowTime();
	}
}
