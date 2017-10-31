package nl.han.ica.world.Beans;

import nl.han.ica.world.BeanWorld;

public class GreenBean extends Bean {

    public GreenBean(BeanWorld world) {
        super(world, "nl/han/ica/world/media/greenbean.png", 4);
        setCurrentFrameIndex(0);
    }
	
	public void pop() {
		super.pop();
	}
}
