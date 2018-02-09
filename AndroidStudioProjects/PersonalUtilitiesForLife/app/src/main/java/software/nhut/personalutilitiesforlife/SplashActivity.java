package software.nhut.personalutilitiesforlife;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Nhut on 11/1/2016.
 */

public class SplashActivity extends AppCompatActivity {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Animation a = AnimationUtils.loadAnimation(this, R.anim.slashscreenanimate);
//        findViewById(R.style.).startAnimation(a);
//        startService(new Intent(this, MyLockScreenService.class));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
