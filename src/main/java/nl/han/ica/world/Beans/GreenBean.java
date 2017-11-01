package nl.han.ica.world.Beans;

import nl.han.ica.world.BeanWorld;

/**
 * @author Jesse Oukes & Mustafa Piynirci
 * Greenbean
 */

public class GreenBean extends Bean {

    /**
     * Constructor
     * @param world
     * 			World has the world object
     */
    public GreenBean(BeanWorld world) {
        super(world, "nl/han/ica/world/media/greenbean.png", 4);
        setCurrentFrameIndex(0);
    }
}
