package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * @author Jesse Oukes & Mustafa Piynirci
 * Used to show text
 */
public class TextObject extends GameObject {

    private String text;

    public TextObject(String text) {
        this.text=text;
    }

    public void setText(String text) {
        this.text=text;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(PGraphics g) {
        g.textAlign(PConstants.LEFT,PConstants.TOP);
        g.textSize(50);
        g.text(text,getX(),getY());
    }
}
