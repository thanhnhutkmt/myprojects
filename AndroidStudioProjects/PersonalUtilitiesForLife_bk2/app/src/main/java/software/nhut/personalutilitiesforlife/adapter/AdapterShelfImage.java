package software.nhut.personalutilitiesforlife.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import util.MyData;
import util.MyImage;
import util.MyMedia;

/**
 * Created by Nhut on 11/4/2016.
 */

public class AdapterShelfImage {
    private List<Bitmap> listBitmap;
    private File folderImage;
    private List<ImageView> listImageView;
    private static int IMAGE_PER_SCREEN = 15;
    private static int CACHED_SHELF = 4;
    private static int CACHED_IMAGE = IMAGE_PER_SCREEN * CACHED_SHELF;
    public static final List<String> listImageExtension = Arrays.asList(new String[] {".jpg", ".jpeg", ".png", ".gif", ".bmp"});
    public static final List<String> listVideoExtension = Arrays.asList(new String[] {".mp4"});
    private int screenHeight;
    private int thumbnailSize;
    private int shelf, position = 0, maxPos;
    private File arrayImageFile[];

    public static FilenameFilter fnf = new FilenameFilter() {
        @Override public boolean accept(File dir, String filename) {
            File f = new File(dir + File.separator + filename);
            if (f.isDirectory() && !filename.startsWith(".")) {
                return true;
            } else if (f.isFile()) {
                for (String ext : listImageExtension) if (filename.toLowerCase().endsWith(ext)) return true;
                for (String ext : listVideoExtension) if (filename.toLowerCase().endsWith(ext)) return true;
            }
            return false;
        }
    };

    public AdapterShelfImage(Activity context, File folderImage, List<ImageView> listImageView, int image_per_screen, int cached_shelf) {
        this.IMAGE_PER_SCREEN = image_per_screen;
        this.CACHED_SHELF = cached_shelf;
        init(context, folderImage, listImageView);
    }

    public AdapterShelfImage(Activity context, File folderImage, List<ImageView> listImageView) {
        init(context, folderImage, listImageView);
    }

    private void init(Activity context, File folderImage, List<ImageView> listImageView) {
        this.folderImage = (folderImage != null) ? folderImage :
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
        this.listImageView = listImageView;
        Point size = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(size);
        this.screenHeight = size.y;
        this.thumbnailSize = (int) (0.6 * screenHeight / 5);
        arrayImageFile = this.folderImage.listFiles(fnf);
        maxPos = (arrayImageFile.length + IMAGE_PER_SCREEN - 1)/IMAGE_PER_SCREEN; // divide and round up
        initListBitMap();
        initListImageView();
    }

    private void initListBitMap() {
        int limit = 0;
        listBitmap = new ArrayList<>();
        for (File f : arrayImageFile) {//folderImage.listFiles(fnf)) {
            if (f.isDirectory()) listBitmap.add(getThumbnailFolder(f));
            else if (f.isFile()) listBitmap.add(listVideoExtension.contains(f.getName().substring(f.getName().length() - 4))
                    ? MyMedia.getVideoThumbnail(f, thumbnailSize): MyImage.getThumbnail(f, thumbnailSize));
            if (++limit >= CACHED_IMAGE) break;
        }
        while (listBitmap.size() < CACHED_IMAGE) listBitmap.add(null);
    }

    private Bitmap getThumbnailFolder(File f) {
        Bitmap b = null;
        if (f.isDirectory()) {
            File arrayF[] = f.listFiles(fnf);
            if (arrayF != null && arrayF.length > 0) {
                int i = 0;
                while(arrayF[i].isDirectory()) i++;
                Log.i("MyTag", "getThumbnailFolder - loaded image : " + arrayF[i].getAbsolutePath());
                b = (listVideoExtension.contains(arrayF[i].getName().substring(arrayF[i].getName().length() - 4)))
                        ? MyMedia.getVideoThumbnail(arrayF[i], thumbnailSize) : MyImage.getThumbnail(arrayF[i], thumbnailSize);
            }
        }
        return b;
    }

    private void initListImageView() {
        this.shelf = 0;
        this.position = 0;
        for (int i = 0; i < IMAGE_PER_SCREEN; i++) setImage(i, listBitmap.get(i));
    }

    private void setImage(int pos, Bitmap b) {
        if (b != null) {
            this.listImageView.get(pos).setVisibility(ImageView.VISIBLE);
            this.listImageView.get(pos).setImageBitmap(b);
            Log.i("MyTag", "ImageView " + pos + ", size " + b.getHeight() + "x" + b.getWidth());
        } else this.listImageView.get(pos).setVisibility(ImageView.INVISIBLE);
    }

    public void next() {
        slide(1);
    }

    public void previous() {
        slide(-1);
    }

    // direction : 1 next, -1 previous
    private void slide(int direction) {
        if (((this.position == maxPos - 1) && (direction == 1))
                || ((this.position == 0) && (direction == -1))) return;
        this.shelf += direction;
        this.position += direction;
        if (this.shelf == 4) {
            this.shelf = 3;
            loadNext(loadNextListBitMapFromDisk(this.position));
        } else if (this.shelf == -1) {
            this.shelf = 0;
            loadPrevious(loadNextListBitMapFromDisk(this.position));
        }
        loadListImageView();
        /* load new image :
                if (shelf + 1 < 4 || shelf - 1 > -1)
                    from list bitmap
                else {
                    newly load from file

                }

        */
    }

    private void loadNext(List<Bitmap> listB) {
        for (int i = 0; i < IMAGE_PER_SCREEN; i++) {
            listBitmap.remove(0);
            listBitmap.add(CACHED_IMAGE-1, listB.get(i));
        }
    }

    private void loadPrevious(List<Bitmap> listB) {
        for (int i = 0; i < IMAGE_PER_SCREEN; i++) {
            listBitmap.remove(CACHED_IMAGE - 1);
            listBitmap.add(0, listB.get(i));
        }
    }

    private List<Bitmap> loadNextListBitMapFromDisk(int pos) {
        List<Bitmap> list = new ArrayList<>();
        for (int i = pos * IMAGE_PER_SCREEN; (i < (pos + 1) * IMAGE_PER_SCREEN) && (i < arrayImageFile.length); i++)
            list.add(MyImage.getThumbnail(arrayImageFile[i], thumbnailSize));
        while (list.size() < IMAGE_PER_SCREEN) list.add(null);
        return list;
    }

    private void loadListImageView() {
        int stopIndex = (this.shelf + 1) * IMAGE_PER_SCREEN;
        for (int j = 0, i = this.shelf * IMAGE_PER_SCREEN; i < stopIndex; i++, j++)
            this.listImageView.get(j).setImageBitmap(listBitmap.get(i));
    }

    public int getPosition() {
        return this.position;
    }

    public String getImagePath(int imageNumber) throws ArrayIndexOutOfBoundsException {
        return arrayImageFile[this.position * IMAGE_PER_SCREEN + imageNumber].getAbsolutePath();
    }

    public static int COMPARENAME = 1;
    public static int COMPAREDATE = 2;
    public static int COMPARESIZE = 3;
    public void sort(int type) {
        for (File f : arrayImageFile) Log.i("MyTag", f.getName() + " " + f.lastModified() + " " + ((f.isDirectory()) ? MyData.getFolderSize(f) : f.length()));
        MyData.sortArrayFile(arrayImageFile, type);
        for (File f : arrayImageFile) Log.i("MyTag", f.getName() + " " + f.lastModified() + " " + ((f.isDirectory()) ? MyData.getFolderSize(f) : f.length()));
//        loadListImageView();
        initListBitMap();
        initListImageView();
    }
}

