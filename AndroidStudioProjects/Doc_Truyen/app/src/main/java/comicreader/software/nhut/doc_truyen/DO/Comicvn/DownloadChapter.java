package comicreader.software.nhut.doc_truyen.DO.Comicvn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.util.MyPhone;

import static org.jsoup.helper.HttpConnection.DEFAULT_UA;

/**
 * Created by Nhut on 7/3/2017.
 */

public class DownloadChapter extends AsyncTask<String, String, List<Truyen>> {
    private ProgressDialog pdialog;
    private Activity activity;
    private ComicvnServer.onDataReadyCallback onDataReady;

    public DownloadChapter(Activity activity, ComicvnServer.onDataReadyCallback onDataReady) {
        this.activity = activity;
        this.onDataReady = onDataReady;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdialog = new ProgressDialog(activity);
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.setIndeterminate(true);
        pdialog.setTitle(R.string.comicvn_waitdownload_title);
        pdialog.setMessage(activity.getString(R.string.comicvn_waitdownload_message));
        pdialog.setCancelable(false);
        pdialog.show();
    }

    @Override
    protected List<Truyen> doInBackground(String... linkChapterList) {
        List<Truyen> listChapter = new ArrayList<>();
        Log.i("MyTag", "DownloadChapter : doInBG : linkChapterList " + linkChapterList.length);
        for (int i = 0; i < linkChapterList.length; i++) {
            try {
                String link = linkChapterList[i];
                final List<String> listdata = new ArrayList<>();
                final Document doc = Jsoup.connect(link)
                        .userAgent("Android" + MyPhone.getOSVersion()).get();
                if (doc == null) {
                    listChapter.add(new Truyen("", "", "", listdata));
                    continue;
                }
                // 0 : title; 1 : thumbnail; 2 : description; 3 : list
                // get comic title
//                Log.i("MyTag", "DownloadChapter : link : " + link + ", result : " + doc);

//                Log.i("MyTag", "DownloadChapter : index " + i + ", es" + " " + es.toString());
//                Log.i("MyTag", "DownloadChapter : " + "es" + " " + es.get(0).toString());
//                Log.i("MyTag","\nes.get(0).getElementsByTag(option)"
//                        + es.get(0).getElementsByTag("option"));
//                Thread.sleep(2000);
                Elements es = doc.getElementsByAttributeValue("class", "loadChapter");
                List<String> chapterNameList = new ArrayList<>();
                for (Element e : es.get(0).getElementsByTag("option"))
                    chapterNameList.add(e.text());
                listdata.add(chapterNameList.get(i));
                // get comic decription : chapter has no description
                listdata.add("");
                // get image page hyperlink
                Element ei = doc.getElementById("txtarea");
                for (String part : ei.toString().split("\""))
                    if (part.startsWith("http")) listdata.add(part.split(" ")[0]);//.split("imgmax")[0]);
//                System.out.println(listdata.toString());
                if (listdata.size() < 3) continue;
                // get thumbnail and add to position 2;
                listdata.add(1, listdata.get(2));
                listChapter.add(new Truyen(listdata.remove(0), listdata.remove(0),
                        listdata.remove(0), listdata));
            } catch (Exception e) {
                Log.i("MyTag", activity.getResources().getString(R.string.networkerror));
                publishProgress(activity.getString(R.string.networkerror) + "(c" + i + ")");
            }
        }
        return listChapter;
    }

    @Override
    protected void onProgressUpdate(final String... values) {
        super.onProgressUpdate(values);
        pdialog.setMessage(values[0]);
        TextView tv = new TextView(activity);
        tv.postDelayed(new Runnable() {
            @Override
            public void run() {
                pdialog.setMessage(activity.getString(R.string.comicvn_waitdownload_message));
            }
        }, 2000);
    }

    @Override
    protected void onPostExecute(List<Truyen> list) {
        super.onPostExecute(list);
        pdialog.dismiss();
        onDataReady.onDataReady(list);
    }
}
