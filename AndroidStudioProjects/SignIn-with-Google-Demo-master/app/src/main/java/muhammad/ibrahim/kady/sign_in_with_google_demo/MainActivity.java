package muhammad.ibrahim.kady.sign_in_with_google_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.signIn)
    SignInButton signIn;
    @BindView(R.id.signInCustom)
    Button signInCustom;
    @BindView(R.id.signOut)
    Button signOut;
    @BindView(R.id.revokeAccess)
    Button revokeAccess;
    @BindView(R.id.state)
    TextView state;
//    @BindView(R.id.profilePic)
//    ImageView profilePic;
    @BindView(R.id.list)
    ListView list;

    private GoogleApiClient mGoogleApiClient;
    private static final int SIGN_IN_REQUEST_CODE = 0x0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createGoogleApiClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
        silentSignIn();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "onConnectionFailed");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "onActivityResult");
        switch (requestCode) {
            case SIGN_IN_REQUEST_CODE:
                GoogleSignInResult googleSignInResult =
                        Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(googleSignInResult);
                break;
        }
    }

    @OnClick({R.id.signIn, R.id.signInCustom})
    void signIn() {
        Log.d(LOG_TAG, "signIn");
        Intent i = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(i, SIGN_IN_REQUEST_CODE);
    }

    @OnClick(R.id.signOut)
    void signOut() {
        Log.d(LOG_TAG, "signOut");
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).
                setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                });
    }

    @OnClick(R.id.revokeAccess)
    void revokeAccess() {
        Log.d(LOG_TAG, "revokeAccess");
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).
                setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                });
    }

    private void silentSignIn() {
        Log.d(LOG_TAG, "silentSignIn");
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.
                silentSignIn(mGoogleApiClient);
        Log.d(LOG_TAG, "silentSignIn : " + opr.isDone() + "");
        if (opr.isDone()) {
            GoogleSignInResult googleSignInResult = opr.get();
            handleSignInResult(googleSignInResult);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });

        }
    }

    private void handleSignInResult(GoogleSignInResult googleSignInResult) {
        Log.d(LOG_TAG, "handleSignInResult");
        if (googleSignInResult.isSuccess()) {
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            if (account != null) {
                state.setText(getString(R.string.state, account.getEmail(), account.getDisplayName()));
                Plus.PeopleApi.loadVisible(mGoogleApiClient, null).
                        setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
                            @Override
                            public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
                                final int peopleCount = loadPeopleResult.getPersonBuffer().getCount();
                                String[] peoples = new String[peopleCount];
                                for (int i = 0; i < peopleCount; i++) {
                                    peoples[i] = loadPeopleResult.getPersonBuffer().get(i).getDisplayName();
                                }
                                list.setAdapter(new ArrayAdapter<>(
                                        MainActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        Arrays.asList(peoples)));
                            }
                        });
//                Picasso.with(this).load(account.getPhotoUrl()).into(profilePic);
                updateUI(true);
            }
        } else {
            updateUI(false);
        }
    }

    /*Creating GoogleApiClient to connect to GooglePlayServices*/
    private void createGoogleApiClient() {
        Log.d(LOG_TAG, "createGoogleApiClient");
        /*You can here also request also scopes*/
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestScopes(new Scope(Scopes.PLUS_LOGIN)).
                requestEmail().
                build();

        /*enableAutoManage : Enables automatic lifecycle management in a support library FragmentActivity that
        connects the client in onStart() and disconnects it in onStop().
        It handles user recoverable errors appropriately and calls if the ConnectionResult has no
        resolution.This eliminates most of the boiler plate associated with using GoogleApiClient.*/
        mGoogleApiClient = new GoogleApiClient.Builder(this).
                enableAutoManage(this, this).
                addApi(Auth.GOOGLE_SIGN_IN_API, gso).
                addApi(Plus.API).
                build();
    }

    private void updateUI(boolean signedIn) {
        Log.d(LOG_TAG, "updateUI : " + signedIn + "");
        if (signedIn) {
            signIn.setEnabled(false);
            signInCustom.setEnabled(false);
            signOut.setEnabled(true);
            revokeAccess.setEnabled(true);
//            profilePic.setVisibility(View.VISIBLE);
        } else {
            signIn.setEnabled(true);
            signInCustom.setEnabled(true);
            signOut.setEnabled(false);
            revokeAccess.setEnabled(false);
            state.setText(null);
//            profilePic.setVisibility(View.INVISIBLE);
        }
    }
}
