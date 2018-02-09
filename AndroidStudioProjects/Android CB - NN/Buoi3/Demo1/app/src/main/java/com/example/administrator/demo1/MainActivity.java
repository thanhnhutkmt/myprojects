package com.example.administrator.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    EditText et1,et2,et3;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.textView);
        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText3);
        bt=(Button)findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.parseInt(et1.getText().toString());
                int b=Integer.parseInt(et2.getText().toString());
                int c=Integer.parseInt(et3.getText().toString());

                /*
                if(dieukien)
                 {
                    lenh;
                    if(dieukien)
                        lenh;
                    else
                        lenh;
                 }
                 else if(dieukien2)
                 {
                    lenh;
                 }
                 else if(diemkien3)
                 {
                    lenh;
                 }
                 else
                 {

                 }

                 */

                if ( a == 0 )//bac 1
                {
                    float n=-c/(float)b;
                    tv.setText("bac 1 nghiem " + n);
                }
                else //bac 2
                {
                    float denta = b * b - 4 * a * c ;
                    if(denta<0)
                        tv.setText("vo nghiem");
                    else if(denta==0)
                    {
                        float n=(-b / 2.0f) * a;
                        tv.setText("nghiem kep " + n);
                    }
                    else
                    {
                        float n1= (-b - (float)Math.sqrt(denta) )/ (2 * a);
                        float n2=(-b + (float)Math.sqrt(denta))/(2*a);
                        tv.setText("nghiem 1: " + n1 + "\n" + "nghiem 2: "+ n2);
                    }
                }

            }
        });
    }
}
