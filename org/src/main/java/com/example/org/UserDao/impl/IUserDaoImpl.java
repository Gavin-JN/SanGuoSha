package com.example.org.UserDao.impl;

import com.example.org.UserDao.IUserDao;

import java.sql.SQLException;

public class IUserDaoImpl implements IUserDao {
    @Override
    public void UserLogin(String account, String password) throws SQLException {
        SQLExecImpl sqlExec=new SQLExecImpl();
        if(sqlExec.queryAccount(account,password))
        {
//            允许连接
        }

    }

    @Override
    public void UserRegister(String username,String account, String pwd1, String pwd2,String email) throws SQLException {
        SQLExecImpl sqlExec=new SQLExecImpl();
        if(!sqlExec.queryAccount(account)) {
            if(pwd1.equals(pwd2)){
                sqlExec.addAccount(username,account,pwd1,email);
                System.out.println("注册成功");
            }
        }
    }
}
