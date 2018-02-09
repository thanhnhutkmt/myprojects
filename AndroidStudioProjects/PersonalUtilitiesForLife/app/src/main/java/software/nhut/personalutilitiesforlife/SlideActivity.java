package software.nhut.personalutilitiesforlife;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import software.nhut.personalutilitiesforlife.adapter.AdapterShelfImage;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import util.MyImage;
import util.MyMedia;
import util.MyPhone;

public class SlideActivity extends AppCompatActivity {
    private ImageView imv;
    private TextView txt_shelf;
    private CheckBox checkBox_random;
    private VideoView album_viv;
    private View control[];
    private File imageFiles[];
    private int position, sw, sh;
    private boolean nonstop = true;
    private HorizontalScrollView touchlayer;
    private float x1,x2;
    private final int length = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        addControl();
        addEvent();
    }

    private void addEvent() {
        String path = getIntent().getStringExtra(AppConstant.ALBUM_PATHSTRING);
        if (path != null) {
            File f = new File(path);
            if (f != null) {
                if (f.isDirectory()) {
                    imageFiles = f.listFiles(AdapterShelfImage.fnf);
                    show(imageFiles[0]);
                } else if (f.isFile()) {
                    imageFiles = new File[]{f};
                    show(f);
                }
                position = 0;
                txt_shelf.setText((position + 1) + "/" + imageFiles.length);
            } else {
                imageFiles = new File[0];
                txt_shelf.setText("0/0");
            }
        }

        checkBox_random.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new Thread(new Runnable() {
                        @Override public void run() {
                            nonstop = true;
                            while (nonstop) {
                                position = (int) (Math.random() * imageFiles.length - 1);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        show(imageFiles[position]);
//                                        imv.setImageBitmap(MyImage.loadImage(imageFiles[position], sw, sh));
                                        txt_shelf.setText((position + 1) + "/" + imageFiles.length);
                                    }
                                });
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                } else nonstop = false;
            }
        });

        touchlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                Log.i("MyTag", "action " + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showControlForAWhile(5000);
                    x1 = event.getX();
                    Log.i("MyTag", "x1 " + x1);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    x2 = event.getX();
                    Log.i("MyTag", "x2 " + x2);
                    if (x2 > length + x1) {
                        if (position > 0) {
                            show(imageFiles[--position]);
                            txt_shelf.setText((position + 1) + "/" + imageFiles.length);
                        }
                    } else if (x1 > length + x2) {
                        if (position < imageFiles.length - 1) {
                            show(imageFiles[++position]);
                            txt_shelf.setText((position + 1) + "/" + imageFiles.length);
                        }
                    }
                }
                return false;
            }
        });
    }

    private void show(File f) {
        if (MyMedia.isVideoFile(f)) {
            imv.setVisibility(View.GONE);
            touchlayer.setVisibility(View.GONE);
            album_viv.setVisibility(View.VISIBLE);
            AppConstant.VIDEOPOS = f.getAbsolutePath();
            album_viv.setVideoPath(f.getAbsolutePath());
            MyPhone.setLandscapeOrient(this);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(album_viv);
            album_viv.setMediaController(mediaController);
            album_viv.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            album_viv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mp) {
                    Point screenSize = MyPhone.getScreenSize(SlideActivity.this);
                    Point vidSize = new Point(mp.getVideoWidth(), mp.getVideoHeight());
                    float xscale = (float)screenSize.x/vidSize.x;
                    float yscale = (float)screenSize.y/vidSize.y;
                    int x = (xscale > yscale) ? (int) ((screenSize.x - vidSize.x * yscale) / 2) : 0;
                    int y = (xscale > yscale) ? 0 : (int) ((screenSize.y - vidSize.y * xscale) / 2);
                    Point pos = new Point(x, y);
                    Log.i("MyTag", String.format("vid : %dx%d, screen : %dx%d, pos : %dx%d",
                        vidSize.x, vidSize.y, screenSize.x, screenSize.y, pos.x, pos.y));
                    album_viv.setX(pos.x);
                    album_viv.setY(pos.y);
                }
            });
            album_viv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    album_viv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            album_viv.setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                        }
                    }, 5000);
                    Log.i("MyTag", "ontouch refullscreen");
                    return false;
                }
            });
//            album_viv.start();
        } else {
            imv.setVisibility(View.VISIBLE);
            touchlayer.setVisibility(View.VISIBLE);
            album_viv.setVisibility(View.GONE);
            imv.setImageBitmap(MyImage.loadImage(imageFiles[position], sw, sh));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        int pos = album_viv.getCurrentPosition();
        Log.i("MyTag", "onPause time " + pos);
        getSharedPreferences(AppConstant.PREF, MODE_PRIVATE).edit()
            .putInt(AppConstant.VIDEOPOS, pos).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int pos = getSharedPreferences(
                AppConstant.PREF, MODE_PRIVATE).getInt(AppConstant.VIDEOPOS, 0);
        Log.i("MyTag", "onResume time " + pos);
        album_viv.seekTo(pos);
    }

    private void addControl() {
        imv = (ImageView) findViewById(R.id.album_imv);
        txt_shelf = (TextView) findViewById(R.id.txt_shelf);
        checkBox_random = (CheckBox) findViewById(R.id.checkBox_random);
        album_viv = (VideoView) findViewById(R.id.album_videoview);
//        album_viv.setMediaController(new MediaController(SlideActivity.this));
        control = new View[] {txt_shelf, checkBox_random};
        touchlayer = (HorizontalScrollView) findViewById(R.id.touchlayer);
        hideControl();
        Point size = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(size);
        this.sh = size.y;
        this.sw = size.x;
    }

    private void hideControl() {
        for (View v : control) v.setVisibility(View.GONE);
    }

    private void showControlForAWhile(int ms) {
        for (final View v : control) {
            v.setVisibility(View.VISIBLE);
            v.startAnimation(AnimationUtils.loadAnimation(SlideActivity.this, R.anim.blur5));
            v.postDelayed(new Runnable() {
                @Override public void run() {
                    v.setVisibility(View.GONE);
                }
            }, ms);
        }
    }
}
