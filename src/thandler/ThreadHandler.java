package thandler;
// c'est juste un test d'une idée que j'ai eu


import audio.AudioProcessor;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.Timer;

public class ThreadHandler {

    private Thread threadSon;
    private boolean isthreadsondead;


    public ThreadHandler(){}

    public void setIsthreadsondead(boolean isthreadsondead) {
        this.isthreadsondead = isthreadsondead;
    }

    public boolean getisIsthreadsondead() {
        return isthreadsondead;
    }

    public Thread getThreadSon() {
        return threadSon;
    }

    public void startThread(/*AnimationTimer timer*/){
        threadSon.start();
        //timer.start();
    }



    public void setThreadSon(Thread threadSon) {
        this.threadSon = threadSon;
        this.isthreadsondead=false;
    }

    public void stopThreadSon( AudioProcessor as) throws InterruptedException {
        as.terminateAudioThread();
        this.threadSon.join();
        this.isthreadsondead=true;
    }




}
