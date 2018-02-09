package util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nhut on 7/14/2016.
 */
public class MyMedia {
    public static void playSound(Context context, int index) {
        // Chay file nhac hieu ung
        MediaPlayer mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset(); // fix bug app show warning "W/MediaPlayer: mediaplayer went away with unhandled events"
                mp.release();
                mp = null;
            }
        });
        try {
            String []listMusic = context.getAssets().list("music");
            for (String n : listMusic) Log.i("MyTag", n);
            index = (index > (listMusic.length - 1)) ? listMusic.length - 1 : (index < 0) ? 0 : index;
            AssetFileDescriptor afd = context.getAssets().openFd("music"
                    + System.getProperty("file.separator") + listMusic[index]);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    public static int getScreenWidth(Activity a) {
        Point size = new Point();
        a.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    public static int getScreenHeight(Activity a) {
        Point size = new Point();
        a.getWindowManager().getDefaultDisplay().getSize(size);
        return size.y;
    }

    public static Bitmap getVideoThumbnail(File f, int size) {
        return getVideoThumbnail(f, size, size);
    }

    public static Bitmap getVideoThumbnail(File f, int sw, int sh) {
        int scw = sw;
        int sch = (int)((float)sw/512 * 384);
        return Bitmap.createScaledBitmap(ThumbnailUtils.createVideoThumbnail
                (f.getAbsolutePath(), MediaStore.Video.Thumbnails.MINI_KIND), scw, sch, false);
    }

    public static final List<String> listImageExtension = Arrays.asList(new String[] {".jpg", ".jpeg", ".png", ".gif", ".bmp"});
    public static final List<String> listVideoExtension = Arrays.asList(new String[] {".mp4"});

    public static boolean isVideoFile(File f) {
        String name = f.getName();
        return listVideoExtension.contains(name.substring(name.length() - 4).toLowerCase());
    }


}
