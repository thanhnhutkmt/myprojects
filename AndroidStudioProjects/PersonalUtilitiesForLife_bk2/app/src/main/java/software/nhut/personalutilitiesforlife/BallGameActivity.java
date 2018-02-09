package software.nhut.personalutilitiesforlife;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import util.MyMedia;
import util.MyPhone;

public class BallGameActivity extends AppCompatActivity {
    private int score = 0;
    private TextView txtscore;
    private String scoreStringLabel, levelString;
    private AbsoluteLayout layoutbubble;

    private final int maxHeight = 200;
    private final int maxWidth = 128;
    private int level = 1;
    private boolean gamecontinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_game);
        MyPhone.setPortraitOrient(this);
        txtscore = (TextView) findViewById(R.id.txt_score);
        layoutbubble = (AbsoluteLayout) findViewById(R.id.layoutbubble);
        scoreStringLabel = getResources().getString(R.string.BallGameact_scorelabel);
        levelString = getResources().getString(R.string.BallGameact_level);
        txtscore.setText(scoreStringLabel + " : 0" + " - " + levelString + " : " + level);
        gamecontinue = true;
        new Thread(new Runnable() {
            @Override public void run() {
                while(gamecontinue) {
                    BallGameActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < level + 1; i++) runGame();
                        }
                    });
                    try {
                        Thread.sleep(3000 - level * 200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /*
        level 1 :      no score --, score++, no level--, level ++
        level 2 - 13 : score--,     score++, level--,     level ++
        level 14 :     score--,     score++, level--,    no level++
     */

    private void runGame() {
        final ImageView img = getBubble();
        final ObjectAnimator oa = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.bubblemove);
        oa.setDuration(4000);
        oa.setTarget(img);
        oa.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {}
            @Override public void onAnimationEnd(Animator animation) {
                // missed bubble ...
                if (!gamecontinue) return;
                layoutbubble.removeView(img);
                if (level > 1) score--;
                if (score == 10 * level && level < 14) {level++; score = 0;}
                if (score == -20 && level > 1) {level--; score = 0;}
                if (score == 10 * level && level == 14) {
                    gamecontinue = false;
                    layoutbubble.removeAllViews();
                    Toast.makeText(BallGameActivity.this, R.string.BallGameact_endgame, Toast.LENGTH_SHORT).show();
                }
                txtscore.setText(scoreStringLabel + " : " + score + " - " + levelString + " : " + level);
            }
            @Override public void onAnimationCancel(Animator animation) {
                if (level > 1) score++;
            }
            @Override public void onAnimationRepeat(Animator animation) {}
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                new Thread(new Runnable() {
                    @Override public void run() {
                        MyMedia.playSound(BallGameActivity.this, 5);
                        BallGameActivity.this.runOnUiThread(new Runnable() {
                            @Override public void run() {
                                oa.cancel();
                            }
                        });
                    }
                }).start();
            }
        });

        ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutbubble.addView(img, param);
        oa.start();
    }

    private ImageView getBubble() {
        ImageView img = new ImageView(this);
        final int screenWidth = MyPhone.getScreenWidth(this);
        float pos = (float) (Math.random() * screenWidth * 4 / 5);//(screenWidth - maxWidth));
        Log.i("MyTag", "Position " + pos);
        img.setX(pos);
        img.setBackgroundDrawable(randomBubble());
        return img;
    }

    private Drawable randomBubble() {
        switch((int)(1 + Math.random() * 17)) {
            case 1 : return getResources().getDrawable(R.drawable.n3b);
            case 2 : return getResources().getDrawable(R.drawable.n3g);
            case 3 : return getResources().getDrawable(R.drawable.n3o);
            case 4 : return getResources().getDrawable(R.drawable.n3p);
            case 5 : return getResources().getDrawable(R.drawable.n3r);
            case 6 : return getResources().getDrawable(R.drawable.n3y);

            case 7 : return getResources().getDrawable(R.drawable.n4b);
            case 8 : return getResources().getDrawable(R.drawable.n4g);
            case 9 : return getResources().getDrawable(R.drawable.n4o);
            case 10 : return getResources().getDrawable(R.drawable.n4p);
            case 11 : return getResources().getDrawable(R.drawable.n4r);
            case 12 : return getResources().getDrawable(R.drawable.n4pi);

            case 13 : return getResources().getDrawable(R.drawable.n5b);
            case 14 : return getResources().getDrawable(R.drawable.n5g);
            case 15 : return getResources().getDrawable(R.drawable.n5o);
            case 16 : return getResources().getDrawable(R.drawable.n5p);
            case 17 : return getResources().getDrawable(R.drawable.n5r);
            case 18 : return getResources().getDrawable(R.drawable.n5y);

            default : return getResources().getDrawable(R.drawable.n4r);
        }
    }
}
