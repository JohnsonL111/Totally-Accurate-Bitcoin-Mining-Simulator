package cmpt276.assignments.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import cmpt276.assignments.assignment3.ui.MainMenuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView bitCoin = findViewById(R.id.bitCoinImg);
        ImageView gridBlock1 = findViewById(R.id.gridBlock1);
        ImageView gridBlock2 = findViewById(R.id.gridBlock2);

        setMenuButton();
        beginAnimations(bitCoin, gridBlock1, gridBlock2);
    }

    // Citation: https://abhiandroid.com/ui/countdown-timer
    private void waitFourSeconds() {
        // four second delay until switch to main menu activity.
        new CountDownTimer(4000, 1000){
            public void onTick(long millisUntilFinished){ }
            public  void onFinish(){
                // make intent to launch HelpActivity
                Intent helpIntent = MainMenuActivity.makeIntent(MainActivity.this);
                startActivity(helpIntent);
            }
        }.start();
    }

    private void beginAnimations(ImageView bitCoin, ImageView gridBlock1, ImageView gridBlock2) {
        bitCoinAnimation(bitCoin);
        gridBlockAnimation(gridBlock1);
        gridBlockAnimation(gridBlock2);
    }

    private void gridBlockAnimation(ImageView gridBlock) {
        Animation gridBlockRot = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotation_animation);
        gridBlock.startAnimation(gridBlockRot);

        // Tracks when the animation finishes and switches to main menu activity
        // 4 seconds after animation is done.
        gridBlockRot.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                waitFourSeconds();
            }
        });
    }

    private void bitCoinAnimation(ImageView bitCoin) {
        // Set animation for bitcoin.
        final int bitCoinDuration = 10000;
        // Citation: http://android-coding.blogspot.com/2015/10/interactive-flip-imageview-using.html
        // Although our code is very different, we still found some inspiration from it.
        ObjectAnimator flip = ObjectAnimator.ofFloat(bitCoin, "rotationY", -360f, 360f);
        flip.setDuration(bitCoinDuration);
        flip.start();
    }

    private void setMenuButton() {
        Button menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(view -> {

            // make intent to launch HelpActivity
            Intent helpIntent = MainMenuActivity.makeIntent(MainActivity.this);
            startActivity(helpIntent);
        });
    }
}