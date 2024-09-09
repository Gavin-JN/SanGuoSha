package com.example.org.UserDao;

import java.sql.SQLException;

public interface IUserDao
{
//    public boolean UserLogin(String username, String password) throws SQLException, ClassNotFoundException;

    public boolean UserRegister(String username,String account,String pwd1,String pwd2,String email) throws SQLException, ClassNotFoundException;
}
