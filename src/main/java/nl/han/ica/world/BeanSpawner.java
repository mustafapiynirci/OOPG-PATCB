package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.Beans.GreenBean;
import nl.han.ica.world.Beans.RainbowBean;
import nl.han.ica.world.Beans.TimeSlowingBean;
import nl.han.ica.world.Beans.WhiteBean;

import java.util.Random;

public class BeanSpawner implements IAlarmListener {
	
	private final static String[] beanTypes = { "RainbowBean", "GreenBean" };
	private final static double[] beanChances = { 0.05, 1 };
	
	private double beansPerSecond;
	private Random random;
	private BeanWorld world;
	
	/**
	 * Constructor
	 * 
	 * @param world
	 *            Referentie naar de wereld
	 * @param beansPerSecond
	 *            Aantal bellen dat per seconden gemaakt moet worden
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
		beansPerSecond += 0.05;
		System.out.println(beansPerSecond);
	}
	
	@Override
	public void triggerAlarm(String alarmName) {
		int beanSize = 32;
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
			b = new RainbowBean(world, beanSize);
		else if (whichBean == "WhiteBean")
			b = new WhiteBean(world, beanSize);
		else if (whichBean == "TimeSlowingBean")
			b = new TimeSlowingBean(world, beanSize);
		else
			b = new GreenBean(world, beanSize);
		
		int lengthHelper = (world.getWidth() / world.getTileSize()) - 1;
		int[] spawnHelper = new int[lengthHelper];
		for (int i = 0; i < lengthHelper; i++) {
			spawnHelper[i] = i * world.getTileSize();
		}
		world.addGameObject(b, spawnHelper[random.nextInt(lengthHelper)], 0 - b.getHeight());
		startAlarm();
	}
}
