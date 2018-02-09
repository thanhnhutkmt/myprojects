package software.nhut.personalutilitiesforlife;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.internal.zzaba;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import software.nhut.personalutilitiesforlife.adapter.AdapterShelfImage;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.ViTri;
import util.FirebaseAction;
import util.InputData;
import util.MyAssetsAndPreferences;
import util.MyCheckSum;
import util.MyDateTime;
import util.MyDialog;
import util.MyFileIO;
import util.MyFireBase;

public class MainActivity extends AppCompatActivity {
    private MyFireBase firebase = null;
    private float lock_state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //        uploadFile();
        if (lock_state == 0) askForLogin();
        if (firebase == null || !firebase.isLoggedIn()) loginFirebase();
        initFacebook();
        Log.i("MyTag", "onResume");
    }

    private void uploadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(AppConstant.BUCKETNAME);
        Uri file = Uri.fromFile(new File("/storage/emulated/0/zalo/abc.jpg"));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file); // start the upload task
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(MainActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "Upload OK!", Toast.LENGTH_SHORT).show();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.i("MyTag", "Download link " + downloadUrl);
            }
        });
    }

    private void loginFirebase() {
        firebase = new MyFireBase(this);
        firebase.connect(loginAction);
        firebase.logFireBase(MyFireBase.LOGIN);
    }

    private void initFacebook() {
        FacebookSdk.setApplicationId(AppConstant.FACEBOOK_APP_ID);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mainactivity, menu);
        mnufirebase = menu.findItem(R.id.mnufirebase);
        mnufirebase_logout = menu.findItem(R.id.mnufirebase_logout);
        mnufirebase_changePassword = menu.findItem(R.id.mnufirebase_changePassword);
//        mnufacebook = menu.findItem(R.id.mnufacebook);
//        mnufacebook_logout = menu.findItem(R.id.mnufacebook_logout);
//        displayFacebookMenu();
        return super.onCreateOptionsMenu(menu);
    }

    MenuItem mnufirebase;
    MenuItem mnufirebase_logout;
    MenuItem mnufirebase_changePassword;
    MenuItem mnufacebook;
    MenuItem mnufacebook_logout;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.mnufirebase) {
//            displayFirebaseMenu();
        } if (i == R.id.mnufirebase_login) {
            firebase.login(loginAction);
        } else if (i == R.id.mnufirebase_logout) {
            firebase.logOut(loginAction);
        } else if (i == R.id.mnufirebase_register) {
            firebase.registerUserAccount();
        } else if (i == R.id.mnufirebase_changeaccount) {
            firebase.changeAccount(loginAction);
        } else if (i == R.id.mnufirebase_changePassword) {
            firebase.changePassword();
        } else if (i == R.id.mnufacebook_logout) {
            logOutFacebook();
        }
//        else if (i == R.id.mnufacebook) {
//            displayFacebookMenu();
//        }
//        else if (i == R.id.mnufacebook_logout) {
//            logOutFacebook();
//        } else if (i == R.id.mnutwitter_logout) {
//            logOutTwitter();
//        }
        return super.onOptionsItemSelected(item);
    }

//    private static final String TWITTER_KEY = "tamnhatdn@gmail.com";
//    private static final String TWITTER_SECRET = "Thanhnhut";
//    private void logInTwitter() {
//        Fabric.with(this, new Twitter(new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)));
//    }

    private void logOutFacebook() {
        LoginManager.getInstance().logOut();
    }

    private void displayFacebookMenu() {
//        if (mnufacebook != null) {
//            callbackManager = CallbackManager.Factory.create();
//
//            LoginButton loginButton = new LoginButton(this);
//            loginButton.setReadPermissions("public_profile");
//            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//                @Override
//                public void onSuccess(LoginResult loginResult) {
//                    Log.i("MyTag", "onSuccess");
//                    LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
//                    Profile p = Profile.getCurrentProfile();
//                    boolean loggedin = (p != null);
//                    Log.i("MyTag", "state " + ((loggedin) ? "logged in" + p.getName() : "logged out"));
//                    mnufacebook.setTitle(loggedin ?
//                            getString(R.string.OptionMenu_facebook) + p.getName() :
//                            getString(R.string.OptionMenu_facebook));
//                    mnufacebook_logout.setEnabled(loggedin);
//                }
//
//                @Override
//                public void onCancel() {
//                    Log.i("MyTag", "onCancel");
//                }
//
//                @Override
//                public void onError(FacebookException error) {
//                    Log.i("MyTag", "onError");
//                }
//            });
//            loginButton.callOnClick();
////            android.support.v7.app.AlertDialog ad = new android.support.v7.app.AlertDialog.Builder(this)
////                    .setView(loginButton).create();
////            ad.show();
//
//        }
    }

    FirebaseAction loginAction = new FirebaseAction() {
        @Override public void postLoginOkAction() {
            if (mnufirebase != null) {
                editMenu();
                displayFirebaseMenu();
            }
        }
        @Override public void postLoginFailedAction() {}
        @Override public void postLogOut() {
            if (mnufirebase != null) {
                editMenu();
                displayFirebaseMenu();
            }
        }
    };

    private void displayFirebaseMenu() {
        boolean logged_in = firebase.isLoggedIn();
        mnufirebase_changePassword.setEnabled(logged_in);
        mnufirebase_logout.setEnabled(logged_in);
        Log.i("MyTag", "status log in : " + ((logged_in) ? "yes" : "no"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            editMenu();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void editMenu() {
        //firebase menu
        if (firebase.isLoggedIn())
            mnufirebase.setTitle(getString(R.string.OptionMenu_firebase) + " - " + firebase.getUser().getEmail());
        else mnufirebase.setTitle(getString(R.string.OptionMenu_firebase));
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backPressed) startActivity(new Intent(this, EndActivity.class));
//        finish();
    }

    private boolean backPressed = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPressed = true;
    }

    private void askForLogin() {
        final EditText txtPass[] = new EditText[] {new EditText(this), new EditText(this)};
        txtPass[0].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPass[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        if (MyAssetsAndPreferences.getStringFromPreferences(MainActivity.this, AppConstant.PASSWORD).equals("")) {
            MyDialog.showNewPasswordDiaLog(this, txtPass, new InputData() {
                    @Override public Object inputData(Object o) {return null;}
                    @Override public void inputData(String s) {}
                    @Override public void inputData(DialogInterface dialog) {
                        if (txtPass[0].getText().toString().equals(txtPass[1].getText().toString())) {
                            MyAssetsAndPreferences.saveToPreferences(MainActivity.this, AppConstant.PASSWORD, txtPass[0].getText().toString());
                            dialog.dismiss();
                        } else Toast.makeText(MainActivity.this, R.string.Toast_xacthucpassword_notduplicated, Toast.LENGTH_SHORT).show();
                    }
                    @Override public void inputData(String... s) {}
                    @Override public void inputData(List<String> s) {}
                    @Override public void inputData(String s, int color) {}
                });
        } else {
            MyDialog.showNewPasswordDiaLog(MainActivity.this, new View[]{txtPass[0]}, new InputData() {
                @Override public Object inputData(Object o) {return null;}
                    @Override public void inputData(String s) {}
                    @Override public void inputData(DialogInterface dialog) {
                        if (txtPass[0].getText().toString().equals
                                (MyAssetsAndPreferences.getStringFromPreferences(MainActivity.this, AppConstant.PASSWORD))) {
                            lock_state = 1;
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, R.string.Toast_xacthucpassword_wrongpass, Toast.LENGTH_SHORT).show();
                            txtPass[0].setText("");
                        }
                    }
                    @Override public void inputData(String... s) {}
                    @Override public void inputData(List<String> s) {}
                    @Override public void inputData(String s, int color) {}
                });
        }
    }

    public void moCuocHen(View view) {
        openActivity(QuanLyCuocHenActivity.class);
    }

    public void moKaraoke(View view) {
        openActivity(KaraokeActivity.class);
    }

    public void moTinNhan(View view) {
        openActivity(XacThucActivity.class);
    }

    public void moTroChoi(View view) {
        startActivity(new Intent(MainActivity.this, Slashgame.class));
    }

    public void moHinhPhim(View view) {
        startActivity(new Intent(MainActivity.this, AlbumActivity.class));
    }

    private final int LOCKSCREEN = 0;
    public void khoaManHinh(View view) {
        openActivity(XacThucActivity.class, AppConstant.LOCKCODESTRING, LOCKSCREEN);
        startService(new Intent(this, MyLockScreenService.class));
    }

    public void moDongBo(View view) {
        openActivity(DongBoActivity.class);
    }

    public void moBanDo(View view) {
        openActivity(BanDoActivity.class);
    }

    public void moDoiTien(View view) {
        openActivity(TiGiaActivity.class);
    }

    private void openActivity(Class<?> nextActivity) {
        Intent i = new Intent(this, nextActivity);
        startActivity(i);
    }

    private void openActivity(Class<?> nextActivity, String CODESTRING, int CODE) {
        Intent i = new Intent(this, nextActivity);
        i.putExtra(CODESTRING, CODE);
        startActivity(i);
        if (CODE == LOCKSCREEN) finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("firebase", firebase);
        outState.putFloat("SIGN IN STATE", lock_state);
        Log.i("MyTag", "onSaveState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        firebase = (MyFireBase) savedInstanceState.getSerializable("firebase") ;
        lock_state = savedInstanceState.getFloat("SIGN IN STATE");
        Log.i("MyTag", "onRestoreState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("MyTag", "onConfigurationChanged");
    }
}


/*
    private void loginFirebase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = InstanceID.getInstance(MainActivity.this).getToken(getString(R.string.gcm_defaultSenderId),"");
                    Log.i("MyTag", "info : instance ID " + InstanceID.getInstance(MainActivity.this).getId() + " , token " + token);
                    FirebaseMessaging.getInstance().subscribeToTopic(AppConstant.Topic_ID);
                    startService(new Intent(MainActivity.this, MyFirebaseMessagingService.class));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

// Can't not send upstream message because HTTP protocol does not support
//        FirebaseMessaging fm = FirebaseMessaging.getInstance();
//        fm.send(new RemoteMessage.Builder(AppConstant.SENDER_ID + "@gcm.googleapis.com")
//                .setMessageId(Integer.toString((int) (Math.random() * 1000)))
//                .addData("my_message", "Nhut : Hello World")
//                .addData("my_action","SAY_HELLO")
//                .build());


        // Write a message to the database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        String timestamp = MyDateTime.getDateString(System.currentTimeMillis(), AppConstant.FULLTIMEFORMATWITHSECOND_WITHOUTNEWLINE);
        myRef.child(AppConstant.UID_FIREBASE).child(timestamp).setValue("Login is run!", new DatabaseReference.CompletionListener() {
            @Override public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) Toast.makeText(MainActivity.this, databaseError.toString() + "\n", Toast.LENGTH_SHORT).show();
            }
        });
        myRef.child(AppConstant.UID_FIREBASE).addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
//                    Toast.makeText(MainActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    Log.i("MyTag", dataSnapshot.getValue().toString());
                }
            }
            @Override public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) Toast.makeText(MainActivity.this, "Signed-in.", Toast.LENGTH_SHORT).show();
//                else Toast.makeText(MainActivity.this, "Signed out.", Toast.LENGTH_SHORT).show();
            }
        };
        mAuth.addAuthStateListener(mAuthListener);

        String username_firebase = MyAssetsAndPreferences.getStringFromPreferences(this, AppConstant.USERNAME_FIREBASE);
        String password_firebase = MyAssetsAndPreferences.getStringFromPreferences(this, AppConstant.PASS_FIREBASE);
        if (username_firebase.isEmpty() || password_firebase.isEmpty()) {
            final EditText txtPass[] = new EditText[] {new EditText(this), new EditText(this)};
            txtPass[0].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txtPass[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            MyDialog.showNewPasswordDiaLog(this, txtPass, new InputData() {
                @Override public void inputData(String s) {}
                @Override public void inputData(DialogInterface dialog) {
                    if (txtPass[0].getText().toString().equals(txtPass[1].getText().toString())) {
                        MyAssetsAndPreferences.saveToPreferences(MainActivity.this, AppConstant.PASSWORD, txtPass[0].getText().toString());
                        dialog.dismiss();
                    } else Toast.makeText(MainActivity.this, R.string.Toast_xacthucpassword_notduplicated, Toast.LENGTH_SHORT).show();
                }
                @Override public void inputData(String... s) {}
                @Override public void inputData(List<String> s) {}
                @Override public void inputData(String s, int color) {}
            });
        }
        mAuth.signInWithEmailAndPassword(username_firebase, password_firebase).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) Toast.makeText(MainActivity.this, "Error sign-in", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, "Sign-in successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
*/
