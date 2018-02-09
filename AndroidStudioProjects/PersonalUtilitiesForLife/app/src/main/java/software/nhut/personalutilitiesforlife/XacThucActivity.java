package software.nhut.personalutilitiesforlife;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import util.FingerPrintHandler;
import util.MyAssetsAndPreferences;
import util.MyDialog;
import util.MyPhone;

import com.pantech.fingerscan.FingerScanHelper;
import com.pantech.fingerscan.FingerScanUtils;
import com.pantech.fingerscan.OnTimeoutListener;
import com.pantech.fingerscan.OnVerifyListener;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

public class XacThucActivity extends AppCompatActivity {
    private TextView txtCambienvantay_xacthuc, txtMatKhau_xacthuc;
    private ImageView imgFingerPrint_xacthuc, imgPassword_xacthuc;
    private int CODE = -1;
    private OverlayDialog mOverlayDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MyTag", "onCreate");
        CODE = getIntent().getIntExtra(AppConstant.LOCKCODESTRING, -1);
        if (CODE != AppConstant.LOCKCODE) {
            if (((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                //No calling functionality
                Toast.makeText(XacThucActivity.this, R.string.Toast_nophonenetwork_xacthuc_kiemtrachucnangphone
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            ((KeyguardManager)getSystemService(KEYGUARD_SERVICE)).newKeyguardLock("IN").disableKeyguard();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
            // Hide the status bar.
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            mOverlayDialog = new OverlayDialog(this);
            mOverlayDialog.show();
        }
        setContentView(R.layout.activity_xac_thuc);
        MyPhone.setPortraitOrient(this);
        txtCambienvantay_xacthuc = (TextView) findViewById(R.id.txtCambienvantay_xacthuc);
        txtMatKhau_xacthuc = (TextView) findViewById(R.id.txtMatKhau_xacthuc);
        imgFingerPrint_xacthuc = (ImageView) findViewById(R.id.imgFingerPrint_xacthuc);
        imgPassword_xacthuc = (ImageView) findViewById(R.id.imgPassword_xacthuc);
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if(!hasFocus) {
//            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//            sendBroadcast(closeDialog);
//        }
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.i("MyTag", "onKeyDown " + event.toString() + " " + keyCode);
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        Log.i("MyTag", "dispatchKeyEvent " + event.toString());
//        return super.dispatchKeyEvent(event);
//    }

    //    @Override
//    protected void onUserLeaveHint() {
//        Log.d("MyTag","Home button pressed");
//        super.onUserLeaveHint();
//    }


    @Override
    protected void onStart() {
        Log.i("MyTag", "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MyTag", "onResume");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) authenticateUser();
        else authenticateUserCompat();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void authenticateUser() {
        FingerprintManager fingerprintManager =
                (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (fingerprintManager.isHardwareDetected()
                && fingerprintManager.hasEnrolledFingerprints()) {
            txtMatKhau_xacthuc.setVisibility(View.GONE);
            imgPassword_xacthuc.setVisibility(View.GONE);
            CheckFingerPrint();
        } else checkPassword();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void CheckFingerPrint() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(AppConstant.PASSWORD,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(AppConstant.PASSWORD, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            new FingerPrintHandler(this).startAuth((FingerprintManager) getSystemService(FINGERPRINT_SERVICE),
                    new FingerprintManager.CryptoObject(cipher));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPassword() {
        txtCambienvantay_xacthuc.setVisibility(View.GONE);
        imgFingerPrint_xacthuc.setVisibility(View.GONE);
        final EditText txtPass = new EditText(this);
        txtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        MyDialog.showPasswordDiaLog(this, new View[]{txtPass}, new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (txtPass.getText().toString().equals
                        (MyAssetsAndPreferences.getStringFromPreferences(XacThucActivity.this, AppConstant.PASSWORD)))
                    passCheck();
                else {
                    Toast.makeText(XacThucActivity.this, R.string.Toast_xacthucpassword_wrongpass, Toast.LENGTH_SHORT).show();
                    txtPass.setText("");
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    private void passCheck() {
        if (mOverlayDialog != null) mOverlayDialog.dismiss();
        txtCambienvantay_xacthuc.setTextColor(Color.GREEN);
        if (CODE != AppConstant.LOCKCODE) {
            int notificationID = getIntent().getIntExtra("NotificationID", -1);
            Intent i = new Intent(XacThucActivity.this, QuanLyTinNhanActivity.class);
            i.putExtra("NotificationID", notificationID);
            i.putExtra(AppConstant.PASSCHECK, AppConstant.PASSCHECKVALUE);
            startActivity(i);
        } else
            stopService(new Intent(this, MyLockScreenService.class));
        finish();
    }

    private void authenticateUserCompat() {
        Log.i("Mytag", "authenticateUserCompat : Vendor : " + android.os.Build.MANUFACTURER);
        if (android.os.Build.MANUFACTURER.equals("Samsung")) {
            checkFingerPrintSamsung();
        } else if (android.os.Build.MANUFACTURER.equals("PANTECH")) {
            checkFingerPrintSkyPantech();
        } else if (android.os.Build.MANUFACTURER.equals("Lenovo")) {
            checkFingerPrintLenovo();
        } else checkPassword();
    }

    private void checkFingerPrintLenovo() {
        // searching Lenovo FingerPrint API lib ...
        checkPassword();
    }

    private void checkFingerPrintSkyPantech() {
        Log.i("Mytag", "checkFingerPrintSkyPantech : ");
        if (FingerScanUtils.isDeviceSupportFingerScan(this) && FingerScanUtils.isEnrolled(this)) {
            Log.i("Mytag", "Fingerprint sensor ok !!");
            txtMatKhau_xacthuc.setVisibility(View.GONE);
            imgPassword_xacthuc.setVisibility(View.GONE);
            checkFPSP();
        } else {
            Log.i("Mytag", "No Fingerprint sensor !");
            checkPassword();
        }
    }

    private FingerScanHelper mFingerScanHelper;
    private void checkFPSP() {
        Log.i("Mytag", "checkFPSP : ");
        try {
            mFingerScanHelper = new FingerScanHelper(this);
            OnVerifyListener v = new OnVerifyListener() {
                @Override  public void onVerified(boolean b) {
                    if (b) {
                        passCheck();
                        mFingerScanHelper.stopRequestVerify();
                    } else {
                        txtCambienvantay_xacthuc.setTextColor(Color.RED);
                        txtCambienvantay_xacthuc.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                txtCambienvantay_xacthuc.setTextColor(Color.BLACK);
                            }
                        }, 3000);
                    }
                }
            };
            OnTimeoutListener t = new OnTimeoutListener() {
                @Override public void onTimeouted() {
                    Toast.makeText(XacThucActivity.this,
                        R.string.fingerprint_sensor_requestFingerPrintScan_xacthucactivity, Toast.LENGTH_SHORT).show();
                }
            };
            mFingerScanHelper.requestVerify(v);
            mFingerScanHelper.setOnTimeoutListener(t);
        } catch (Exception e) {
            mFingerScanHelper.stopRequestVerify();
            e.printStackTrace();
        }
    }

    private void checkFingerPrintSamsung() {
        Spass spass = new Spass();
        boolean isFingerPrintSupported = true;
        try {
            spass.initialize(XacThucActivity.this);
        } catch (Exception e) {
            isFingerPrintSupported = false;
            e.printStackTrace();
        }
        if (isFingerPrintSupported && spass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT)
                && spass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT_FINGER_INDEX)
                && spass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT_AVAILABLE_PASSWORD)) {
            txtMatKhau_xacthuc.setVisibility(View.GONE);
            imgPassword_xacthuc.setVisibility(View.GONE);
            checkFPSS();
        } else checkPassword();
    }

    private void checkFPSS() {
        final SpassFingerprint spassFingerprint = new SpassFingerprint(XacThucActivity.this);
        SpassFingerprint.IdentifyListener identifyListener = new SpassFingerprint.IdentifyListener() {
            @Override public void onFinished(int eventStatus) {
                if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS) {
                    passCheck();
                } else if (eventStatus == SpassFingerprint.STATUS_TIMEOUT_FAILED) {
                    Toast.makeText(XacThucActivity.this,
                            R.string.fingerprint_sensor_requestFingerPrintScan_xacthucactivity, Toast.LENGTH_SHORT).show();
                } else if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_FAILED
                        && eventStatus == SpassFingerprint.STATUS_QUALITY_FAILED) {
                    txtCambienvantay_xacthuc.setTextColor(Color.RED);
                    txtCambienvantay_xacthuc.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtCambienvantay_xacthuc.setTextColor(Color.BLACK);
                        }
                    }, 3000);
                }
            }
            @Override public void onReady() {}
            @Override public void onStarted() {}
            @Override public void onCompleted() {}
        };
        spassFingerprint.startIdentify(identifyListener);
    }

    @Override
    public void onBackPressed() {
        if (CODE != AppConstant.LOCKCODE) {
            finish();
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        if (mFingerScanHelper != null) mFingerScanHelper.stopRequestVerify();
        Log.i("MyTag", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("MyTag", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("MyTag", "onDestroy");
        super.onDestroy();
    }

    public TextView getTxtCambienvantay_xacthuc() {
        return txtCambienvantay_xacthuc;
    }

    private static class OverlayDialog extends AlertDialog {

        public OverlayDialog(Activity activity) {
            super(activity, R.style.OverlayDialog);
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            params.dimAmount = 0.0F;
            params.width = 0;
            params.height = 0;
            params.gravity = Gravity.BOTTOM;
            getWindow().setAttributes(params);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    0xffffff);
            setOwnerActivity(activity);
            setCancelable(false);
        }

        // consume touch events
        public final boolean dispatchTouchEvent(MotionEvent motionevent) {
            return true;
        }
    }
}
