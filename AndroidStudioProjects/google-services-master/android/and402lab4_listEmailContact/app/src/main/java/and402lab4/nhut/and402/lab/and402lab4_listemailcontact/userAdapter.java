package and402lab4.nhut.and402.lab.and402lab4_listemailcontact;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Created by Nhut on 7/24/2017.
 */

public class userAdapter extends ArrayAdapter {
    private EmailAccount array[];
    private Context context;
    private int itemlayout;

    public userAdapter(Context context, int itemlayout, EmailAccount[] array) {
        super(context, itemlayout, array);
        this.array = array;
        this.itemlayout = itemlayout;
        this.context = context;
        downloadImageTask();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, itemlayout, null);
        }
        TextView nametv = (TextView) convertView.findViewById(R.id.nametv);
        TextView gendertv = (TextView) convertView.findViewById(R.id.gendertv);
        TextView emailtv = (TextView) convertView.findViewById(R.id.emailtv);
        ImageView avatariv = (ImageView) convertView.findViewById(R.id.avatariv);
        nametv.setText(array[position].getName());
        gendertv.setText(array[position].getGender());
        emailtv.setText(array[position].getEmail());
        avatariv.setImageBitmap(readImage(position));
        return convertView;
    }

    private Bitmap readImage(int position) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(
                new File(context.getCacheDir() + File.separator + position + ".jpeg")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void downloadImageTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < array.length; i++) {
                    try {
                        BitmapFactory.decodeStream(new URL(array[i].getPictureurl()).openStream())
                            .compress(Bitmap.CompressFormat.JPEG, 90, new FileOutputStream(
                                context.getCacheDir() + File.separator + i + ".jpeg"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return array[position];
    }

    @Override
    public int getCount() {
        return array.length;
    }
}

