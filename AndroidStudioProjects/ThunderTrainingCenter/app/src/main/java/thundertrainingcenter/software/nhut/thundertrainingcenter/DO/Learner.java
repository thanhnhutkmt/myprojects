package thundertrainingcenter.software.nhut.thundertrainingcenter.DO;

/**
 * Created by Nhut on 8/30/2017.
 */

public class Learner {
    String fullName;
    String sex;
    String birthday;
    String address;
    String mobilePhone;
    String email;
    String iDcard;
    String image;

    @Override
    public String toString() {
        return "Learner{" +
                "fullName='" + fullName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", iDcard='" + iDcard + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getiDcard() {
        return iDcard;
    }

    public void setiDcard(String iDcard) {
        this.iDcard = iDcard;
    }
}
