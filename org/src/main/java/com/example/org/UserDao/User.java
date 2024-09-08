package com.example.org.UserDao;

public class
User
{
    //用户名
    private String username;
    //昵称
    private String nickname;
    //密码
    private String password;
    //邮箱
    private String email;

    private String status;
    private String duration;


    //set和get方法
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getDuration() {
        return duration;
    }


    public String toString()
    {
        return "User{"+
                "username="+getUsername()+
                ",nickname="+getNickname()+
                ",duartion="+getDuration();
        //////wodsfhsiefjo
    }
}
