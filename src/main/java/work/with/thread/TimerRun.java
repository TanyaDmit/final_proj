package work.with.thread;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;
import work.with.info.PostalPackage;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerRun extends TimerTask {
    private static int i;
    private ConnectWithDB connectForSend;
    private ArrayList<PostalPackage> stPackage;
    private WriteInFile generalWriteInFile;
    private boolean acceptFlag;

    public TimerRun(ConnectWithDB connectForSend, ArrayList<PostalPackage> stPackage, WriteInFile generalWriteInFile){
        System.out.println("i am here");
        this.connectForSend = connectForSend;
        this.stPackage = stPackage;
        this.generalWriteInFile = generalWriteInFile;
    }

    public TimerRun(){

    }

    @Override
    public void run() {
        try{
//            while(true){
//                stPackage = PostalPackage.getSendPackage(connectForSend,generalWriteInFile);
//                acceptFlag = PostalPackage.changeStatus(connectForSend, stPackage, generalWriteInFile);
//                if(!acceptFlag){
//                    break;
//                }
//            }
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("hd \t " + i);
    }
}
