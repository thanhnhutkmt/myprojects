package and402lab4.nhut.and402.lab.android402lab4_listemailcontact;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhut on 7/24/2017.
 */

public class userAdapter extends BaseExpandableListAdapter {
    private EmailAccount array[];
    private List<List<EmailAccount>> listChild;
    private Context context;
    private int itemlayout;

    public userAdapter(Context context, int itemlayout, EmailAccount[] array) {
        this.array = array;
        this.itemlayout = itemlayout;
        this.context = context;
        this.listChild = new ArrayList<>();
        while (this.listChild.size() < array.length) this.listChild.add(new ArrayList<EmailAccount>());
        downloadImageTask();
    }

    public void addToListChild(int position, List<EmailAccount> list, String token) {
        this.listChild.get(position).addAll(list);
        this.array[position].setToken(token);
        downloadImageTask1();
    }

    public void setListChild(List<List<EmailAccount>> listChild) {
        this.listChild.addAll(listChild);
    }

    public List<List<EmailAccount>> getListChild() {
        return listChild;
    }

    private Bitmap readImage1(int parentpos, int position) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(
                new File(context.getCacheDir() + File.separator
                        + parentpos + "_" + position + ".jpeg")));
        } catch (Exception e) {
//            e.printStackTrace();
            Log.i("MyTag", "Image is not downloaded");
            return null;
        }
    }

    private Bitmap readImage(int position) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(
                    new File(context.getCacheDir() + File.separator + position + ".jpeg")));
        } catch (Exception e) {
//            e.printStackTrace();
            Log.i("MyTag", "Image is not downloaded");
            return null;
        }
    }

    private void downloadImageTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < array.length; i++) {
                    try {
                        BitmapFactory.decodeStream(new URL(array[i].getPictureurl()).openStream())
                            .compress(Bitmap.CompressFormat.JPEG, 90, new FileOutputStream(
                                context.getCacheDir() + File.separator + i + ".jpeg"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userAdapter.this.notifyDataSetChanged();
                        ((MainActivity)context).adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void downloadImageTask1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < listChild.size(); i++) {
                    for (int j = 0; j < listChild.get(i).size(); j++) {
                        try {
                            BitmapFactory.decodeStream(new URL(
                                    listChild.get(i).get(j).getPictureurl()).openStream())
                                .compress(Bitmap.CompressFormat.JPEG, 90, new FileOutputStream(
                                    context.getCacheDir() + File.separator + i + "_" + j + ".jpeg"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public int getGroupCount() {
        return array.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return listChild.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return array[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return listChild.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int position, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = View.inflate(context, itemlayout, null);
        }
        TextView nametv = (TextView) convertView.findViewById(R.id.nametv);
        TextView gendertv = (TextView) convertView.findViewById(R.id.gendertv);
        TextView emailtv = (TextView) convertView.findViewById(R.id.emailtv);
        ImageView avatariv = (ImageView) convertView.findViewById(R.id.avatariv);
        nametv.setText(array[position].getName());
        gendertv.setText(array[position].getGender());
        emailtv.setText(array[position].getEmail());
        avatariv.setImageBitmap(readImage(position));
        avatariv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ExpandableListView lv = ((MainActivity)context).getLv();
                lv.collapseGroup(position);
                listChild.get(position).clear();
                lv.expandGroup(position, true);
                return false;
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int parentpos, int position,
                             boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = View.inflate(context, itemlayout, null);
        }
        TextView nametv = (TextView) convertView.findViewById(R.id.nametv);
        TextView gendertv = (TextView) convertView.findViewById(R.id.gendertv);
        TextView emailtv = (TextView) convertView.findViewById(R.id.emailtv);
        ImageView avatariv = (ImageView) convertView.findViewById(R.id.avatariv);
        EmailAccount ea = listChild.get(parentpos).get(position);

        nametv.setText(ea.getName());
        gendertv.setText(ea.getGender());
        emailtv.setText(ea.getEmail());
        avatariv.setImageBitmap(readImage1(parentpos, position));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

