package lab.and401.nhut.and401lab6_tranzition_multiscreen_locale;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private List<String> listitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listitem = new ArrayList<>();
        listitem.add("Android ATC");
        final EditText input = (EditText) findViewById(R.id.inputText);
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString().trim();
                if (text != null && text.length() > 0) {
                    listitem.add(text);
                    ((ArrayAdapter<String>) list.getAdapter()).notifyDataSetChanged();
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(), 0);
                }
                input.setText("");
            }
        });

        list = (ListView) findViewById(R.id.listview);
        list.setAdapter(new ArrayAdapter<>(this, R.layout.item, R.id.itemlistview, listitem));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions transitionActivityOptions =
                            ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                                    (TextView)findViewById(R.id.itemlistview),
                                    getString(R.string.transition_name));
                    startActivity(new Intent(MainActivity.this, Main2Activity.class)
                            .putExtra("item", listitem.get(position)),
                            transitionActivityOptions.toBundle());
                } else
                    startActivity(new Intent(MainActivity.this, Main2Activity.class)
                            .putExtra("item", listitem.get(position)));
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listitem.remove(position);
                ((ArrayAdapter<String>)list.getAdapter()).notifyDataSetChanged();
                return true;
            }
        });

    }
}
