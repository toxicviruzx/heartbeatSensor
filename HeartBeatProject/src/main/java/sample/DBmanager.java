package sample;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;




/**
 * Created by TuanDung on 12/1/2017.
 */
public class DBmanager {
    private static DBmanager uniqueInstance=null;
    private static Connection connection = null ;

    private DBmanager(){
        if(!dbExists())
        {
            System.err.println(">>> DBManager: The database doesn't exist ...") ;
        }
    }

    public static synchronized DBmanager getInstance() {
        if (uniqueInstance==null) {
            uniqueInstance = new DBmanager();
        }
        return uniqueInstance;
    }

    private Boolean dbExists() {
        Boolean exists = false;
        Statement statement = null;
        try {
            if(connection == null) makeConnection();
            statement = connection.createStatement();
            statement.executeQuery( "USE heartbeat;" );
        }
        catch( SQLException se ) {
            se.printStackTrace();
        }
        finally {
            try {
                if(statement != null) {
                    statement.close();
                    exists = true;
                }
            }
            catch( SQLException se ) {
                se.printStackTrace();
                statement = null;
            }
        }
        return exists;
    }

    public void makeConnection() {
        int lport=5656;
        String rhost="localhost";
        String host="45.32.58.144";
        int rport=3306;
        String userssh="root";
        String passwordssh="wujijilzx";
        String dbuserName = "root";
        String dbpassword = "123";
        String url = "jdbc:mysql://localhost:"+lport+"/sys?autoReconnect=true&useSSL=false"; // disable SSL
        String driverName="com.mysql.jdbc.Driver";
//        Connection conn = null;
        Session session= null;
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        try {
            session=jsch.getSession(userssh, host, 22);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        session.setPassword(passwordssh);
        session.setConfig(config);
        try {
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        System.out.println("Connected");
        int assinged_port= 0;
        try {
            assinged_port = session.setPortForwardingL(lport, rhost, rport);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
        System.out.println("Port Forwarded");

        //mysql database connectivity
        try {
            Class.forName(driverName).newInstance();
            connection = DriverManager.getConnection (url, dbuserName, dbpassword);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        finally{
//            try {
//                if(connection != null && !connection.isClosed()){
//                    System.out.println("Closing Database Connection");
//                    try {
//                        connection.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            if(session !=null && session.isConnected()){
//                System.out.println("Closing SSH Connection");
//                session.disconnect();
//            }
//        }
    }

    public void close() {
        try {
            connection.close();
            uniqueInstance = null;
            connection = null;
        } catch (SQLException
                e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }

}

/*
public class DBmanager {
    private static DBmanager uniqueInstance=null;
    private static Connection connection = null ;

    private DBmanager(){
        if(!dbExists())
        {
            System.err.println(">>> DBManager: The database doesn't exist ...") ;
        }
    }

    public static synchronized DBmanager getInstance() {
        if (uniqueInstance==null) {
            uniqueInstance = new DBmanager();
        }
        return uniqueInstance;
    }

    private Boolean dbExists() {
        Boolean exists = false;
        Statement statement = null;
        try {
            if(connection == null) makeConnection();
            statement = connection.createStatement();
            statement.executeQuery( "USE ltdung_weatherproject;" );
        }
        catch( SQLException se ) {
            se.printStackTrace();
        }
        finally {
            try {
                if(statement != null) {
                    statement.close();
                    exists = true;
                }
            }
            catch( SQLException se ) {
                se.printStackTrace();
                statement = null;
            }
        }
        return exists;
    }


    public void makeConnection()
    {
        FileInputStream in = null;
        try{
            Properties props = new Properties();
            in = new FileInputStream("database.properties");
            props.load(in);
            in.close();
            String db_url = props.getProperty("jdbc.db_url");
            String db_params = props.getProperty("jdbc.db_params");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            connection = DriverManager.getConnection(db_url+db_params, username, password);
        } catch( SQLException se ) {
            System.err.println("Connection error....") ;
        }catch ( Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            } // nothing we can do
        }

    public void close() {
        try {
            connection.close();
            uniqueInstance=null;
            connection=null;
        } catch (SQLException
                e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }

}
 */