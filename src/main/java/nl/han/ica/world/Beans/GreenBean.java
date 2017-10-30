package nl.han.ica.world.Beans;

import nl.han.ica.world.BeanWorld;

public class GreenBean extends Bean {

    public GreenBean(BeanWorld world, int beanSize) {
        super(world, beanSize, "nl/han/ica/world/media/greenbean.png", 4);
        setCurrentFrameIndex(0);
    }
	
	public void pop() {
		super.pop();
	}
}
