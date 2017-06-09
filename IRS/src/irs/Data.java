/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Statement;

/**
 *
 * @author Oliver
 */
public class Data {
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String PROTOCOL = "jdbc:derby:";
    private static final String DBNAME = "IRS";
    private Connection conn;
    
    public Data(){
    
    }
    
    //build connection to database
    public Connection connect(){
        Connection connect = null;
        
        try{
            Class.forName(DRIVER).newInstance();
		System.out.println("Loaded the embedded driver.");
        } catch(ClassNotFoundException | IllegalAccessException 
                | InstantiationException err){
            System.err.println("Unable to load the embedded driver.");
			err.printStackTrace(System.err);
			//System.exit(0);
        }
                
        try {
            connect = DriverManager.getConnection(PROTOCOL + DBNAME );
            
        } catch(SQLException err){
           System.err.println("database connection error.");
           err.printStackTrace(System.err);
           //System.exit(0);
        }
        
        return connect;
    }
    
    //disconnect to database
    public boolean disconnect(Connection connect){
        try {
            connect.close();   
            
        } catch(SQLException err){
           System.err.println("disconnect database fails.");
           err.printStackTrace(System.err);   
           return false;
        }
        return true;
    }
    
    /*************************************************************************
     *    codes for create DB and tables and insert test data                *
     *  from OLD PROJECT HeartRateMonitor (Git: oliverchi.heartratemonitor ) *
     *               connectDB()                                             *
     *               closeConnection()                                       *
     *               createDB()                                              *
     *               executeSQL()                                            *
     *               createTables()                                          *
    **************************************************************************/
    /**
     * Method connectDB()
     * @return boolean value if it is successful to connect database
     */
    /*
    public boolean connectDB(){
                
        try {
            this.conn = DriverManager.getConnection(PROTOCOL + DBNAME );
            
        } catch(SQLException err){
           System.err.println("database connection error.");
           err.printStackTrace(System.err);
           //System.exit(0);
           return false;
        }
        return true;        
    }
    */
          
    
    /**
     * Method closeConnection()
     * This method has to be called after all other methods are completed
     */
    /*
    public void closeConnection(){
        try {
            this.conn.close();   
            
        } catch(SQLException err){
           System.err.println("disconnect database fails.");
           err.printStackTrace(System.err);           
        }
    }
    
    /**
     * Method createDB()
     * @return boolean value if it is successful to create database
     * used by createTables() to initial data
     */
    /*
    public boolean createDB(){
        try{
            Class.forName(DRIVER).newInstance();
		System.out.println("Loaded the embedded driver.");
        } catch(ClassNotFoundException | IllegalAccessException 
                | InstantiationException err){
            System.err.println("Unable to load the embedded driver.");
			err.printStackTrace(System.err);
			//System.exit(0);
        }
        
        try {
            this.conn = DriverManager.getConnection(PROTOCOL + DBNAME + ";create=true");
            
        } catch(SQLException err){
           System.err.println("create database error.");
           err.printStackTrace(System.err);
           //System.exit(0);
           return false;
        }
        return true;        
    }
    */
    
    /**
     * Method executeSQL()
     * @param sql: SQL statement for execution
     * @return boolean value if it is successful to execute SQL statement
     */
    /*
    public boolean executeSQL(String sql){
        try {
            Statement s = conn.createStatement();
            s.execute(sql);   
            
        } catch(SQLException err){
           System.err.println("SQL execute error.");
           err.printStackTrace(System.err);
           //System.exit(0);
           return false;
        }
        return true; 
    }
    */
    
    /**
     * Method createTables()
     * initial the tables in the IRS database
     * @return boolean
     */
    /*
    public boolean createTables(){
           
        //create database
        if (createDB()){
            System.out.println("create DB successful");
        } else {
            System.out.println("create DB unsuccessful");
            return false;
        }
        
        
        //create table for all users and initialise test data
        if ( connectDB() ) {
            System.out.println("connnect database successful(login table)");
            if ( executeSQL ( "CREATE TABLE login ("
                    + " username VARCHAR(40) NOT NULL , "
                    + "password VARCHAR(40) NOT NULL, "
                    + "staffid VARCHAR(40) NOT NULL, "
                    + "position VARCHAR(40) NOT NULL, "
                    + "PRIMARY KEY (username) )" ) ) {
                System.out.println("login table create successfully.");
            } else {
                System.out.println("unable to create login table.");
                return false;                
            }
            //give test data for user
            executeSQL("INSERT INTO login (username, password, staffid, position) VALUES "
                    + "('Betty123', 'b1234567', 'NM1001', 'NM')");
            executeSQL("INSERT INTO login (username, password, staffid, position) VALUES "
                    + "('Emma123', 'e1234567', 'RN1001', 'RN')");
            closeConnection();
        } else {
            System.out.println("connect database unsuccessful");
            return false;
        }
        
        //create table for patient information
        if ( connectDB() ) {
            System.out.println("connnect database successful (table patient)");
            if (executeSQL( "CREATE TABLE patient ( id VARCHAR(5) NOT NULL, "
                    + "firstname VARCHAR(40) NOT NULL, "
                    + "lastname VARCHAR(40) NOT NULL, "
                    + "dob DATE NOT NULL , "
                    + "phone VARCHAR(10) NOT NULL, "
                    + "street VARCHAR(60) NOT NULL, "
                    + "city VARCHAR(40) NOT NULL, "
                    + "state VARCHAR(3) NOT NULL , "
                    + "postcode VARCHAR(4) NOT NULL, "
                    + "PRIMARY KEY (id))" ) ){
                System.out.println("table patient creates successfully");
            } else {
                System.out.println("unable to create table patient"); 
                return false;
            }
            //insert data
            executeSQL("INSERT INTO patient (id, firstname, "
                    + "lastname, dob, phone, street, city, "
                    + "state, postcode) VALUES ('10001',"
                    + " 'Oliver', 'Chi', '1986-04-04', '0401553311', "
                    + " '104 Radford Street', 'Manly', "
                    + "'QLD', '4135')" );
            executeSQL("INSERT INTO patient (id, firstname, "
                    + "lastname, dob, phone, street, city, "
                    + "state, postcode) VALUES ('10002',"
                    + " 'James', 'Jackson', '1966-01-02', '0402323232', "
                    + " '56 Logan Road', 'Runcorn', "
                    + "'QLD', '4172')" );
            executeSQL("INSERT INTO patient (id, firstname, "
                    + "lastname, dob, phone, street, city, "
                    + "state, postcode) VALUES ('10003',"
                    + " 'Michael', 'Washington', '1945-06-07', '0423553313', "
                    + " '12 melbourne Street', 'West end', "
                    + "'QLD', '4101')" );
            executeSQL("INSERT INTO patient (id, firstname, "
                    + "lastname, dob, phone, street, city, "
                    + "state, postcode) VALUES ('10004',"
                    + " 'Lucy', 'Thomas', '1943-02-06', '0413553314', "
                    + " '30 west Street', 'Sunnybank', "
                    + "'QLD', '4138')" );
            closeConnection();
        } else {
            System.out.println("fail to connect database");
            return false;
        }
        
        //create table for results
        if (connectDB()) {
            System.out.println("connnect database successfully");
            if (executeSQL ( "CREATE TABLE results ("
                    + " id VARCHAR(5) NOT NULL , "
                    + "date DATE NOT NULL, "
                    + "result INTEGER NOT NULL, "
                    + "PRIMARY KEY (id, date))" ) ) {
                System.out.println("table results created successfully");
            } else {
                System.out.println("unable to create table results");   
                return false;
            }
            //insert data
            //for James Jackson
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-01', 89)");            
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-02', 94)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-03', 100)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-04', 103)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-05', 89)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-06', 78)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-07', 54)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-08', 68)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-09', 78)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10002', '2013-01-10', 54)");
            //for Michael Washington
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-01', 56)");            
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-02', 78)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-03', 79)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-04', 69)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-05', 78)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-06', 67)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-07', 59)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-08', 58)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-09', 89)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10003', '2013-01-10', 58)");
            //for Lucy Thomas
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-01', 90)");            
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-02', 90)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-03', 98)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-04', 97)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-05', 100)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-06', 102)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-07', 90)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-08', 102)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-09', 105)");
            executeSQL("INSERT INTO results (id, date, result) VALUES "
                    + "('10004', '2013-01-10', 104)");
            closeConnection();
        } else {
            System.out.println("connect database unsuccessfully");
            return false;
        }
        
        //create table for parameters of recommendation algorithm and initialise them with test data
        if ( connectDB() ) {
            System.out.println("connnect database successful(parameters table)");
            if ( executeSQL ( "CREATE TABLE parameters ("
                    + "patientID VARCHAR(5) NOT NULL , "
                    + "minrate INTEGER NOT NULL , "
                    + "maxrate INTEGER NOT NULL, "
                    + "k INTEGER NOT NULL, "
                    + "p INTEGER NOT NULL, "
                    + "PRIMARY KEY (patientID) )" ) ) {
                System.out.println("parameters table create successfully.");
            } else {
                System.out.println("unable to create parameters table.");
                return false;                
            }
            //give test data for parameters
            executeSQL("INSERT INTO parameters (patientID, minrate, maxrate, k, p) VALUES "
                    + "('00000', 60, 100, 7, 100)");
            executeSQL("INSERT INTO parameters (patientID, minrate, maxrate, k, p) VALUES "
                    + "('10002', 60, 100, 7, 100)");
            executeSQL("INSERT INTO parameters (patientID, minrate, maxrate, k, p) VALUES "
                    + "('10003', 60, 100, 7, 100)");
            executeSQL("INSERT INTO parameters (patientID, minrate, maxrate, k, p) VALUES "
                    + "('10004', 60, 100, 7, 100)");
            closeConnection();
        } else {
            System.out.println("connect database unsuccessful");
            return false;
        }
        
        return true;
    }
    */
    
}
