package lab.and401.nhut.and401lab2_activitylifecycle;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String TAG = "AndroidATC lesson 2";
    private TextView log;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log = (TextView)findViewById(R.id.log);
//        log.setMovementMethod(new ScrollingMovementMethod());
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onGoToNextActivity() invoked");
                log.append(sdf.format(new Date()) + " " + TAG + " : onGoToNextActivity() invoked\n");
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
        Log.i(TAG, "onCreate() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onCreate() invoked\n");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onStart() invoked\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onResume() invoked\n");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onPause() invoked\n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onStop() invoked\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onDestroy() invoked\n");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onRestart() invoked\n");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onSaveInstanceState() invoked\n");
        outState.putString("log", log.getText().toString()); // just save some not all for orientation
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { // called when change orientation
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onRestoreInstanceState() invoked\n");
        log.append(savedInstanceState.getString("log")); // restore some log not all for orientation
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i(TAG, "onPostCreate() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onPostCreate() invoked\n");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onPostResume() invoked\n");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "onBackPressed() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onBackPressed() invoked\n");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown() invoked keycode : " + keyCode + ", keyevent : " + event.toString());
        log.append(sdf.format(new Date()) + " " + TAG + " : onKeyDown() invoked\n");
        if (keyCode == KeyEvent.KEYCODE_HOME) { //        on API 19 : not usable,    API 24 : not usable
            Log.i(TAG, "onHomePressed() invoked");
            log.append(sdf.format(new Date()) + " " + TAG + " : onHomePressed() invoked\n");
        } else if (keyCode == KeyEvent.KEYCODE_MENU) { // on API 19 : usable,        API 24 : not usable
            Log.i(TAG, "onMenuPressed() invoked");
            log.append(sdf.format(new Date()) + " " + TAG + " : onMenuPressed() invoked\n");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.i(TAG, "onUserLeaveHint() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onUserLeaveHint() invoked\n");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.i(TAG, "onUserInteraction() invoked");
        log.append(sdf.format(new Date()) + " " + TAG + " : onUserInteraction() invoked\n");
    }
}
