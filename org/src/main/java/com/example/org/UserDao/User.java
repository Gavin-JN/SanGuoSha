package com.example.org.UserDao;

public class
User
{
    private String username;
    private String account;

    private String email;
    private String password;

    private String status;

    private String duration;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUsername() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getDuration() {
        return duration;
    }

    public String toString()
    {
        return "User{"+
                "username="+getUsername()+
                ",account="+getAccount()+
                ",duartion="+getDuration();
    }
}
