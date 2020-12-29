package com.example.birdshooting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying = true, isLevelUp = false;
    private Paint paint;
    private Background background1, background2;
    private int screenX, screenY, score =0, birdCount =0;
    private List<Bullet> bullets;
    private Flight flight;
    private Random random;
    private MediaPlayer mediaPlayer, gunSound;
    private boolean isGameOver = false;
    private Bird[] birds;
    private boolean isWiner = false;
    private int[] listScore = new int[]{68,60,50,40,30,20};
    private HighScoreActivity high;
    GameView gameView;
    public GameView(Context context , int screenX, int screenY) {
        super(context);

        high = new HighScoreActivity();
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.touch_sound);
        gunSound = MediaPlayer.create(getContext(), R.raw.plane_shoot);
        this.screenX = screenX;
        this.screenY = screenY;
      //  bird = new Bird(getResources());
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        flight = new Flight(this,screenY, getResources());
        background2.x =  background1.width; //background 2 se k hien len man hinh
        bullets = new ArrayList<>();
        paint = new Paint();
        paint.setTextSize(130);
        paint.setColor(Color.YELLOW);
        random = new Random();
        birds = new Bird[4];
        for(int i=0;i<4;i++){
            birdCount++;
            Bird bird = new Bird(getResources(), screenX);
            birds[i]=bird;
        }
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

        background1.x -= 30;
        background2.x -= 30;
        if(background1.x + background1.width < 0){ //chay het ve ben trai man hinh
            background1.x = background2.x+background2.width;
        }
        if(background2.x + background2.width < 0){ //chay het ve ben trai man hinh
            background2.x = background1.x+background1.width;
        }

        if(flight.isGoingUp){
            flight.isGoingRight = false;
            flight.isGoingLeft = false;

            if(flight.y >=0){
                flight.y -= 20;
            }
            else {
                flight.isGoingUp =false;
                flight.isGoingDown= true;
            }
        }
        if (flight.isGoingDown){
            flight.isGoingRight = false;
            flight.isGoingLeft = false;
            flight.isGoingUp = false;
            if(flight.y + flight.height < screenY){
                flight.y += 20;
            }
            else {
                flight.isGoingDown= false;
                flight.isGoingUp =true;


            }

        }
        if (flight.isGoingRight){
            flight.isGoingDown= false;
            flight.isGoingUp =false;

            if (flight.x + flight.width <= screenX/2)
                flight.x +=20;
            else
            {
                flight.isGoingLeft = true;
                flight.isGoingRight = false;
            }
        }
        if(flight.isGoingLeft){
            flight.isGoingDown= false;
            flight.isGoingUp =false;
            if (flight.x >0)
                flight.x -= 20;
            else
            {

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
            for (Bird bird : birds){
                if(Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())){
                    score++;
                    bird.x = -500;
                    bullet.x = screenX +500;
                    bird.wasShot = true;
                }
            }
        }
        for(Bullet bullet:trash){
            bullets.remove(bullet);
        }

        for (Bird bird : birds){

          //  Log.d("TAG", "1111 ");
            if(score ==68){
                 isWiner = true;
            }
            if (score >= 25){
                isLevelUp = true;
                bird.x -=15;
            }
            else
             bird.x -= 5; //speed = 20
            Log.d("TAG", bird.x +"111 ");
            if (bird.x + bird.width < 0){
                if(!bird.wasShot){
                    isGameOver = true;
                    return;
                }
                bird.x = screenX;
                bird.y = random.nextInt(screenY - bird.height);
                bird.wasShot = false;

            }
            if(Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())){
                isGameOver = true;
                return;
            }
        }

    }
    public void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background , background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background , background2.x, background2.y, paint);
            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y,paint);
           for (Bird bird: birds){
               if(isLevelUp){
                   canvas.drawBitmap(bird.getBird2(), bird.x, bird.y, paint);
               }
               else
                 canvas.drawBitmap(bird.getBird(), bird.x, bird.y, paint);
           }
            canvas.drawText(score+"", screenX-150, 164, paint);
            for(Bullet bullet :bullets){
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            }
            if(isGameOver){
                saveScore();
                Bitmap gameOver = BitmapFactory.decodeResource(getResources(), R.drawable.end);
                gameOver = Bitmap.createScaledBitmap(gameOver, gameOver.getWidth(), gameOver.getHeight(), false);
                canvas.drawBitmap(gameOver,screenX/4, screenY/4, paint);
                getHolder().unlockCanvasAndPost(canvas);
                isPlaying = false;
                return;

            }
            if(isWiner){
                saveScore();
                Bitmap gameOver = BitmapFactory.decodeResource(getResources(), R.drawable.winner);
                gameOver = Bitmap.createScaledBitmap(gameOver, gameOver.getWidth(), gameOver.getHeight(), false);
                canvas.drawBitmap(gameOver,screenX/4, screenY/4, paint);
                getHolder().unlockCanvasAndPost(canvas);
                isPlaying = false;
                return;

            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void saveScore() {
        if(score> listScore[5]){

        for (int i =0;i <6;i++){
            Log.d("TAG", score+" gameview1");
            if (score >= listScore[i]){
                for(int j =5; j > i; j--){
                    listScore[j] = listScore[j-1];
                }
                listScore[i] = score;
                break;
            }

        }
            for (int i =0;i<6;i++){
                Log.d("TAG", listScore[i]+" gameview");
            }
        SharedPreferences sh = getContext().getSharedPreferences("score", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        for(int i =0;i<6;i++){
            editor.putString("score"+i, String.valueOf(listScore[i]));
        }

        editor.commit();
    }
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
                if(event.getX()<flight.x){

                    flight.isGoingRight =false;
                    flight.isGoingDown = false;
                    flight.isGoingUp = false;
                    flight.isGoingLeft = true;
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
        gunSound.start();
        bullets.add(bullet);
    }
}
