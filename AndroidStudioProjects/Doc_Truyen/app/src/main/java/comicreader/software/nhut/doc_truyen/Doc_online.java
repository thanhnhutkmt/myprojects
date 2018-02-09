package comicreader.software.nhut.doc_truyen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Collections;
import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Comicvn.ComicvnServer;
import comicreader.software.nhut.doc_truyen.DO.Comicvn.DownloadChapter;
import comicreader.software.nhut.doc_truyen.DO.Server.A3MangaServer;
import comicreader.software.nhut.doc_truyen.DO.Server.ComicServerDO;
import comicreader.software.nhut.doc_truyen.DO.Server.ComicServerDOAction;
import comicreader.software.nhut.doc_truyen.DO.Server.HamtruyenServer;
import comicreader.software.nhut.doc_truyen.DO.Server.ThichtruyentranhServer;
import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.adapter.MyRVSimpleAdapter;

import static comicreader.software.nhut.doc_truyen.util.MyPhone.setPortraitOrient;

public class Doc_online extends AppCompatActivity {
    private RecyclerView rv_listNoiDungTruyen;
    private TextView title_noidungtruyen;
    private int pageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_truyen);
        setTitle("");
        showAd();
        setPortraitOrient(this);
        final Truyen truyen = (Truyen) getIntent().getExtras().getSerializable(Constant.TRUYEN);
        List<String> listTenChapter = getIntent().getExtras().getStringArrayList(Constant.LINKLIST);

        rv_listNoiDungTruyen = (RecyclerView) findViewById(R.id.rv_listNoiDungTruyen);
        rv_listNoiDungTruyen.setHasFixedSize(true);
        rv_listNoiDungTruyen.setLayoutManager(new LinearLayoutManager(this));
        rv_listNoiDungTruyen.setItemAnimator(new DefaultItemAnimator());
        title_noidungtruyen = (TextView) findViewById(R.id.title_noidungtruyen);
        title_noidungtruyen.setText(truyen.getName());
        title_noidungtruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (truyen != null && truyen.getLink().size() > 1
                        && truyen.getLink().get(0).contains(ThichtruyentranhServer.SERVERNAME)) {
                    pageNumber = (pageNumber >= truyen.getLink().size() - 1) ? 0 : pageNumber + 1;
                    Truyen truyenpage = new Truyen();
                    truyenpage.getLink().add(truyen.getLink().get(pageNumber));
                    Log.i("MyTag", "Change truyen to page " + truyenpage.getLink().get(0));
                    loadTruyenContent(truyenpage);
                }
            }
        });

        if (truyen != null && truyen.getLink().size() > 0
                && truyen.getLink().get(0).length() > 0) {
            Log.i("MyTag", "new Quick mode doc online");
            loadTruyenContent(truyen);
        } else if (truyen != null && listTenChapter == null) { // normal mode
            DownloadChapter down = new DownloadChapter(this,
                    new ComicvnServer.onDataReadyCallback() {
                @Override
                public void onDataReady(List<Truyen> listChapter) {
                    rv_listNoiDungTruyen.setAdapter(
                        new MyRVSimpleAdapter(Doc_online.this, listChapter));
                }
            });
            String linkChapterList[] = new String[truyen.getLinkList().size()];
            for (int i = 0; i < truyen.getLinkList().size(); i++)
                linkChapterList[i] = truyen.getLinkList().get(i);
            down.execute(linkChapterList);
        } else if (truyen != null && listTenChapter != null) { // quick mode
            Log.i("MyTag", "Quick mode doc online");
            Log.i("MyTag", "chapter " + listTenChapter.toString());
            if (listTenChapter.size() >= 2
                && listTenChapter.get(0).compareTo(listTenChapter.get(1)) == 1) {
                Collections.reverse(listTenChapter);
                truyen.reverselinkList();
            }
            Log.i("MyTag", "chapter " + listTenChapter.toString());
            rv_listNoiDungTruyen.setAdapter(
                    new MyRVSimpleAdapter(Doc_online.this, truyen, listTenChapter));
        }
    }

    private void loadTruyenContent(Truyen truyen) {
        ComicServerDO server = ComicServerDO.newInstance(truyen.getLink().get(0));
        Log.i("MyTag", "loadTruyenContent " + truyen.getLink().get(0));
        Log.i("MyTag", "server HamTruyen " + (server instanceof HamtruyenServer));
        Log.i("MyTag", "server A3Manga " + (server instanceof A3MangaServer));
        server.getTruyenInfo(this, truyen,
                new ComicServerDOAction.onComicReadyCallback() {
            @Override
            public void onDataReady(final Truyen truyen) {
                if (truyen.getTenChapterList().size() >= 2 && truyen.getTenChapterList().get(0)
                        .compareTo(truyen.getTenChapterList().get(1)) == 1)
                    truyen.reverselinkList();
                Log.i("MyTag", "loadTruyenContent chapter "
                        + truyen.getTenChapterList().toString());
                Log.i("MyTag", "loadTruyenContent chapterlink "
                        + truyen.getLinkList().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rv_listNoiDungTruyen.setAdapter(new MyRVSimpleAdapter(
                                Doc_online.this, truyen, truyen.getTenChapterList()));
                    }
                });
            }
        });
    }

    private void showAd() {
        AdRequest.Builder b = new AdRequest.Builder();
        AdView adv = (AdView) findViewById(R.id.adView_noidungtruyen);
        if (Constant.TESTING) {
            b.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
            b.addTestDevice(Constant.myTestID);
        }
        adv.loadAd(b.build());
    }
}
