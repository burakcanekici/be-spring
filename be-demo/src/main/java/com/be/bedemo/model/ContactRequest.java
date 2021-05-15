package com.be.bedemo.model;

public class ContactRequest {
    private String name;
    private String surname;
    private String contact;
    private String userName;
    private int averageGross;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAverageGross() {
        return averageGross;
    }

    public void setAverageGross(int averageGross) {
        this.averageGross = averageGross;
    }
}
