package comicreader.software.nhut.doc_truyen;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Comicvn.ComicvnServer;
import comicreader.software.nhut.doc_truyen.DO.Comicvn.DownloadComicPage;
import comicreader.software.nhut.doc_truyen.adapter.MyRVComicPageAdapter;
import comicreader.software.nhut.doc_truyen.util.MyFileIO;
import comicreader.software.nhut.doc_truyen.util.MyPhone;

import static comicreader.software.nhut.doc_truyen.util.MyPhone.setPortraitOrient;

public class ReadComicsActivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
    private int index;
    private List<String> listImagePagelink;
    private ImageView iv;
    private boolean isNext = true;
    private String tempFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comics);
        setTitle("");
        setPortraitOrient(this);
        iv = (ImageView) findViewById(R.id.comicpage_tv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNext) forward();
                else backward();
            }
        });
        /*
            1 : doc online :
                doc file tu cache
                thoat thi xoa cache
            2 : doc tu disk
                doc file tu thu muc
         */

        //1 : doc online :
        listImagePagelink = getIntent().getStringArrayListExtra(Constant.LINKLIST);
        if (listImagePagelink != null) {
            DownloadComicPage down = new DownloadComicPage(this,
                    getIntent().getStringExtra(Constant.INPUT),
                    new ComicvnServer.onComicReadyCallback() {
                @Override
                public void onDataReady(List<String> linkImagePageList) {
                    if (linkImagePageList != null && linkImagePageList.size() > 0) {
                        listImagePagelink.clear();
                        listImagePagelink.addAll(linkImagePageList);
                        index = -1;
                        iv.callOnClick();
                    }
                }
            });
            tempFolder = Long.toString(System.currentTimeMillis());
            down.execute(tempFolder, listImagePagelink);
        } else { //2 : doc tu disk
            listImagePagelink = getIntent().getStringArrayListExtra(Constant.PATHLIST);
            if (listImagePagelink == null) listImagePagelink = new ArrayList<>();
            index = -1;
            iv.callOnClick();
        }
    }

    @Override
    public void onBackPressed() {
        boolean temp = isNext;
        isNext = !temp;
        if (temp != isNext) Toast.makeText(this,
                R.string.toastReversedirection, Toast.LENGTH_SHORT).show();
        showSnackBar();
    }

    private void showSnackBar() {
        Snackbar sb = Snackbar.make(findViewById(R.id.activity_read_comics),
                getResources().getString(R.string.ngungdoctruyen), Snackbar.LENGTH_LONG);
        sb.setAction(getResources().getString(R.string.exit), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sb.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        backward();
        return false;
    }

    private void backward() {
        index--;
        Constant.countPage--;
        if (index < 0) {
            index = 0;
            return;
        }
        iv.setImageBitmap(MyFileIO.loadBitmapFromDisk(listImagePagelink.get(index)));
        System.out.println("back " + index);
    }

    private void forward() {
        index++;
        Constant.countPage += 2;
        if (index > listImagePagelink.size() - 1) {
            index--;
            showSnackBar();
            return;
        }
        iv.setImageBitmap(MyFileIO.loadBitmapFromDisk(listImagePagelink.get(index)));
        System.out.println("next " + index);
    }

    @Override
    protected void onDestroy() {
        Log.i("MyTag", "onDestroy");
        MyFileIO.clearFolderContent(getCacheDir().getAbsolutePath()
            + File.separator + tempFolder, true);
        super.onDestroy();
    }
}