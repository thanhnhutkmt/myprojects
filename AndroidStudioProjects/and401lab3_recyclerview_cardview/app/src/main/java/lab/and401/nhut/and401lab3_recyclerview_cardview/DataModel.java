package lab.and401.nhut.and401lab3_recyclerview_cardview;

/**
 * Created by Nhut on 6/22/2017.
 */

public class DataModel {
    private String name;
    private int id_;

    public DataModel(String name, int id) {
        this.name = name;
        this.id_ = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }

    @Override
    public String toString() {
        return id_ + ")" + name;
    }
}

