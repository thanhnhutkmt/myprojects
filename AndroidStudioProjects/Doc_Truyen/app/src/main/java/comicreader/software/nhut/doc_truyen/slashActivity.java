package comicreader.software.nhut.doc_truyen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import static comicreader.software.nhut.doc_truyen.util.MyPhone.setPortraitOrient;

public class slashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setPortraitOrient(this);
        startAdmob();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void startAdmob() {
        MyGAdService.activity = this;
        startService(new Intent(this, MyGAdService.class));
    }
}
