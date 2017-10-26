package nl.han.ica.world;

import java.util.ArrayList;
import java.util.Arrays;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Dashboard.Dashboard;
import nl.han.ica.OOPDProcessingEngineHAN.Engine.GameEngine;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.FilePersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.IPersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileMap;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileType;
import nl.han.ica.OOPDProcessingEngineHAN.View.View;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.tiles.BoardsTile;
import processing.core.PApplet;

/**
 * @author Ralph Niels
 */
@SuppressWarnings("serial")
public class BeanWorld extends GameEngine {
	
	private Sound backgroundSound;
	private Sound bubblePopSound;
	private TextObject highscoreTekst, currentScoreTekst;
	private BeanSpawner beanSpawner;
	private IPersistence persistence;
	private Pajaro pajaro;
	private int worldWidth, worldHeight, tileSize, totalHighscore, currentScore;
	private ArrayList<Alarm> alarms = new ArrayList<>();
	private ArrayList<Bean> beans = new ArrayList<>();
	private int[][] tilesMap;
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "nl.han.ica.world.BeanWorld" });
	}

	public ArrayList<Bean> getBeans() {
		return beans;
	}

	/**
	 * Deze methode voegt een alarm toe aan een alarm lijst
	 * 
	 * @param a
	 *            Deze parameter moet een object van het type Alarm bevatten.
	 */
	public void addAlarmToList(Alarm a) {
		alarms.add(a);
	}
	
	/**
	 * Stop alle alarms
	 */
	public void stopAllAlarms() {
		for (Alarm a : alarms) {
			a.stop();
		}
	}
	
	/**
	 * Deze methode geeft de grootte van tile terug
	 * 
	 * @return tileSize
	 */
	public int getTileSize() {
		return tileSize;
	}
	
	public int getWorldWidth() {
		return worldWidth;
	}
	
	public int getWorldHeight() {
		return worldHeight;
	}
	
	/**
	 * In deze methode worden de voor het spel
	 * noodzakelijke zaken geïnitialiseerd
	 */
	@Override
	public void setupGame() {
		
		worldWidth = 960;
		worldHeight = 704;
		tileSize = 32;
		
		initializeSound();
		createDashboard(worldWidth, 100);
		initializeTileMap();
		initializePersistence();
		createObjects();
		createBeanSpawner();
		createViewWithoutViewport(worldWidth, worldHeight);
		
	}

	private void initializePersistence() {
		persistence = new FilePersistence("main/java/nl/han/ica/world/media/highscore.txt");
		if (persistence.fileExists()) {
			totalHighscore = Integer.parseInt(persistence.loadDataString());
			refreshDasboardText();
		}
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	/**
	 * Creeërt de view zonder viewport
	 * 
	 * @param screenWidth
	 *            Breedte van het scherm
	 * @param screenHeight
	 *            Hoogte van het scherm
	 */
	private void createViewWithoutViewport(int screenWidth, int screenHeight) {
		View view = new View(screenWidth, screenHeight);
		view.setBackground(loadImage("src/main/java/nl/han/ica/world/media/background.jpg"));
		
		setView(view);
		size(screenWidth, screenHeight);
	}
	
	/**
	 * Initialiseert geluid
	 */
	private void initializeSound() {
		backgroundSound = new Sound(this, "src/main/java/nl/han/ica/world/media/Waterworld.mp3");
		backgroundSound.loop(-1);
		bubblePopSound = new Sound(this, "src/main/java/nl/han/ica/world/media/pop.mp3");
	}
	
	/**
	 * Maakt de spelobjecten aan
	 */
	private void createObjects() {
		pajaro = new Pajaro(this);
		addGameObject(pajaro, worldWidth / 2 - pajaro.getWidth() / 2, worldHeight - tileSize - pajaro.getHeight());
	}
	
	/**
	 * Maakt de spawner voor de bonen aan
	 */
	public void createBeanSpawner() {
		beanSpawner = new BeanSpawner(this, 0.5);
	}
	
	/**
	 * Maakt het dashboard aan
	 * 
	 * @param dashboardWidth
	 *            Gewenste breedte van dashboard
	 * @param dashboardHeight
	 *            Gewenste hoogte van dashboard
	 */
	private void createDashboard(int dashboardWidth,int dashboardHeight) {
		Dashboard highScoreDashboard = new Dashboard(0,0, dashboardWidth/2, dashboardHeight);
		System.out.println(dashboardWidth/2);
		Dashboard currentScoreDashboard = new Dashboard((dashboardWidth/2)/2, 0, dashboardWidth, dashboardHeight);
		highscoreTekst = new TextObject("");
		currentScoreTekst = new TextObject("");
		highScoreDashboard.addGameObject(highscoreTekst);
		currentScoreDashboard.addGameObject(currentScoreTekst);
		addDashboard(highScoreDashboard);
		addDashboard(currentScoreDashboard);
	}
	
	/**
	 * Initialiseert de tilemap
	 */
	private void initializeTileMap() {
		Sprite boardsSprite = new Sprite("src/main/java/nl/han/ica/world/media/ground.png");
		TileType<BoardsTile> boardTileType = new TileType<>(BoardsTile.class, boardsSprite);
		
		TileType[] tileTypes = { boardTileType };
		tilesMap = new int[worldHeight / tileSize][worldWidth / tileSize];

		for (int i = 0; i < tilesMap.length - 1; i++) {
			Arrays.fill(tilesMap[i], -1);
		}
		
		tileMap = new TileMap(tileSize, tileTypes, tilesMap);
	}

	public void resetTileMap() {
		System.out.println("reset tilesmap");
//		for(int i = 0; i < tilesMap[tilesMap.length - 1].length; i++) {
//			tilesMap[tilesMap.length - 1][i] = 0;
//		}
		Sprite boardsSprite = new Sprite("src/main/java/nl/han/ica/world/media/ground.png");
		TileType<BoardsTile> boardTileType = new TileType<>(BoardsTile.class, boardsSprite);
		TileType[] tileTypes = { boardTileType };
		for(int i = 0; i < tilesMap[tilesMap.length - 1].length; i++) {
			tilesMap[tilesMap.length - 1][i] = 0;
		}

		tileMap = new TileMap(tileSize, tileTypes, tilesMap);
	}

	@Override
	public void update() {
		// System.out.println(alarms.size());
	}
	
	/**
	 * Alle acties doe uitgevoerd moeten worden wanneer de speler af is
	 */
	public void gameOver() {
		stopAllAlarms();
		clearAllGameObjects();
		showGameOver();
	}
	
	/**
	 * Laat de "Game Over" bericht zien op het scherm
	 */
	public void showGameOver() {
		highscoreTekst.setText("Game Over");
		currentScoreTekst.setText("");
	}
	
	public void clearAllGameObjects() {
		getGameObjectItems().removeAllElements();
	}
	
	/**
	 * Vernieuwt het dashboard
	 */
	 public void refreshDasboardText() {
		 if(currentScore > totalHighscore) {
			 persistence.saveData(Integer.toString(currentScore));
		 }
		 highscoreTekst.setText("Highscore: " + totalHighscore);
		 currentScoreTekst.setText("Score: " + currentScore);
	 }
}
