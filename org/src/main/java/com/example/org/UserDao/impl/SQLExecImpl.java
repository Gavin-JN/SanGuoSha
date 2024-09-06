package com.example.org.UserDao.impl;

import com.example.org.UserDao.SQLExec;

import java.sql.*;

public class SQLExecImpl implements SQLExec
{
    private Connection conn;
    SQLExecImpl()
    {
        String url="jdbc:mysql://192.168.3.31:3306/db1";
        String username="username";
        String password="Lijingwen";

        try
        {
            this.conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
        }catch (SQLException e)
        {
            System.out.println("Connctions failed:"+e);
        }
        System.out.println("Connctions success");
    }

    @Override
    public boolean queryAccount(String account, String password) throws SQLException {
        boolean isExist=false;
        String sql="SELECT * FROM account where account=? and password=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,account);
        pstmt.setString(2,password);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()) {
            isExist=true;
            System.out.println("登录成功");

        }
        else{
            System.out.println("登录失败");
        }
        pstmt.close();
        return isExist;
    }

    @Override
    public boolean queryAccount(String account) throws SQLException {
        boolean isExist=false;
        String sql="SELECT * FROM account where account=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,account);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()) {
            isExist=true;
            System.out.println("账号已存在");

        }
        pstmt.close();
        return isExist;
    }

    @Override
    public void addAccount(String username,String account, String password,String email) throws SQLException {
        String sql="INSERT INTO account(username,account,password,email) value(?,?,?,?)";
        PreparedStatement pstmt=conn.prepareStatement(sql);

        pstmt.setString(1,username);
        pstmt.setString(2,account);
        pstmt.setString(3,password);
        pstmt.setString(4,email);

        int count=pstmt.executeUpdate();
        System.out.println(count>0);

        pstmt.close();
    }

    @Override
    public void removeAccount(String account) throws SQLException {
        String sql="DELETE FROM account where account=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,account);
        int count=pstmt.executeUpdate();
        System.out.println(count>0);

        pstmt.close();
    }

    @Override
    public void changeData(String account,String password) throws SQLException {
        String sql="UPDATE account\n"+
                "set password=?\n"+
                "where account=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,password);
        pstmt.setString(2,account);
        int count=pstmt.executeUpdate();
        System.out.println(count>0);

        pstmt.close();

    }

    public void closeConnection() throws SQLException {
        if(conn!=null||!conn.isClosed())
        {
            conn.close();
        }
    }


}
