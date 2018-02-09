package lab.and401.nhut.and401lab9_ormlitelib;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Nhut on 6/29/2017.
 */

public class Person {
    @DatabaseField(generatedId = true)
    private int accountId;
    @DatabaseField
    private String name;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
