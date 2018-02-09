package comicreader.software.nhut.doc_truyen;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import comicreader.software.nhut.doc_truyen.adapter.MyRVComicAdapter;
import comicreader.software.nhut.doc_truyen.adapter.MyRVComicChapterAdapter;
import comicreader.software.nhut.doc_truyen.util.MyFileIO;

public class MyCacheThumbnailService extends Service {
    public static Activity activity;
    public static List<MyRVComicAdapter.MyViewHolder> listAComicViewHolder;
    public static List<MyRVComicChapterAdapter.MyViewHolder> listAChapterViewHolder;
    private boolean stopFlag = false;

    public MyCacheThumbnailService(){};

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Toast.makeText(activity, R.string.comicvn_waitdownload_message, Toast.LENGTH_SHORT).show();
//        Log.i("MyTag", "Start Cache thumbnail service");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String tempFolderName = intent.getStringExtra(Constant.DOWNLOADPATH);
                List<String> list = intent.getStringArrayListExtra(Constant.LINKLIST);
                for (int i = 0, size = list.size(); i < size; i++) {
//                    Log.i("MyTag", "onStartCommand " + list.get(i));
                    try {
//                        Log.i("MyTag", "saving... " + list.get(i));
                        ProviderInstaller.installIfNeeded(activity);
                        MyFileIO.saveBitmapToCache(new URL(list.get(i)).openStream(),
                            tempFolderName + File.separator + Integer.toString(i),
                            Constant.THUMBNAILSIZE, Constant.THUMBNAILSIZE);
                    } catch(Exception e) {
                        e.printStackTrace();
                        MyFileIO.saveNullBitmap(
                            tempFolderName + File.separator + Integer.toString(i));
                    }
                    final int index = i;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (listAComicViewHolder != null) {
                                for (MyRVComicAdapter.MyViewHolder h : listAComicViewHolder)
                                    if (h.position == index) h.loadImage();
                            } else if (listAChapterViewHolder != null) {
                                for (MyRVComicChapterAdapter.MyViewHolder h : listAChapterViewHolder)
                                    if (h.position == index) h.loadImage();
                            }
                        }
                    });
                    if (stopFlag) {
                        Log.i("MyTag", "Terminate cache thumbnail service");
                        break;
                    }
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                            Toast.makeText(activity, R.string.downloadfinish, Toast.LENGTH_SHORT).show();
                        listAComicViewHolder = null;
                        listAChapterViewHolder = null;
                    }
                });
            }
        }).start();
//        Log.i("MyTag", "Service is running");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        stopFlag = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopFlag = true;
        Log.i("MyTag", "onDestroy() cache thumbnail service");
    }
}
