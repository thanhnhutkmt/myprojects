package comicreader.software.nhut.doc_truyen.DO.Server;

import android.app.Activity;

import java.util.List;

import comicreader.software.nhut.doc_truyen.DO.Truyen;

/**
 * Created by Nhut on 7/18/2017.
 */

public interface ComicServerDOAction {
    public void getTruyen(final Activity activity, final String serverLink,
                          final onDataWithChapterNameReadyCallback onDataReady);
    public void getTruyenInfo(final Activity activity, final Truyen truyen,
                              final onComicReadyCallback onComicReady);
    public void getChapterContent(final Activity activity, final String linkChapter,
                                  final onLinkPageReadyCallback onLinkPageReadyCallback);

    public interface onDataWithChapterNameReadyCallback {
        public void onDataReady(List<Truyen> truyen, List<List<String>> listTenChapter);
    }

    public interface onLinkPageReadyCallback {
        public void onDataReady(List<String> listImageLink);
    }

    public interface onComicReadyCallback {
        public void onDataReady(Truyen truyen);
    }
}
