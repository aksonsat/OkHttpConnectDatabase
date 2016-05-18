package com.softwareranger.okhttpconnectdatabase.model;

/**
 * Created by Pratik on 04-Jan-16.
 */
public class Model {

    private int id;

    private String user;

    private String password;

    private String name;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
