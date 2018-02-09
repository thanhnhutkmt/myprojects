package nhut.software.viewflipperdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private float x1, x2;
    private int length = 140;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_main2);
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Log.i("MyTag", "touchlayer action " + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x1 = event.getX();
                    Log.i("MyTag", "touchlayer x1 " + x1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    x2 = event.getX();
                    Log.i("MyTag", "touchlayer x2 " + x2);
                    if (x2 > length + x1) {

                    } else if (x1 > length + x2) {

                    }
                }
                return true;
            }
        });

        txt = (TextView) findViewById(R.id.txt);
        txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.i("MyTag", "....");
    }
}
