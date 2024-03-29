package nl.han.ica.world;

import java.util.ArrayList;
import java.util.Arrays;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.OOPDProcessingEngineHAN.Dashboard.Dashboard;
import nl.han.ica.OOPDProcessingEngineHAN.Engine.GameEngine;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.FilePersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.IPersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.EmptyTile;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.Tile;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileMap;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileType;
import nl.han.ica.OOPDProcessingEngineHAN.View.View;
import nl.han.ica.world.Beans.Bean;

import nl.han.ica.world.effects.Poof;
import nl.han.ica.world.player.Pajaro;
import nl.han.ica.world.spawners.BeanSpawner;
import nl.han.ica.world.tiles.BoardsTile;
import processing.core.PApplet;

/**
 * @author Jesse Oukes & Mustafa Piynirci
 *         Is the base of the game
 */
@SuppressWarnings("serial")
public class BeanWorld extends GameEngine implements IAlarmListener {
	
	private TextObject highscoreTekst, currentScoreTekst;
	private BeanSpawner beanSpawner;
	private IPersistence persistence;
	private Pajaro pajaro;
	private int worldWidth, worldHeight, tileSize, highscore, currentScore, scoreSpeedUpTrigger;
	private Alarm slowAlarm;
	private boolean isSlowTime;
	private float speedFactor = 1;
	private ArrayList<Alarm> alarms = new ArrayList<>();
	private ArrayList<Bean> beans = new ArrayList<>();
	Sprite boardsSprite = new Sprite("src/main/java/nl/han/ica/world/media/ground.png");
	TileType<BoardsTile> boardTileType = new TileType<>(BoardsTile.class, boardsSprite);
	TileType<?>[] tileTypes = { boardTileType };
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "nl.han.ica.world.BeanWorld" });
	}
	
	/**
	 * Get list of all beans
	 * @return beans
	 *         list of all beans
	 */
	public ArrayList<Bean> getBeans() {
		return beans;
	}
	
	/**
	 * In this method the given alarm gets added to an alarm list
	 * @param a
	 *            This parameter should be an object of the type Alarm
	 */
	public void addAlarmToList(Alarm a) {
		alarms.add(a);
	}
	
	/**
	 * Remove given alarm
	 * @param a
	 *            Parameter of type Alarm
	 */
	public void removeAlarmFromList(Alarm a) {
		alarms.remove(a);
	}
	
	/**
	 * In this method all alarms in a list gets stopped
	 */
	private void stopAllAlarms() {
		for (Alarm a : alarms) {
			a.stop();
		}
	}
	
	/**
	 * This method returns the tile size
	 * @return tileSize
	 *         Size of the tile
	 */
	public int getTileSize() {
		return tileSize;
	}
	
	/**
	 * This method returns the world width
	 * @return wereld worldWidth
	 *         Width of the world
	 */
	public int getWorldWidth() {
		return worldWidth;
	}
	
	/**
	 * This method returns the world height
	 * @return worldHeight
	 *         Height of the world
	 */
	public int getWorldHeight() {
		return worldHeight;
	}
	
	/**
	 * This method initializes the needed methods and variables
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
	 * This method initializes the percistence ands sets the dashboard value of the
	 * highscore
	 */
	private void initializePersistence() {
		persistence = new FilePersistence("main/java/nl/han/ica/world/media/highscore.txt");
		if (persistence.fileExists()) {
			highscore = Integer.parseInt(persistence.loadDataString());
			refreshDasboardText();
		}
		highscoreTekst.setText("Highscore: " + highscore);
	}
	
	/**
	 * This method returns the current highscore
	 * @return currentScore
	 *         Current score
	 */
	public int getCurrentScore() {
		return currentScore;
	}
	
	/**
	 * This method puts the current score to the given parameter value
	 * @param currentScore
	 *            Value that you want to set the score to
	 */
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
		refreshDasboardText();
	}
	
	/**
	 * Add value to score and refresh dashboard
	 * @param score
	 *            value to set the current score value to
	 */
	public void addToScore(int score) {
		setCurrentScore(getCurrentScore() + score);
		refreshDasboardText();
	}
	
	/**
	 * Generates the view without viewport
	 * @param screenWidth
	 *            Width of the window
	 * @param screenHeight
	 *            Height of the window
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
	 *            Bean parameter that contains an Bean object
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
	 *            Width that the dashboard should be
	 * @param dashboardHeight
	 *            Height the dashboard should be
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
		int[][] tilesMap = new int[worldHeight / tileSize][worldWidth / tileSize];
		for (int i = 0; i < tilesMap.length - 1; i++) {
			Arrays.fill(tilesMap[i], -1);
		}
		tileMap = new TileMap(tileSize, tileTypes, tilesMap);
	}
	
	/**
	 * Regenerates the lowest row of the tilemap
	 */
	public void resetTileMap(int tileType) {
		for (int i = 0; i < worldWidth / tileSize; i++) {
			Tile tile = tileMap.getTileOnIndex(i, worldHeight / tileSize - 1);
			if (tile instanceof EmptyTile) {
				tileMap.setTile(i, worldHeight / tileSize - 1, tileType);
				addGameObject(new Poof(this), i * tileSize - tileSize, worldHeight - tileSize * 2);
			}
		}
	}
	
	/**
	 * Slows down time of beans
	 */
	public void slowTime() {
		if (isSlowTime) {
			slowAlarm.stop();
		} else {
			speedFactor = 0.5f;
			for (Bean b : beans) {
				b.setSpeed(b.getSpeed() * speedFactor);
			}
		}
		slowAlarm = new Alarm("slow", 10);
		slowAlarm.addTarget(this);
		slowAlarm.start();
		isSlowTime = true;
	}
	
	/**
	 * Triggers the given alarm
	 * @param alarmName
	 *            the name of the alarm.
	 */
	@Override
	public void triggerAlarm(String alarmName) {
		if (alarmName != "slow") return;
		
		for (Bean b : beans)
			b.setSpeed(b.getSpeed() / speedFactor);
		speedFactor = 1;
		
		isSlowTime = false;
	}
	
	/**
	 * Returns the current speed multiplication
	 * @return float speedFactor
	 */
	public float getSpeedFactor() {
		return speedFactor;
	}
	
	/**
	 * Increase the speed of dropping beans every 1000 points
	 */
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
		if (currentScore > highscore) {
			persistence.saveData(Integer.toString(currentScore));
			highscoreTekst.setText("Highscore: " + currentScore);
		}
		currentScoreTekst.setText("Score: " + currentScore);
	}
}
