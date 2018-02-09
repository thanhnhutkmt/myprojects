package comicreader.software.nhut.doc_truyen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;

import static comicreader.software.nhut.doc_truyen.util.MyPhone.setPortraitOrient;

public class Nativeexpressad extends AppCompatActivity {
    private NativeExpressAdView NEAView;
    private Button btnnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_interestitial);
        btnnext = (Button)findViewById(R.id.btnnext);
        btnnext.setEnabled(false);

        setTitle("");
        setPortraitOrient(this);
        showAd();
    }

    private void showAd() {
        AdRequest.Builder b = new AdRequest.Builder();
        AdView adv = (AdView) findViewById(R.id.adView_adinter);
        if (Constant.TESTING) {
            b.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
            b.addTestDevice(Constant.myTestID);
        }
        adv.loadAd(b.build());

        NEAView = (NativeExpressAdView) findViewById(R.id.NEAView);
        NEAView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                btnnext.setEnabled(true);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
        NEAView.loadAd(b.build());
    }

    public void nextAct(View view) {
//        nextActivity();
        NEAView.destroy();
        finish();
    }
}
