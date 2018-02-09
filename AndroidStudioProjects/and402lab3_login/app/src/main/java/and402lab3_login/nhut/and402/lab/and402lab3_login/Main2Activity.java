package and402lab3_login.nhut.and402.lab.and402lab3_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView titlelogin = (TextView) findViewById(R.id.titlelogin);
        TextView usernametv = (TextView) findViewById(R.id.usernametv);
        TextView passwordtv = (TextView) findViewById(R.id.passwordtv);

        Intent i = getIntent();
        usernametv.setText("Username : " + i.getStringExtra("username"));
        passwordtv.setText("Password : " + i.getStringExtra("password"));
        titlelogin.setText("Main2Activity");
    }
}
