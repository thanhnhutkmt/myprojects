package software.nhut.personalutilitiesforlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Nhut on 11/1/2016.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Animation a = AnimationUtils.loadAnimation(this, R.anim.slashscreenanimate);
//        findViewById(R.style.).startAnimation(a);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
