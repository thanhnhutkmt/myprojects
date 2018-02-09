package software.nhut.personalutilitiesforlife.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import software.nhut.personalutilitiesforlife.QuanLyTinNhanActivity;
import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;
import util.InputData;
import util.MyClipboard;
import util.MyData;
import util.MyDateTime;
import util.MyDialog;
import util.MyFileIO;
import util.MyImage;
import util.MyPhone;
import util.MyStringFormater;

/**
 * Created by Nhut on 7/22/2016.
 */
public class AdapterTextViewMauNen extends ArrayAdapter {
    private Activity context;
    private int rowid;
    private List<TinNhanDanhBaCuocGoi> listContent;
    private Filter filter;
    private boolean clickable = true;
    public static final String SORTBY_TIME = "sortbytime";
    public static final String SORTBY_GROUP = "sortbygroup";
    public static final String SORTBY_CONTACT = "sortbycontact";
    private List<List<TinNhanDanhBaCuocGoi>> list;
    private AlertDialog ad;

    public AdapterTextViewMauNen(Activity context, int rowid, List<TinNhanDanhBaCuocGoi> listContent) {
        super(context, rowid, listContent);
        this.context = context;
        this.rowid = rowid;
        this.listContent = listContent;
    }

    public AdapterTextViewMauNen(Activity context, int rowid, List<TinNhanDanhBaCuocGoi> listContent, boolean clickable) {
        super(context, rowid, listContent);
        this.context = context;
        this.rowid = rowid;
        this.listContent = listContent;
        this.clickable = clickable;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(rowid, null);
        final TextView txtText = (TextView)v;
        TinNhanDanhBaCuocGoi tndb = listContent.get(position);
        txtText.setTextColor(tndb.getColor());
        v.setAlpha(listContent.get(position).isSelected() ? 0.5f : 1.0f);
        setColorBackground(tndb, txtText);
        String content = listContent.get(position).getContent();
        int index = ((QuanLyTinNhanActivity) context).getIndex();
        if (listContent.get(position).getPhoneNumber().length() > 0 && (index == 1 || index == 2)) {
            if (position == 0) {
                list = new ArrayList<>();
                MyPhone.loadDataFromApp(((QuanLyTinNhanActivity) context).getThisFeatureFolder(), list);
            }
            for (TinNhanDanhBaCuocGoi t : list.get(1))
                if (t.getPhoneNumber().equals(listContent.get(position).getPhoneNumber()))
                    content = content.replace(t.getPhoneNumber(), t.getListInfo().get(0) + "-" + t.getPhoneNumber());
        }
        txtText.setText(content);
        if (clickable)
            txtText.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    itemClick(position, v);
                }
            });
        if (((QuanLyTinNhanActivity) context).getIndex() < 3)
            txtText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override public boolean onLongClick(View v) {
                    itemLongClick(position);
                    return false;
                }
            });
        return v;
    }

    private void setColorBackground(TinNhanDanhBaCuocGoi tndb, TextView txtText) {
        if (tndb.getGroupNumber().size() > 0)
            if (tndb.getGroupNumber().size() > 1)
                txtText.setBackgroundDrawable(new BitmapDrawable(context.getResources(), createColorfulLine(tndb)));
            else
                txtText.setBackgroundColor(
                        ((QuanLyTinNhanActivity)context).getListNhom_spinner().get(tndb.getGroupNumber().get(0)).getColor());
    }

    private Bitmap createColorfulLine(TinNhanDanhBaCuocGoi tndb) {
        int mybg[] = new int[tndb.getGroupNumber().size()];
        for (int i = 0; i < mybg.length; i++)
            mybg[i] = ((QuanLyTinNhanActivity)context).getListNhom_spinner().get(tndb.getGroupNumber().get(i)).getColor();
        return Bitmap.createBitmap(mybg, mybg.length, 1, Bitmap.Config.ARGB_8888);
    }

    private void itemLongClick(final int position) {
        ((QuanLyTinNhanActivity) context).setItemIndex(position);
        final int index = ((QuanLyTinNhanActivity) context).getIndex();
        ad = new AlertDialog.Builder(context).create();
        int[] menuid = null;
        View.OnClickListener[] action = null;
        /*
            index = 0 : listcontact : delete & call & edit
            index = 1 : listmessage : delete & call & reply
            index = 2 : listcalllog : delete & call & send message
            index = 3... : none
         */
        menuid = new int[6];
        action = new View.OnClickListener[6];
        if (index == 0) { // listcontact
            menuid[5] = R.string.AlertDialog_menutitle_quanlytinnhan_editcontact;
            action[5] = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((QuanLyTinNhanActivity) context).resetTimer();
                    ad.dismiss();
                    List<String> listInfo = listContent.get(position).getListInfo();
                    ad = MyDialog.showContactDialog(context, R.string.AlertDialog_title_quanlytinnhan_editcontact, listInfo, new InputData() {
                        @Override public Object inputData(Object o) {return null;}
                        @Override public void inputData(String s) {} @Override public void inputData(String... s) {}
                        @Override public void inputData(String s, int color) {} @Override public void inputData(DialogInterface dialog) {}
                        @Override public void inputData(List<String> s) {
                            TinNhanDanhBaCuocGoi currentContact = listContent.get(position);
                            StringBuilder info = new StringBuilder();
                            s.set(1, MyStringFormater.standardizePhoneNumber(s.get(1)));
                            int gn = Integer.parseInt(s.remove(s.size() - 1));
                            if (gn != 0) {
                                List<Integer> listGroupNumber = new ArrayList<>();
                                listGroupNumber.add(gn);
                                currentContact.setGroupNumber(listGroupNumber);
                            }
                            info.append(s.get(0) + " - " + s.get(1));
                            for (int i = 2; i < s.size(); i++)
                                if (s.get(i).trim().length() > 0) info.append("\n" + s.get(i));
                            currentContact.setContent(info.toString());
                            currentContact.setListInfo(s);
                            ((QuanLyTinNhanActivity)context).luuVaNapLaiDuLieu();
                        }
                    });
                    ad.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            ((QuanLyTinNhanActivity) context).resetTimer();
                            return false;
                        }
                    });
                }
            };
        } else if (index == 1 || index == 2) { // index = 1, 2
            menuid[5] = (index == 1) ? R.string.AlertDialog_menutitle_quanlytinnhan_reply : R.string.AlertDialog_menutitle_quanlytinnhan_sendmessage;
            action[5] = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((QuanLyTinNhanActivity) context).resetTimer();
                    ad.dismiss();
                    List<String> listInfo = listContent.get(position).getListInfo();
                    ((QuanLyTinNhanActivity) context).guiTinNhan(listInfo.get(1));
                }
            };
        }
        menuid[0] = R.string.AlertDialog_textview_delete_vitri;
        action[0] = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                ad.dismiss();
                MyDialog.showDeleteConfirmDiaLog(context, R.string.AlertDialog_title_Delete_Confirm,
                    R.string.AlertDialog_body_Delete_Confirm, new InputData() {
                    @Override public Object inputData(Object o) {
                        if (o instanceof AlertDialog) {
                            listContent.remove(position);
                            ((QuanLyTinNhanActivity) context).luuVaNapLaiDuLieu();
                            ((AlertDialog) o).dismiss();
                        }
                        return null;
                    }
                    @Override public void inputData(String s) {}
                    @Override public void inputData(DialogInterface dialog) {}
                    @Override public void inputData(String... s) {}
                    @Override public void inputData(List<String> s) {}
                    @Override public void inputData(String s, int color) {}
                });
            }
        };
        menuid[1] = R.string.AlertDialog_menutitle_quanlytinnhan_call;
        action[1] = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                ad.dismiss();
                List<String> listInfo = listContent.get(position).getListInfo();
                ((QuanLyTinNhanActivity) context).goiDienThoai(listInfo.get(1));
            }
        };
        menuid[2] = R.string.AlertDialog_menutitle_quanlytinnhan_copyphonenumber;
        action[2] = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                ad.dismiss();
                MyClipboard.sendTextToClipboard(context, listContent.get(position).getListInfo().get(1));
            }
        };
        menuid[3] = R.string.AlertDialog_menutitle_quanlytinnhan_copycontent;
        action[3] = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                ad.dismiss();
                MyClipboard.sendTextToClipboard(context, listContent.get(position).getContent());
            }
        };
        menuid[4] = R.string.AlertDialog_menutitle_quanlytinnhan_copySerial;
        action[4] = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                ad.dismiss();
                final List<String> listtemp = new ArrayList<>();
                for (String s : listContent.get(position).getContent().split(" ")) {
                    if (s.length() >= 5) listtemp.add(s);
                }
                ad = MyDialog.showListViewDiaLog(context, R.id.cast_notification_id, listtemp, null,
                    new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            MyClipboard.sendTextToClipboard(context, listtemp.get(position));
                            ad.dismiss();
                            return false;
                        }
                    });
                ad.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        ((QuanLyTinNhanActivity) context).resetTimer();
                        return false;
                    }
                });
            }
        };
        ((QuanLyTinNhanActivity)context).resetTimer();
        MyDialog.showContextMenu(context, ad, menuid, action);
    }

    private void itemClick(int position, View v) {
        ((QuanLyTinNhanActivity) context).resetTimer();
        ((QuanLyTinNhanActivity) context).setItemIndex(position);
        boolean temp = !listContent.get(position).isSelected();
        listContent.get(position).setSelected(temp);
        v.setAlpha(temp ? 0.5f : 1.0f);
    }

    @Override
    public Filter getFilter() {
        if (filter == null) filter = new adapterFilter(listContent);
        return filter;
    }

    private class adapterFilter extends Filter {
        private List<TinNhanDanhBaCuocGoi> sourceObjects;

        public adapterFilter(List<TinNhanDanhBaCuocGoi> list) {
            sourceObjects = new ArrayList<TinNhanDanhBaCuocGoi>();
            synchronized (this) {
                sourceObjects.addAll(list);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults resultFilter = new FilterResults();
            String filterSeq = MyStringFormater.removeAccent(constraint.toString()).toLowerCase().trim();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<TinNhanDanhBaCuocGoi> filter = new ArrayList<TinNhanDanhBaCuocGoi>();
                for (TinNhanDanhBaCuocGoi s : sourceObjects) {
                    if (MyStringFormater.removeAccent(s.getContent()).toLowerCase().contains(filterSeq))
                        filter.add(s);
                }
                resultFilter.count = filter.size();
                resultFilter.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    resultFilter.values = sourceObjects;
                    resultFilter.count = sourceObjects.size();
                }
            }
            return resultFilter;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.
            List<TinNhanDanhBaCuocGoi> filtered = (ArrayList<TinNhanDanhBaCuocGoi>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filtered.size(); i < l; i++)
                add((TinNhanDanhBaCuocGoi) filtered.get(i));
            notifyDataSetInvalidated();
            listContent.clear();
            listContent.addAll(filtered);
            ((QuanLyTinNhanActivity)context).setFinishSearching(true);
        }
    }

    public void setListContent(List<TinNhanDanhBaCuocGoi> listContent) {
        this.listContent = listContent;
        this.filter = null;
        notifyDataSetChanged();
        clear();
        addAll(this.listContent);
        notifyDataSetInvalidated();
    }

    public List<TinNhanDanhBaCuocGoi> getListContent() {
        return listContent;
    }
}
