package lab.and401.nhut.and401lab5_customviewindrawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Nhut on 6/23/2017.
 */

public class AndroidATCView extends View {
    private int mSquareCol, mLabelCol;
    private String mSquareText;
    private Paint mSquarePaint;
    private Context context;

    public AndroidATCView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mSquarePaint = new Paint();
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.AndroidATCView, 0, 0);
        try {
            mSquareText = typedArray.getString(R.styleable.AndroidATCView_squareLabel);
            mSquareCol = typedArray.getColor(R.styleable.AndroidATCView_squareColor, 0);
            mLabelCol = typedArray.getColor(R.styleable.AndroidATCView_labelColor, 0);
        } finally {
            typedArray.recycle();
        }
    }

    public int getmSquareCol() {
        return mSquareCol;
    }

    public void setmSquareCol(int newColor) {
        this.mSquareCol = newColor;
        invalidate();
        requestLayout();
    }

    public int getmLabelCol() {
        return mLabelCol;
    }

    public void setmLabelCol(int newColor) {
        this.mLabelCol = newColor;
        invalidate();
        requestLayout();
    }

    public String getmSquareText() {
        return mSquareText;
    }

    public void setmSquareText(String newText) {
        this.mSquareText = newText;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInEditMode()) {
            mSquarePaint.setStyle(Paint.Style.FILL);
            mSquarePaint.setAntiAlias(true);
            mSquarePaint.setColor(mSquareCol);
            canvas.drawRect(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight(), mSquarePaint);

            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher)
                    , this.getMeasuredWidth()/4, this.getMeasuredWidth()/4, mSquarePaint);

            mSquarePaint.setTextAlign(Paint.Align.CENTER);
            mSquarePaint.setColor(mLabelCol);
            mSquarePaint.setTextSize(this.getMeasuredHeight()/6);
            canvas.drawText(mSquareText, this.getMeasuredWidth()/2, this.getMeasuredWidth()/2, mSquarePaint);

        }
    }
}
