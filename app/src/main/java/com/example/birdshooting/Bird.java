package com.example.birdshooting;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class Bird {
    public int speed =20;
    public boolean wasShot = true;
    public boolean startFly =false;
    int x, y, width, height;
    Bitmap bird;
    Bird(Resources res){
        bird = BitmapFactory.decodeResource(res, R.drawable.bird);
        width = bird.getWidth();
        height = bird.getHeight();
        width /=10;
        height /=10;
        bird = Bitmap.createScaledBitmap(bird, width, height, false);
    }
    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }
}

