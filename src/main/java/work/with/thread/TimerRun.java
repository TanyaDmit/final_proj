package work.with.thread;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerRun extends TimerTask {
    private static int i;

    @Override
    public void run(){
        System.out.println(++i);
        try{
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("\t " + i);
    }
}
