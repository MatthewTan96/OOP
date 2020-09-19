package DatabaseB.src;

import java.sql.*;  

public class Main{
        public static void main(String args[]) throws Exception{  
            
            String url = "jdbc:mysql://localhost:3306/psa?serverTimezone=UTC";
            String username = "root";
            String password = ""; // <-- Guys, if you have password here make changes here

            System.out.println("Select all user");
            System.out.println();

            String QueryCode1 = "select * from useraccount"; // Query statment will change depending on the method output we want.
            selectAll(url, username, password, QueryCode1); // get all useraccount

            System.out.println();
            System.out.println("Select all ship infomation");
            System.out.println();
            
            String QueryCode2 = "select * from shippinginfo"; // Query statment will change depending on the method output we want.
            selectAllShips(url, username, password, QueryCode2); // get all useraccount

            System.out.println();
            System.out.println("Insert new user test@outlook.com");
            System.out.println();

            String QueryCode3 = " insert into UserAccount (email, password)"
            + " values (?, ?)"; // Query statment will change depending on the method output we want.

            // Values that we want to insert into table
            String emailEntry = "test@outlook.com";
            String passwordEntry = "123";

            insertIntoUser(url, username, password, QueryCode3, emailEntry, passwordEntry);

            selectAll(url, username, password, QueryCode1); 

            System.out.println();
            System.out.println("Delete user with email test@outlook.com");
            System.out.println();

            String QueryCode4 = " DELETE from UserAccount Where email = ?"; // Query statment will change depending on the method output we want.

            deleteUser(url, username, password, QueryCode4, emailEntry);

            selectAll(url, username, password, QueryCode1); 
        }

        // Get all useraccount method 
        public static void selectAll(String url, String username, String password, String query) throws Exception{

            Class.forName("com.mysql.cj.jdbc.Driver");  

            Connection con = DriverManager.getConnection(url,username,password);  

            Statement stmt = con.createStatement();  

            ResultSet rs = stmt.executeQuery(query); 
                
            while(rs.next()) {
                System.out.println(rs.getString("email") + "  " + rs.getString("password"));  
            } 

            con.close();  
        }

        // Get all ships method 
        public static void selectAllShips(String url, String username, String password, String query) throws Exception{

            Class.forName("com.mysql.cj.jdbc.Driver");  
        
            Connection con = DriverManager.getConnection(url,username,password);  

            Statement stmt = con.createStatement();  
        
            ResultSet rs = stmt.executeQuery(query); 

            while(rs.next()) {
                System.out.println( rs.getString("vesselName") + "  " + rs.getString("incomingVoyage") + " " + 
                    rs.getString("outgoingVoyage") + "  " + rs.getString("bethingTime") + " " + 
                    rs.getString("departureTime") + "  " + rs.getString("berthNo") + " " + 
                    rs.getString("status"));  
            } 
            
            con.close();  
        }

        // Insert into useraccount method 
        public static void insertIntoUser(String url, String username, String password, String query , 
            String emailEntry, String passwordEntry) throws Exception{

            Class.forName("com.mysql.cj.jdbc.Driver");  
                
            Connection con = DriverManager.getConnection(url,username,password);  
            
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, emailEntry);
            preparedStmt.setString(2, passwordEntry);

            preparedStmt.execute();
                                
            con.close();  
        }

        // delete user from useraccount method 
        public static void deleteUser(String url, String username, String password, String query , 
            String emailEntry) throws Exception{
    
                Class.forName("com.mysql.cj.jdbc.Driver");  
                    
                Connection con = DriverManager.getConnection(url,username,password);  
                
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, emailEntry);
    
                preparedStmt.execute();
                                    
                con.close();  
        }
    
}



 
