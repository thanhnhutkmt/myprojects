package thundertrainingcenter.software.nhut.thundertrainingcenter.DO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhut on 9/1/2017.
 */

public class ShowCourse {
    String title;
    String description;
    List<Regcourse> list = new ArrayList<>();

    @Override
    public String toString() {
        return "showCourse{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", list=" + list +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Regcourse> getList() {
        return list;
    }

    public void setList(List<Regcourse> list) {
        this.list = list;
    }
}
