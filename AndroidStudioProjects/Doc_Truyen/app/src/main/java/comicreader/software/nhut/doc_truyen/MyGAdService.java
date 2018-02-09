package comicreader.software.nhut.doc_truyen;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;

public class MyGAdService extends Service {
    public static Activity activity;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    private AdRequest.Builder b;
    private boolean runFlag;

    public MyGAdService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        runFlag = true;
        b = new AdRequest.Builder();
        if (Constant.TESTING) {
            b.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
            b.addTestDevice(Constant.myTestID);
        }
        //Interstitial advertisement
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4724020476164030/6142599506");
        mInterstitialAd.loadAd(b.build());

        //Rewarded video advertisement
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.loadAd("ca-app-pub-4724020476164030/4107463100", b.build());
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                mRewardedVideoAd.loadAd("ca-app-pub-4724020476164030/4107463100",
                        b.build());
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Integer> count = new ArrayList<Integer>();
                try {
                    while (count.size() == 0) {
                        Thread.sleep(2000);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                    count.add(0);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("MyTag", "mInterstitialAd service finishes");
            }
        }).start();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(runFlag) {
                    try {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ((Constant.countPage >
                                        Constant.thresholdPage + Constant.lastCountPage)
                                    && mRewardedVideoAd.isLoaded()) {
                                    mRewardedVideoAd.show();
                                    Constant.lastCountPage = Constant.countPage;
                                }
                            }
                        });
                        Thread.sleep(1000L*60); // recheck after 60s
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("MyTag", "ad service running");
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
        runFlag = false;
    }
}
