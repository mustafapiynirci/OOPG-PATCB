package nl.han.ica.world.Beans;

import java.util.Iterator;

import nl.han.ica.world.BeanWorld;

public class RainbowBean extends Bean {
	
	public RainbowBean(BeanWorld world) {
		super(world, "nl/han/ica/world/media/rainbowbean.png", 4);
	}
	
	public void pop() {
		super.pop();
		System.out.println(world.getBeans().size());
		
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
