package com.android.tongsotunhien;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TongsotunhienActivity extends Activity {
    /** Called when the activity is first created. */
	
	EditText so_N;
	Button bt_th;
	TextView tv_day;
	TextView tv_tong;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        so_N=(EditText)findViewById(R.id.editText1);
        bt_th=(Button)findViewById(R.id.button1);
        tv_day=(TextView)findViewById(R.id.textView2);
        tv_tong=(TextView)findViewById(R.id.textView3);
        
        bt_th.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				int N=Integer.parseInt(so_N.getText().toString());
				
				
				
				
				int tong=0;
				String chuoi="";
				for(int i=1;i<=N;i++)
				{
					tong+=i;
					chuoi+= String.valueOf(i);
					chuoi+=(i!=N)?"+":"";
				}
				tv_day.setText("S="+chuoi);
				tv_tong.setText("S="+tong);
				
			}
			
		});
        
    }
}