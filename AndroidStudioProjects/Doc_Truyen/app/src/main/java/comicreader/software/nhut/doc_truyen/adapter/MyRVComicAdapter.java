package comicreader.software.nhut.doc_truyen.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comicreader.software.nhut.doc_truyen.Constant;
import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.Doc_online;
import comicreader.software.nhut.doc_truyen.MyCacheThumbnailService;
import comicreader.software.nhut.doc_truyen.NoiDung_Truyen;
import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.Select_truyen;
import comicreader.software.nhut.doc_truyen.Truyen_daluu;
import comicreader.software.nhut.doc_truyen.util.MyFileIO;
import comicreader.software.nhut.doc_truyen.util.MyStringFormater;

/**
 * Created by Nhut on 7/02/2017.
 */

public class MyRVComicAdapter extends RecyclerView.Adapter<MyRVComicAdapter.MyViewHolder> {
    private List<Truyen> list;
    private Activity activity;
    private int loai;
    public static final int TRUYENDALUU = 1;
    public static final int CHONTRUYEN = 0;
    private String tempFolderName;
    private List<List<String>> listChapterNameComics;

    /**
     * @param loai 0 : chon truyen; 1 : truyen da luu
     */
    public MyRVComicAdapter(Activity activity, List<Truyen> list, int loai) {
        init(activity, list, loai);
    }

    private void init(Activity activity, List<Truyen> list, int loai) {
        this.list = list;
        this.activity = activity;
        this.loai = loai;
        this.mapImageIndex = new HashMap<>();
        listActiveViewHolder = new ArrayList<>();
        if (loai == CHONTRUYEN) {
            tempFolderName = Long.toString(System.currentTimeMillis());
            new File(Constant.CACHEDIR + File.separator + tempFolderName).mkdirs();
            List<String> listThumbnail = new ArrayList<>();
            for (Truyen t : list) listThumbnail.add(t.getThumbnailLink());
            MyCacheThumbnailService.activity = activity;
            MyCacheThumbnailService.listAComicViewHolder = listActiveViewHolder;
            activity.startService(new Intent(activity, MyCacheThumbnailService.class)
                    .putExtra(Constant.DOWNLOADPATH, tempFolderName)
                    .putStringArrayListExtra(Constant.LINKLIST, (ArrayList<String>) listThumbnail));
        }
    }

    public MyRVComicAdapter(Activity activity, List<Truyen> list, int loai,
                                                    List<List<String>> listChapterNameComics) {
        this.listChapterNameComics = listChapterNameComics;
        init(activity, list, loai);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(activity.getLayoutInflater()
            .inflate(R.layout.itemtruyen, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.position = position;
        if (mapImageIndex.size() > 0) {
            Integer temp = mapImageIndex.get(position);
            if (temp != null) holder.position = temp;
        }
        if (listActiveViewHolder != null) listActiveViewHolder.add(holder);

        final Truyen truyen = list.get(position); // item is a comic info

        holder.tvtentruyen.setText(truyen.getName());
        holder.btndownload.setText((loai==TRUYENDALUU) ? activity.getString(R.string.redownloadbtn)
                                                       : holder.btndownload.getText().toString());
        holder.btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if is in truyen da luu act : assume truyeninfo ton tai
                if (loai == TRUYENDALUU) {
                    Truyen t = (Truyen)MyFileIO.loadObjectInstance(Constant.EXTDIR
                            + File.separator + truyen.getName() + File.separator
                            + Truyen.TRUYENINFO);
                    if (t == null) {
                        Toast.makeText(activity,
                                R.string.loadTruyenInfoError, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    activity.startActivity(new Intent(activity, NoiDung_Truyen.class)
                        .putExtra(Constant.TRUYEN, t)
                        .putExtra(Constant.INPUT, NoiDung_Truyen.TAILAI));
                }
                if(loai == CHONTRUYEN) {
                    activity.startActivity(new Intent(activity, NoiDung_Truyen.class)
                            .putExtra(Constant.TRUYEN, list.get(position))
                            .putExtra(Constant.INPUT, NoiDung_Truyen.TAI));
                    activity.finish();
                }
            }
        });
        if (loai == TRUYENDALUU) {
            holder.btnreadremove.setText(activity.getString(R.string.removebtn));
            Bitmap b = MyFileIO.loadBitmapFromDisk(Constant.EXTDIR + File.separator
                    + truyen.getName() + File.separator + Truyen.THUMBNAILFILE);
            if (b != null) holder.image.setImageBitmap(b);
            holder.btndoc_offline.setVisibility(View.VISIBLE);
            holder.btndoc_offline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, NoiDung_Truyen.class)
                            .putExtra(Constant.TRUYEN, list.get(position))
                            .putExtra(Constant.INPUT, NoiDung_Truyen.MOTRUYENDALUU));
                }
            });
        } else if (loai == CHONTRUYEN) {
            holder.btnreadremove.setText(activity.getString(R.string.readbtn));
            holder.loadImage();
        }

        holder.btnreadremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loai == TRUYENDALUU) xoaTruyen(position);
                else readTruyen(position);
            }
        });
    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        if (listActiveViewHolder != null)
            for (int i = 0, size = listActiveViewHolder.size(); i < size; i++)
                if(listActiveViewHolder.get(i) == holder) {
                    listActiveViewHolder.remove(i).image.setImageBitmap(null);
                    break;
                }
    }

    private void readTruyen(int position) {
        Log.i("MyTag", "btn Doc online");
        Intent i = new Intent(activity, Doc_online.class);
        i.putExtra(Constant.TRUYEN, list.get(position));
        if (listChapterNameComics != null) {
            i.putStringArrayListExtra(Constant.LINKLIST,
                    (ArrayList<String>) listChapterNameComics.get(position));
            Log.i("MyTag", "Select truyen :btn read :readTruyen :"
                    + listChapterNameComics.get(position).toString());
        }
        activity.startActivity(i);
    }

    private void xoaTruyen(final int position) {
        final ProgressDialog pd = new ProgressDialog(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.setIndeterminate(true);
                        pd.setTitle(R.string.xoatruyen);
                        pd.setMessage(activity.getResources().getString(R.string.comicvn_waitdownload_message));
                        pd.setCancelable(false);
                        pd.show();
                    }
                });
                MyFileIO.clearFolderContent(Constant.EXTDIR
                        + File.separator + list.get(position).getName(), true);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });
                activity.startActivity(new Intent(activity, Truyen_daluu.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                activity.finish();
            }
        }).start();
    }

    private List<MyViewHolder> listActiveViewHolder;

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : -1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public Button btnreadremove, btndownload, btndoc_offline;
        public TextView tvtentruyen;
        public int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.thumbnail_page);
            tvtentruyen = (TextView) itemView.findViewById(R.id.tentruyen_tv);
            btnreadremove = (Button) itemView.findViewById(R.id.btn_read_remove);
            btndownload = (Button) itemView.findViewById(R.id.btn_download);
            btndoc_offline = (Button)itemView.findViewById(R.id.btn_doc_offline);
        }

        public void loadImage() {
            String path = Constant.CACHEDIR + File.separator + tempFolderName
                    + File.separator + Integer.toString(position);
            if (new File(path).exists()) {
                image.setBackgroundResource(R.drawable.frameimageview);
                image.setImageBitmap(MyFileIO.loadBitmapFromDisk(path));
            } else {
                image.setBackgroundResource(R.drawable.loading);
                ((AnimationDrawable)image.getBackground()).start();
            }
        }
    }

    private Filter TruyenFilter;
    private Map<Integer, Integer> mapImageIndex;

    public Filter getFilter() {
        if (TruyenFilter == null)
            TruyenFilter = new TruyenFilter(list);
        return TruyenFilter;
    }

    private class TruyenFilter extends Filter {
        private List<Truyen> sourceObjects;

        public TruyenFilter(List<Truyen> list) {
            sourceObjects = new ArrayList<Truyen>();
            synchronized (this) {
                sourceObjects.addAll(list);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mapImageIndex.clear();
            FilterResults resultFilter = new FilterResults();
            String filterSeq = MyStringFormater.removeAccent(constraint.toString()).toLowerCase().trim();
            Log.i("MyTag", filterSeq);
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<Truyen> filter = new ArrayList<Truyen>();
                for (int i = 0; i < list.size(); i++)
                    if (MyStringFormater.removeAccent(
                            list.get(i).getName()).toLowerCase().trim().contains(filterSeq))
                        filter.add(list.get(i));
                resultFilter.count = filter.size();
                resultFilter.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    resultFilter.values = sourceObjects;
                    resultFilter.count = sourceObjects.size();
                    mapImageIndex.clear();
                }
            }
            return resultFilter;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.
            List<Truyen> filtered = (ArrayList<Truyen>) results.values;
            if (filtered == null) {
                ((Select_truyen)activity).setFinishSearching(true);
                return;
            }
            for(int i = 0, size = filtered.size(); i < size; i++)
                for(int j = 0, s = sourceObjects.size(); j < s; j++)
                    if(filtered.get(i).getName().equals(sourceObjects.get(j).getName())) {
                        mapImageIndex.put(i, j);
                            Log.i("MyTag", j + ", " + i);
                    }
            Log.i("MyTag", "clear list");
            list.clear();
            Log.i("MyTag", "cleared list");
            for (Truyen t : filtered) {
                Log.i("MyTag", "add truyen result " + t.getName()) ;
                list.add(t);
            }
            Log.i("MyTag", "add filtered list");
            ((Select_truyen)activity).setFinishSearching(true);
            notifyDataSetChanged();
        }
    }
}