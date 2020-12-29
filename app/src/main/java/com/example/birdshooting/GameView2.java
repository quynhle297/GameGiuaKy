package com.example.birdshooting;

import android.content.Context;
import android.view.SurfaceView;

public class GameView2  extends SurfaceView implements Runnable {
    public GameView2(Context context) {
        super(context);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        boolean isPlaying = true;
        while (isPlaying){
            update();
            draw();
           // sleep();
        }

    }

    private void update() {

    }
    private void draw() {

    }
}
