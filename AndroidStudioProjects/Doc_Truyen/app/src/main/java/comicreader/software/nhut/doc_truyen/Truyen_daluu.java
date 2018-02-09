package comicreader.software.nhut.doc_truyen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Comicvn.ComicvnServer;
import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.adapter.MyRVComicAdapter;
import comicreader.software.nhut.doc_truyen.util.MyFileIO;

import static comicreader.software.nhut.doc_truyen.util.MyPhone.setPortraitOrient;

public class Truyen_daluu extends AppCompatActivity {
    private RecyclerView rv_listTruyen;
    private TextView title_danhsachtruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_truyen);
        setTitle("");
        setPortraitOrient(this);
        showAd();
        startActivity(new Intent(this, Nativeexpressad.class));
        rv_listTruyen = (RecyclerView) findViewById(R.id.rv_listNoiDungTruyen);
        rv_listTruyen.setAdapter(
            new MyRVComicAdapter(this, loadTruyenFromDisk(), MyRVComicAdapter.TRUYENDALUU));
        rv_listTruyen.setHasFixedSize(true);
        rv_listTruyen.setLayoutManager(new LinearLayoutManager(this));
        rv_listTruyen.setItemAnimator(new DefaultItemAnimator());
        title_danhsachtruyen = (TextView) findViewById(R.id.title_noidungtruyen);
        title_danhsachtruyen.setText(getString(R.string.danhsachtruyen));
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

    private List<Truyen> loadTruyenFromDisk() {
        List<Truyen> list = new ArrayList<>();
        File f = new File(Constant.EXTDIR);
        File truyenArrayFolder[] = f.listFiles();
        if (truyenArrayFolder != null)
            for (int i = 0; i < truyenArrayFolder.length; i++) {
                File chapterFileList[] = truyenArrayFolder[i].listFiles();
                String chapterFoldersArray[] = new String[chapterFileList.length];
                if (chapterFileList != null) {
                    for (int ii = 0; ii < chapterFileList.length; ii++)
                        chapterFoldersArray[ii] = chapterFileList[ii].getAbsolutePath();
                }
                list.add(new Truyen(truyenArrayFolder[i].getName(),
                    truyenArrayFolder[i].getAbsolutePath() + File.separator + Truyen.THUMBNAILFILE,
                    "", MyFileIO.convertToArrayList(chapterFoldersArray)));
            }
        return list;
    }
}
