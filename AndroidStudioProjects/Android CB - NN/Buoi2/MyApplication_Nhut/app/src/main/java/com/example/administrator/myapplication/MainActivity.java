package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText lenet, widet;
    Button calbtn;
    TextView Atv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lenet = (EditText)findViewById(R.id.editText);
        widet = (EditText)findViewById(R.id.editText2);
        calbtn = (Button)findViewById(R.id.button);
        Atv = (TextView)findViewById(R.id.textView3);
        calbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int len = Integer.parseInt(lenet.getText().toString());
                int wid = Integer.parseInt(widet.getText().toString());
                Atv.setText(getResources().getString(R.string.Atv) + " " + len*wid +
                        getResources().getString(R.string.Ltv) + " " + 2*(len+wid));
            }
        });
    }
}
