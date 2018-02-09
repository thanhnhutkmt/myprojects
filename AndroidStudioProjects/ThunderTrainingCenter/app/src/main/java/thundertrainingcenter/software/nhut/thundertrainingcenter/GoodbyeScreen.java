package thundertrainingcenter.software.nhut.thundertrainingcenter;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.text.style.CharacterStyle;
import android.util.Property;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GoodbyeScreen extends AppCompatActivity {
    TextView ctext, ttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.welcomeescreen);

        ctext = (TextView) findViewById(R.id.ctext);
        ttext = (TextView) findViewById(R.id.ttext);
        ttext.setText("SEE YOU AGAIN");
        TextView btext = (TextView) findViewById(R.id.btext);
        btext.setVisibility(View.GONE);
        rl.startAnimation(AnimationUtils.loadAnimation(this, R.anim.welcomescreen_anim));
        rl.setBackgroundResource(R.drawable.skyline);
        rl.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 3000);

        rl.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        addGradient();
        animatedRainbow();
    }

    private void addGradient() {
        Shader s = new LinearGradient(0, 0, 0, ttext.getTextSize(),
                Color.WHITE, Color.RED, Shader.TileMode.CLAMP);
        ttext.getPaint().setShader(s);
    }

    private void animatedRainbow() {
        final SpannableString ss = new SpannableString(getString(R.string.goodbye));
        AnimatedColorSpan span = new AnimatedColorSpan();
        ss.setSpan(span, 0, ss.length(), 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(span, ANIMATED_COLOR_SPAN_FLOAT_PROPERTY, 0, 100);
        objectAnimator.setEvaluator((new FloatEvaluator()));
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ctext.setText(ss);
            }
        });
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(DateUtils.MINUTE_IN_MILLIS * 1);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }

    private static final Property<AnimatedColorSpan, Float> ANIMATED_COLOR_SPAN_FLOAT_PROPERTY =
            new Property<AnimatedColorSpan, Float>(Float.class, "ANIMATED_COLOR_SPAN_FLOAT_PROPERTY") {
                public void set(AnimatedColorSpan span, Float value) {
                    span.setTranslateXPercentage(value);
                }
                public Float get(AnimatedColorSpan span) {
                    return span.getTranslateXPercentage();
                }
            };

    private static class AnimatedColorSpan extends CharacterStyle {
        private float translateXPercentage;
        private int[] colors;
        private Shader shader;
        private Matrix matrix;

        public AnimatedColorSpan() {
            colors = new int[7];
//            colors[0] = Color.RED;
//            colors[1] = Color.rgb(244, 124, 19); //orange
//            colors[2] = Color.rgb(244, 229, 19); //yellow
//            colors[3] = Color.rgb(45, 244, 19);  //green
//            colors[4] = Color.rgb(19, 173, 244); //blue
//            colors[5] = Color.rgb(19, 83, 244);  //indigo
//            colors[6] = Color.rgb(132, 19, 244); //purple
            colors[0] = Color.BLACK;
            colors[1] = Color.rgb(244, 229, 19); //yellow
            this.matrix = new Matrix();
        }

        public void setTranslateXPercentage(float value) {
            translateXPercentage = value;
        }

        public float getTranslateXPercentage() {
            return translateXPercentage;
        }

        public void updateDrawState(TextPaint paint) {
            paint.setStyle(Paint.Style.FILL);
            float width = paint.getTextSize() * colors.length * 2;
            if (this.shader == null) {
                this.shader = new LinearGradient(0, 0, 0, width, colors, null, Shader.TileMode.MIRROR);
            }
            this.matrix.reset();
            this.matrix.setRotate(90);
            this.matrix.postTranslate(width * translateXPercentage, 0);
            this.shader.setLocalMatrix(this.matrix);
            paint.setShader(this.shader);
        }
    }
}
