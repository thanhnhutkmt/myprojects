package comicreader.software.nhut.doc_truyen.DO.Server;

import android.app.Activity;
import android.os.Bundle;

import comicreader.software.nhut.doc_truyen.DO.Truyen;

/**
 * Created by Nhut on 7/19/2017.
 */

public abstract class ComicServerDO implements ComicServerDOAction {

    public static ComicServerDO newInstance(String link) {
        if (link.contains(HamtruyenServer.SERVERNAME))
            return new HamtruyenServer();
        if (link.contains(ThichtruyentranhServer.SERVERNAME))
            return new ThichtruyentranhServer();
        //if (link.contains(A3Manga.SERVERNAME))
            return new A3MangaServer();
    }
}
