package com.example.org.UserDao;

import java.sql.SQLException;

public interface SQLExec{

    public boolean queryAccount(String username,String password) throws SQLException;
    public boolean queryAccount(String username) throws SQLException;
    public void addAccount(String username,String nickname,String password,String email) throws SQLException;

    public void removeAccount(String username) throws SQLException;
    public void changeData(String username,String password) throws SQLException;
    public void closeConnection() throws SQLException;
}
