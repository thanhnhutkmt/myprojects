package comicreader.software.nhut.doc_truyen.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comicreader.software.nhut.doc_truyen.Constant;
import comicreader.software.nhut.doc_truyen.DO.Comicvn.ComicvnServer;
import comicreader.software.nhut.doc_truyen.DO.Server.ComicServerDO;
import comicreader.software.nhut.doc_truyen.DO.Server.ComicServerDOAction;
import comicreader.software.nhut.doc_truyen.DO.Server.HamtruyenServer;
import comicreader.software.nhut.doc_truyen.DO.Server.ThichtruyentranhServer;
import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.ReadComicsActivity;

/**
 * Created by Nhut on 7/2/2017.
 */

public class MyRVSimpleAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Truyen> listChapter;
    private List<String> listChapterName;
    private Truyen truyen;

    public MyRVSimpleAdapter(Activity activity, List<Truyen> listChapter) {
        this.activity = activity;
        this.listChapter = listChapter;
    }

    public MyRVSimpleAdapter(Activity activity, Truyen truyen, List<String> listChapterName) {
        this.activity = activity;
        this.truyen = truyen;
        this.listChapter = null;
        this.listChapterName = listChapterName;
        Log.i("MyTag", "MyRVSimpleAdapter : quick mode : listchapter "
                + this.listChapterName.toString());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(
                R.layout.itemsimplechapter, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TextView name = ((MyViewHolder)holder).tv_chapter;
        name.setText(getTenChuong(position));
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listChapter != null)
                    activity.startActivity(new Intent(activity, ReadComicsActivity.class)
                    .putStringArrayListExtra(Constant.LINKLIST,
                    (ArrayList<String>) listChapter.get(position).getLinkList()));
                else
                    loadChuongTruyen(position, name.getText().toString());
            }
        });
    }

    private void loadChuongTruyen(int position, final String chapterName) {
        if (truyen.getLink().size() == 0)
            ComicvnServer.getTruyen(activity, truyen.getLinkList().get(position),
            new ComicvnServer.onComicReadyCallback() {
                @Override
                public void onDataReady(List<String> linkImageList) {
                    Log.i("MyTag", "ComicvnServer link page " + linkImageList.toString());
                    activity.startActivity(new Intent(activity, ReadComicsActivity.class)
                        .putStringArrayListExtra(Constant.LINKLIST,
                            (ArrayList<String>) linkImageList)
                        .putExtra(Constant.INPUT, chapterName)
                    );
                }
            });
        else {
            final ComicServerDO server = ComicServerDO.newInstance(truyen.getLink().get(0));
            server.getChapterContent(activity, truyen.getLinkList().get(position),
                new HamtruyenServer.onLinkPageReadyCallback() {
                    @Override
                    public void onDataReady(List<String> listImageLink) {
                        Log.i("MyTag", server + " link page " + listImageLink.toString());
                        activity.startActivity(new Intent(activity, ReadComicsActivity.class)
                            .putStringArrayListExtra(Constant.LINKLIST,
                                (ArrayList<String>) listImageLink)
                            .putExtra(Constant.INPUT, chapterName)
                        );
                    }
                });
        }
    }

    private String getTenChuong(int position) {
        return (listChapter != null) ?
                    listChapter.get(position).getName() :
                    listChapterName.get(position);
    }

    @Override
    public int getItemCount() {
        return (listChapter != null) ? listChapter.size() :
            (listChapterName != null) ? listChapterName.size() : -1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_chapter;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_chapter = (TextView) itemView.findViewById(R.id.tv_chaptername_itemsimplechapter);
        }
    }
}
