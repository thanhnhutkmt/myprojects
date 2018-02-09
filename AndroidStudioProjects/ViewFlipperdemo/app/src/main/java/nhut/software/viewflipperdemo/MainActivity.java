package nhut.software.viewflipperdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    private ViewFlipper vf;
    private float x1, x2;
    private int length = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vf = (ViewFlipper) findViewById(R.id.vf);
        ImageView i1 = new ImageView(this);
        ImageView i2 = new ImageView(this);
        vf.addView(i1); vf.addView(i2);
        vf.setOnTouchListener(new View.OnTouchListener() {
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
                        vf.showPrevious();
                    } else if (x1 > length + x2) {
                        vf.showNext();
                        Intent i = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(i);
                    }
                }
                return true;
            }
        });
    }
}
