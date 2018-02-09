package comicreader.software.nhut.doc_truyen.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by Nhut on 6/15/2016.
 */
public class MyStringFormater {
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
