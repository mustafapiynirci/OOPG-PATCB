package nl.han.ica.world.Beans;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.world.BeanWorld;

public class RainbowBean extends Bean {
	
	public RainbowBean(BeanWorld world, int beanSize) {
		super(world, beanSize, "nl/han/ica/world/media/rainbowbean.png", 4);
	}
	
	public void pop() {
		super.pop();
		for (GameObject g : world.getBeans()) {
			if (g == this) continue;
			world.setCurrentScore(world.getCurrentScore() + getLowScore());
			world.deleteGameObject(g);
		}
		world.resetTileMap(0);
	}
}
