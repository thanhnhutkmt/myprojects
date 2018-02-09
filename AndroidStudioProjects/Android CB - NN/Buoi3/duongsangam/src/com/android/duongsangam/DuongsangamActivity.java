package com.android.duongsangam;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DuongsangamActivity extends Activity {
    /** Called when the activity is first created. */
	
	EditText et_nam;
	Button bt_th;
	TextView tv_kq;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        et_nam=(EditText)findViewById(R.id.editText1);
        bt_th=(Button)findViewById(R.id.button1);
        tv_kq=(TextView)findViewById(R.id.textView2);
        
        bt_th.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				int nam=Integer.parseInt(et_nam.getText().toString());
				String can="",chi="";
				
				switch(nam%10)
				{
					case 0: can="Canh"; break;
					case 1: can="Tân"; 	break;
					case 2: can="Nhâm"; break;
					case 3: can="Quí" ; break;
					case 4: can="Giáp"; break;
					case 5: can="Ất" ; 	break;
					case 6: can="Bính"; break;
					case 7: can="Đinh"; break;
					case 8: can="Mậu" ;	break;
					case 9: can="Kỷ" ; 	break;
				}
				switch(nam%12)
				{
					case 0: chi="Thân";	break;
					case 1: chi="Dậu"; 	break;
					case 2: chi="Tuất"; break;
					case 3: chi="Hợi"; 	break;
					case 4: chi="Tí"; 	break;
					case 5: chi="Sửu"; 	break;
					case 6: chi="Dần"; 	break;
					case 7: chi="Mẹo"; 	break;
					case 8: chi="Thìn"; break;
					case 9: chi="Tị"; 	break;
					case 10: chi="Ngọ"; break;
					case 11: chi="Mùi"; break;
				}
				
				tv_kq.setText("nam " + nam + " la " + can + " "+ chi);
			
				
			}
		});
        
    }
}