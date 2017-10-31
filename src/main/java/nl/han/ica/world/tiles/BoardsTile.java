package nl.han.ica.world.tiles;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.Tile;

/**
 * @author Jesse Oukes & Mustafa Piynirci
 * Contains the base of BoardsTile
 */
public class BoardsTile extends Tile {

    /**
     * @param sprite The image which will be drawn whenever the draw method of the Tile is called.
     */
    public BoardsTile(Sprite sprite) {
        super(sprite);
    }
}
