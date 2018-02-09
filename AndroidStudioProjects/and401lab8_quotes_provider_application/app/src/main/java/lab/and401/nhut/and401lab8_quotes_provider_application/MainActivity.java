package lab.and401.nhut.and401lab8_quotes_provider_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyPreferences pref = new MyPreferences(MainActivity.this);
        if(!pref.isFirstTime()) {
            Intent i = new Intent(getApplicationContext(), FortuneActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            finish();
        }

        final Intent i = new Intent(this, BackgroundService.class);
        Button btn_startservice = (Button) findViewById(R.id.btn_startservice);
        Button btn_stopservice = (Button) findViewById(R.id.btn_stopservice);
        btn_startservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(i);
            }
        });
        btn_stopservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(i);
            }
        });
    }

    public void SaveUserName(View v) {
        EditText usrName = (EditText)findViewById(R.id.editText1);
        MyPreferences pref = new MyPreferences(MainActivity.this);
        pref.setUserName(usrName.getText().toString().trim());
        Intent i = new Intent(getApplicationContext(), FortuneActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }
}
