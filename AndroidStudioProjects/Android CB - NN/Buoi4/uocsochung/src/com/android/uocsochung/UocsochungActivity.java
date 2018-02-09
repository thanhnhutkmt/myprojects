package com.android.uocsochung;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UocsochungActivity extends Activity {
    /** Called when the activity is first created. */
	
	EditText so_a,so_b;
	Button bt_th;
	TextView tv_kq;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        so_a=(EditText)findViewById(R.id.editText1);
        so_b=(EditText)findViewById(R.id.editText2);
        bt_th=(Button)findViewById(R.id.button1);
        tv_kq=(TextView)findViewById(R.id.textView3);
        
        bt_th.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int a=Integer.parseInt(so_a.getText().toString());
				int b=Integer.parseInt(so_b.getText().toString());
				String chuoi="";
				for(int i=1;i<=a;i++)
				{
					if(a%i==0 && b%i==0)
					{
						chuoi+= i + " ";
					}
				}
				tv_kq.setText("các ước số chung : " + chuoi);
				
				
				
			}
		});
        
    }
}