package com.royken.bracongo.mobile.util;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by royken on 21/04/16.
 */
public class Toto {
    @Expose
    String user;
    @Expose
    String pass;
    @Expose(serialize = false)
    Date date;
    @Expose(serialize = false)
    Boolean zozo;
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Toto(String user, String pass, Date date) {
        this.user = user;
        this.pass = pass;
        this.date = date;
    }

    public Toto(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getZozo() {
        return zozo;
    }

    public void setZozo(Boolean zozo) {
        this.zozo = zozo;
    }
}
