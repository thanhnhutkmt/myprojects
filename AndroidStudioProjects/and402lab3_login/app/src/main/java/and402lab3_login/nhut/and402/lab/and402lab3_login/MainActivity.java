package and402lab3_login.nhut.and402.lab.and402lab3_login;

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
        final EditText uset = (EditText) findViewById(R.id.uset);
        final EditText pwet = (EditText) findViewById(R.id.pwet);
        Button btnlogin = (Button) findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("username", uset.getText().toString());
                intent.putExtra("password", pwet.getText().toString());
//                intent.setAction("loginaction");
                intent.setClass(MainActivity.this, Main2Activity.class);
//                        ("and402lab3_login.nhut.and402.lab.and402lab3_login",
//                        "and402lab3_login.nhut.and402.lab.and402lab3_login.Main2Activity");
//                          String name must be full name with package path included
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
            }
        });
    }
}

