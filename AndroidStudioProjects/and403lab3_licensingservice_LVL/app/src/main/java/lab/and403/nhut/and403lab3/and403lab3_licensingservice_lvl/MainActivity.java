package lab.and403.nhut.and403lab3.and403lab3_licensingservice_lvl;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;

public class MainActivity extends AppCompatActivity {
    private String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAioNn7/2fkohz/3fvMXbB7VX+Uc/IQUjT5SQ5PNFJoZN1Cb0rAqJVxwYhfQSTSTdiSqt/sHpYEGN+2B2mbBXkeJNgRrSFqSxezLlBSZy7o0OeV8IKuFR0sE8skIHv+x5JHw7i+xJuqDuZLhL4qfiLp/wV7AkzUghp6jOCJcTGWuZ46Otl5y/ktfd5FA/uvmB91mKBCRCViw7RLCNcjcujFY2OD0m7OudrDmEqrCl4mH8T4TCYMiuFR96ROHdYUO9YKmIfImeUgFq97ObK83UEICh9E7j7BmG8g6YXWQITk+n+o6juV/V6lu0F7VI9+8qV9pWvPvVbuozFawj8OBEfBwIDAQAB";
    private static final byte[] SALT = new byte[] {
            -46, 65, 30, -128, -103, -57, 74, -64, 51, 88, -95,
            -45, 77, -117, -36, -113, -11, 32, -64, 89
    };
    private Handler mHandler;
    private LicenseChecker mChecker;
    private LicenseCheckerCallback mLicenseCheckerCallback;
    boolean licensed;
    boolean checkingLicense;
    boolean didCheck;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        mLicenseCheckerCallback = new MyLicenseCheckerCallback();
        mChecker = new LicenseChecker(
                this, new ServerManagedPolicy(this,
                new AESObfuscator(SALT, getPackageName(),
                    Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))),
                BASE64_PUBLIC_KEY  // Your public licensing key.
        );
    }

    private void doCheck() {
        didCheck = false;
        checkingLicense = true;
        setProgressBarIndeterminateVisibility(true);
        mChecker.checkAccess(mLicenseCheckerCallback);
    }

    public void sendtracker(View view) {
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(5);

        tracker = analytics.newTracker("UA-103574984-1");
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        tracker.setScreenName("Android ATC - Main Screen");
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("Contact Us").build());
        tracker.setScreenName("back screen");
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("click")
                .setLabel("Submit")
//                .set(Fields.SCREEN_NAME, "Android ATC - Main Screen")
                .build());

    }

    public void checklicense(View view) {
        doCheck();
    }

    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {

        @Override
        public void allow(int reason) {
            if (isFinishing()) {
                return;
            }
            licensed = true;
            checkingLicense = false;
            didCheck = true;
        }

        @Override
        public void dontAllow(int reason) {
            if (isFinishing()) {
                return;
            }
            licensed = false;
            checkingLicense = false;
            didCheck = true;
            showDialog(0);
        }

        @Override
        public void applicationError(int errorCode) {
            if (isFinishing()) {
                return;
            }
            licensed = true;
            checkingLicense = false;
            didCheck = false;
            showDialog(0);
        }
    }

    protected Dialog onCreateDialog(int id) {
        return new AlertDialog.Builder(this).setTitle("LICENSING ERROR")
            .setMessage("App not licensed. Get the latest version")
            .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    finish();
                }
            }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNeutralButton("Check again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doCheck();
                    }
                }).setCancelable(false).setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        finish();
                        return true;
                    }
                }).create();
    }


}
