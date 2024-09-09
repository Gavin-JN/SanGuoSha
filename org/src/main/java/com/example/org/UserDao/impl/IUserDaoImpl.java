package com.example.org.UserDao.impl;

import com.example.org.UserDao.IUserDao;

import java.sql.SQLException;

public class IUserDaoImpl implements IUserDao {

    public IUserDaoImpl(){

    }


//    @Override
//    public boolean UserLogin(String username, String password) throws SQLException, ClassNotFoundException {
//       SQLExecImpl sqlExec=new SQLExecImpl();
//       return sqlExec.queryAccount(username,password);
//    }




    @Override
    public boolean UserRegister(String username,String nickname, String pwd1, String pwd2,String email) throws SQLException, ClassNotFoundException {
        boolean isReg=false;
        SQLExecImpl sqlExec=new SQLExecImpl();
        if(!sqlExec.queryAccount(username)) {
            if(pwd1.equals(pwd2)){
                sqlExec.addAccount(username,nickname,pwd1,email);
                isReg=true;
                //相应成功
                System.out.println("注册成功");
            }
            else
            {
                System.out.println("密码不一致，请重新注册");
            }
        }else{
            System.out.println("账号已存在");
            //相应注册界面
        }
        return isReg;
    }

}
