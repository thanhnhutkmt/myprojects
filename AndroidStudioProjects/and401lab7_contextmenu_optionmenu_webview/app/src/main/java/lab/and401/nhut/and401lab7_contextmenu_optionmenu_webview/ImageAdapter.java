package lab.and401.nhut.and401lab7_contextmenu_optionmenu_webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhut on 6/25/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Activity act;
    private List<Bitmap> listImage;

    public ImageAdapter(Activity act, Integer[] listImageID) {
        this.act = act;
        this.listImage = new ArrayList<Bitmap>();
        for (Integer imageid : listImageID) {
            Bitmap a = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(act.getResources(), imageid), 100, 100, false);
            listImage.add(a);
        }
    }

    @Override
    public int getCount() {
        return (listImage != null) ? listImage.size() : -1;
    }

    @Override
    public Object getItem(int position) {
        return (listImage != null && listImage.size() > position) ? listImage.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = act.getLayoutInflater().inflate(R.layout.item_imagelist, null);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.imageitem);
//        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        image.setImageResource(listImageID.get(position));
//        image.setImageDrawable(act.getResources().getDrawable((listImageID.get(position))));

        image.setImageBitmap(listImage.get(position));
        return convertView;
//        ImageView image = new ImageView(act);
//        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        image.setImageResource(listImageID.get(position));
//        image.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 200));
//        return image;
    }
}
