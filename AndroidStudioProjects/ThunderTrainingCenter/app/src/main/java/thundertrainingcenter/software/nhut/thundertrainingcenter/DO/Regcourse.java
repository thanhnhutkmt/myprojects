package thundertrainingcenter.software.nhut.thundertrainingcenter.DO;

/**
 * Created by Nhut on 8/30/2017.
 */

public class Regcourse {
    String title;
    String timeTable;
    String room;
    String openDay;
    String fee;
    String feeStatus;

    @Override
    public String toString() {
        return "Regcourse{" +
                "title='" + title + '\'' +
                ", timeTable='" + timeTable + '\'' +
                ", room='" + room + '\'' +
                ", openDay='" + openDay + '\'' +
                ", fee='" + fee + '\'' +
                ", feeStatus='" + feeStatus + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(String timeTable) {
        this.timeTable = timeTable;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getOpenDay() {
        return openDay;
    }

    public void setOpenDay(String openDay) {
        this.openDay = openDay;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(String feeStatus) {
        this.feeStatus = feeStatus;
    }
}
