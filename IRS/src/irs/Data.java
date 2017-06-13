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
    private static final String DBNAME = "IRS_Data";
    //private Connection conn;
    
    public Data(){
    
    }
    
    //build connection to database
    public Connection connect(){
        Connection connect = null;
        
        try{
            Class.forName(DRIVER).newInstance();
		//System.out.println("Loaded the embedded driver.");
        } catch(ClassNotFoundException | IllegalAccessException 
                | InstantiationException err){
            System.err.println("Unable to load the embedded driver.");
			err.printStackTrace(System.err);
			//System.exit(0);
        }
                
        try {
            connect = DriverManager.getConnection(PROTOCOL + DBNAME );
            //System.out.println("connect DB successfully");
            
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
            //System.out.println("disconnect successfully");
            
        } catch(SQLException err){
           System.err.println("disconnect database fails.");
           err.printStackTrace(System.err);   
           return false;
        }
        return true;
    }    
}
