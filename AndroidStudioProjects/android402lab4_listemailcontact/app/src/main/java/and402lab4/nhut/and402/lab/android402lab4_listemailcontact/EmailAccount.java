package and402lab4.nhut.and402.lab.android402lab4_listemailcontact;

import com.google.api.services.people.v1.model.Person;

import java.util.List;

/**
 * Created by Nhut on 7/22/2017.
 */

public class EmailAccount {
    private String name;
    private String gender;
    private String email;
    private String pictureurl;
    private String token;
    private long endtime;

    public EmailAccount(String name, String gender, String email, String pictureurl, String token) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.pictureurl = pictureurl;
        this.token = "";
        this.endtime = 0;
        setToken(token);
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public String getToken() {
        if (System.currentTimeMillis() > getEndtime()) {
            this.token = "";
            this.endtime = -1;
        }
        return token;
    }

    public void setToken(String token) {
        if (!this.token.equals(token)) {
            this.token = token;
            if (this.token.length() > 0)
                setEndtime(System.currentTimeMillis() + (1000L * (15*60)));
        }
    }

    @Override
    public String toString() {
        return "EmailAccount{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", pictureurl='" + pictureurl + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public static EmailAccount convertFromPeople(Person p) {
        String name = "", gender = "", email = "", photourl = "";
        if (p.getNames() != null) name = p.getNames().get(0).getDisplayName();
        if (p.getPhotos() != null) photourl = p.getPhotos().get(0).getUrl();
        if (p.getEmailAddresses() != null) {
            email = p.getEmailAddresses().get(0).getValue();
            if (p.getEmailAddresses().size() > 1)
                gender = p.getEmailAddresses().get(1).getValue();
        }
        if (p.getGenders() != null) gender = p.getGenders().get(0).getValue();
        if (p.getPhoneNumbers() != null) email += "\n" + p.getPhoneNumbers().get(0).getValue();
        return new EmailAccount(name, gender, email, photourl, "");
    }

    public static EmailAccount[] convertListPerson(List<Person> listPerson) {
        EmailAccount eaarray[] = new EmailAccount[listPerson.size()];
        for (int i = 0; i < listPerson.size(); i++)
            eaarray[i] = EmailAccount.convertFromPeople(listPerson.get(i));
        return eaarray;
    }
}
