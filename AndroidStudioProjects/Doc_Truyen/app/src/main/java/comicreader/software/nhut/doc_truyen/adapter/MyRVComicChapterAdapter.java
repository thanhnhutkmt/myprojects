package comicreader.software.nhut.doc_truyen.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import comicreader.software.nhut.doc_truyen.Constant;
import comicreader.software.nhut.doc_truyen.DO.Comicvn.ComicvnServer;
import comicreader.software.nhut.doc_truyen.DO.Comicvn.DownloadComicPage;
import comicreader.software.nhut.doc_truyen.DO.Truyen;
import comicreader.software.nhut.doc_truyen.NoiDung_Truyen;
import comicreader.software.nhut.doc_truyen.R;
import comicreader.software.nhut.doc_truyen.ReadComicsActivity;
import comicreader.software.nhut.doc_truyen.util.MyFileIO;
import comicreader.software.nhut.doc_truyen.util.MyPhone;

/**
 * Created by Nhut on 7/02/2017.
 */

public class MyRVComicChapterAdapter extends RecyclerView.Adapter<MyRVComicChapterAdapter.MyViewHolder> {
    private List<Truyen> listChapter;
    private Activity activity;
    private final int MAXDOWNLOAD = MyPhone.getNumberOfCPUCore();
    private int numOfDownload = 0;
    private String tempFolderName;
    private List<StatusViewHolder> listDownloadStatus = new ArrayList<>();
    private List<MyViewHolder> listActiveViewHolder = new ArrayList<>();

    public MyRVComicChapterAdapter(Activity activity, List<Truyen> listChapter) {
        if (listChapter.size() > 2 && listChapter.get(0).getName().
                compareTo(listChapter.get(1).getName()) == 1) Collections.reverse(listChapter);
        this.listChapter = listChapter;

        this.activity = activity;
        tempFolderName = Long.toString(System.currentTimeMillis());
        new File(Constant.CACHEDIR + File.separator + tempFolderName).mkdirs();
        for (int i = 0, size = this.listChapter.size(); i < size; i++) {
            listDownloadStatus.add(new StatusViewHolder(0,
                this.listChapter.get(i).getLinkList().size(), false,
                activity.getResources().getString(R.string.downloadbtn), true,
                this.listChapter.get(i).getName(), false,
                Constant.CACHEDIR + File.separator + tempFolderName
                    + File.separator + Integer.toString(i))
            );
//            Log.i("MyTag", "pb init : " + listDownloadStatus.get(i).getProgress()+"/"+listDownloadStatus.get(i).getMax());
        }
        cachedThumbnailThread.start();
        countDownloadedParts.start();
        Toast.makeText(activity, R.string.comicvn_waitdownload_message, Toast.LENGTH_SHORT).show();
    }

    private Thread countDownloadedParts = new Thread(new Runnable() {
        @Override
        public void run() {
            //read current status
            for (int i = 0, size = listChapter.size(); i < size; i++) {
                File f = new File(Constant.EXTDIR + File.separator
                        + ((NoiDung_Truyen) activity).getTenTruyen() + File.separator
                        + listChapter.get(i).getName());
                int completed = (!f.exists()) ? 0 : f.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".png");
                    }
                }).length;
                listDownloadStatus.get(i).setProgress(completed);
            }
        }
    });

    private Thread cachedThumbnailThread =  new Thread(new Runnable() {
        @Override
        public void run() {
            Log.i("MyTag", "start cache thumbnail thread");
            int i = 0;
            try {
                for (int size = listChapter.size(); i < size; i++) {
                    Log.i("MyTag", "cache thumbnail thread " + listChapter.get(i).getThumbnailLink());
                    String pathDownloaded = Constant.EXTDIR + File.separator
                            + ((NoiDung_Truyen)activity).getTenTruyen() + File.separator
                            + listChapter.get(i).getName() + File.separator + Truyen.THUMBNAILFILE;
                    if (new File(pathDownloaded).exists()) continue;
                    String writenfile = MyFileIO.saveBitmapToCache(
                            new URL(listChapter.get(i).getThumbnailLink()).openStream(),
                            tempFolderName + File.separator + Integer.toString(i),
                            Constant.THUMBNAILSIZE, Constant.THUMBNAILSIZE);
                    Log.i("MyTag", "cache thumbnail thread ok " + writenfile);
                    Message msg = new Message(); msg.arg1 = i;
                    handler.sendMessage(msg);
                }
                Message msg = new Message(); msg.arg1 = -1;
                handler.sendMessage(msg);
            } catch(Exception e) {
                Message msg = new Message(); msg.arg1 = -2; msg.arg2 = i;
                handler.sendMessage(msg);
                e.printStackTrace();
            }
        }
    });

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.i("MyTag", "listActiveViewHolder is " + ((listActiveViewHolder == null)
                    ? "null" : listActiveViewHolder.size()));
            if (listActiveViewHolder != null) {
                for (MyViewHolder h : listActiveViewHolder) {
                    Log.i("MyTag", "row " + h.position + " is active");
                    if (h.position == msg.arg1) {
                        h.loadImage();
                        break;
                    }
                }
            }
            if (msg.arg1 == -1) listActiveViewHolder = null;
            if (msg.arg1 == -2) {
                StatusViewHolder svh = listDownloadStatus.get(msg.arg2);
                svh.setProgress(0);
                svh.setMax(0);
                svh.setTenChapter(svh.getTenChapter()
                    + activity.getResources().getString(R.string.downloaderror));
            }
            return false;
        }
    });

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(activity.getLayoutInflater()
                .inflate(R.layout.itemnoidungtruyen, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.position = position;
        if (listActiveViewHolder != null) listActiveViewHolder.add(holder);
        holder.loadImage();
        final Truyen chapter = listChapter.get(position);

        updateStatus(position, holder);

        holder.tv_downloadpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String downloadlabel = activity.getResources().getString(R.string.downloadbtn);
                final DownloadComicPage down = new DownloadComicPage(
                        activity, holder, listDownloadStatus, ((NoiDung_Truyen)activity).getTenTruyen(),
                        new ComicvnServer.onComicReadyCallback() {
                    @Override
                    public void onDataReady(final List<String> pathList) {
                        synchronized (this) {
                            numOfDownload--;
                        }
                    }
                });

                String label = holder.tv_downloadpause.getText().toString();
                if (label.equals(downloadlabel)) {
                    if (numOfDownload < MAXDOWNLOAD) {
                        down.execute(chapter.getLinkList());
                        synchronized (this) {
                            numOfDownload++;
                        }
                    } else {
                        final int tempcolor = holder.tv_downloadpause.getCurrentTextColor();
                        holder.tv_downloadpause.setTextColor(Color.RED);
                        holder.tv_downloadpause.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.tv_downloadpause.setTextColor(tempcolor);
                                Toast.makeText(activity,
                                    R.string.warning_overloadcpu, Toast.LENGTH_SHORT).show();
                            }
                        }, 1000);
                    }
                }
                listDownloadStatus.get(position).updateStatus(holder);
            }
        });
    }

    private void updateStatus(final int position, final MyViewHolder holder) {
//        holder.pb.setIndeterminate(true);
        if (listDownloadStatus.get(position).getProgress()
                == listDownloadStatus.get(position).getMax())
            listDownloadStatus.get(position).setLabel_TVdownloadpause(
                    activity.getResources().getString(R.string.downloadfinish));
        listDownloadStatus.get(position)
            .setNopercent(listDownloadStatus.get(position).isDownloadingFlag());
        listDownloadStatus.get(position).updateStatus(holder);
        StatusViewHolder svh = listDownloadStatus.get(position);
        Log.i("MyTag", "pbar : " + svh.getProgress() + "/" + svh.getMax()
                + ", " + Boolean.toString(svh.isNopercent())
        );
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

    @Override
    public int getItemCount() {
        return (listChapter != null) ? listChapter.size() : -1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ProgressBar pb;
        public TextView tv_downloadpause;
        public TextView tvtenChapter;
        public LinearLayout item_noidungtruyen;
        public int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.thumbnail_page_noidungtruyen);
            tvtenChapter = (TextView) itemView.findViewById(R.id.tenchapter_noidungtruyen);
            pb = (ProgressBar) itemView.findViewById(R.id.download_pb_noidungtruyen);
            tv_downloadpause = (TextView) itemView.findViewById(R.id.tv_downloadpause_noidungtruyen);
            item_noidungtruyen = (LinearLayout) itemView.findViewById(R.id.item_noidungtruyen);
        }

        public void loadImage() {
            String path = Constant.CACHEDIR + File.separator + tempFolderName
                + File.separator + Integer.toString(position);
            String pathDownloaded = Constant.EXTDIR + File.separator
                + ((NoiDung_Truyen)activity).getTenTruyen() + File.separator
                + listChapter.get(position).getName() + File.separator + Truyen.THUMBNAILFILE;
            if (new File(path).exists()) {
                image.setBackgroundResource(R.drawable.frameimageview);
                image.setImageBitmap(MyFileIO.loadBitmapFromDisk(path));
            } else if (new File(pathDownloaded).exists()) {
                image.setBackgroundResource(R.drawable.frameimageview);
                image.setImageBitmap(MyFileIO.loadBitmapFromDisk(pathDownloaded));
            } else {
                image.setBackgroundResource(R.drawable.loading);
                ((AnimationDrawable)image.getBackground()).start();
            }
        }
    }

    public class StatusViewHolder {
        private int progress;
        private int max;
        private boolean nopercent;
        private String label_TVdownloadpause;
        private boolean tvdownpause_clickable;
        private boolean downloadingFlag;
        private String thumbnailPath;
        private String tenChapter;

        public StatusViewHolder(int progress, int max, boolean nopercent,
                                String label_TVdownloadpause, boolean tvdownpause_clickable,
                                String tenChapter, boolean downloadingFlag, String thumbnailPath) {
            this.progress = progress;
            this.max = max;
            this.nopercent = nopercent;
            this.label_TVdownloadpause = label_TVdownloadpause;
            this.tvdownpause_clickable = tvdownpause_clickable;
            this.tenChapter = tenChapter;
            this.downloadingFlag = downloadingFlag;
            this.thumbnailPath = thumbnailPath;
        }

        public void updateStatus(final MyViewHolder holder) {
            // without prior max value, setting progress has no effect
            holder.pb.setIndeterminate(nopercent);
            holder.pb.setMax(max);
            holder.pb.setProgress(progress);
            holder.tv_downloadpause.setText(label_TVdownloadpause);
            holder.tv_downloadpause.setClickable(tvdownpause_clickable);
            holder.tvtenChapter.setText(tenChapter);
            holder.loadImage();
            Log.i("MyTag", "pbar of item " + holder.position + ": indetermine "
                    + Boolean.toString(nopercent)
                       + "\n         progress " + progress + "/" + max
                       + "\ndownloadpause Textview : label " + label_TVdownloadpause
                       + "\n                         clickable "
                                                        + Boolean.toString(tvdownpause_clickable)
                       + "\ntvtenChapter   : " + tenChapter
            );
        }

        public void setLabel_TVdownloadpause(String label) {
            label_TVdownloadpause = label;
        }

        public String getLabel_TVdownloadpause() {
            return this.label_TVdownloadpause;
        }


        public boolean isDownloadingFlag() {
            return downloadingFlag;
        }

        public void setDownloadingFlag(boolean downloadingFlag) {
            this.downloadingFlag = downloadingFlag;
        }

        public String getThumbnailPath() {
            return thumbnailPath;
        }

        public void setThumbnailPath(String thumbnailPath) {
            this.thumbnailPath = thumbnailPath;
        }

        public boolean isNopercent() {
            return nopercent;
        }

        public void setNopercent(boolean nopercent) {
            this.nopercent = nopercent;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public boolean isTvdownpause_clickable() {
            return tvdownpause_clickable;
        }

        public void setTvdownpause_clickable(boolean tvdownpause_clickable) {
            this.tvdownpause_clickable = tvdownpause_clickable;
        }

        public String getTenChapter() {
            return tenChapter;
        }

        public void setTenChapter(String tenChapter) {
            this.tenChapter = tenChapter;
        }
    }
}
