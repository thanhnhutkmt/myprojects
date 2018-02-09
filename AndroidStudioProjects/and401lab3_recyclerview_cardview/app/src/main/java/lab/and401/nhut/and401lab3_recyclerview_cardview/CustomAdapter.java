package lab.and401.nhut.and401lab3_recyclerview_cardview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Nhut on 6/22/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<DataModel> list;

    public CustomAdapter(List<DataModel> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        view.setOnClickListener(MainActivity.onClickAction);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView name = holder.name;
        ImageView image = holder.image;

        name.setText(list.get(position).toString());
        image.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : -1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.text_cv);
            image = (ImageView) itemView.findViewById(R.id.image_cv);
        }
    }
}
