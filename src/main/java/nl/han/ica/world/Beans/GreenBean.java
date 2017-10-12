package nl.han.ica.world.Beans;

import nl.han.ica.world.BeanWorld;
import processing.core.PGraphics;

public class GreenBean extends Bean {

    public GreenBean(BeanWorld world, int beanSize) {
        super(world, beanSize);
    }

    @Override
    public void draw(PGraphics g) {
        g.ellipseMode(g.CORNER); // Omdat cirkel anders vanuit midden wordt getekend en dat problemen geeft bij collisiondetectie
        g.stroke(0, 50, 200, 100);
        g.fill(255);
        g.ellipse(getX(), getY(), beanSize, beanSize);
    }
}
