package thundertrainingcenter.software.nhut.thundertrainingcenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private UserLoginTask mAuthTask = null;
    private EditText mUserid;
    private EditText mPasswordView;
    private ProgressBar mProgressView;
    private Spinner sp_server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserid = (EditText) findViewById(R.id.userid);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        sp_server = (Spinner) findViewById(R.id.sp_server);
        sp_server.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
            new String[] {"thundertrainingcenter.tk", "192.168.0.105:8989", "localhost:8080"}));
        sp_server.setSelection(0);
        sp_server.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mainUI.SERVER = ((TextView)view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                mEmailSignInButton.setEnabled(false);
                mEmailSignInButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEmailSignInButton.setEnabled(true);
                    }
                }, 3000);
            }
        });

        mProgressView = (ProgressBar) findViewById(R.id.login_progress);
    }

    private void attemptLogin() {
        Log.i("MyTag", "username " + mUserid.getText().toString() + " pwd " + mPasswordView.getText().toString());
         mAuthTask = new UserLoginTask(mUserid.getText().toString(),
            mPasswordView.getText().toString());
         mAuthTask.execute("http://" + mainUI.SERVER + "/TrainingCenter/servicelogin.controller");
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(this,
//            Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                    ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//            ContactsContract.Contacts.Data.MIMETYPE +
//                    " = ?", new String[]{ContactsContract.CommonDataKinds.Email
//            .CONTENT_ITEM_TYPE},
//            ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//        List<String> emails = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS));
//            cursor.moveToNext();
//        }
//
//        addEmailsToAutoComplete(emails);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }

    public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
        private final String mUserid;
        private final String mPassword;

        UserLoginTask(String userid, String password) {
            mUserid = userid;
            mPassword = password;
        }
        /*
            result : {"result":"succeed/fail"}
         */
        @Override
        protected Boolean doInBackground(String... params) {
            StringBuilder sb = new StringBuilder("");
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(params[0]).openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                osw.write("userid=" + mUserid + "&pwd=" + mPassword);
                osw.flush();
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
                String temp = "";
                while ((temp = br.readLine()) != null) sb.append(temp);
                br.close();
                System.out.println("json result " + sb.toString());
                String loginResult = new JSONObject(sb.toString()).getString("result");
                return loginResult.equals("succeed");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                startActivity(new Intent(LoginActivity.this, mainUI.class));
                mainUI.USERID = mUserid;
                mainUI.PASS = mPassword;
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }
    }

    public void exit(View v) {
        System.exit(0);
    }
}

