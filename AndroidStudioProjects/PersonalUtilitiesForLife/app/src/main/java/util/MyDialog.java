package util;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import software.nhut.personalutilitiesforlife.ChiTietCuocHenActivity;
import software.nhut.personalutilitiesforlife.QuanLyCuocHenActivity;
import software.nhut.personalutilitiesforlife.QuanLyTinNhanActivity;
import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.XacThucActivity;
import software.nhut.personalutilitiesforlife.adapter.AdapterTextViewMauNen;
import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.TinNhanDanhBaCuocGoi;

/**
 * Created by Nhut on 7/21/2016.
 */
public class MyDialog {
    public static AlertDialog showSearchDialog(final Context context, int idTitle,
                                                final List<String> list, final InputData interfaceInputData) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(idTitle);
        if (list != null) {
            // Set up the listview to show listContact
            ListView lvTinNhan = new ListView(context);
            ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, list);
            lvTinNhan.setAdapter(adapter);
            lvTinNhan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    interfaceInputData.inputData(list.get(position));
                    Toast.makeText(context, R.string.Toast_txtlistInput_ChiTietCuocHen, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            builder.setView(lvTinNhan);
        } else {
            // Set up the input
            final EditText input = new EditText(context);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);// | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton(R.string.AlertDialog_NutTimKiem_quanlytinnhan, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    interfaceInputData.inputData(input.getText().toString());
                }
            });
        }
        builder.setNegativeButton(R.string.txtInput_NutHuy_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.show();
    }

    private final static int []LABLEID = {R.string.groupname_quanlytinnhan_colorbox_blue,
            R.string.groupname_quanlytinnhan_colorbox_green,
            R.string.groupname_quanlytinnhan_colorbox_red,
            R.string.groupname_quanlytinnhan_colorbox_alpha
    };
    public static AlertDialog showGroupNameAndColorDialog(final Context context,
                                                          int idTitle, final InputData interfaceInputData) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(idTitle);
        builder.setCancelable(false);
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        TableLayout tableLayout = new TableLayout(context);
        final SeekBar []arraySeekBar = new SeekBar[4];
        tableLayout.setColumnStretchable(1, true);

        for (int i = 0; i < arraySeekBar.length; i++) {
            final EditText value = new EditText(context);
            value.setText("000");
            value.setInputType(InputType.TYPE_CLASS_NUMBER);
            arraySeekBar[i] = new SeekBar(context);
            arraySeekBar[i].setMax(0xFF);
            arraySeekBar[i].setProgress(0);
            arraySeekBar[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            arraySeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int color = 0;
                    for (int j = 0; j < arraySeekBar.length; j++) {
                        int colorChannel = (arraySeekBar[j] == seekBar) ? progress : arraySeekBar[j].getProgress();
                        color += (colorChannel << (j * 8));
                    }
                    input.setBackgroundColor(color);
                    value.setText(Integer.toString(progress));
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
            final int j = i;
            value.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_UP)){
                        String a = value.getText().toString().trim();
                        arraySeekBar[j].setProgress(Integer.parseInt((a.length() == 0) ? "0" : a));
                    }
                    return false;
                }
            });

            TextView label = new TextView(context);
            label.setText(context.getResources().getString(LABLEID[i]));
            label.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TableRow tr = new TableRow(context);
            tr.addView(label, new TableRow.LayoutParams(0));
            tr.addView(arraySeekBar[i], new TableRow.LayoutParams(1));
            tr.addView(value, new TableRow.LayoutParams(2));
            tableLayout.addView(tr);
        }

        layout.addView(input);
        layout.addView(tableLayout);
        builder.setView(layout);
        builder.setPositiveButton(R.string.txtInput_NutLuu_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int color = 0;
                for (int i = 0; i < arraySeekBar.length; i++) {
                    color += (arraySeekBar[i].getProgress() << (i * 8));
                }
                interfaceInputData.inputData(input.getText().toString(), color);
            }
        });
        builder.setNegativeButton(R.string.txtInput_NutHuy_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.show();
    }

    public static AlertDialog showContactDialog(final Activity activity, int idTitle,
                                                List<String> listInfo, final InputData interfaceInputData) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(idTitle);
        builder.setCancelable(false);
        ScrollView sv = new ScrollView(activity);
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        final Spinner spinner_SelectGroup = new Spinner(activity);
        List<TinNhanDanhBaCuocGoi> list = new ArrayList<>();
        for (int i = 3; i < ((QuanLyTinNhanActivity)activity).getListAdapter().get(0).getListContent().size(); i++)
            list.add(((QuanLyTinNhanActivity)activity).getListAdapter().get(0).getListContent().get(i));
        list.add(0, new TinNhanDanhBaCuocGoi("_", Color.BLACK));
        AdapterTextViewMauNen adapter = new AdapterTextViewMauNen(activity, R.layout.item_textviewmaunen, list, false);
        spinner_SelectGroup.setAdapter(adapter);
        spinner_SelectGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((QuanLyTinNhanActivity) activity).resetTimer();
                return false;
            }
        });
        spinner_SelectGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((QuanLyTinNhanActivity) activity).resetTimer();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinner_SelectGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(spinner_SelectGroup);
        final List<EditText> listEditText = new ArrayList<EditText>();
        for (int i = 0; i < MyPhone.HINTID.length; i++) {
            EditText input = new EditText(activity);
            input.setInputType((i == 1) ? InputType.TYPE_CLASS_PHONE : InputType.TYPE_CLASS_TEXT);
            input.setHint(MyPhone.HINTID[i]);
            input.setText((listInfo == null) ? "" : listInfo.get(i));
            input.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ((QuanLyTinNhanActivity) activity).resetTimer();
                }
            });
            input.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ((QuanLyTinNhanActivity) activity).resetTimer();
                    return false;
                }
            });
            listEditText.add(input);
            layout.addView(input);
        }
        sv.addView(layout);
        final List<Button> listButton = new ArrayList<>();
        builder.setView(sv).setPositiveButton(R.string.txtInput_NutLuu_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((QuanLyTinNhanActivity) activity).resetTimer();
                List<String> listContactInfo = new ArrayList<String>();
                for (EditText et : listEditText) listContactInfo.add(et.getText().toString().trim());
                int gn = spinner_SelectGroup.getSelectedItemPosition();
                listContactInfo.add(String.valueOf((gn == 0) ? 0 : gn + 2));
                interfaceInputData.inputData(listContactInfo);
                listButton.get(0).setEnabled(false);
            }
        }).setNegativeButton(R.string.txtInput_NutHuy_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((QuanLyTinNhanActivity) activity).resetTimer();
                dialog.cancel();
            }
        });
        AlertDialog ad = builder.show();
        listButton.add(ad.getButton(DialogInterface.BUTTON_POSITIVE));
        return ad;
    }

    public static void showContextMenu(Context context, AlertDialog ad, int []arrayMenuTitleId, View.OnClickListener []arrayAction) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        for (int i = 0; i < arrayMenuTitleId.length; i++) {
            TextView menu = new TextView(context);
            menu.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            menu.setText(arrayMenuTitleId[i]);
            menu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            menu.setPadding(10, 10, 10, 10);
            menu.setOnClickListener(arrayAction[i]);
            TextView lineBetween = new TextView(context);
            lineBetween.setHeight(1);
            lineBetween.setBackgroundColor(Color.BLACK);
            lineBetween.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ll.addView(menu);
            ll.addView(lineBetween);
        }
        ad.setView(ll);
        ad.setCancelable(true);
        ad.show();
    }

    public static AlertDialog showMyDialog(Context context, int titleID, View []arrayView, DialogInterface.OnClickListener action) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        for (View v : arrayView) {
            ll.addView(v);
        }
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(titleID);
        b.setView(ll);
        b.setCancelable(true);
        b.setPositiveButton(R.string.txtInput_NutLuu_ChiTietCuocHen, action);
        b.setNegativeButton(R.string.txtInput_NutHuy_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return b.show();
    }

    public static AlertDialog showExportWifiDialog(final Activity context) {
        final List<InetAddress> listIP = new ArrayList<>();
        TextView txtPort = new TextView(context); txtPort.setText(R.string.ExportwifiDialog_txtPort);
        TextView txtSubnet = new TextView(context); txtSubnet.setText(R.string.ExportwifiDialog_txtSubnet);
        TextView txtIP = new TextView(context); txtIP.setText(R.string.ExportwifiDialog_IPlist);
        final EditText edtPort = new EditText(context);
        edtPort.setText("9999");
        final EditText edtSubnet = new EditText(context);
        edtSubnet.setText("192.168.0");
        final Spinner spinnerIP = new Spinner(context);
        final ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, listIP);
        spinnerIP.setAdapter(adapter);

        TableLayout bd = new TableLayout(context);
        TableLayout.LayoutParams tableRowParams= new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(20, 0, 20, 0);
        bd.setLayoutParams(tableRowParams);
        bd.requestLayout();
        TableRow tr1 = new TableRow(context);
        tr1.addView(txtPort);
        tr1.addView(edtPort);
        TableRow tr2 = new TableRow(context);
        tr2.addView(txtSubnet);
        tr2.addView(edtSubnet);
        TableRow tr3 = new TableRow(context);
        tr3.addView(txtIP);
        tr3.addView(spinnerIP);
        bd.setColumnStretchable(1, true);
        bd.addView(tr1);
        bd.addView(tr2);
        bd.addView(tr3);


        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(R.string.ExportwifiDialog_title);
        b.setView(bd);
        b.setCancelable(true);
        b.setPositiveButton(R.string.ExportwifiDialog_btnNutGui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MySocket.sendFile(Integer.parseInt(edtPort.getText().toString()), ((InetAddress)spinnerIP.getSelectedItem()).getHostAddress(),
                                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + AppConstant.SYNC + File.separator + "datawithpass.zip",
                                context);
                        context.runOnUiThread(new Runnable() {
                            @Override public void run() {
                                Toast.makeText(context, R.string.AlertDialog_guiquawifi_xong, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).start();
            }
        });
        b.setNegativeButton(R.string.ExportwifiDialog_btnNutReload, null);
        AlertDialog ad = b.show();
        ad.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        listIP.clear();
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                        listIP.addAll(MySocket.scanIP(edtSubnet.getText().toString(), 100, 111, 160));
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
            }
        });
        return ad;
    }

    public static AlertDialog showNewPasswordDiaLog(Context context, View []arrayView, final InputData actionYes) {
        return showNewPasswordDiaLog(context, 0, arrayView, actionYes, true);
    }

    public static AlertDialog showNewPasswordDiaLog(Context context, int titleid, View []arrayView, final InputData actionYes) {
        return showNewPasswordDiaLog(context, titleid, arrayView, actionYes, false);
    }

    public static AlertDialog showNewPasswordDiaLog(Context context, int titleid, View []arrayView, final InputData actionYes, final boolean exit) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        for (View v : arrayView) ll.addView(v);
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle((titleid == 0) ? R.string.AlertDialog_title_newPassword_confirmDialg : titleid);
        b.setView(ll);
        b.setCancelable(false);
        b.setPositiveButton(R.string.Button_label_ok_MyDialog_passwordDialog, null);
        b.setNegativeButton(R.string.Button_label_close_MyDialog_passwordDialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (exit) System.exit(0);
                else dialog.dismiss();
            }
        });
        final AlertDialog ad =  b.show();
        ad.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionYes.inputData(ad);
            }
        });

        return ad;
    }

    public static AlertDialog showPasswordDiaLog(Context context, View []arrayView,
                 View.OnClickListener actionYes, DialogInterface.OnClickListener actionNo) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        for (View v : arrayView) ll.addView(v);
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(R.string.AlertDialog_title_xacthucPassword_confirmDialg);
        b.setView(ll);
        b.setCancelable(false);
        b.setPositiveButton(R.string.Button_label_ok_MyDialog_passwordDialog, null);
        b.setNegativeButton(R.string.Button_label_close_MyDialog_passwordDialog, actionNo);
        AlertDialog ad =  b.show();
        ad.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(actionYes);
        return ad;
    }

    public static AlertDialog showChangePasswordDiaLog(Context context, View []arrayView, final InputData actionYes) {
        LinearLayout ll = new LinearLayout(context);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        for (View v : arrayView) ll.addView(v);
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(R.string.AlertDialog_title_ChangePassword_confirmDialg);
        b.setView(ll);
        b.setCancelable(false);
        b.setPositiveButton(R.string.Button_label_ok_MyDialog_passwordDialog, null);
        b.setNegativeButton(R.string.Button_label_close_MyDialog_passwordDialog, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {}
        });
        final AlertDialog ad =  b.show();
        ad.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                actionYes.inputData(ad);
            }
        });
        return ad;
    }

    public static AlertDialog showListViewDiaLog(Context context, int titleID, List<?> list,
             AdapterView.OnItemClickListener onItemClick, AdapterView.OnItemLongClickListener onItemLongClick) {
        ListView lvContent = new ListView(context);
        lvContent.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1, list));
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(titleID).setView(lvContent).setCancelable(true);
        lvContent.setOnItemClickListener(onItemClick);
        lvContent.setOnItemLongClickListener(onItemLongClick);
        return b.show();
    }

    public static AlertDialog showYesNoDiaLog(Context context, int titleID, int bodyID,
            DialogInterface.OnClickListener yesAction, DialogInterface.OnClickListener noAction) {
        TextView body = new TextView(context);
        body.setPadding(10, 10, 10, 10);
        body.setText(bodyID);
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(titleID);
        b.setView(body);
        b.setCancelable(true);
        b.setPositiveButton(R.string.ConfirmDialog_Yes_commonAlertDialog, yesAction);
        b.setNegativeButton(R.string.ConfirmDialog_No_commonAlertDialog, noAction);
        return b.show();
    }

    public static AlertDialog showDeleteConfirmDiaLog(final Context context, int titleID, int bodyID,
                                                      final InputData yesAction) {
        TextView body = new TextView(context);
        body.setPadding(10, 10, 10, 10);
        body.setText(bodyID);
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(titleID);
        b.setCancelable(true);
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        Button btYes = new Button(context);
        Button btNo = new Button(context);
        btYes.setText(R.string.ConfirmDialog_Yes_commonAlertDialog);
        btNo.setText(R.string.ConfirmDialog_No_commonAlertDialog);
        LinearLayout llbt = new LinearLayout(context);
        llbt.setOrientation(LinearLayout.HORIZONTAL);
        llbt.setGravity(Gravity.CENTER);

        llbt.addView(btYes);
        llbt.addView(btNo);
        ll.addView(body);
        ll.addView(llbt);
        b.setView(ll);
        final AlertDialog ad = b.create();
        btYes.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                Button bt = ((Button)v);
                String s = bt.getText().toString();
                if (s.equals(context.getResources().getString(
                        R.string.ConfirmDialog_Yes_commonAlertDialog))) bt.setText("1");
                else if (s.equals("1")) bt.setText("2");
                else if (s.equals("2")) bt.setText("3");
                else if (s.equals("3")) yesAction.inputData((Object)ad);
            }
        });
        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                ad.dismiss();
            }
        });
        ad.show();
        return ad;
    }

//    public static AlertDialog showSendMsgDialog(final Activity context,
//                    List<TinNhanDanhBaCuocGoi> listContact, final InputData saveMsg, String phoneNo) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(R.string.AlertDialog_Title_sendMsg);
//        builder.setCancelable(false);
//        LinearLayout layout = new LinearLayout(context);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        TableLayout layoutPhoneNumber = new TableLayout(context);
//        TableRow tr = new TableRow(context);
//        final EditText txtMsg = new EditText(context);
//        final EditText txtPhoneNo = new EditText(context);
//        txtPhoneNo.setInputType(InputType.TYPE_CLASS_PHONE);
//        if (phoneNo != null && phoneNo.trim().length() > 0) txtPhoneNo.setText(phoneNo);
//        final Button btnAddContact = new Button(context);
//        btnAddContact.setText("+");
//        final ListView lvContact = new ListView(context);
//        final List<String> list = new ArrayList<String>();
//        for (TinNhanDanhBaCuocGoi t : listContact) list.add(t.getContent());
//        lvContact.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1, list));
//        layoutPhoneNumber.setColumnStretchable(1, true);
//        tr.addView(btnAddContact);
//        tr.addView(txtPhoneNo);
//        layoutPhoneNumber.addView(tr);
//        layout.addView(layoutPhoneNumber);
//        layout.addView(lvContact); lvContact.setVisibility(View.GONE);
//        layout.addView(txtMsg);
//        builder.setView(layout);
//        builder.setPositiveButton(R.string.AlertDialog_SendButton_sendMsg, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                List<String> list = new ArrayList<>();
//                list.add(txtMsg.getText().toString().trim());
//                list.add(txtPhoneNo.getText().toString().trim());
//                list.addAll(Arrays.asList(txtPhoneNo.getText().toString().split(";")));
//                saveMsg.inputData(list);
//            }
//        });
//        builder.setNegativeButton(R.string.AlertDialog_CloseButton_sendMsg, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        btnAddContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                txtMsg.setVisibility((txtMsg.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
//                lvContact.setVisibility((lvContact.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
//            }
//        });
//        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String phoneNumber = MyStringFormater.standardizePhoneNumber(list.get(position).split(" - ")[1].split("\n")[0]);
//                String temp = txtPhoneNo.getText().toString().trim();
//                if (temp.length() == 0) txtPhoneNo.setText(phoneNumber);
//                else txtPhoneNo.setText(temp + ";" + phoneNumber);
//            }
//        });
//        AlertDialog ad = builder.show();
//        final Button b = ad.getButton(DialogInterface.BUTTON_POSITIVE); b.setEnabled(false); b.setAlpha(0.5f);
//        txtMsg.addTextChangedListener(new TextWatcher() {
//            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override public void afterTextChanged(Editable s) {}
//            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
//                boolean sendOK = (txtMsg.getText().toString().trim().length() > 2 && txtPhoneNo.getText().toString().trim().length() > 7);
//                b.setEnabled((sendOK) ? true : false); b.setAlpha((sendOK) ? 1.0f :0.5f);
//            }
//        });
//        return ad;
//    }

    public static AlertDialog showCallDialog(final Activity context,
                     List<TinNhanDanhBaCuocGoi> listContact, final InputData saveMsg, String phoneNo) {
        return showCallSendSMSDialog(context, listContact, saveMsg, phoneNo, 1);
    }

    public static AlertDialog showSendMsgDialog(Activity context,
                    List<TinNhanDanhBaCuocGoi> listContact, final InputData saveMsg, String phoneNo) {
        return showCallSendSMSDialog(context, listContact, saveMsg, phoneNo, 2);
    }

    public static AlertDialog showSpamDialog(Activity context,
                                                List<TinNhanDanhBaCuocGoi> listContact, final InputData saveMsg, String phoneNo) {
        return showCallSendSMSDialog(context, listContact, saveMsg, phoneNo, 3);
    }


    private static AlertDialog showCallSendSMSDialog(final Activity context,
                     List<TinNhanDanhBaCuocGoi> listContact, final InputData saveMsg,
                     String phoneNo, final int type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle((type == 2) ? R.string.AlertDialog_Title_sendMsg : (type == 1) ? R.string.AlertDialog_CallButton_callPhone : R.string.AlertDialog_Title_Spam);
        builder.setCancelable(false);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        TableLayout layoutPhoneNumber = new TableLayout(context);
        TableRow tr = new TableRow(context);
        final List<EditText> listet = new ArrayList<>();
        listet.add(new EditText(context)); listet.add(new EditText(context));
        //final EditText txtMsg = listet.get(0);//new EditText(context);
        //final EditText txtPhoneNo = listet.get(1);//new EditText(context);
        if (type != 3) listet.get(0).setInputType(InputType.TYPE_CLASS_PHONE);
        if (phoneNo != null && phoneNo.trim().length() > 0) listet.get(0).setText(phoneNo);
        final Button btnAddContact = new Button(context);
        btnAddContact.setText("+");
        final ListView lvContact = new ListView(context);
        final List<String> list = new ArrayList<String>();
        for (TinNhanDanhBaCuocGoi t : listContact) list.add(t.getContent());
        lvContact.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1, list));
        layoutPhoneNumber.setColumnStretchable(1, true);
        tr.addView(btnAddContact);
        tr.addView(listet.get(0));
        layoutPhoneNumber.addView(tr);
        layout.addView(layoutPhoneNumber);
        layout.addView(lvContact); lvContact.setVisibility(View.GONE);
        if (type != 3 && type != 1) layout.addView(listet.get(1));
        builder.setView(layout);
        builder.setPositiveButton((type == 2) ? R.string.AlertDialog_SendButton_sendMsg : (type == 1) ? R.string.AlertDialog_CallButton_callPhone : R.string.AlertDialog_setbutton_Spam, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                if (type == 1) {
                    MyPhone.call(context, listet.get(0).getText().toString().trim());
                } else if (type == 3) {
                    List<String> list = new ArrayList<>();
                    list.addAll(Arrays.asList(listet.get(0).getText().toString().split(";")));
                    saveMsg.inputData(list);
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(listet.get(1).getText().toString().trim());
                    list.add(listet.get(0).getText().toString().trim());
                    list.addAll(Arrays.asList(listet.get(0).getText().toString().split(";")));
                    saveMsg.inputData(list);
                }
            }
        });
        builder.setNegativeButton(R.string.AlertDialog_CloseButton_sendMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                dialog.cancel();
            }
        });
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                btnAddContact.setText(btnAddContact.getText().toString().equals("+") ? "-" : "+");
                if (type != 3) listet.get(1).setVisibility((listet.get(1).getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
                lvContact.setVisibility((lvContact.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
            }
        });
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                String phoneNumber = MyStringFormater.standardizePhoneNumber(list.get(position).split(" - ")[1].split("\n")[0]);
                if (type == 1) listet.get(0).setText(phoneNumber); //Call phone
                else { //Send Msg
                    String temp = listet.get(0).getText().toString().trim();
                    if (temp.length() == 0) listet.get(0).setText(phoneNumber);
                    else listet.get(0).setText(temp + ";" + phoneNumber);
                }
            }
        });
        lvContact.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                ((QuanLyTinNhanActivity) context).resetTimer();
                return false;
            }
        });
        AlertDialog ad = builder.show();
        if (type == 2) {//Send Msg
            final Button b = ad.getButton(DialogInterface.BUTTON_POSITIVE);
            b.setEnabled(false); b.setAlpha(0.5f);
            listet.get(1).addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ((QuanLyTinNhanActivity) context).resetTimer();
                    boolean sendOK = (listet.get(1).getText().toString().trim().length() > 1 && listet.get(0).getText().toString().trim().length() > 2);
                    b.setEnabled(sendOK);
                    b.setAlpha((sendOK) ? 1.0f : 0.5f);
                }
            });
        }
        listet.get(0).addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((QuanLyTinNhanActivity) context).resetTimer();
            }
        });
        listet.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
            }
        });
        listet.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuanLyTinNhanActivity) context).resetTimer();
            }
        });
        return ad;
    }

    public static void scaleListView(ListView listView) {
        Log.i("MyTag", "scaleListView()");
        ListAdapter listAdapter = listView.getAdapter();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (listAdapter == null || listAdapter.getCount() == 0) params.height = -1;
        else {
            int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        }
        Log.i("MyTag", "scaleListView() param height : " + params.height);
        listView.setLayoutParams(params);
    }

    public static void scaleListViewAtFirst(ListView listView) {
        Log.i("MyTag", "scaleListView()");
        ListAdapter listAdapter = listView.getAdapter();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (listAdapter == null || listAdapter.getCount() == 0) params.height = -1;
        else {
            int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            params.height = (int)(totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)));
        }
        Log.i("MyTag", "scaleListViewAAtFirst() param height : " + params.height);
        listView.setLayoutParams(params);
    }

    public static int createFCMNtf(Context context, String msg, String title) {
        int notificationID = (int) (Math.random() * 100);
        createNotification(context, notificationID, R.drawable.newmessage_server, title,
                msg, MyPhone.getSavedRingTone(context), XacThucActivity.class);
        return notificationID;
    }

    public static int createMessageNtf(Context context, String msg) {
        int notificationID = (int) (Math.random() * 100);
        createNotification(context, notificationID, R.drawable.newmessage, context.getResources().getString(R.string.title_notification_newmessage),
                msg, MyPhone.getSavedRingTone(context), XacThucActivity.class);
        return notificationID;
    }

    public static int createAppointmentNtf(Context context, String msg) {
        int notificationID = (int) (Math.random() * 100);
        createNotification(context, notificationID, R.drawable.appointment_soon, context.getResources().getString(R.string.title_notification_appointmentsoon),
                msg, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), QuanLyCuocHenActivity.class);
        return notificationID;
    }

    public static void createNotification(Context context, int notificationId, int smallIcon, String title,
                                          String content, Uri soundUri, Class resultClass) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(content);
        Intent resultIntent = new Intent(context, resultClass);
        resultIntent.putExtra("MessageType", QuanLyTinNhanActivity.SMS);
        resultIntent.putExtra("NotificationID", notificationId);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setSound(soundUri);
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notificationId, mBuilder.build());
    }

    public static AlertDialog selectFileDialog(final Context context, final InputData doSomeThingWithSelectedFile) {
        File storage = new File(File.separator);
        final TextView txtCurrentPath = new TextView(context);
        txtCurrentPath.setText(File.separator);
        final List<String> listFileName = new ArrayList<>();
        listFileName.addAll(Arrays.asList(storage.list()));
        Collections.sort(listFileName);
        final ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, listFileName);
        final ListView lvFolderContent = new ListView(context);
        lvFolderContent.setAdapter(adapter);
        lvFolderContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFileName;
                if (listFileName.get(position).equals("...")) {
                    selectedFileName = txtCurrentPath.getText().toString();
                    selectedFileName = selectedFileName.substring(0, selectedFileName.lastIndexOf(File.separator));
                    if (selectedFileName.equals("")) selectedFileName = File.separator;
                } else selectedFileName = (txtCurrentPath.getText().toString().equals(File.separator))
                        ? File.separator + listFileName.get(position)
                        : txtCurrentPath.getText().toString() + File.separator + listFileName.get(position);
                File selectedFile = new File(selectedFileName);
                if (selectedFile.isDirectory()) {
                    String temp[] = selectedFile.list();
                    if (temp != null) {
                        txtCurrentPath.setText(selectedFileName);
                        listFileName.clear();
                        listFileName.addAll(Arrays.asList(temp));
                        Collections.sort(listFileName);
                        if (!txtCurrentPath.getText().toString().equals(File.separator)) listFileName.add(0, "...");
                        adapter.notifyDataSetChanged();
                    } else Toast.makeText(context, R.string.AlertDialog_cannotopenfolder_selectFileDialog, Toast.LENGTH_SHORT).show();
                } else if (selectedFile.isFile()) {
                    doSomeThingWithSelectedFile.inputData(selectedFileName, txtCurrentPath.getText().toString());
                }
            }
        });
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(txtCurrentPath);
        ll.addView(lvFolderContent);
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(R.string.AlertDialog_title_selectFileDialog).setCancelable(true).setView(ll);
        return b.show();
    }

    public static AlertDialog selectFolderDialog(final Context context, String defaultPath, boolean cancelable,
                                                 final InputData doSomeThingWithSelectedFolder) {
        final FilenameFilter fnf = new FilenameFilter() {
            @Override public boolean accept(File dir, String filename) {
                return new File(dir + File.separator + filename).isDirectory();
            }
        };
        File storage = new File((defaultPath == null || defaultPath.trim().length() == 0) ? File.separator : defaultPath);
        final TextView txtCurrentPath = new TextView(context);
        txtCurrentPath.setText(storage.getAbsolutePath());
        final List<String> listFileName = new ArrayList<>();
        String content[] = storage.list(fnf);
        if (content != null) listFileName.addAll(Arrays.asList(content));
        Collections.sort(listFileName);
        final ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, listFileName);
        final ListView lvFolderContent = new ListView(context);
        lvFolderContent.setAdapter(adapter);
        lvFolderContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFileName;
                if (listFileName.get(position).equals("...")) {
                    selectedFileName = txtCurrentPath.getText().toString();
                    selectedFileName = selectedFileName.substring(0, selectedFileName.lastIndexOf(File.separator));
                    if (selectedFileName.equals("")) selectedFileName = File.separator;
                } else selectedFileName = (txtCurrentPath.getText().toString().equals(File.separator))
                        ? File.separator + listFileName.get(position)
                        : txtCurrentPath.getText().toString() + File.separator + listFileName.get(position);
                File selectedFile = new File(selectedFileName);
                if (selectedFile.isDirectory()) {
                    String temp[] = selectedFile.list(fnf);
                    if (temp != null) {
                        txtCurrentPath.setText(selectedFileName);
                        listFileName.clear();
                        listFileName.addAll(Arrays.asList(temp));
                        Collections.sort(listFileName);
                        if (!txtCurrentPath.getText().toString().equals(File.separator)) listFileName.add(0, "...");
                        adapter.notifyDataSetChanged();
                    } else Toast.makeText(context, R.string.AlertDialog_cannotopenfolder_selectFileDialog, Toast.LENGTH_SHORT).show();
                }
            }
        });
        final List<AlertDialog> list = new ArrayList<>();
        lvFolderContent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFolderName = (txtCurrentPath.getText().toString().equals(File.separator))
                        ? File.separator + listFileName.get(position)
                        : txtCurrentPath.getText().toString() + File.separator + listFileName.get(position);
                String arrayFile[] = new File(selectedFolderName).list();
                String array[] = new String[arrayFile.length + 1];
                array[0] = selectedFolderName;
                for (int i = 1; i < array.length; i++) array[i] = arrayFile[i - 1];
                doSomeThingWithSelectedFolder.inputData(array);
                list.get(0).dismiss();
                return false;
            }
        });
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(txtCurrentPath);
        ll.addView(lvFolderContent);
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(R.string.AlertDialog_title_selectFolderDialog).setCancelable(cancelable).setView(ll);
        AlertDialog ad = b.show();
        list.add(ad);
        return ad;
    }

    public static AlertDialog selectFolderDialog(Context context, InputData doSomeThingWithSelectedFolder) {
        return selectFolderDialog(context, null, true, doSomeThingWithSelectedFolder);
    }

    public static AlertDialog showInfoDialog(Context context, HashMap<String, String> info) {
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setColumnStretchable(1, true);
        for (String k : info.keySet()) {
            TableRow tr = new TableRow(context);
            TextView label = new TextView(context); label.setText("   " + k);
            TextView content = new TextView(context); content.setText("  " + info.get(k));
            tr.addView(label, new TableRow.LayoutParams(0));
            tr.addView(content, new TableRow.LayoutParams(1));
            tableLayout.addView(tr);
        }
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(R.string.AlertDialog_title_AlbumInfoDialog).setCancelable(false)
                .setView(tableLayout).setPositiveButton(R.string.AlertDialog_button_AlbumInfoDialog, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog ad = b.show();
        return ad;
    }

    public static AlertDialog showInputDialog(Context context, int idTitle, final InputData inputData) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(idTitle);
        final EditText txtInput = new EditText(context);
        txtInput.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(txtInput);
        builder.setPositiveButton(R.string.AlertDialogInputText_buttonCreate_createalbum, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputData.inputData(txtInput.getText().toString());
            }
        });

        builder.setNegativeButton(R.string.txtInput_NutHuy_ChiTietCuocHen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.show();
    }

    public static TwitterLoginButton showTweetDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(R.layout.twitter_dialog_layout);
//        builder.setTitle("Tweet twitter");
        TwitterLoginButton tlb = new TwitterLoginButton(context);
        builder.setView(tlb);
        builder.show();
        return tlb;
    }
}
