package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.Beans.GreenBean;

import java.util.Random;

public class BeanSpawner implements IAlarmListener {

    private double beansPerSecond;
    private Random random;
    private BeanWorld world;


    /** Constructor
     * @param world Referentie naar de wereld
     * @param beansPerSecond Aantal bellen dat per seconden gemaakt moet worden
     */
    public BeanSpawner(BeanWorld world, double beansPerSecond) {
        this.beansPerSecond=beansPerSecond;
        this.world=world;
        random=new Random();
        startAlarm();
    }

    private void startAlarm() {
        Alarm alarm=new Alarm("New bubble",1/beansPerSecond);
        alarm.addTarget(this);
        alarm.start();
    }

    @Override
    public void triggerAlarm(String alarmName) {
        int beanSize = 32;
        Bean b = new GreenBean(world, beanSize);
//        Bubble b=new Bubble(bubbleSize,world,popSound);
//        world.addGameObject(b, random.nextInt(world.getWidth()), world.getHeight());
        world.addGameObject(b, random.nextInt(world.getWidth()), 0);
        startAlarm();
    }
}
