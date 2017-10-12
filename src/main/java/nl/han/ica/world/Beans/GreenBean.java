package nl.han.ica.world.Beans;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.world.BeanWorld;

public class GreenBean extends Bean implements IAlarmListener {

    public GreenBean(BeanWorld world, int beanSize) {
        super(world, beanSize, "nl/han/ica/world/media/greenbean.png", 4);
        setCurrentFrameIndex(4);
        startAlarm();
    }

    private void startAlarm() {
//        Alarm alarm=new Alarm("New bubble", 0.5 );
//        world.addAlarmToList(alarm);
//        alarm.addTarget(this);
//        alarm.start();
    }

    private int maxSpriteFrameCount = 3;

    @Override
    public void triggerAlarm(String alarmName) {
//        System.out.println("Trigger Alarm GreenBean");
//        setCurrentFrameIndex(spriteFrameCountStart);
//        spriteFrameCountStart++;
//        if(spriteFrameCountStart > maxSpriteFrameCount) {
//            spriteFrameCountStart = 0;
//        }
//        startAlarm();
    }



//    private void startAlarm() {
//        Alarm alarm = new Alarm("Sprite changer", 1 / 30);
//        alarm.addTarget(this);
//        alarm.start();
//        System.out.println("Start alarm");
//    }
//
//    @Override
//    public void triggerAlarm(String alarmName) {
//        System.out.println("Alarm triggered");
////        int maxSpriteFrameCount = 4;
////        System.out.println("Alarm triggeredd!!");
////        setCurrentFrameIndex(spriteFrameCountStart);
////        System.out.println(spriteFrameCountStart);
////        spriteFrameCountStart = spriteFrameCountStart + 1;
////        if(spriteFrameCountStart > maxSpriteFrameCount) {
////            spriteFrameCountStart = 0;
////        }
//    }
}
