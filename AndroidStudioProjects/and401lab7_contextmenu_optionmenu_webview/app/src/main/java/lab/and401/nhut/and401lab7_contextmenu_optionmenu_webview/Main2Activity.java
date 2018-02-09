package lab.and401.nhut.and401lab7_contextmenu_optionmenu_webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView image = (ImageView) findViewById(R.id.imagefullscreen);
        int imageid = getIntent().getIntExtra("Image", 0);
        if (imageid != 0) image.setImageResource(imageid);
    }
}
