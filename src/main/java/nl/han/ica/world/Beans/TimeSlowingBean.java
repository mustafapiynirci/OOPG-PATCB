package nl.han.ica.world.Beans;

import nl.han.ica.world.BeanWorld;

public class TimeSlowingBean extends Bean {
	public TimeSlowingBean(BeanWorld world, int beanSize) {
		super(world, beanSize, "nl/han/ica/world/media/whitebean.png", 4);
	}
	
	public void pop() {
		super.pop();
	}
}
