package com.example.birdshooting;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class Bird {
    public int speed =20;
    public boolean wasShot = true;
    public boolean startFly =false;
    int x =0 , y, width, height;
    Bitmap bird, bird2;
    Bird(Resources res, int screenX){
        this.x = screenX;
        bird = BitmapFactory.decodeResource(res, R.drawable.bird2);
        bird2 = BitmapFactory.decodeResource(res, R.drawable.bird);
        width = bird.getWidth();
        height = bird.getHeight();
        width /=12;
        height /=12;

        bird = Bitmap.createScaledBitmap(bird, width, height, false);
        bird2 = Bitmap.createScaledBitmap(bird, width, height, false);
        y= -height;
    }
    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }

    public Bitmap getBird() {
        return bird;
    }
    public Bitmap getBird2() {
        return bird2;
    }
}

