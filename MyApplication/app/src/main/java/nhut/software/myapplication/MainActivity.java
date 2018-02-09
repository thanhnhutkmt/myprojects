package nhut.software.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    AdView adView;
    Button button;
    TextView result;
    LinearLayout ads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView)findViewById(R.id.result);
        button = (Button)findViewById(R.id.btBanner);
        ads = (LinearLayout)findViewById(R.id.ads);
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                result.append("\nBanner accessed!");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                result.append("\nBanner failed to load!");
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                result.append("\nBanner Opened!");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                result.append("\nBanner Loaded!");
            }
        });
        adView.setAdUnitId(getString(R.string.admob_banner_ad_unit_id));
        final ImageView bannerImage = new ImageView(this);
        bannerImage.setImageResource(R.drawable.box);
        adView.addView(bannerImage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adView.loadAd(new AdRequest.Builder().build());
                ads.addView(adView);
            }
        });
        adView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adView.removeView(bannerImage);
            }
        });
    }
}
