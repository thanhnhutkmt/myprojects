package lab.and401.nhut.and401lab7_dialog_toast_snackbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] buttonidarray = { R.id.btn_dialog1, R.id.btn_dialog2, R.id.btn_dialog3,
                                R.id.btn_alertdialog1, R.id.btn_alertdialog2,
                                R.id.btn_progressdialog1, R.id.btn_progressdialog2,
                                R.id.btn_Toast1, R.id.btn_Toast2,
                                R.id.btn_snackbar1, R.id.btn_snackbar2,
                                R.id.btn_TimeDatePicker
                              };
        for (int buttonid : buttonidarray) ((Button)findViewById(buttonid)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_dialog1:
                showDialog1();
                break;
            case R.id.btn_dialog2:
                showDialog2();
                break;
            case R.id.btn_dialog3:
                showDialog3();
                break;
            case R.id.btn_alertdialog1:
                showalertdialog1();
                break;
            case R.id.btn_alertdialog2:
                showalertdialog2();
                break;
            case R.id.btn_progressdialog1:
                showprogressdialog1();
                break;
            case R.id.btn_progressdialog2:
                showprogressdialog2();
                break;
            case R.id.btn_Toast1:
                showToast1();
                break;
            case R.id.btn_Toast2:
                showToast2();
                break;
            case R.id.btn_snackbar1:
                showsnackbar1();
                break;
            case R.id.btn_snackbar2:
                showsnackbar2();
                break;
            case R.id.btn_TimeDatePicker:
                showTimeDatePicker();
                break;
        }
    }

    private void showDialog3() {
        startActivity(new Intent(this, DialogActivity.class));
    }

    private void showTimeDatePicker() {
        Calendar c = Calendar.getInstance();
        final TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(MainActivity.this, hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(MainActivity.this, dayOfMonth+"/"+(month+1)+"/"+year, Toast.LENGTH_SHORT).show();
                tpd.show();
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    private void showsnackbar2() {
// view is get from findViewById(id of layout of this activity)
        Snackbar sn = Snackbar.make(findViewById(R.id.activity_main),
                "It is fun to try a snack", Snackbar.LENGTH_LONG)
                .setAction("Open", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "snackbar action", Toast.LENGTH_SHORT).show();
                    }
                });
        sn.setActionTextColor(Color.RED);
        sn.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                Toast.makeText(MainActivity.this, "snackbar callback onDismissed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
                Toast.makeText(MainActivity.this, "snackbar callback onShown", Toast.LENGTH_SHORT).show();
            }
        });
        sn.show();
    }

    private void showsnackbar1() {
        // view is get from findViewById(id of layout of this activity)
        Snackbar.make(findViewById(R.id.activity_main),
                "It is fun to try a snack", Snackbar.LENGTH_LONG).show();
    }

    private void showToast2() {
        Toast t = Toast.makeText(this, "Hello, this is a toast", Toast.LENGTH_LONG);
        LinearLayout ll = new LinearLayout(this);
        ll.setBackgroundColor(Color.DKGRAY);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(this);
        tv.setText("Text View text"); tv.setPadding(10,10,10,10);
        tv.setTextColor(Color.BLUE);
        Button b = new Button(this); // toast is not clickable so button is not usable
        b.setText("OK"); b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "This is button of toast", Toast.LENGTH_SHORT).show();
            }
        });
        b.setClickable(true);
        ll.setClickable(true);
        ll.addView(tv);
        ll.addView(b);
        t.setView(ll);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }

    private void showToast1() {
        Toast.makeText(this, "Hello, this is a toast", Toast.LENGTH_SHORT).show();
    }

    private void showprogressdialog2() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("This is progressDialog");
        pd.setMessage("Please wait while loading...");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setButton(ProgressDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Yes", Toast.LENGTH_SHORT).show();
            }
        });
        pd.setButton(ProgressDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        pd.setButton(ProgressDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
            }
        });
//        pd.setButton3("Change Style", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
        pd.show();
    }

    private void showprogressdialog1() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("This is progressDialog");
        pd.setMessage("Downloading...");
//        pd.setIndeterminate(true);
        pd.setMax(100);
        pd.setProgress(0);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();
        CountDownTimer timer = new CountDownTimer(100000, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
//                Toast.makeText(MainActivity.this, "onTick()", Toast.LENGTH_SHORT).show();
                pd.setProgress(pd.getProgress()+1);
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "onFinish()", Toast.LENGTH_SHORT).show();
            }
        };
        timer.start();
    }

    private void showalertdialog2() {
        AlertDialog b = new AlertDialog.Builder(this).create();
        b.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Yes", Toast.LENGTH_SHORT).show();
            }
        });
        b.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        b.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
            }
        });
        b.setTitle("This is alertdialog");
        b.setIcon(R.mipmap.ic_launcher);
        b.setMessage("Hello android coder");
        b.show();
    }

    private void showalertdialog1() {
        AlertDialog b = new AlertDialog.Builder(this)
            .setTitle("This is AlertDialog")
            .setMessage("Hello android coder")
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                }
            })
            .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                }
            })
            .create();
        b.show();
    }

    private void showDialog2() {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("This is the dialog");
        dialog.setContentView(R.layout.dialoglayout);
        Window w = dialog.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();// NOTHING different with FLAG_DIM_BEHIND and FLAG_BLUR_BEHIND
    }

    private void showDialog1() {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("This is the dialog");
        dialog.setContentView(R.layout.dialoglayout);
        Window w = dialog.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();// NOTHING different with FLAG_DIM_BEHIND and FLAG_BLUR_BEHIND
    }
}
