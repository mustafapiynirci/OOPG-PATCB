package nl.han.ica.world;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.world.Beans.Bean;
import nl.han.ica.world.Beans.GreenBean;
import nl.han.ica.world.Beans.RainbowBean;

import java.util.Random;

public class BeanSpawner implements IAlarmListener {

    private double beansPerSecond;
    private Random random;
    private BeanWorld world;


    /** Constructor
     * @param world reference to the BeanWorld
     * @param beansPerSecond Amount of beans per second that will get generated
     */
    public BeanSpawner(BeanWorld world, double beansPerSecond) {
        this.beansPerSecond=beansPerSecond;
        this.world=world;
        random=new Random();
        startAlarm("New beanspawner");
    }

    private void startAlarm(String alarmName) {
        Alarm alarm=new Alarm(alarmName,1/beansPerSecond);
        world.addAlarmToList(alarm);
        alarm.addTarget(this);
        alarm.start();
    }

    /**
     * This method gets called when the alarm should go off
     * @param alarmName
     *            Name of the alarm
     */
    @Override
    public void triggerAlarm(String alarmName) {
        int beanSize = 32;
        int whichBean = 0;
        Bean b;
        double r = random.nextDouble();
        if (r < 0.4) { whichBean = 1; }
        switch (whichBean) {
            case 1:
                b = new RainbowBean(world, beanSize);
                break;
            default:
                b = new GreenBean(world, beanSize);
        }
        int lengthHelper = (world.getWidth() / world.getTileSize()) - 1;
        int[] spawnHelper = new int[lengthHelper];
        for(int i = 0; i < lengthHelper; i++) {
            spawnHelper[i] = i * world.getTileSize();
        }
        world.addGameObject(b, spawnHelper[random.nextInt(lengthHelper)], 0 - b.getHeight());
        startAlarm(alarmName);
    }
}
