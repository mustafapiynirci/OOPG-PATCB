package nl.han.ica.world.Beans;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.world.BeanWorld;

public class RainbowBean extends Bean implements IAlarmListener {

    public RainbowBean(BeanWorld world, int beanSize) {
        super(world, beanSize, "nl/han/ica/world/media/rainbowbean.png", 4);
        setCurrentFrameIndex(0);
    }

    public void popBean() {
//        for (GameObject g : world.getGameObjectItems()) {
//            System.out.println(g.getHeight());
//            if(g instanceof Bean) {
//                System.out.println("50 punten!");
//                world.deleteGameObject(g);
//            }
//        }
    }

    @Override
    public void triggerAlarm(String alarmName) {

    }
}
