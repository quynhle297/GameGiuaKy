package com.example.birdshooting;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


public class Flight {

    public boolean isGoingDown = false;
    int stt =0;
    int x, y, width, height;
    boolean isGoingUp = false;
    public boolean isGoingLeft = false;
    public boolean isGoingRight = false;
    private Bitmap flight1, flight2;
    private GameView gameView;
    Flight(GameView gameView, int screenY, Resources res ){
        this.gameView = gameView;
        flight1 = BitmapFactory.decodeResource(res, R.drawable.fly);
        //flight2 = BitmapFactory.decodeResource(res, R.drawable.thunder_plane);
        width = flight1.getWidth();
        height = flight1.getHeight();
        width /=6;
        height /=6;
//        width *= (int) screenRatioX;
//        height *= (int)screenRatioY;
//        Log.d("TAG", String.valueOf((int)screenRatioX));
//        Log.d("TAG", String.valueOf(height));
        flight1= Bitmap.createScaledBitmap(flight1, width, height, false);
        y = (int) screenY/2;
        x = (int)(64);
    }
    Bitmap getFlight(){
        stt++;
      //  Log.d("TAG", String.valueOf(stt));
        if(stt ==10){
            stt=0;
            gameView.newBullet();

        }

        return  flight1;
    }
    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }
}
