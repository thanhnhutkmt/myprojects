package comicreader.software.nhut.doc_truyen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Comicvn.ComicvnServer;
import comicreader.software.nhut.doc_truyen.DO.Server.A3MangaServer;
import comicreader.software.nhut.doc_truyen.DO.Server.ComicServerDO;
import comicreader.software.nhut.doc_truyen.DO.Server.ComicServerDOAction;
import comicreader.software.nhut.doc_truyen.DO.Server.HamtruyenServer;
import comicreader.software.nhut.doc_truyen.DO.Server.ThichtruyentranhServer;
import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.adapter.MyRVComicAdapter;

import static comicreader.software.nhut.doc_truyen.util.MyPhone.setPortraitOrient;

public class Select_truyen extends AppCompatActivity {
    private RecyclerView rv_listSelectTruyen;
    private MyRVComicAdapter adapter;
    boolean finishSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_truyen);
        setTitle("");
        setPortraitOrient(this);
        showAd();
        startActivity(new Intent(this, Nativeexpressad.class));
        getTruyen();
        rv_listSelectTruyen = (RecyclerView) findViewById(R.id.rv_listTruyen);
        rv_listSelectTruyen.setHasFixedSize(true);
        rv_listSelectTruyen.setLayoutManager(new LinearLayoutManager(this));
        rv_listSelectTruyen.setItemAnimator(new DefaultItemAnimator());
    }

    private void getTruyen() {
        final String serverLink = getIntent().getStringExtra(Constant.TRUYENSERVER);
        Log.i("MyTag", "serverLink " + serverLink);
        if (serverLink.contains(ComicvnServer.SERVERNAME))
            ComicvnServer.getTruyen(serverLink, this,
                new ComicvnServer.onDataWithChapterNameReadyCallback() {
                    @Override
                    public void onDataReady(List<Truyen> listTruyen,
                                            List<List<String>> listTenChapterComics) {
                        adapter = new MyRVComicAdapter(Select_truyen.this, listTruyen,
                                MyRVComicAdapter.CHONTRUYEN, listTenChapterComics);
                        int i = 0;
                        for (List<String> l : listTenChapterComics)
                            Log.i("MyTag", "select truyen : result " + i++ + " : " + l.toString());
                        rv_listSelectTruyen.setAdapter(adapter);
                    }
                }
            );
        else {
            Log.i("MyTag", serverLink);
            final ComicServerDO server = ComicServerDO.newInstance(serverLink);
            Log.i("MyTag", "server HamTruyen " + (server instanceof HamtruyenServer));
            Log.i("MyTag", "server A3Manga " + (server instanceof A3MangaServer));

            server.getTruyen(this, serverLink,
                new ComicServerDOAction.onDataWithChapterNameReadyCallback() {
                    @Override
                    public void onDataReady(List<Truyen> listTruyen, List<List<String>> listTenChapter) {
                        Log.i("MyTag", server + ".getTruyen() done");
                        int i = 0;
                        for (Truyen t : listTruyen)
                            Log.i("MyTag", "select truyen : result " + i++ + " : " + t);
                        adapter = new MyRVComicAdapter(Select_truyen.this, listTruyen,
                                MyRVComicAdapter.CHONTRUYEN);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rv_listSelectTruyen.setAdapter(adapter);
                            }
                        });
                    }
                });
        }
    }

    private void showAd() {
        AdRequest.Builder b = new AdRequest.Builder();
        AdView adv = (AdView) findViewById(R.id.adView_selectruyen);
        if (Constant.TESTING) {
            b.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
            b.addTestDevice(Constant.myTestID);
        }
        adv.loadAd(b.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chontruyen, menu);
        MenuItem mnuSearch=menu.findItem(R.id.search_menu_truyen);
        android.widget.SearchView searchView= (android.widget.SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter == null) return false;
                final ProgressDialog pd = new ProgressDialog(Select_truyen.this);
                final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (msg.arg1 == 1) {
                            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pd.setIndeterminate(true); // chi so % khong thay doi
                            pd.setMessage(Select_truyen.this.getResources()
                                    .getString(R.string.comicvn_waitdownload_message));
//                            pd.setCancelable(false);
                            pd.show(); //Log.i("My Tag", System.currentTimeMillis() + " : Show pd");
                        } else if (msg.arg1 == 2) {
                            pd.dismiss();
                            //Log.i("My Tag", System.currentTimeMillis() + " : Close pd");
                        }
                        return false;
                    }
                });
                new Thread() {
                    public void run() {
                        Message msg = new Message();
                        msg.arg1 = 1; // show progress dialog
                        handler.sendMessage(msg);
                    }}.start();
                //Log.i("My Tag", System.currentTimeMillis() + " : set false finishsearching");
                finishSearching = false;
                new Thread() {
                    public void run() {
                        Message msg = new Message();
                        msg.arg1 = 2; // close progress dialog
                        int maxwait = 0;
                        while (!finishSearching) {
                            //insert this sleep to increase stability
                            try {
                                sleep(500);
                                if (maxwait++ > 6) break;
                            } catch (Exception e) {e.printStackTrace();}
                        }
                        handler.sendMessage(msg);
//                        Log.i("My Tag", "close pdialog");
                    }}.start();
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() == 0 && adapter != null) adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.search_menu_truyen:

                break;
        }
        return true;
    }

    public void setFinishSearching(boolean finishSearching) {
        this.finishSearching = finishSearching;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyCacheThumbnailService.class));
    }
}
