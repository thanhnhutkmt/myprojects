package comicreader.software.nhut.doc_truyen.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import comicreader.software.nhut.doc_truyen.Constant;
import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.R;

/**
 * Created by Nhut on 6/19/2016.
 */
public class MyFileIO {
    public static boolean clearFolderContent(String path, boolean deleteFolder) {
        boolean result = true;
        File folder = new File(path);
        if (folder.exists()) {
            if (folder.isDirectory()) {
                File[] content = folder.listFiles();
                if (content != null)
                    for (File f : content) {
                        if (f.isDirectory()) clearFolderContent(f.getAbsolutePath(), true);
                        else if (f.isFile()) result = f.delete();
                    }
            } else if (folder.isFile()) folder.delete();
        }
        if (deleteFolder) result = folder.delete();
        return result;
    }

    public static String saveBitmapToCache(InputStream is,
                                           String name, int width, int height) {
        File cachedImage = new File(Constant.CACHEDIR + File.separator + name);
        Log.i("MyTag", cachedImage.getAbsolutePath());
        return saveBitmapToCache(is, cachedImage, width, height);
    }

    public static String saveBitmap(InputStream is,
                                    String pathWithFileName, int width, int height) {
        return saveBitmapToCache(is, new File(pathWithFileName), width, height);
    }

    public static String saveBitmapToCache(final InputStream is,
                                           final File writtenImage, int swidth, int sheight) {
        try {
            final List<Bitmap> listBitmap = new ArrayList<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    Log.i("MyTag", "start to download and save file "
//                            + writtenImage.getAbsolutePath());
                    listBitmap.add(BitmapFactory.decodeStream(is));
                }
            }).start();
            int waitTime = 0;
            while(waitTime++ < 8) {
//                Log.i("MyTag", "wait for data ... " + waitTime);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (listBitmap.size() > 0 && listBitmap.get(0) != null) break;
            }
            if (waitTime >= 8) return "";
            Bitmap b = listBitmap.get(0);
            int width = (swidth <= 0) ? 10 : swidth;
            int height = (sheight <= 0) ? 10 : sheight;
            float scaledFactor = (b.getWidth() > b.getHeight())
                ? (float)width/b.getWidth() : (float)height/b.getHeight();
//            Log.i("MyTag", "image size : " + b.getWidth() + "x" + b.getHeight()
//                    + " ScaledFactor " + scaledFactor);
            b = Bitmap.createScaledBitmap(b, (int)(b.getWidth() * scaledFactor),
                                    (int)(b.getHeight() * scaledFactor) , false);
            FileOutputStream fos = new FileOutputStream(writtenImage);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush(); fos.close();
//            Log.i("MyTag", "Save bitmap ok " + writtenImage.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return writtenImage.getAbsolutePath();
    }

    public static void saveNullBitmap(String filename) {
        try {
            Bitmap.createBitmap(new int[]{0}, 1, 1, Bitmap.Config.RGB_565)
                .compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadBitmapFromCache(Activity activity, String name) {
        try {
            return BitmapFactory.decodeFile(
                    activity.getCacheDir().getAbsolutePath() + File.separator + name);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap loadBitmapFromDisk(String path) {
        return BitmapFactory.decodeFile(path);
    }

    public static <T> ArrayList<T> convertToArrayList(List<T> list) {
        ArrayList<T> a = new ArrayList<T>();
        for (T t : list) a.add(t);
        return a;
    }
    public static <T> ArrayList<T> convertToArrayList(T[] list) {
        ArrayList<T> a = new ArrayList<T>();
        for (T t : list) a.add(t);
        return a;
    }

    public static String[] convertToStringArrray(List<String> list) {
        String a[] = new String[list.size()];
        for (int i = 0, size = list.size(); i < size; i++) a[i] = list.get(i);
        return a;
    }

    public static void saveObjectInstance(String fileNameWithPath, Truyen truyen) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(fileNameWithPath));
            oos.writeObject(truyen);
            oos.flush(); oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object loadObjectInstance(String fileNameWithPath) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileNameWithPath));
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
