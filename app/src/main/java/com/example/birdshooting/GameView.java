package com.example.birdshooting;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying = true;
    private Paint paint;
    private Background background1, background2;
    private int screenX, screenY, score =0, birdCount =0;
    private List<Bullet> bullets;

    private Flight flight;
    private Bird bird;
    private Random random;
    private MediaPlayer mediaPlayer;
    private boolean isGameOver = false;


    public GameView(Context context , int screenX, int screenY) {
        super(context);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.touch_sound);
        this.screenX = screenX;
        this.screenY = screenY;
      //  bird = new Bird(getResources());
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        flight = new Flight(this,screenY, getResources());
        background2.x = screenX; //background 2 se k hien len man hinh
        bullets = new ArrayList<>();
        paint = new Paint();
        paint.setTextSize(130);
        paint.setColor(Color.YELLOW);
        random = new Random();
//        for(int i=0;i<6;i++){
//            birdCount++;
//            if(birdCount==68){
//                isGameOver=true;
//            }
//            else{
//                Bird bird = new Bird(getResources());
//                birds[i]=bird;
//            }
//
//        }
    }

    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();
        }
    }
    public void update(){
        //moi lan update se move 10 pixel sang ben trai
        background1.x -= 10;
        background2.x -= 10;

        if(background1.x + background1.background.getWidth() <0){ //neu <0 background completely out of screen
            background1.x = screenX;
        }
        if(background2.x + background2.background.getWidth() <0){
            background2.x = screenX;
        }
//
//        if(bird.startFly){
//            bird.x += 20;
//        }
        if(flight.isGoingUp){
            flight.isGoingRight = false;
            flight.isGoingLeft = false;

            if(flight.y >=50){
                flight.y -= 10;
            }
            else {
                mediaPlayer.start();
                flight.isGoingUp =false;
                flight.isGoingDown= true;
            }
        }
        if (flight.isGoingDown){
            flight.isGoingRight = false;
            flight.isGoingLeft = false;
            flight.isGoingUp = false;
            if(flight.y + flight.height < screenY){
                flight.y += 10;
            }
            else {
                mediaPlayer.start();
                flight.isGoingDown= false;
                flight.isGoingUp =true;


            }

        }
        if (flight.isGoingRight){
            flight.isGoingDown= false;
            flight.isGoingUp =false;

            if (flight.x + flight.width <= screenX/2)
                flight.x +=10;
            else
            {
                mediaPlayer.start();
                flight.isGoingLeft = true;
                flight.isGoingRight = false;
            }
        }
        if(flight.isGoingLeft){
            flight.isGoingDown= false;
            flight.isGoingUp =false;
            if (flight.x >0)
                flight.x -= 10;
            else
            {
                mediaPlayer.start();
                flight.isGoingLeft = false;
                flight.isGoingRight = true;
            }
        }
        if(flight.y <0){
            flight.y =0;
        }
        if(flight.y > screenY - flight.height)
            flight.y = screenY - flight.height;



        List<Bullet> trash = new ArrayList<>();

        for(Bullet bullet : bullets){
            if(bullet.x >screenX)
            trash.add(bullet);
            bullet.x +=50;

//            for (Bird bird : birds){
//                if(Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())){
//                    score++;
//                    bird.x = -500;
//                    bullet.x = screenX +500;
//                    bird.wasShot = true;
//                }
//            }
        }
        for(Bullet bullet:trash){
            bullets.remove(bullet);

        }
//        for (Bird bird:birds){
           // bird.x =200;
//            Log.d("Tag", "1");
//
//            bird.x += 20; //move the bird towards the flight
//            if (bird.x + bird.width < 0){ // left side
//                if(!bird.wasShot){
//                    isGameOver = true;
//                    return;
//                }
//
//                bird.wasShot = false;
//            }
//            if(Rect.intersects(bird.getCollisionShape(),flight.getCollisionShape())){
//                isGameOver = true;
//                return;
//            }
//            if(bird.x >screenX)
//               // trash.add(bullet);
//            bird.x +=50;
//        }

    }
    public void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background , background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background , background2.x, background2.y, paint);
            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y,paint);
            canvas.drawBitmap(bird.bird, screenX-200, 200, paint);
            canvas.drawText(score+"", screenX-150, 164, paint);
//            for(Bird bird: birds){
//                canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
//            }
            for(Bullet bullet :bullets){
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }
            if(isGameOver){
                saveScore();
                //return;

            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void saveScore() {
    }

    public void sleep(){

    }
    public void resume(){
        thread = new Thread(this);
        thread.start();
    }
    public void pause(){
        try {
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Implement this method to handle touch screen motion events.
     * <p>
     * If this method is used to detect click actions, it is recommended that
     * the actions be performed by implementing and calling
     * {@link #performClick()}. This will ensure consistent system behavior,
     * including:
     * <ul>
     * <li>obeying click sound preferences
     * <li>dispatching OnClickListener calls
     * </ul>
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()>screenY/2){ //cham ben phai man hinh
                    flight.isGoingLeft = false;
                    flight.isGoingDown = false;
                    flight.isGoingUp = false;
                    flight.isGoingRight =true;

                }
                if(event.getX() < screenX/2 && event.getY()>screenY/2){
                    flight.isGoingLeft = false;
                    flight.isGoingUp = false;
                    flight.isGoingRight =false;
                    flight.isGoingDown = true;

                }
                if(event.getX()< screenX/2 && event.getY()<screenY/2){ //cham trai tren
                    flight.isGoingLeft = false;
                    flight.isGoingRight =false;
                    flight.isGoingDown = false;
                    flight.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                    //flight.isGoingUp = false;
                break;
        }
        return true; // neu return fale se k nhan touch event
    }
    public void newBullet(){
        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height/2);
        bullets.add(bullet);
      //  mediaPlayer.start();
    }

}
