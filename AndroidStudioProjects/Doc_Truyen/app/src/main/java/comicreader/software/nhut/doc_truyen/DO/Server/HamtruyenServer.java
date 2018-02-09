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
import java.util.Map;

import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.util.MyPhone;

/**
 * Created by Nhut on 7/18/2017.
 */

public class HamtruyenServer extends ComicServerDO {
    public static String SERVERNAME = "hamtruyen";

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
                    Elements es = doc.getElementsByAttributeValue("class", "listtruyen").get(0)
                            .getElementsByAttributeValue("class", "item_truyennendoc");
                    if (es == null || es.size() == 0) throw new Exception();
                    for (Element e : es) {
                        //get link
                        String link = e.getElementsByTag("a").get(0).attr("abs:href");
                        //get ten truyen
                        String ten = e.getElementsByAttributeValue("class", "tentruyen_slide").text();
                        //get thumbnail image
                        String thumbnail = e.getElementsByTag("img").get(0).attr("src");
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
                            Toast.makeText(activity, R.string.erroropenchapter, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void getTruyenInfo(final Activity activity, final Truyen truyen,
                                     final onComicReadyCallback onComicReady) {
        Log.i("MyTag","HamTruyen getTruyenInfo : " + truyen.getLink());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Document doc = Jsoup.connect(truyen.getLink().get(0))
                            .userAgent("Android" + MyPhone.getOSVersion()).get();
                    if (doc == null) throw new Exception("Server return null");
                    Log.i("MyTag", "Get truyen info");
                    Elements es = doc.getElementsByAttributeValue("class", "col_chap tenChapter");
                    Log.i("MyTag", "Get truyen info " + es.toString());
                    if (es == null || es.size() == 0) throw new Exception();
                    es.remove(0);
                    Log.i("MyTag", "Get by class col_chap tenChapter : " + es.toString());
                    for (Element e : es) {
                        Log.i("MyTag", "item by class col_chap tenChapter : " + e.toString());
                        Element te = e.getElementsByTag("a").get(0);
                        Log.i("MyTag", "item by tag a : " + te.toString());
                        String chapterlink = te.attr("abs:href");
                        Log.i("MyTag", "item by tag a href: " + chapterlink);
                        String ten = te.text();
                        Log.i("MyTag", "item by tag a text: " + ten);
                        truyen.getTenChapterList().add(ten);
                        truyen.getLinkList().add(chapterlink);
                    }
                    onComicReady.onDataReady(truyen);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("MyTag", "getTruyenInfo " + activity.getResources().getString(R.string.erroropenchapter));
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
                    Elements es = doc.getElementsByAttributeValue("class", "content_chap")
                        .get(0).getElementsByTag("img");
                    Log.i("MyTag","getChapterContent : es " + es.toString());
                    if (es == null || es.size() == 0) throw new Exception();
                    List<String> listUrl = new ArrayList<String>();
                    for (Element e : es) {
                        Log.i("MyTag","getChapterContent : img " + e.toString());
                        String url = e.attr("src");
                        Log.i("MyTag","getChapterContent : img src " + url);
                        listUrl.add(url);
                    }
                    onLinkPageReadyCallback.onDataReady(listUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("MyTag", "getChapterContent " + activity.getResources().getString(R.string.erroropenchapter));
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
}
