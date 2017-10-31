package nl.han.ica.world.spawners;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.world.BeanWorld;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.Beans.GreenBean;
import nl.han.ica.world.Beans.RainbowBean;
import nl.han.ica.world.Beans.TimeSlowingBean;
import nl.han.ica.world.Beans.WhiteBean;

import java.util.Random;

public class BeanSpawner implements IAlarmListener {
	private final static String[] beanTypes = { "RainbowBean", "WhiteBean", "GreenBean" };
	private final static double[] beanChances = { 0.03, 0.1, 1 };
	private double beansPerSecond;
	private Random random;
	private BeanWorld world;
	
	/**
	 * Constructor
	 * 
	 * @param world
	 *            reference to the BeanWorld
	 * @param beansPerSecond
	 *            Amount of beans per second that will get generated
	 */
	public BeanSpawner(BeanWorld world, double beansPerSecond) {
		this.beansPerSecond = beansPerSecond;
		this.world = world;
		random = new Random();
		startAlarm();
	}
	
	private void startAlarm() {
		Alarm alarm = new Alarm("New bubble", 1 / beansPerSecond + random.nextDouble());
		alarm.addTarget(this);
		alarm.start();
	}
	
	public void increaseSpeed() {
		System.out.println(beansPerSecond);
		beansPerSecond += 0.1;
	}
	
	/**
	 * This method gets called when the alarm should go off
	 * 
	 * @param alarmName
	 *            Name of the alarm
	 */
	@Override
	public void triggerAlarm(String alarmName) {
		String whichBean = "GreenBean";
		Bean b;
		double r = random.nextDouble();
		
		for (int i = 0; i < beanTypes.length && i < beanChances.length; i++) {
			if (r < beanChances[i]) {
				whichBean = beanTypes[i];
				break;
			}
		}
		
		if (whichBean == "RainbowBean")
			b = new RainbowBean(world);
		else if (whichBean == "WhiteBean")
			b = new WhiteBean(world);
		else if (whichBean == "TimeSlowingBean")
			b = new TimeSlowingBean(world);
		else
			b = new GreenBean(world);
		
		int lengthHelper = (world.getWidth() / world.getTileSize()) - 1;
		int[] spawnHelper = new int[lengthHelper];
		for (int i = 0; i < lengthHelper; i++) {
			spawnHelper[i] = i * world.getTileSize();
		}
		world.addGameObject(b, spawnHelper[random.nextInt(lengthHelper)], 0 - b.getHeight());
		startAlarm();
	}
}
