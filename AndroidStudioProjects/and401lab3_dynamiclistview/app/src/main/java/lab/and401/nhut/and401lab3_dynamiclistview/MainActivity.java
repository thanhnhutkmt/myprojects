package lab.and401.nhut.and401lab3_dynamiclistview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> listCourse;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add = (Button) findViewById(R.id.btnadd);
        final EditText itemname = (EditText)findViewById(R.id.itemname);
        ListView list = (ListView) findViewById(R.id.dlist);
        listCourse = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listCourse);
        list.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = itemname.getText().toString().trim();
                if (temp != null & temp.length() > 0) {
                    listCourse.add(temp);
                    adapter.notifyDataSetChanged();
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(itemname.getWindowToken(), 0);
                }
                itemname.setText("");
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listCourse.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}
