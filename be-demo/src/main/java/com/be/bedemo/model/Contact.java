package com.be.bedemo.model;

public class Contact {
    private String name;
    private String surname;
    private String mail;
    private String userName;
    private int annualGross;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAnnualGross() {
        return annualGross;
    }

    public void setAnnualGross(int annualGross) {
        this.annualGross = annualGross;
    }
}
