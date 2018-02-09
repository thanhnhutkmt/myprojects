package comicreader.software.nhut.doc_truyen.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.util.MyFileIO;
import comicreader.software.nhut.doc_truyen.util.MyPhone;

/**
 * Created by Nhut on 7/02/2017.
 */

public class MyRVComicPageAdapter extends RecyclerView.Adapter<MyRVComicPageAdapter.MyViewHolder> {

    private List<String> list;
    private Activity activity;


    public MyRVComicPageAdapter(Activity activity, List<String> list) {
//        Log.i("MyTag", "MyRecyclerAdapter");
        this.list = list;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.i("MyTag", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcomicpage, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Log.i("MyTag", "onBindViewHolder");
        String link = list.get(position);
        if (link.startsWith("http")) downloadImage(link, holder.image, position);
        else holder.image.setImageBitmap(MyFileIO
                .loadBitmapFromCache(activity, link));
    }

    private void downloadImage(final String link, final ImageView image, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = new URL(link).openStream();
                    Point screenSize = MyPhone.getScreenSize(activity);
                    System.out.println("download image !");
                    final String cachedFileName = Integer.toString(position);
                    MyFileIO.saveBitmapToCache(is, cachedFileName, screenSize.x, screenSize.y);
                    list.set(position, cachedFileName);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageBitmap(MyFileIO.loadBitmapFromCache(activity, cachedFileName));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Bitmap decodeBitmap(InputStream is, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream (is, new Rect(),options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream (is, new Rect(),options);
    }

    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : -1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_page);
        }
    }
}