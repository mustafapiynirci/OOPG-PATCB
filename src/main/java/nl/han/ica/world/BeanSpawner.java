package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.Beans.GreenBean;

import java.util.ArrayList;
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

        int lengthHelper = (world.getWidth() / world.getTileSize()) - 1;
        int[] spawnHelper = new int[lengthHelper];
        for(int i = 0; i < lengthHelper; i++) {
            spawnHelper[i] = i * world.getTileSize();
        }

        world.addGameObject(b, spawnHelper[random.nextInt(lengthHelper)], 0 - b.getHeight());
        startAlarm();
    }
}
