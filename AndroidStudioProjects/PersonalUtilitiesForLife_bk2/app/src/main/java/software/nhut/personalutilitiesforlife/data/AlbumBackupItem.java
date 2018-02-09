package software.nhut.personalutilitiesforlife.data;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;

import software.nhut.personalutilitiesforlife.constant.AppConstant;
import util.MyData;
import util.MyDateTime;

/**
 * Created by Nhut on 11/25/2016.
 */

public class AlbumBackupItem {
//    private String link;
//    private String title;
//    private String createdDate;
//    private String type;
//    private String size;
    private Metadata md;
    private DriveFile df;

    public AlbumBackupItem(Metadata md) {
        this.md = md;
        this.df = md.getDriveId().asDriveFile();
    }

    public String getLink() {
        return md.getWebContentLink();
    }

    public String getTitle() {
        return md.getTitle();
    }

    public String getCreatedDate() {
        return MyDateTime.getDateString(md.getCreatedDate().getTime(), AppConstant.FULLTIMEFORMAT_WITHOUTNEWLINE);
    }

    public String getType() {
        return md.getMimeType();
    }

    public String getSize() {
        return MyData.getStringSize(md.getFileSize(), null);
    }

    public long getLongSize() {
        return md.getFileSize();
    }

    public DriveFile getFile() {
        return this.df;
    }

    public String toString() {
        return getTitle() + " " + getCreatedDate() + " " + getSize();
    }
}
