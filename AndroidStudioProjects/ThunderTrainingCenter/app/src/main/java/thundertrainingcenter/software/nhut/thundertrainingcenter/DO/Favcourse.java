package thundertrainingcenter.software.nhut.thundertrainingcenter.DO;

/**
 * Created by Nhut on 8/30/2017.
 */

public class Favcourse {
    String title;
    String group;
    String fee;
    String time;
    String description;

    @Override
    public String toString() {
        return "Favcourse{" +
            "title='" + title + '\'' +
            ", group='" + group + '\'' +
            ", fee='" + fee + '\'' +
            ", time='" + time + '\'' +
            ", description='" + description + '\'' +
            '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
