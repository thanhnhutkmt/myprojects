package software.nhut.personalutilitiesforlife;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Slashgame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slashgame);
        final ImageView imv = (ImageView) findViewById(R.id.anim_img);
        imv.setBackgroundResource(R.drawable.animslashscreen);
        ((AnimationDrawable) imv.getBackground()).start();

        imv.postDelayed(new Runnable() {
            @Override public void run() {
                ((AnimationDrawable) imv.getBackground()).stop();
                startActivity(new Intent(Slashgame.this, BallGameActivity.class));
                finish();
            }
        }, 5000);
    }
}
