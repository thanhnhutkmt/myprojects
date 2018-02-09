package util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import software.nhut.personalutilitiesforlife.BallGameActivity;

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

    public static void playSoundEff(Context context, int index) {
        SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        String []listMusic = new String[0];
        AssetFileDescriptor afd = null;
        try {
            listMusic = context.getAssets().list("music");
//            for (String mf : listMusic) Log.i("MyTag", mf);
            afd = context.getAssets().openFd("music" + File.separator + listMusic[index]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int soundID = soundPool.load(afd, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundID, 1, 1, 1, 0, 1);
            }
        });
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
        try {
            return Bitmap.createScaledBitmap(ThumbnailUtils.createVideoThumbnail
                    (f.getAbsolutePath(), MediaStore.Video.Thumbnails.MINI_KIND), scw, sch, false);
        } catch (Exception e) {
            return Bitmap.createBitmap(scw, sch, Bitmap.Config.RGB_565);
        }
    }

    public static final List<String> listImageExtension = Arrays.asList(new String[] {".jpg", ".jpeg", ".png", ".gif", ".bmp"});
    public static final List<String> listVideoExtension = Arrays.asList(new String[] {".mp4"});

    public static boolean isVideoFile(File f) {
        String name = f.getName();
        return listVideoExtension.contains(name.substring(name.length() - 4).toLowerCase());
    }


}
