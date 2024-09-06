package com.example.org.UserDao;

import java.sql.SQLException;

public interface SQLExec{

    public boolean queryAccount(String account,String password) throws SQLException;
    public boolean queryAccount(String account) throws SQLException;
    public void addAccount(String username,String account,String password,String email) throws SQLException;
    public void removeAccount(String account) throws SQLException;

    public void changeData(String account,String password) throws SQLException;

    public void closeConnection() throws SQLException;
}
