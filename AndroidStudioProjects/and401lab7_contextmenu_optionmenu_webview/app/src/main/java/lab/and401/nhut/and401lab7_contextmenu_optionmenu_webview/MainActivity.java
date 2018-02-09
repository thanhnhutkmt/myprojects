package lab.and401.nhut.and401lab7_contextmenu_optionmenu_webview;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private GridView grid;
    private ImageAdapter adapter;
    private Integer[] imageIDarray = {R.drawable.img1, R.drawable.img2, R.drawable.img3,
                                    R.drawable.img4, R.drawable.img5, R.drawable.img6,
                                    R.drawable.img7, R.drawable.img8, R.drawable.img9};
//    private int selectedImageID = imageIDarray[0];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = (GridView) findViewById(R.id.grid);
        adapter = new ImageAdapter(this, imageIDarray);
        grid.setAdapter(adapter);
//        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                selectedImageID = imageIDarray[position];
//                return false;
//            }
//        });
        registerForContextMenu(grid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.about: // view in full-screen
                startActivity(new Intent(MainActivity.this, Main3Activity.class));
                break;
            case R.id.exit: // set as wallpaper
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Context menu");
//        menu.add(Menu.NONE, Menu.FIRST, 0, "View in Full-screen");
//        menu.add(Menu.NONE, Menu.FIRST+1, 1, "Set as wallpaper");
        menu.add(Menu.FIRST, ((AdapterView.AdapterContextMenuInfo)menuInfo).position, 0, "View in full screen");
        menu.add(Menu.FIRST+1, ((AdapterView.AdapterContextMenuInfo)menuInfo).position, 0, "Set as wallpaper");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case Menu.FIRST: // view in full-screen
//                startActivity(new Intent(MainActivity.this, Main2Activity.class).putExtra("Image", selectedImageID));
//                break;
//            case Menu.FIRST+1: // set as wallpaper
//                try {
//                    WallpaperManager.getInstance(MainActivity.this).setResource(selectedImageID);
//                    Toast.makeText(this, "Wallpaper changed", Toast.LENGTH_LONG).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
        //second method : use the AdapterView.AdapterContextMenuInfo to get position of clicked imageview
        switch(item.getGroupId()) {
            case Menu.FIRST: // view in full-screen
                startActivity(new Intent(MainActivity.this, Main2Activity.class).
                        putExtra("Image", imageIDarray[item.getItemId()]));
                break;
            case Menu.FIRST+1: // set as wallpaper
                try {
                    WallpaperManager.getInstance(MainActivity.this).
                            setResource(imageIDarray[item.getItemId()]);
                    Toast.makeText(this, "Wallpaper changed", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }
}
