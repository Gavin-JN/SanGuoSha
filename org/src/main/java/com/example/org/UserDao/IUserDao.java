package com.example.org.UserDao;

import java.sql.SQLException;

public interface IUserDao
{
//    public void UserLogin(String account, String password) throws SQLException, ClassNotFoundException;

    public void UserRegister(String username,String account,String pwd1,String pwd2,String email) throws SQLException, ClassNotFoundException;
}
