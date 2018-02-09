package com.example.administrator.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2;
    Button bt;
    TextView tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        bt=(Button)findViewById(R.id.button);
        tv3=(TextView)findViewById(R.id.textView3);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x=Integer.parseInt(et1.getText().toString());
                int y=Integer.parseInt(et2.getText().toString());
                int z=x+y;
                tv3.setText(getResources().getString(R.string.kq)+ z);
            }
        });

    }
}
