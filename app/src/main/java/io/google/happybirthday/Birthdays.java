package io.google.happybirthday;

/**
 * Created by patel on 10/9/2019.
 */

class Birthdays {
    private int id;
    private String name;
    private String phnum;
    private String date;
    private String wishStr;

    public Birthdays(String name, String phnum, String date,String wishStr) {
        this.name = name;
        this.phnum = phnum;
        this.date = date;
        this.wishStr = wishStr;
    }

    public Birthdays(int id, String name, String phnum, String date, String wishStr) {
        this.id = id;
        this.name = name;
        this.phnum = phnum;
        this.date = date;
        this.wishStr = wishStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhnum() {
        return phnum;
    }

    public void setPhnum(String phnum) {
        this.phnum = phnum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWishStr() {
        return wishStr;
    }

    public void setWishStr(String wishStr) {
        this.wishStr = wishStr;
    }

}
