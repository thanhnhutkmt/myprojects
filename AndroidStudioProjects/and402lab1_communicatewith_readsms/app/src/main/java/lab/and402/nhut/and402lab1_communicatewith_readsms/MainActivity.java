package lab.and402.nhut.and402lab1_communicatewith_readsms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textviewresult;
    Button btn_open;
    final int requestCode = 0;
    int error = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textviewresult = (TextView) findViewById(R.id.textviewresult);
        btn_open = (Button) findViewById(R.id.openAppReadSMS);

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("lab.and402.nhut.and402lab1_readsms");
                i.addCategory("android.intent.category.DEFAULT");
                try {
                    startActivityForResult(i, requestCode);
                } catch (Exception e) {
                    textviewresult.setText(e.getMessage());
                    error = 1;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode && error == 0) {
            textviewresult.setText("Opened App read SMS");
        } else if (error == 1){
            Toast.makeText(this, "Error open app", Toast.LENGTH_SHORT).show();
        }
    }
}
