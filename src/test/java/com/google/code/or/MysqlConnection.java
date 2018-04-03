package com.google.code.or;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MysqlConnection{
    private static final Logger logger = LoggerFactory.getLogger(MysqlConnection.class);

    private static Connection conn;

    private static String host;
    private static int port;
    private static String user;
    private static String password;

    public static void setConnection(String hostArg, int portArg, String userArg, String passwordArg){
        try {
            if(conn == null || conn.isClosed()){
                Class.forName("com.mysql.jdbc.Driver");

                host = hostArg;
                port = portArg;
                user = userArg;
                password = passwordArg;

                conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/",user,password);
                logger.info("connected to mysql:{} : {}",user,password);
            }
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
    }

    public static Connection getConnection(){
        try {
            if(conn == null || conn.isClosed()){
                setConnection(host,port,user,password);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
        return conn;
    }

    public static BinlogMasterStatus getBinlogMasterStatus(){
        BinlogMasterStatus binlogMasterStatus = new BinlogMasterStatus();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("show master status");
            while(resultSet.next()){
                binlogMasterStatus.setBinlogName(resultSet.getString("File"));
                binlogMasterStatus.setPosition(resultSet.getLong("Position"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally{
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
            }
        }

        return binlogMasterStatus;
    }

    public static long getServerId(){
        long serverId=6789;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("show variables like 'server_id'");
            while(resultSet.next()){
                serverId = resultSet.getLong("Value");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally{
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
            }
        }

        return serverId;
    }


}