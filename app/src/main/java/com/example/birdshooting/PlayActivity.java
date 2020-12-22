package com.example.birdshooting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.List;
import java.util.Random;

public class PlayActivity extends Activity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        //gameView = new GameView(this,point.x, point.y );
        gameView = new GameView(this, point.x, point.y);
        setContentView(gameView);
    }

    /**
     * Called as part of the activity lifecycle when the user no longer actively interacts with the
     * activity, but it is still visible on screen. The counterpart to {@link #onResume}.
     *
     * <p>When activity B is launched in front of activity A, this callback will
     * be invoked on A.  B will not be created until A's {@link #onPause} returns,
     * so be sure to not do anything lengthy here.
     *
     * <p>This callback is mostly used for saving any persistent state the
     * activity is editing, to present a "edit in place" model to the user and
     * making sure nothing is lost if there are not enough resources to start
     * the new activity without first killing this one.  This is also a good
     * place to stop things that consume a noticeable amount of CPU in order to
     * make the switch to the next activity as fast as possible.
     *
     * <p>On platform versions prior to {@link Build.VERSION_CODES#Q} this is also a good
     * place to try to close exclusive-access devices or to release access to singleton resources.
     * Starting with {@link Build.VERSION_CODES#Q} there can be multiple resumed
     * activities in the system at the same time, so {@link #onTopResumedActivityChanged(boolean)}
     * should be used for that purpose instead.
     *
     * <p>If an activity is launched on top, after receiving this call you will usually receive a
     * following call to {@link #onStop} (after the next activity has been resumed and displayed
     * above). However in some cases there will be a direct call back to {@link #onResume} without
     * going through the stopped state. An activity can also rest in paused state in some cases when
     * in multi-window mode, still visible to user.
     *
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onResume
     * @see #onSaveInstanceState
     * @see #onStop
     */
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    /**
     * Called after {@link #onRestoreInstanceState}, {@link #onRestart}, or
     * {@link #onPause}, for your activity to start interacting with the user. This is an indicator
     * that the activity became active and ready to receive input. It is on top of an activity stack
     * and visible to user.
     *
     * <p>On platform versions prior to {@link Build.VERSION_CODES#Q} this is also a good
     * place to try to open exclusive-access devices or to get access to singleton resources.
     * Starting  with {@link Build.VERSION_CODES#Q} there can be multiple resumed
     * activities in the system simultaneously, so {@link #onTopResumedActivityChanged(boolean)}
     * should be used for that purpose instead.
     *
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onRestoreInstanceState
     * @see #onRestart
     * @see #onPostResume
     * @see #onPause
     * @see #onTopResumedActivityChanged(boolean)
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}