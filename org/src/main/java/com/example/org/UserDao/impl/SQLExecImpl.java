package com.example.org.UserDao.impl;

import com.example.org.UserDao.SQLExec;

import java.sql.*;
public class SQLExecImpl implements SQLExec
{

    private Connection conn;
    public SQLExecImpl() throws ClassNotFoundException {
        String url="jdbc:mysql://192.168.185.82:3306/db1?useSSL=false&allowPublicKeyRetrieval=true";
        String username="root";
        String password="17727096201";

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, username, password);
//            Statement stmt = conn.createStatement();
            if (this.conn != null) {
                System.out.println("Connection success");
            } else {
                System.out.println("Connection failed: Connection object is null");
            }
        }catch (SQLException e)
        {
            System.out.println("Connctions failed:"+e);
        }
        System.out.println("Connctions success");
    }




    @Override
    public boolean queryAccount(String username, String password) throws SQLException {
        if (this.conn == null) {
            System.out.println("No database connection available.");
            return false;
        }
        boolean isExist=false;
        String sql="SELECT * FROM account where username=? and password=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,username);
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
    public boolean queryAccount(String username) throws SQLException {
        if (this.conn == null) {
            System.out.println("No database connection available.");
            return false;
        }
        boolean isExist=false;
        System.out.printf(String.valueOf(isExist));
        String sql="SELECT * FROM account where username=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,username);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()) {
            isExist=true;
            System.out.println("账号已存在");
        }
        pstmt.close();
        return isExist;
    }

    @Override
    public void addAccount(String username,String password, String nickname,String email) throws SQLException {
        if (this.conn == null) {
            System.out.println("No database connection available.");
            return;
        }
        String sql="INSERT INTO account(username,account,password,email) value(?,?,?,?)";
        PreparedStatement pstmt=conn.prepareStatement(sql);

        pstmt.setString(1,username);
        pstmt.setString(2,password);
        pstmt.setString(3,nickname);
        pstmt.setString(4,email);

        int count=pstmt.executeUpdate();
        System.out.println(count>0);

        pstmt.close();
    }







    @Override
    public void removeAccount(String username) throws SQLException {
        if (this.conn == null) {
            System.out.println("No database connection available.");
            return;
        }
        String sql="DELETE FROM account where username=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,username);
        int count=pstmt.executeUpdate();
        System.out.println(count>0);

        pstmt.close();
    }

    @Override
    public void changeData(String username,String password) throws SQLException {
        if (this.conn == null) {
            System.out.println("No database connection available.");
            return;
        }
        String sql="UPDATE account\n"+
                "set password=?\n"+
                "where username=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,password);
        pstmt.setString(2,username);
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
