package util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import software.nhut.personalutilitiesforlife.MyFirebaseMessagingService;
import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.constant.AppConstant;

/**
 * Created by Nhut on 2/25/2017.
 */

public class MyFireBase implements Serializable, Parcelable {
    private Activity act;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private String username_firebase;
    private String password_firebase;
    private FirebaseUser user;

    public MyFireBase(Activity act, String username_firebase, String password_firebase) {
        this(act);
        this.username_firebase = username_firebase;
        this.password_firebase = password_firebase;
    }

    public MyFireBase(Activity act) {
        this.act = act;
        mAuth = FirebaseAuth.getInstance();
        username_firebase = MyAssetsAndPreferences.getStringFromPreferences(act, AppConstant.USERNAME_FIREBASE);
        password_firebase = MyAssetsAndPreferences.getStringFromPreferences(act, AppConstant.PASS_FIREBASE);
    }

    protected MyFireBase(Parcel in) {
//        username_firebase = in.readString();
//        password_firebase = in.readString();
    }

    public static final Creator<MyFireBase> CREATOR = new Creator<MyFireBase>() {
        @Override
        public MyFireBase createFromParcel(Parcel in) {
            return new MyFireBase(in);
        }

        @Override
        public MyFireBase[] newArray(int size) {
            return new MyFireBase[size];
        }
    };

    public void connect(final FirebaseAction loginAction) {
        if (username_firebase.isEmpty() || password_firebase.isEmpty()) {
            MyDialog.showYesNoDiaLog(act, R.string.MyFireBase_connect_title, R.string.MyFireBase_connect,
                new DialogInterface.OnClickListener() { // yes action
                    @Override public void onClick(DialogInterface dialog, int which) {registerUserAccount();}
            }, new DialogInterface.OnClickListener() {  // no action
                    @Override public void onClick(DialogInterface dialog, int which) {login(loginAction);}
            });
        } else login(loginAction);
    }

    public void login(final FirebaseAction loginAction) {
        if (!(username_firebase.isEmpty() || password_firebase.isEmpty())) {
            mAuth.signInWithEmailAndPassword(username_firebase, password_firebase).addOnCompleteListener(act, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                        Toast.makeText(act, "Error sign-in", Toast.LENGTH_SHORT).show();
                        // else Toast.makeText(act, "Sign-in successfully", Toast.LENGTH_SHORT).show();
                }
            });
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        Toast.makeText(act, "Signed-in.", Toast.LENGTH_SHORT).show();
                        MyAssetsAndPreferences.saveToPreferences(act, AppConstant.USERNAME_FIREBASE, username_firebase);
                        MyAssetsAndPreferences.saveToPreferences(act, AppConstant.PASS_FIREBASE, password_firebase);
                        if (loginAction != null)loginAction.postLoginOkAction();
                    }
                    // else Toast.makeText(MainActivity.this, "Signed out.", Toast.LENGTH_SHORT).show();
                }
            };
            mAuth.addAuthStateListener(mAuthListener);
        } else {
            showDialogLoginFireBase(loginAction);
        }
    }

    public void registerUserAccount() {
        final EditText txtPass[] = new EditText[] {new EditText(act), new EditText(act), new EditText(act)};
        txtPass[0].setHint(R.string.MyFireBase_InputUsername);
        txtPass[1].setHint(R.string.MyFireBase_InputPassword);
        txtPass[2].setHint(R.string.MyFireBase_InputPassword2);
        txtPass[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPass[2].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        MyDialog.showNewPasswordDiaLog(act, R.string.MyFireBase_RegAccount, txtPass, new InputData() {
            @Override public Object inputData(Object o) {return null;}
            @Override public void inputData(String s) {}
            @Override public void inputData(DialogInterface dialog) {
                if (txtPass[1].getText().toString().equals(txtPass[2].getText().toString())) {
                    username_firebase = txtPass[0].getText().toString();
                    password_firebase = txtPass[1].getText().toString();
                    mAuth.createUserWithEmailAndPassword(username_firebase, password_firebase)
                        .addOnCompleteListener(act, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) Toast.makeText(act, "Register falied", Toast.LENGTH_SHORT).show(); // internet connection can cause failure
                                else {
                                    MyAssetsAndPreferences.saveToPreferences(act, AppConstant.USERNAME_FIREBASE, username_firebase);
                                    MyAssetsAndPreferences.saveToPreferences(act, AppConstant.PASS_FIREBASE, password_firebase);
                                    Toast.makeText(act, "Register successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    dialog.dismiss();
                } else Toast.makeText(act, R.string.Toast_xacthucpassword_notduplicated, Toast.LENGTH_SHORT).show();
            }
            @Override public void inputData(String... s) {}
            @Override public void inputData(List<String> s) {}
            @Override public void inputData(String s, int color) {}
        });
    }

    private void showDialogLoginFireBase(final FirebaseAction loginAction) {
        final EditText txtPass[] = new EditText[] {new EditText(act), new EditText(act)};
        txtPass[0].setHint(R.string.MyFireBase_InputUsername);
        txtPass[1].setHint(R.string.MyFireBase_InputPassword);
        txtPass[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        MyDialog.showNewPasswordDiaLog(act, R.string.MyFireBase_Login, txtPass, new InputData() {
            @Override public Object inputData(Object o) {return null;}
            @Override public void inputData(String s) {}
            @Override public void inputData(DialogInterface dialog) {
                username_firebase = txtPass[0].getText().toString();
                password_firebase = txtPass[1].getText().toString();
                login(loginAction);
                dialog.dismiss();
            }
            @Override public void inputData(String... s) {}
            @Override public void inputData(List<String> s) {}
            @Override public void inputData(String s, int color) {}
        });
    }

    public void logOut(FirebaseAction loginoutAction) {
        if (mAuth != null) mAuth.signOut();
        user = null;
        if (loginoutAction != null) loginoutAction.postLogOut();
        Toast.makeText(act, R.string.MyFireBase_logged_out, Toast.LENGTH_SHORT).show();
    }

    public void changePassword() {
        final EditText txtPass[] = new EditText[] {new EditText(act), new EditText(act)};
        txtPass[0].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPass[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPass[0].setHint(R.string.MyFireBase_InputPassword);
        txtPass[1].setHint(R.string.MyFireBase_InputPassword);
        MyDialog.showNewPasswordDiaLog(act, 0, txtPass, new InputData() {
            @Override public Object inputData(Object o) {return null;}
            @Override public void inputData(String s) {}
            @Override public void inputData(DialogInterface dialog) {
                if (txtPass[0].getText().toString().equals(txtPass[1].getText().toString())) {
                    password_firebase = txtPass[0].getText().toString().trim();
                    user = mAuth.getCurrentUser();
                    if (user != null)
                        user.updatePassword(password_firebase)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) Toast.makeText(act, "Change password falied", Toast.LENGTH_SHORT).show();
                                    else {
                                        MyAssetsAndPreferences.saveToPreferences(act, AppConstant.PASS_FIREBASE, password_firebase);
                                        Toast.makeText(act, "Change password successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else Toast.makeText(act, R.string.Toast_xacthucpassword_notduplicated, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            @Override public void inputData(String... s) {}
            @Override public void inputData(List<String> s) {}
            @Override public void inputData(String s, int color) {}
        });
    }

    public void changeAccount(FirebaseAction loginAction) {
        showDialogLoginFireBase(loginAction);
    }

    public void disconnect() {
        if (mAuthListener != null && mAuth != null) mAuth.removeAuthStateListener(mAuthListener);
        if (mAuth != null) mAuth.signOut();
    }

    public FirebaseUser getUser() {
        return user;
    }
    public boolean isLoggedIn() {
        return (user != null);
    }
    public static final String LOGIN = "Log-in run";
    public void logFireBase(String logData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token = InstanceID.getInstance(act).getToken(act.getString(R.string.gcm_defaultSenderId),"");
                    Log.i("MyTag", "info : instance ID " + InstanceID.getInstance(act).getId() + " , token " + token);
                    FirebaseMessaging.getInstance().subscribeToTopic(AppConstant.Topic_ID);
                    act.startService(new Intent(act, MyFirebaseMessagingService.class));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Write a message to the database
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        String timestamp = MyDateTime.getDateString(System.currentTimeMillis(), AppConstant.FULLTIMEFORMATWITHSECOND_WITHOUTNEWLINE);
        myRef.child(AppConstant.UID_FIREBASE).child(timestamp).setValue(logData, new DatabaseReference.CompletionListener() {
            @Override public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) Toast.makeText(act, databaseError.toString() + "\n", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(act, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username_firebase);
        dest.writeString(password_firebase);
    }
}
