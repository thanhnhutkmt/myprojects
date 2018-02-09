package comicreader.software.nhut.doc_truyen.DO.Comicvn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import comicreader.software.nhut.doc_truyen.Constant;
import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.NoiDung_Truyen;
import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.ReadComicsActivity;
import comicreader.software.nhut.doc_truyen.adapter.MyRVComicAdapter;
import comicreader.software.nhut.doc_truyen.adapter.MyRVComicChapterAdapter;
import comicreader.software.nhut.doc_truyen.util.MyFileIO;
import comicreader.software.nhut.doc_truyen.util.MyPhone;

/**
 * Created by Nhut on 7/4/2017.
 */

public class DownloadComicPage {//extends AsyncTask<String, Integer, List<String>> {
    private ProgressDialog pDialog;
    private ProgressBar pbar;
    private Activity activity;
    private ComicvnServer.onComicReadyCallback onComicReady;
    private String tenComic, savingPath;
    private boolean stopFlag;
    private TextView donwloadbtn;
    public int position = -1;
    private MyRVComicChapterAdapter.MyViewHolder holder;
    private List<MyRVComicChapterAdapter.StatusViewHolder> listDownloadStatus;

    public DownloadComicPage(Activity activity, MyRVComicChapterAdapter.MyViewHolder holder,
                             List<MyRVComicChapterAdapter.StatusViewHolder> listDownloadStatus,
                             String tenComic, ComicvnServer.onComicReadyCallback onComicReady) {
        this.activity = activity;
        this.onComicReady = onComicReady;
        this.holder = holder;
        this.pbar = holder.pb;
        this.donwloadbtn = holder.tv_downloadpause;
        this.position = holder.position;
        this.tenComic = tenComic;
        this.savingPath = Constant.EXTDIR + File.separator
                + tenComic + File.separator + holder.tvtenChapter.getText().toString();
        new File(savingPath).mkdirs();
        this.listDownloadStatus = listDownloadStatus;
    }

    public DownloadComicPage(Activity activity, String chapterName,
                             ComicvnServer.onComicReadyCallback onComicReady) {
        this.activity = activity;
        this.onComicReady = onComicReady;
        this.savingPath = null;
        pDialog = new ProgressDialog(activity);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.setTitle(activity.getResources().getString(R.string.Dangtaitrangtruyen) + " " + chapterName);
        pDialog.setMessage(activity.getResources().getString(R.string.comicvn_waitdownload_message));
        pDialog.setProgress(0);
    }

    public DownloadComicPage(Activity activity, String tenComic, String chapterName,
                             ComicvnServer.onComicReadyCallback onComicReady) {
        this.activity = activity;
        this.onComicReady = onComicReady;
        this.tenComic = tenComic;
        File folder = new File(Constant.EXTDIR + File.separator
                + tenComic + File.separator + chapterName);
        folder.mkdirs();
        this.savingPath = folder.getAbsolutePath();

        pDialog = new ProgressDialog(activity);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        pDialog.setIndeterminate(true);
        pDialog.setTitle(activity.getResources().getString(R.string.Dangtaitrangtruyen));
        pDialog.setMessage(activity.getResources().getString(R.string.comicvn_waitdownload_message));
        pDialog.setMax(1);
        pDialog.setProgress(0);
        pDialog.setCancelable(false);
    }

    private void onPreExecute(String[] list) {
        if (pDialog != null) {
            pDialog.setIndeterminate(false);
            pDialog.setMax(list.length);
            pDialog.setProgress(0);
            pDialog.show();
        }
        if (listDownloadStatus != null) {
            listDownloadStatus.get(position).setDownloadingFlag(true);
            listDownloadStatus.get(position).setLabel_TVdownloadpause("");
            listDownloadStatus.get(position).setTvdownpause_clickable(false);
            listDownloadStatus.get(position).setNopercent(true);
        }
    }

    public void execute(final List<String> list) {
        execute(Long.toString(System.currentTimeMillis()), list);
    }

    public void execute(final String... array) {
        execute(Long.toString(System.currentTimeMillis()), array);
    }

    public void execute(final String cacheFolderName, final List<String> list) {
        final String array[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) array[i] = list.get(i);
        execute(cacheFolderName, array);
    }

    public void execute(final String cacheFolderName, final String... list) {
        onPreExecute(list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doInBackground(cacheFolderName, list);
            }
        }).start();
    }

    private void doInBackground(String cacheFolderName, final String... listImagePagelink) {
        stopFlag = false;
        Point p = MyPhone.getScreenSize(activity);
        Log.i("MyTag", "Device screen size : " + p.x + "x" + p.y);
        if (savingPath == null) {
            savingPath = activity.getCacheDir().getAbsolutePath()
                    + File.separator + cacheFolderName;
            new File(savingPath).mkdirs();
        } else checkAndSaveTruyenInfo();
        Log.i("MyTag", "Saving Path " + savingPath);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < listImagePagelink.length; i++) {
            boolean error = false;
            String link = listImagePagelink[i];
            if (!link.startsWith("http")) continue;
            try {
                File downloadFile = new File(
                        savingPath + File.separator + Integer.toString(i) + ".png");
                if (!downloadFile.exists())
                    MyFileIO.saveBitmap(new URL(link).openStream(),
                        savingPath + File.separator + Integer.toString(i) + ".png", p.x, p.y);
                if (i == 0) //save thumbnail
                    MyFileIO.saveBitmap(new URL(link).openStream(),
                            savingPath + File.separator + Truyen.THUMBNAILFILE,
                            Constant.THUMBNAILSIZE, Constant.THUMBNAILSIZE);
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
            }
            if (!error) {
                list.add(savingPath + File.separator + Integer.toString(i) + ".png");
            } else list.add(listImagePagelink[i]);
            final Integer arrayInt[] = {i + 1, listImagePagelink.length};
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    publishProgress(arrayInt);
                }
            });
            if(stopFlag) return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onPostExecute(list);
            }
        });
    }

    private void publishProgress(Integer... values) {
        if (listDownloadStatus != null) {
            listDownloadStatus.get(position).setNopercent(false);
            listDownloadStatus.get(position).setMax(values[1]);
            listDownloadStatus.get(position).setProgress(values[0]);
            listDownloadStatus.get(position).setLabel_TVdownloadpause("");
        }
        if (holder != null && holder.position == position && listDownloadStatus != null)
            listDownloadStatus.get(position).updateStatus(holder);
        if (pDialog != null) {
            if (values[0] == -1) pDialog.setIndeterminate(true);
            else {
                pDialog.setIndeterminate(false);
                pDialog.setMax(values[1]);
                pDialog.setProgress(values[0]);
            }
        }
    }

    private void onPostExecute(List<String> listPageLink) {
        if (listDownloadStatus != null) {
            listDownloadStatus.get(position).setProgress(pbar.getMax());
            listDownloadStatus.get(position).setDownloadingFlag(false);
            listDownloadStatus.get(position).setLabel_TVdownloadpause(
                    activity.getResources().getString(R.string.downloadfinish));
            listDownloadStatus.get(position).setTvdownpause_clickable(false);
        }
        if (holder != null && holder.position == position && listDownloadStatus != null)
            listDownloadStatus.get(position).updateStatus(holder);
        if (pDialog != null) pDialog.dismiss();
        if (listPageLink != null) onComicReady.onDataReady(listPageLink);
    }

    private void checkAndSaveTruyenInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("MyTag", "checkAndSaveTruyenInfo");
                Truyen truyen = null;
                if (activity instanceof NoiDung_Truyen)
                    truyen = ((NoiDung_Truyen) activity).getTruyen();
                else return;
                File folder = new File(Constant.EXTDIR + File.separator + truyen.getName());
                folder.mkdirs();
                File info = new File(folder.getAbsolutePath() + File.separator + Truyen.TRUYENINFO);
                if (!info.exists()) MyFileIO.saveObjectInstance(info.getAbsolutePath(), truyen);
                try {
                    File thumbnail = new File(folder.getAbsoluteFile()
                            + File.separator + Truyen.THUMBNAILFILE);
                    if (!thumbnail.exists())
                        MyFileIO.saveBitmap(new URL(truyen.getThumbnailLink()).openStream(),
                            thumbnail.getAbsolutePath(), Constant.THUMBNAILSIZE,
                                Constant.THUMBNAILSIZE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
