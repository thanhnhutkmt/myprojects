package and403lab.nhut.and403.lab.and403lab1_admobbanner;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {
    private AdView adview;
    private GridView gridview;
    private int count;
    private long time = 0;
    private Integer number[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
            17, 18, 19, 20, 21, 22, 23, 24, 25};
    private Dialog d;
    private View boxarray[] = new View[number.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.playground);
        adview = (AdView) findViewById(R.id.adviewbanner);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("2E1A9928BE0FB9AA3B4A129B0AF32263")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adview.loadAd(request);
        gridview.setAdapter(new ArrayAdapter(this, R.layout.item_box, R.id.Number, number));

//        gridview.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return number.length;
//            }
//
//            @Override
//            public Object getItem(int i) {
//                return number[i];
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                if (view == null) {
//                    view = MainActivity.this.getLayoutInflater().inflate(R.layout.item_box, null);
//                }
//                TextView v = (TextView)view.findViewById(R.id.Number);
//                v.setText(Integer.toString(number[i]));
//                return view;
//            }
//        });

        shufflePlayGround();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                time = (time == 0) ? System.currentTimeMillis() : time;
                boxarray[position] = view;
                int pressedNumber = number[position];
                TextView box = (TextView)view.findViewById(R.id.Number);
                Log.i("MyTag", "click box " + number[position]);
                if (box.isEnabled()) count++;
                else return;
                if (time == -1) return;
                if (pressedNumber != count) {
                    d = showDialog("You lose in " + (System.currentTimeMillis() - time)/1000
                        + " sec(s)" + ". Wrong number order.", "Try again");
                    box.setBackgroundColor(Color.RED);
                    time = -1;
                } else {
                    if (pressedNumber == 25)
                        d = showDialog(
                            "You win in " + (System.currentTimeMillis() - time)/1000 + " secs"
                            , "Play again");
                    box.setBackgroundColor(Color.GREEN);
                    box.setEnabled(false);
                }
            }
        });
    }

    private void shufflePlayGround() {
        count = 0;
        time = 0;
        for (int i = 0; i < number.length; i++) {
            int index = (int)(Math.random() * 24);
            index = (index == i) ? index + 1 : index;
//            Log.e("MyTag", "------SWAP-------");
//            Log.w("MyTag", String.format("box %d : number[%d]=%d, numberswap[%d]=%d)",
//                    i, i, number[i], index, number[index]));
            number[i] = number[index] + number[i];
//            Log.i("MyTag", String.format("box %d : number[%d]=%d, numberswap[%d]=%d)",
//                    i, i, number[i], index, number[index]));
            number[index] = number[i] - number[index];
//            Log.i("MyTag", String.format("box %d : number[%d]=%d, numberswap[%d]=%d)",
//                    i, i, number[i], index, number[index]));
            number[i] = number[i] - number[index];
//            Log.w("MyTag", String.format("box %d : number[%d]=%d, numberswap[%d]=%d)",
//                    i, i, number[i], index, number[index]));
//            Log.e("MyTag", "------ENDSWAP-------");
//            gridview.performItemClick(boxarray[i], i, 0); //cause sound noise
            if (boxarray[i] != null) {
                TextView box = (TextView)boxarray[i].findViewById(R.id.Number);
                box.setBackgroundColor(Color.BLACK);
                box.setEnabled(true);
            }
        }
        ((BaseAdapter)gridview.getAdapter()).notifyDataSetChanged();
    }

    // unusable for android:theme="@android:style/Theme.Translucent"
    private Dialog showAlertDialog(String msg, String btnlabel) {
        AlertDialog.Builder b = new AlertDialog.Builder(this)
            .setTitle("Result")
            .setMessage(msg)
            .setNegativeButton(btnlabel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    shufflePlayGround();
                    d.dismiss();
                }
            })
            .setCancelable(false);
        d = b.create();
        d.show();
        return d;
    }

    private Dialog showDialog(String msg, String btnlabel) {
        d = new Dialog(this);
        d.setTitle("Result");
        View v = View.inflate(this, R.layout.dialog_layout, null);
        TextView tv = (TextView)v.findViewById(R.id.dialog_text_msg);
        tv.setText(msg);
        Button b = (Button)v.findViewById(R.id.dialog_btn);
        b.setText(btnlabel); b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shufflePlayGround();
                d.dismiss();
            }
        });
        d.setContentView(v);
        d.show();
        return d;
    }
}