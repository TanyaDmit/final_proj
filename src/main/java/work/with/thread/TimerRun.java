package work.with.thread;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;
import work.with.info.PostalPackage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerRun {
    Timer timer;
    private int i;
    private ConnectWithDB connectForSend;
    private ArrayList<PostalPackage> stPackage;
    private boolean acceptFlag;
    private WriteInFile generalWriteInFile;

    public TimerRun(int seconds,  WriteInFile generalWriteInFile) {
        connectForSend = new ConnectWithDB(generalWriteInFile);
        this.generalWriteInFile = generalWriteInFile;
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000, 1000);
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("Time's up!");
            i = 5;
            stPackage = PostalPackage.getSendPackage(connectForSend,generalWriteInFile);
            acceptFlag = PostalPackage.changeStatus(connectForSend, stPackage, generalWriteInFile);
            if(!acceptFlag){
                connectForSend.setDisconnect(generalWriteInFile, true);
                timer.cancel(); //Terminate the timer thread
            }
        }
    }
}
