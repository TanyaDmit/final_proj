package work.with.thread;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;
import work.with.info.PostalNotification;
import work.with.info.PostalPackage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimerRun {
    Timer timer;
    private ConnectWithDB connectForSend;
    private ArrayList<PostalPackage> stPackage;
    private ArrayList<PostalNotification> stMessage;
    private boolean acceptFlag;
    public static boolean activeWorkFlag = true;

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
        private boolean emptyTable = false;
        private int counter = 0;

        public TimerSendPackage() {
            this.timerWriteInFile = new WriteInFile("log.txt");
        }

        @Override
        public void run() {
            stPackage = PostalPackage.getSendPackage(connectForSend,timerWriteInFile);
            acceptFlag = PostalPackage.changeStatus(connectForSend, stPackage, timerWriteInFile);
            if(acceptFlag){
                emptyTable = true;
                counter = 0;
            }
            if(!acceptFlag && emptyTable) {
                activeWorkFlag = false;
                counter++;
            }
            if(counter == 10){
                System.out.println("все посылки перешли в финальный статус");
                connectForSend.setDisconnect(timerWriteInFile, true);
                try {
                    timerWriteInFile.close();
                } catch(Exception ex){
                    System.out.println("ошибка при закрытии соединения с файлом в таймере");
                }
                timer.cancel();
            }
        }
    }

    class TimerSendMessage extends TimerTask {
        private WriteInFile timerWriteInFile;
        private boolean emptyTable = false;
        private int counter = 0;

        public TimerSendMessage() {
            this.timerWriteInFile = new WriteInFile("log.txt");
        }

        @Override
        public void run() {
            stMessage = PostalNotification.getSendNotifications(connectForSend,timerWriteInFile);
            acceptFlag = PostalNotification.changeStatus(connectForSend, stMessage, timerWriteInFile);
            if(acceptFlag){
                emptyTable = true;
                counter = 0;
            }
            if(!acceptFlag && emptyTable) {
                activeWorkFlag = false;
                counter++;
            }
            if(counter == 5) {
                System.out.println("все сообщения перешли в финальный статус");
                connectForSend.setDisconnect(timerWriteInFile, true);
                try {
                    timerWriteInFile.close();
                    PostalNotification.closeFileMessages();
                    activeWorkFlag = true;
                } catch (Exception ex) {
                    System.out.println("ошибка при закрытии соединения с файлом в таймере");
                }
                timer.cancel();
            }
        }
    }
}
