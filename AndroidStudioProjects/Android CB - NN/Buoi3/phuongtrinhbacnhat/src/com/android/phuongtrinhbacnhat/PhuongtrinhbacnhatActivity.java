package com.android.phuongtrinhbacnhat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhuongtrinhbacnhatActivity extends Activity {
    /** Called when the activity is first created. */
	
	EditText et_a, et_b;
	Button bt_th;
	TextView tv_ht;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        et_a=(EditText)findViewById(R.id.editText1);
        et_b=(EditText)findViewById(R.id.editText2);
        bt_th=(Button)findViewById(R.id.button1);
        tv_ht=(TextView)findViewById(R.id.textView3);
        
        bt_th.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				float a=Float.parseFloat(et_a.getText().toString());
				float b=Float.parseFloat(et_b.getText().toString());
				if(a!=0)
				{
					tv_ht.setText("nghiem duy nhat " + (-b)/a );
				}
				else
				{
					if(b!=0)
						tv_ht.setText("vo nghiem");
					else
						tv_ht.setText("Vo so nghiem");
				}
			}
			
		});
        
        
    }
}