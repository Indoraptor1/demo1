package mavenapplication;


import java.sql.Connection;
import java.sql.DriverManager;



 
public class MyConnection {
    
    
   
    
    public static Connection getConnection(){
     
        Connection con = null;
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?"
                    + "autoReconnect=true"
                    + "&useSSL=false"
                    + "&useUnicode=true"
                    + "&useJDBCCompliantTimezoneShift=true"
                    + "&useLegacyDatetimeCode=false"
                    + "&serverTimezone=CET"
                    ,"root", "root");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return con;
    }
    
}
