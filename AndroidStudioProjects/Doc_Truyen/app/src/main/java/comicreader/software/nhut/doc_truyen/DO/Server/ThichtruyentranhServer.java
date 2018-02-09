package comicreader.software.nhut.doc_truyen.DO.Server;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.util.MyPhone;

/**
 * Created by Nhut on 7/18/2017.
 */

public class ThichtruyentranhServer extends ComicServerDO {
    public static String SERVERNAME = "thichtruyentranh.com";

    @Override
    public String toString() {
        return "ComicServer{" + SERVERNAME + '}';
    }

    public void getTruyen(final Activity activity, final String serverLink,
                                 final onDataWithChapterNameReadyCallback onDataReady) {
        Log.i("MyTag","getTruyen : link " + serverLink);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Truyen> listTruyen = new ArrayList<>();
                try {
                    final Document doc = Jsoup.connect(serverLink)
                            .userAgent("Android" + MyPhone.getOSVersion()).get();
                    if (doc == null) throw new Exception("Server return null");

                    Elements es = doc.getElementsByAttributeValue("class", "ulListruyen").get(0)
                            .getElementsByTag("li");
                    if (es == null || es.size() == 0) throw new Exception();
                    for (Element e : es) {
                        Element te = e.getElementsByTag("a").get(0);
                        //get link
                        String link = te.attr("abs:href");
                        //get ten truyen
                        String ten = te.attr("title");
                        //get thumbnail image
                        String thumbnail = te.getElementsByTag("img").get(0).attr("src");
                        listTruyen.add(new Truyen(ten, thumbnail, "", link));
                        if (listTruyen.size() > 30) break;
                    }
                    onDataReady.onDataReady(listTruyen, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("MyTag", activity.getResources().getString(R.string.erroropenchapter));
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,
                                    R.string.erroropenchapter, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void getTruyenInfo(final Activity activity, final Truyen truyen,
                                     final onComicReadyCallback onComicReady) {
        Log.i("MyTag","getTruyenInfo : " + truyen.getLink());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Document doc = Jsoup.connect(truyen.getLink().get(0))
                            .userAgent("Android" + MyPhone.getOSVersion()).get();
                    if (doc == null) throw new Exception("Server return null");
                    Elements es;
                    //get next pages if any
                    try {
                        es = doc.getElementsByAttributeValue("class", "paging")
                                .get(0).getElementsByTag("a");
                        for (Element e : es) truyen.getLink().add(e.attr("abs:href"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i("MyTag", "truyen link page: " + truyen.getLink().toString());
                    //get content page 0
                    es = doc.getElementsByAttributeValue("class", "ul_listchap");
                    int index = (es.size() > 1) ? 1 : 0;
                    es = es.get(index).getElementsByTag("a");
                    if (es == null || es.size() == 0) throw new Exception();
                    Log.i("MyTag", "Get by class col_chap tenChapter : " + es.toString());
                    for (Element e : es) {
                        Log.i("MyTag", "item by class col_chap tenChapter : " + e.toString());
                        String chapterlink = e.attr("abs:href");
                        Log.i("MyTag", "item by tag a href: " + chapterlink);
                        String ten = e.text();
                        Log.i("MyTag", "item by tag a text: " + ten);
                        truyen.getTenChapterList().add(ten);
                        truyen.getLinkList().add(chapterlink);
                    }
                    onComicReady.onDataReady(truyen);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("MyTag", activity.getResources().getString(R.string.erroropenchapter));
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,
                                    R.string.erroropenchapter, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void getChapterContent(final Activity activity, final String linkChapter,
                                         final onLinkPageReadyCallback onLinkPageReadyCallback) {
        Log.i("MyTag","getChapterContent : " + linkChapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Document doc = Jsoup.connect(linkChapter)
                            .userAgent("Android" + MyPhone.getOSVersion()).get();
                    if (doc == null) throw new Exception("Server return null");
//                    int i = 0;
//                    for (Element e : doc.getElementsByTag("script")) {
//                        Log.i("MyTag",i + " url get " + e.toString());
//                        i++;
//                    }
                    String imgarray = doc.getElementsByTag("script").get(6).toString();
//                    Log.i("MyTag","url get " + imgarray);
                    List<String> listUrl = new ArrayList<String>();
                    for (String s : imgarray.split("src=\"")) {
                        if (s.startsWith("http")) {
                            String url = s.split("\" alt=\"")[0];
                            Log.i("MyTag","getChapterContent : img src " + url);
                            listUrl.add(url);
                        }
                    }
                    onLinkPageReadyCallback.onDataReady(listUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("MyTag", activity.getResources().getString(R.string.erroropenchapter));
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,
                                    R.string.erroropenchapter, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
