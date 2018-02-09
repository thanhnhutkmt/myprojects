package comicreader.software.nhut.doc_truyen.DO.Comicvn;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.util.MyPhone;

import static org.jsoup.helper.HttpConnection.DEFAULT_UA;

/**
 * Created by Nhut on 7/3/2017.
 */

public class ComicvnServer {
    public static String SERVERNAME = "comicvn.net";
    public static void getTruyen(final Activity activity, final String serverLink,
                                 final onComicReadyCallback onDataReady) {
        Log.i("MyTag","getTruyen : link " + serverLink);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<String> listLinkTruyen = new ArrayList<>();
                try {
                    final Document doc = Jsoup.connect(serverLink)
                        .userAgent("Android" + MyPhone.getOSVersion()).get();
                    if (doc == null) return;
                    Element ei = doc.getElementById("txtarea");
                    for (String part : ei.toString().split("\""))
                        if (part.startsWith("http")) {
                            String temppart = part;
                            if (part.contains(" ")) temppart = temppart.split(" ")[0];
//                            if (part.contains("imgmax")) temppart = temppart.split("imgmax")[0];
                            listLinkTruyen.add(temppart);
                            Log.i("MyTag", "link chuong " + temppart);
                        }
                    onDataReady.onDataReady(listLinkTruyen);
                } catch (Exception e) {
                    Log.w("MyTag", activity.getResources().getString(R.string.erroropenchapter));
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, R.string.erroropenchapter, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public static void getTruyen(final String serverLink, final Activity activity,
                                 final onDataWithChapterNameReadyCallback onDataReady) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<String> listLinkTruyen = new ArrayList<>();
                try {
                    final Document doc;
                    doc = Jsoup.connect(serverLink)
                            .userAgent("Android" + MyPhone.getOSVersion()).get();
                    if (doc == null) return;
                    Elements elems = doc.getElementsByAttributeValue("class", "img");
                    if (elems != null)
                        for (Element e : elems)
                            listLinkTruyen.add(e.attr("abs:href"));
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DownloadTruyen down = new DownloadTruyen(activity, onDataReady);
                            down.execute(getArray(listLinkTruyen));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String[] getArray(List<String> list) {
        String [] array = new String[list.size()];
        for (int i = 0, size = list.size(); i < size; i++)
            array[i] = list.get(i);
        return array;
    }

    public interface onDataWithChapterNameReadyCallback {
        public void onDataReady(List<Truyen> truyen, List<List<String>> listTenChapter);
    }

    public interface onDataReadyCallback {
        public void onDataReady(List<Truyen> truyen);
    }

    public interface onComicReadyCallback {
        public void onDataReady(List<String> truyen);
    }

    public interface onChapterNameReadyCallback {
        public void onDataReady(List<String> listTenChapter);
    }
}