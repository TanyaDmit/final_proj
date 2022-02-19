package work.with.thread;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;
import work.with.info.PostalNotification;
import work.with.info.PostalPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimerRun {
    Timer timer;
    private ConnectWithDB connectForSend;
    private ArrayList<PostalPackage> stPackage;
    private ArrayList<PostalNotification> stMessage;
    private boolean acceptFlag;

    public TimerRun(int seconds,  WriteInFile generalWriteInFile) {
        connectForSend = new ConnectWithDB(generalWriteInFile);
        timer = new Timer();
        if(seconds == 1){
            timer.schedule(new TimerSendPackage(), seconds*1000, 1000);
        } else {
            timer.schedule(new TimerSendMessage(), seconds*1000, 5000);
        }
    }

    class TimerSendPackage extends TimerTask {
        private WriteInFile timerWriteInFile;
        public TimerSendPackage() {
            this.timerWriteInFile = new WriteInFile("log.txt");
        }

        @Override
        public void run() {
//            System.out.println("Time's up!");
            stPackage = PostalPackage.getSendPackage(connectForSend,timerWriteInFile);
            acceptFlag = PostalPackage.changeStatus(connectForSend, stPackage, timerWriteInFile);
            if(!acceptFlag){
                connectForSend.setDisconnect(timerWriteInFile, true);
                try {
                    timerWriteInFile.close();
                } catch(Exception ex){
                    System.out.println("ошибка при закрытии соединения с файлом в таймере");
                }
                timer.cancel(); //Terminate the timer thread
            }
        }
    }

    class TimerSendMessage extends TimerTask {
        private WriteInFile timerWriteInFile;
        public TimerSendMessage() {
            this.timerWriteInFile = new WriteInFile("log.txt");
        }

        @Override
        public void run() {
//            System.out.println("Time's up!");
            stMessage = PostalNotification.getSendNotifications(connectForSend,timerWriteInFile);
            acceptFlag = PostalNotification.changeStatus(connectForSend, stMessage, timerWriteInFile);
            if(!acceptFlag){
                connectForSend.setDisconnect(timerWriteInFile, true);
                try {
                    timerWriteInFile.close();
                } catch(Exception ex){
                    System.out.println("ошибка при закрытии соединения с файлом в таймере");
                }
                timer.cancel(); //Terminate the timer thread
            }
        }
    }
}
