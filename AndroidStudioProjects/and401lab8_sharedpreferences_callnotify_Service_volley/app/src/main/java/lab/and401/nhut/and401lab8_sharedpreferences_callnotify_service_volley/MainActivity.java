package lab.and401.nhut.and401lab8_sharedpreferences_callnotify_service_volley;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    public static final String USERNAME = "name";
    public static final String ISFISTTIME = "is first time";
    private EditText usernameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameInput = (EditText) findViewById(R.id.username);
        sp = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

        usernameInput.setText(sp.getString(USERNAME, ""));
        if (!sp.getString(USERNAME, "").equals("")) {
            startActivity(new Intent(this, Main2Activity.class));
            finish();
        } else {
            sp.edit().putBoolean(ISFISTTIME, true);
        }
    }

    public void startservice(View v) {
        startService(new Intent(this, BackGroundService.class));
    }

    public void startgame(View v) {
        String un = usernameInput.getText().toString().trim();
        if (un != null && un.length() > 0) {
            sp.edit().putString(USERNAME, un).commit();
            startActivity(new Intent(this, Main2Activity.class));
            finish();
        }
    }

    public void stopservice(View v) {
        stopService(new Intent(this, BackGroundService.class));
    }
}
