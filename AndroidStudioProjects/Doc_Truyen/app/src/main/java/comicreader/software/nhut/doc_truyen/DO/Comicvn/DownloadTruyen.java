package comicreader.software.nhut.doc_truyen.DO.Comicvn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.MyGAdService;
import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.util.MyPhone;

/**
 * Created by Nhut on 7/3/2017.
 */

public class DownloadTruyen extends AsyncTask<String, String, List<Truyen>> {
    private ProgressDialog pdialog;
    private Activity activity;
    private ComicvnServer.onDataWithChapterNameReadyCallback onDataReady;
    private List<List<String>> listTenChapterComics = new ArrayList<>();

    public DownloadTruyen(Activity activity,
                          ComicvnServer.onDataWithChapterNameReadyCallback onDataReady) {
        this.activity = activity;
        this.onDataReady = onDataReady;
    }

    @Override
    protected void onPreExecute() {
        pdialog = new ProgressDialog(activity);
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.setIndeterminate(true);
        pdialog.setTitle(R.string.comicvn_waitdownload_title);
        pdialog.setMessage(activity.getString(R.string.comicvn_waitdownload_message));
        pdialog.setCancelable(false);
        pdialog.show();
    }

    @Override
    protected List<Truyen> doInBackground(String... linkList) {
        List<Truyen> listTruyen = new ArrayList<>();
        try {
            for (String link : linkList) {
                final List<String> listChapterName = new ArrayList<>();
                final List<String> listdata = new ArrayList<>();
                final Document doc;
                doc = Jsoup.connect(link)
                        .userAgent("Android" + MyPhone.getOSVersion()).get();
                if (doc == null) {
                    listTruyen.add(new Truyen("", "", "", listdata));
                    continue;
                }
                // get comic title
                listdata.add(doc.getElementsByTag("title").text());
                // get thumbnail
                Elements elems = doc.getElementsByAttributeValue("class", "margin-top-10 manga-detail");
                if (elems != null)
                    listdata.add(elems.get(0).getElementsByTag("img").attr("src"));
                // get comic decription
                elems = doc.getElementsByAttributeValue("class", "margin-top-10 manga-summary");
                if (elems != null) listdata.add(elems.get(0).text());
                // get chapter hyperlink
//                Log.i("MyTag", "Down truyen : result " + elems.toString());
                elems = doc.getElementsByAttributeValue("class", "u84ho3");
//                Log.i("MyTag", "Down truyen : result " + elems.toString());
                if (elems != null) {
                    for (Element e : elems)
                        listdata.add(e.getElementsByTag("a").attr("abs:href"));
//                    Log.i("MyTag", "Down truyen : link " + link + " : " + elems.get(0).getElementsByTag("a").text());
                    for (Element e : elems)
                        listChapterName.add(e.getElementsByTag("a").text());
                }
                while (listdata.size() < 3) listdata.add("");
                listTruyen.add(new Truyen(listdata.remove(0), listdata.remove(0),
                    listdata.remove(0), listdata));
                listTenChapterComics.add(listChapterName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            publishProgress(activity.getString(R.string.networkerror));
            return null;
        }
        return listTruyen;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        pdialog.setMessage(values[0]);
    }

    @Override
    protected void onPostExecute(List<Truyen> list) {
        pdialog.dismiss();
        if (list != null) onDataReady.onDataReady(list, listTenChapterComics);
    }
}
