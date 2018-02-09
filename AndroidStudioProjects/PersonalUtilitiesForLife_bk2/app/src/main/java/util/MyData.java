package util;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import software.nhut.personalutilitiesforlife.data.CuocHen;
import software.nhut.personalutilitiesforlife.data.ViTri;

/**
 * Created by Nhut on 6/23/2016.
 */
public class MyData {
    public static <T> void removeDuplication(List<T> list) {
        int listLength;
        if (list != null && list.size() > 0) listLength = list.size();
        else return;
        for (int i = 0; i < listLength; i++) {
            T ch = list.get(i);
            for (int j = i + 1; j < listLength; j++) {
                if (ch.toString().equals(list.get(j).toString())) {
                    list.remove(j);
                }
            }
        }
    }
    /*
        return : number of CuocHen of listToAdd added to list.
     */
    public static <T> int addAllWithoutDuplication(List<T> list,
                                               List<T> listToAdd,
                                               boolean removeDuplicationIn_listToAdd) {
        int listLength, listToAddLength, start = 0;
        boolean notDuplicated = false;
        if (list == null) list = new ArrayList<T>();
        if (listToAdd == null || listToAdd.size() == 0) return start;
        listLength = list.size();
        listToAddLength = listToAdd.size();
        if (removeDuplicationIn_listToAdd) removeDuplication(listToAdd);
        for (int i = 0; i < listToAddLength; i++) {
            T ch = listToAdd.get(i);
            notDuplicated = true;
            for (int j = start; j < (listLength + start); j++) {
                if (ch.toString().equals(list.get(j).toString())) {
                    notDuplicated = false;
                    break;
                }
            }
            if (notDuplicated) {
                list.add(0, ch);
                start++;
            }
        }
        return start;
    }

//    public static ViTri[] convertListToArray(List<ViTri> list) {
//        ViTri array[] = new ViTri[list.size()];
//        int i = 0;
//        for (ViTri vt : list) {
//            array[i++] = vt;
//        }
//        return array;
//    }

    public static LatLng[] convertListToArray(List<LatLng> list) {
        LatLng array[] = new LatLng[list.size()];
        int i = 0;
        for (LatLng vt : list) {
            array[i++] = vt;
        }
        return array;
    }

    public static void sortArrayFile(File af[], int type) {
        for (int i = 0; i < af.length - 1; i++)
            for (int j = 1; j < af.length; j++)
                if (compareFile(af[i], af[j], type) == 1) {
                    File temp = af[j];
                    af[j] = af[i];
                    af[i] = temp;
                }
    }

    public static int COMPARENAME = 1;
    public static int COMPAREDATE = 2;
    public static int COMPARESIZE = 3;
    public static int compareFile(File f1, File f2, int type) {
        if (type == COMPAREDATE) {
            return (f1.lastModified() > f2.lastModified()) ? 1 : (f1.lastModified() == f2.lastModified()) ? 0 : -1;
        } else if (type == COMPARENAME) {
            return f1.getName().compareTo(f2.getName());
        } else {//if (type == COMPARESIZE) {
            long size1 = f1.isDirectory() ? getFolderSize(f1) : f1.length();
            long size2 = f2.isDirectory() ? getFolderSize(f2) : f2.length();
            return (size1 > size2) ? 1 : (size1 == size2) ? 0 : -1;
        }
    }

    public static long getFolderSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFolderSize(file);
            }
        } else if (f.isFile()) {
            size=f.length();
        }
        return size;
    }

    public static final String UNIT[] = new String[] {"B", "KB", "MB", "GB", "TB"};
    public static String getFolderSize(File f, String unit) {
        long s = getFolderSize(f);
        String size = s + " B";
        float temp = s;
        for (int i = 1; i < UNIT.length; i++) {
            temp = temp / 1024;
            size =  String.format("%.2f " + UNIT[i], temp);
            if (temp < 1024 || ((unit != null) && (unit.equals(UNIT[i])))) return size;
        }
        return size;
    }

    public static String getStringSize(long s, String unit) {
        String size = s + " B";
        float temp = s;
        for (int i = 1; i < UNIT.length; i++) {
            temp = temp / 1024;
            size =  String.format("%.2f " + UNIT[i], temp);
            if (temp < 1024 || ((unit != null) && (unit.equals(UNIT[i])))) return size;
        }
        return size;
    }

    public static String getDate(File f) {
        String date = MyDateTime.getDateString(f.lastModified(), AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE);
        return date;
    }

    public static long countFile(File folder, FilenameFilter fnf) {
        long count = 0;
        if (folder.isDirectory())
            for (File file : folder.listFiles(fnf)) count += countFile(file, fnf);
        else if (folder.isFile()) count = 1;
        return count;
    }
}
