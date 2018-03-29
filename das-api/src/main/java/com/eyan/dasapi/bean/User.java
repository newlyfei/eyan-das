package com.eyan.dasapi.bean;

import java.util.Date;

public class User {
    private String no, name;
    private Date createdDate;

    public User(String s, String s1, Date date) {
        this.no=s;
        this.name=s1;
        this.createdDate=date;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
