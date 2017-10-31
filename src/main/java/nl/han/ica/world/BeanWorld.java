package nl.han.ica.world;

import java.util.ArrayList;
import java.util.Arrays;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Dashboard.Dashboard;
import nl.han.ica.OOPDProcessingEngineHAN.Engine.GameEngine;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.FilePersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.IPersistence;
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
	
	private TextObject highscoreTekst, currentScoreTekst;
	private BeanSpawner beanSpawner;
	private IPersistence persistence;
	private Pajaro pajaro;
	private int worldWidth, worldHeight, tileSize, totalHighscore, currentScore, scoreSpeedUpTrigger;
	private ArrayList<Alarm> alarms = new ArrayList<>();
	private ArrayList<Bean> beans = new ArrayList<>();
	private int[][] tilesMap;
	Sprite boardsSprite = new Sprite("src/main/java/nl/han/ica/world/media/ground.png");
	TileType<BoardsTile> boardTileType = new TileType<>(BoardsTile.class, boardsSprite);
	TileType[] tileTypes = { boardTileType };
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "nl.han.ica.world.BeanWorld" });
	}
	
	public ArrayList<Bean> getBeans() {
		return beans;
	}
	
	/**
	 * In this method the alarm gets added to an alarm list
	 * @param a
	 * 		This parameter should be an object of the type Alarm
	 */
	public void addAlarmToList(Alarm a) {
		alarms.add(a);
	}
	
	/**
	 * In this method all alarms in a list gets stopped
	 */
	public void stopAllAlarms() {
		for (Alarm a : alarms) {
			a.stop();
		}
	}
	
	/**
	 * This method returns the tile size
	 * @return tileSize
	 * 				Size of the tile
	 */
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * This method returns the world width
	 * @return wereld worldWidth
	 * 						Width of the world
	 */
	public int getWorldWidth() {
		return worldWidth;
	}
	
	/**
	 * This method returns the world height
	 * @return worldHeight
	 * 				Height of the world
	 */
	public int getWorldHeight() {
		return worldHeight;
	}
	
	/**
	 * THis method initializes the needed methods and variables
	 */
	@Override
	public void setupGame() {
		worldWidth = 960;
		worldHeight = 704;
		tileSize = 32;
		createDashboard(worldWidth, 100);
		initializeTileMap();
		initializePersistence();
		createObjects();
		createBeanSpawner();
		createViewWithoutViewport(worldWidth, worldHeight);
	}
	
	/**
	 * This method initializes the percistence ands sets the dashboard value of the highscore
	 */
	private void initializePersistence() {
		persistence = new FilePersistence("main/java/nl/han/ica/world/media/highscore.txt");
		if (persistence.fileExists()) {
			totalHighscore = Integer.parseInt(persistence.loadDataString());
			refreshDasboardText();
		}
		highscoreTekst.setText("Highscore: " + totalHighscore);
	}
	
	/**
	 * THis method returns the current highscore
	 * @return currentScore
	 * 				Current scure
	 */
	public int getCurrentScore() {
		return currentScore;
	}
	
	/**
	 * Tis method puts the current score to the given parameter value
	 * @param currentScore
	 * 			Value that you want to set the score to
	 */
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
		refreshDasboardText();
	}

	public void addToScore(int score) {
		setCurrentScore(getCurrentScore() + score);
		refreshDasboardText();
	}
	
	/**
	 * Generates the view without viewport
	 * @param screenWidth
	 * 			  	Width of the window
	 * @param screenHeight
	 * 				Height of the window
	 */
	private void createViewWithoutViewport(int screenWidth, int screenHeight) {
		View view = new View(screenWidth, screenHeight);
		view.setBackground(loadImage("src/main/java/nl/han/ica/world/media/background.jpg"));
		setView(view);
		size(screenWidth, screenHeight);
	}

	/**
	 * This method deletes a bean from GameObjects and the beans list
	 * @param bean
	 * 			Bean parameter that contains an Bean object
	 */
	public void deleteBean(Bean bean) {
		deleteGameObject(bean);
		beans.remove(bean);
	}
	
	/**
	 * Generates the gameobjects
	 */
	private void createObjects() {
		pajaro = new Pajaro(this);
		addGameObject(pajaro, worldWidth / 2 - pajaro.getWidth() / 2, worldHeight - tileSize - pajaro.getHeight());
	}
	
	/**
	 * Creates the bean spawner
	 */
	public void createBeanSpawner() {
		beanSpawner = new BeanSpawner(this, 0.5);
	}
	
	/**
	 * Creates the dashboards
	 * @param dashboardWidth
	 * 				Width that the dashboard should be
	 * @param dashboardHeight
	 * 				Height the dashboard should be
	 */
	private void createDashboard(int dashboardWidth, int dashboardHeight) {
		Dashboard highScoreDashboard = new Dashboard(0, 0, dashboardWidth / 2, dashboardHeight);
		Dashboard currentScoreDashboard = new Dashboard((dashboardWidth / 2) / 2, 0, dashboardWidth, dashboardHeight);
		highscoreTekst = new TextObject("");
		currentScoreTekst = new TextObject("");
		highScoreDashboard.addGameObject(highscoreTekst);
		currentScoreDashboard.addGameObject(currentScoreTekst);
		addDashboard(highScoreDashboard);
		addDashboard(currentScoreDashboard);
	}
	
	/**
	 * Initializes the tilemap
	 */
	private void initializeTileMap() {
		tilesMap = new int[worldHeight / tileSize][worldWidth / tileSize];
		for (int i = 0; i < tilesMap.length - 1; i++) {
			Arrays.fill(tilesMap[i], -1);
		}
		tileMap = new TileMap(tileSize, tileTypes, tilesMap);
	}
	
	/**
	 * Regenerates the lowest row of the tilemap
	 */
	public void resetTileMap(int tileType) {
		for (int i = 0; i < tilesMap[tilesMap.length - 1].length; i++) {
			tilesMap[tilesMap.length - 1][i] = tileType;
		}
		tileMap = new TileMap(tileSize, tileTypes, tilesMap);
	}
	
	@Override
	public void update() {
		if (scoreSpeedUpTrigger <= currentScore) {
			scoreSpeedUpTrigger += 1000;
			beanSpawner.increaseSpeed();
		}
	}
	
	/**
	 * This method calls all methods that should be runned when it's gameover
	 */
	public void gameOver() {
		stopAllAlarms();
		clearAllGameObjects();
		showGameOver();
	}
	
	/**
	 * This methods shows the "Game Over" message on the screen
	 */
	public void showGameOver() {
		highscoreTekst.setText("Game Over");
		currentScoreTekst.setText("");
	}

	/**
	 * This method removes all gameobjects
	 */
	public void clearAllGameObjects() {
		getGameObjectItems().removeAllElements();
	}
	
	/**
	 * This method refreshes the dashboard
	 */
	public void refreshDasboardText() {
		if (currentScore > totalHighscore) {
			persistence.saveData(Integer.toString(currentScore));
			highscoreTekst.setText("Highscore: " + currentScore);
		}
		currentScoreTekst.setText("Score: " + currentScore);
	}
}
