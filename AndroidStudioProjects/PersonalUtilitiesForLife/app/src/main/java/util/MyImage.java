package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import software.nhut.personalutilitiesforlife.R;

/**
 * Created by Nhut on 7/12/2016.
 */
public class MyImage {
    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }

    //Bitmap to String:
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] arr=baos.toByteArray();
        String result= Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }

    //String to Bitmap:
    public static Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static Bitmap drawTextOnBitMap(Bitmap origin, String addedString, float fontSize, int textColor) {
        Canvas canvas = new Canvas(origin);

        Paint paint = new Paint();
        paint.setColor(textColor); // Text Color
        paint.setTextSize(fontSize); // Text Size
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)); // Text Overlapping Pattern

        canvas.drawBitmap(origin, 0, 0, paint);
        canvas.drawText(addedString.substring(0, addedString.indexOf("start_location")), 50, 10 + fontSize, paint);
        return origin;
    }

    public static Bitmap getThumbnail(File f) {
        return getThumbnail(f, 64, 64);
    }

    public static Bitmap getThumbnail(File f, int size) {
        return loadImage(f, size, size);
    }

    public static Bitmap getThumbnail(File f, int width, int height) {
        Bitmap b = null;
        try {
            b = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(
                    new FileInputStream(f)), width, height, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static Bitmap loadImage(File f, int sw, int sh) {
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            int w = b.getWidth();
            int h = b.getHeight();
            int sch, scw;
            if (w > h) {
                scw = sw;
                sch = (int) ((float)sw/w * h);
            } else {
                sch = sh;
                scw = (int) ((float)sh/h * w);
            }
            b = Bitmap.createScaledBitmap(b, scw, sch, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static Bitmap loadDrawable(Context context, int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }
}
