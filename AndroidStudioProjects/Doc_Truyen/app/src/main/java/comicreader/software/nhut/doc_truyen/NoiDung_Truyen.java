package comicreader.software.nhut.doc_truyen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Comicvn.ComicvnServer;
import comicreader.software.nhut.doc_truyen.DO.Comicvn.DownloadChapter;
import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.adapter.MyRVComicChapterAdapter;
import comicreader.software.nhut.doc_truyen.util.MyFileIO;
import comicreader.software.nhut.doc_truyen.util.MyPhone;
import comicreader.software.nhut.doc_truyen.util.MyStringFormater;

import static android.view.View.GONE;
import static comicreader.software.nhut.doc_truyen.util.MyPhone.setPortraitOrient;

public class NoiDung_Truyen extends AppCompatActivity {
    private RecyclerView rv_listNoiDungTruyen;
    private TextView title_noidungtruyen;
    private List<Truyen> listChapter;
    private RecyclerView.Adapter adapter;
    private boolean taiLai;
    private Truyen truyen;
    private int input;
    public static final int TAILAI = 0;
    public static final int TAI = 1;
    public static final int MOTRUYENDALUU = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_truyen);

        setTitle("");
        setPortraitOrient(this);
        showAd();

        rv_listNoiDungTruyen = (RecyclerView) findViewById(R.id.rv_listNoiDungTruyen);
        title_noidungtruyen = (TextView) findViewById(R.id.title_noidungtruyen);

        title_noidungtruyen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (stopFlag) {
                    stopFlag = false;
                    downloadAll(title_noidungtruyen.getText().toString(), listChapter);
                } else {
                    stopAll();
                    Toast.makeText(NoiDung_Truyen.this, R.string.cancelbtn, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        this.listChapter = new ArrayList<>();
        truyen = (Truyen) getIntent().getExtras().getSerializable(Constant.TRUYEN);
        Log.i("MyTag", truyen.getName() + " " + truyen.getThumbnailLink()
                + " " + truyen.getLinkList().toString());
        input = getIntent().getIntExtra(Constant.INPUT, -1);
        rv_listNoiDungTruyen.setHasFixedSize(true);
        rv_listNoiDungTruyen.setLayoutManager(new LinearLayoutManager(this));
        rv_listNoiDungTruyen.setItemAnimator(new DefaultItemAnimator());
        title_noidungtruyen.setText(truyen.getName());
        taiLai = (input == TAILAI);
        if (input == TAILAI || input == TAI) startFromTaiChonTruyen_taiLaiTruyenDaLuu();
        else if (input == MOTRUYENDALUU) startFromTruyenDaLuu();
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

    private void startFromTruyenDaLuu() {
        Log.i("MyTag", "Truyen da luu");
        File f = new File(Constant.EXTDIR + File.separator + truyen.getName());
        final File chapterFolderArray[] = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File f = new File(dir.getAbsolutePath() + File.separator + name);
                if (f.isDirectory()) return true;
                else return false;
            }
        });
        if (chapterFolderArray == null || chapterFolderArray.length == 0) return;

        adapter = new RecyclerView.Adapter() {
            @Override
            public InternalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new InternalViewHolder(
                    NoiDung_Truyen.this.getLayoutInflater()
                        .inflate(R.layout.itemnoidungtruyen, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                InternalViewHolder iholder = (InternalViewHolder)holder;
                iholder.tv_downloadpause.setAlpha(0); iholder.tv_downloadpause.setClickable(false);
                iholder.pb.setAlpha(0); iholder.pb.setClickable(false);
                iholder.item_noidungtruyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File contentArray[] =
                                chapterFolderArray[position].listFiles(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String name) {
                                return (name.endsWith(".png"));
                            }
                        });
                        List<String> listFile = new ArrayList<String>();
                        if (contentArray != null && contentArray.length > 0) {
                            for (File fi : contentArray) listFile.add(fi.getAbsolutePath());
                        }
                        startActivity(new Intent(NoiDung_Truyen.this, ReadComicsActivity.class)
                            .putStringArrayListExtra(Constant.PATHLIST,
                                                        (ArrayList<String>) listFile)
                        );
                    }
                });
                iholder.image.setImageBitmap(MyFileIO.loadBitmapFromDisk(
                    chapterFolderArray[position].getAbsolutePath()
                            + File.separator + Truyen.THUMBNAILFILE
                ));
                iholder.tvtenChapter.setText(chapterFolderArray[position].getName());
            }

            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                ((InternalViewHolder)holder).image.setImageBitmap(null);
            }
            @Override
            public int getItemCount() {
                return chapterFolderArray.length;
            }

            class InternalViewHolder extends RecyclerView.ViewHolder {
                public ImageView image;
                public ProgressBar pb;
                public TextView tv_downloadpause;
                public TextView tvtenChapter;
                public LinearLayout item_noidungtruyen;

                public InternalViewHolder(View itemView) {
                    super(itemView);
                    image = (ImageView) itemView.findViewById(R.id.thumbnail_page_noidungtruyen);
                    tvtenChapter = (TextView) itemView.findViewById(R.id.tenchapter_noidungtruyen);
                    pb = (ProgressBar) itemView.findViewById(R.id.download_pb_noidungtruyen);
                    tv_downloadpause = (TextView) itemView.findViewById(R.id.tv_downloadpause_noidungtruyen);
                    item_noidungtruyen = (LinearLayout) itemView.findViewById(R.id.item_noidungtruyen);
                }
            }
        };
        rv_listNoiDungTruyen.setAdapter(adapter);
    }

    private void startFromTaiChonTruyen_taiLaiTruyenDaLuu() {
        Log.i("MyTag", "startFromTaiChonTruyen_taiLaiTruyenDaLuu");
        DownloadChapter down = new DownloadChapter(this, new ComicvnServer.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Truyen> listChapter) {
                if (listChapter != null && listChapter.size() > 0) {
                    for (int i = 0, size = listChapter.size(); i < size; i++)
                        NoiDung_Truyen.this.listChapter.add(listChapter.get(i));
                    Log.i("MyTag", NoiDung_Truyen.this.listChapter.toString());
                    adapter = new MyRVComicChapterAdapter(NoiDung_Truyen.this, NoiDung_Truyen.this.listChapter);
                    rv_listNoiDungTruyen.setAdapter((MyRVComicChapterAdapter) adapter);
                }
            }
        });
        down.execute(MyFileIO.convertToStringArrray(truyen.getLinkList()));
    }

    public String getTenTruyen() {
        return title_noidungtruyen.getText().toString();
    }

    public Truyen getTruyen() {
        return this.truyen;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (input == MOTRUYENDALUU) return false;
        getMenuInflater().inflate(R.menu.menu_noidungtruyen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mnuTaihet_noidungtruyen:
                String stop = getResources().getString(R.string.mnuDunghet);
                String down = getResources().getString(R.string.mnuTaihet);
                if (item.getTitle().toString().equals(stop)) {
                    item.setTitle(down);
                    stopAll();
                } else {
                    item.setTitle(stop);
                    stopFlag = false;
                    downloadAll(title_noidungtruyen.getText().toString(), listChapter);
                }
                break;
        }
        return true;
    }

    private void stopAll() {
        stopFlag = true;
    }

    private void downloadAll(final String tenComic, final List<Truyen> listChapter) {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(this.getResources()
                .getString(R.string.comicvn_waitdownload_title) + " " + tenComic);
        pDialog.setIndeterminate(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setMax(listChapter.size());
        pDialog.setProgress(0);
        pDialog.setMessage(this.getResources().getString(R.string.comicvn_waitdownload_title)
                + " " + listChapter.get(0).getName());
        pDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadAllBG(tenComic, listChapter, taiLai);
            }
        }).start();
    }
    private ProgressDialog pDialog;

    private void downloadAllBG(String tenComic, List<Truyen> listChapter) {
        downloadAllBG(tenComic, listChapter, false);
    }

    private void downloadAllBG(String tenComic, List<Truyen> listChapter, boolean taiLai) {
        File f = new File(Constant.EXTDIR + File.separator + tenComic);
        f.mkdirs();
        try {
            MyFileIO.saveBitmap(new URL(truyen.getThumbnailLink()).openStream(),
                f.getAbsoluteFile() + File.separator + Truyen.THUMBNAILFILE,
                    Constant.THUMBNAILSIZE, Constant.THUMBNAILSIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyFileIO.saveObjectInstance(Constant.EXTDIR + File.separator
            + tenComic + File.separator + Truyen.TRUYENINFO, truyen);
        Point p = MyPhone.getScreenSize(this);
        if (f.exists())
            for (final Truyen chapter : listChapter) {
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.setMessage(NoiDung_Truyen.this.getResources()
                            .getString(R.string.comicvn_waitdownload_title)
                                + " " + chapter.getName());
                    }
                });
                File chapterFolder = new File(f.getAbsolutePath() + File.separator
                                                                        + chapter.getName());
                chapterFolder.mkdirs();
                List<String> linkImageList = chapter.getLinkList();
                Log.i("MyTag", "list Image link " + linkImageList.size()
                        + " pics " + linkImageList.toString() );
                for (int i = 0, size = linkImageList.size(); i < size; i++) {
                    File writtenImage = new File(
                        chapterFolder.getAbsolutePath() + File.separator
                            + Integer.toString(i) + ".png");
                    Log.i("MyTag", "DownloadALL : " + writtenImage.getAbsolutePath());
                    if (writtenImage.exists())
                        if(taiLai) writtenImage.delete();
                        else continue;
                    try {
                        MyFileIO.saveBitmapToCache(new URL(linkImageList.get(i)).openStream(),
                                writtenImage, p.x, p.y);
                        if (i == 0) //save thumbnail
                            MyFileIO.saveBitmap(new URL(linkImageList.get(i)).openStream(),
                                chapterFolder.getAbsolutePath() + File.separator
                                + Truyen.THUMBNAILFILE, Constant.THUMBNAILSIZE,
                                    Constant.THUMBNAILSIZE);
                        Log.i("MyTag", "stopFlag " + Boolean.toString(stopFlag));
                        if (stopFlag) return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.incrementProgressBy(1);
                    }
                });
            }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        if Toast.makeText(this, R.string.continueDownloadwarning, Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }


    private boolean stopFlag = false;
    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }

    public boolean getStopFlag() {
        return stopFlag;
    }
//    @Override
//    protected void onStop() {
//        StopFlag = true;
//    }
//    @Override
//    protected void onStart() {
//        stopFlag = false;
//    }
}
