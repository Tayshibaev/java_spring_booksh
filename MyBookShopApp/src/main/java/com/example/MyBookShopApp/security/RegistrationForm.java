package com.example.MyBookShopApp.security;

public class RegistrationForm {

    private String name;
    private String mail;

    private String phone;
    private String pass;

    private String passApprove;

    public String getPassApprove() {
        return passApprove;
    }

    public void setPassApprove(String passApprove) {
        this.passApprove = passApprove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                ", pass='" + pass + '\'' +
                ", passApprove='" + passApprove + '\'' +
                '}';
    }
}
