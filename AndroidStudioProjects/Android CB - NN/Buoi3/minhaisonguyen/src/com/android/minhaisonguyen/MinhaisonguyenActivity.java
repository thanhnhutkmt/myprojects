package com.android.minhaisonguyen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MinhaisonguyenActivity extends Activity {
    /** Called when the activity is first created. */
	
	EditText et_so1, et_so2;
	Button bt_timmin;
	TextView tv_kq;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        et_so1=(EditText)findViewById(R.id.editText1);
        et_so2=(EditText)findViewById(R.id.editText2);
        bt_timmin=(Button)findViewById(R.id.button1);
        tv_kq=(TextView)findViewById(R.id.textView3);
        
        bt_timmin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				int so1=Integer.parseInt(et_so1.getText().toString());
				int so2=Integer.parseInt(et_so2.getText().toString());
				if(so1<so2)
					tv_kq.setText("min: " + so1 );
				else if(so1>so2)
					tv_kq.setText("min: "+  so2 );
				else
					tv_kq.setText("2 so bang nhau");
			}
		});
        
    }
}