package lab.and401.nhut.and401lab3_recyclerview_cardview;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static List<DataModel> list, removedList;
    private String[] courses = {"AND400", "AND401", "AND402", "AND403", "Java fundamental", "Java Swing", "Java EE"};
    private int[] coursesID = {1, 2, 3, 4, 5, 6, 7};
    private static CustomAdapter rvadapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        rv = (RecyclerView) findViewById(R.id.rvlist);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        list = new ArrayList<DataModel>();
        removedList = new ArrayList<DataModel>();
        for (int i = 0; i < courses.length; i++) list.add(new DataModel(courses[i], coursesID[i]));

        rvadapter = new CustomAdapter(list);
        rv.setAdapter(rvadapter);

        btn_add.setOnClickListener(this);

//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (removedList.size() > 0) {
//                    list.add(0, removedList.remove(0));
//                    rvadapter.notifyItemInserted(0);
//                }
//            }
//        });
    }

    static View.OnClickListener onClickAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView tvname = (TextView) v.findViewById(R.id.text_cv);
            String name = tvname.getText().toString();
            int position = 0;
            for (int i = 0; i < list.size(); i++)
                if (name.equals(list.get(i).toString())) {
                    position = i;
                    break;
                }
            removedList.add(list.remove(position));
            rvadapter.notifyItemRemoved(position);
        }
    };

    @Override
    public void onClick(View v) {
        if (removedList.size() > 0) {
            list.add(0, removedList.remove(0));
            rvadapter.notifyItemInserted(0);
            rv.scrollToPosition(0);
        }
    }
}
