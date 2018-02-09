package com.android.cuuchuong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CuuchuongActivity extends Activity {
    /** Called when the activity is first created. */
	
	EditText et_N;
	Button bt_th;
	TextView tv_kq;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        et_N=(EditText)findViewById(R.id.editText1);
        bt_th=(Button)findViewById(R.id.button1);
        tv_kq=(TextView)findViewById(R.id.textView2);
        
        bt_th.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int n=Integer.parseInt(et_N.getText().toString());
				String chuoi="";
				for(int i=1;i<=9;i++)
				{
					chuoi+=n +" x " + i + "=" + n*i + "\n";
				}
				tv_kq.setText(chuoi);
				
				
			}
		});
        
    }
}