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

    /**
     * Constructor
     * @param text
     *          Tekst that you want to use
     */
    public TextObject(String text) {
        this.text=text;
    }

    /**
     * This method sets the text value to equal the parameter value
     * @param text
     *          Tekst that you want to use
     */
    public void setText(String text) {
        this.text=text;
    }

    /**
     * This method runs constantly
     */
    @Override
    public void update() {}

    /**
     * This method runs constantly and draws the text on screen
     * @param g
     *          PGraphics object will be given by the GameEngine.
     */
    @Override
    public void draw(PGraphics g) {
        g.textAlign(PConstants.LEFT,PConstants.TOP);
        g.textSize(50);
        g.text(text,getX(),getY());
    }
}
