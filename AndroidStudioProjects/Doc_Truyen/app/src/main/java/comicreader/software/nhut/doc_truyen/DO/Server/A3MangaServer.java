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

public class A3MangaServer extends ComicServerDO {
    public static String SERVERNAME = "a3manga.com";

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
                    Elements es = doc.getElementsByAttributeValue("class", "comic-img");
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
        Log.i("MyTag","A3Manga getTruyenInfo : " + truyen.getLink());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Document doc = Jsoup.connect(truyen.getLink().get(0))
                            .userAgent("Android" + MyPhone.getOSVersion()).get();
                    if (doc == null) throw new Exception("Server return null");
                    Elements es = doc.getElementsByTag("tbody")
                            .get(0).getElementsByTag("a");
                    if (es == null || es.size() == 0) throw new Exception();
                    Log.i("MyTag", "Get by tag tbody then tag a : " + es.toString());
                    for (Element e : es) {
                        Log.i("MyTag", "item by tag tbody then tag a : " + e.toString());
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
                    Log.w("MyTag", "A3Manga getTruyenInfo " +activity.getResources().getString(R.string.erroropenchapter));
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
                    Elements es = doc.getElementsByAttributeValue("class",
                            "text-center view-chapter").get(0).getElementsByTag("img");
                    List<String> listUrl = new ArrayList<String>();
                    for (Element e : es) listUrl.add(e.attr("src"));
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
