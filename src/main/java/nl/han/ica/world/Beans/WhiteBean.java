package nl.han.ica.world.Beans;

import java.util.ArrayList;
import java.util.Random;

import nl.han.ica.OOPDProcessingEngineHAN.Tile.EmptyTile;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.Tile;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileMap;
import nl.han.ica.world.BeanWorld;
import nl.han.ica.world.Poof;

public class WhiteBean extends Bean {
	
	/**
	 * Constructor
	 * 
	 * @param world
	 *            World has the world object
	 */
	public WhiteBean(BeanWorld world) {
		super(world, "nl/han/ica/world/media/whitebean.png", 4);
	}
	
	/**
	 * This method calls the pop from its super
	 */
	@Override
	public void pop() {
		super.pop();
		
		int worldWidth = world.getWorldHeight(), worldHeight = world.getWorldHeight(), tileSize = world.getTileSize();
		TileMap tileMap = world.getTileMap();
		
		ArrayList<Integer> freeList = new ArrayList<>();
		
		for (int i = 0; i < worldWidth / tileSize; i++) {
			Tile tile = tileMap.getTileOnIndex(i, worldHeight / tileSize - 1);
			if (tile instanceof EmptyTile) freeList.add(i);
		}
		
		if (freeList.size() > 0) {
			Random rand = new Random();
			int i = freeList.get(rand.nextInt(freeList.size()));
			
			tileMap.setTile(i, worldHeight / tileSize - 1, 0);
			world.addGameObject(new Poof(world), i * tileSize - tileSize, worldHeight - tileSize * 2);
		}
	}
}
