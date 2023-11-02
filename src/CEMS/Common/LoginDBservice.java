package CEMS.Common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDBservice {

    public User userLogin(User usrObj) {
        try
        {
            Connection conn = Globals.getConnection();
            if (conn != null)
            {
                System.out.println("Database - attempting admin login: " + usrObj.getUserName() );

                String query = "select top 1 [UserID], [FirstName], [LastName], [DOB], [Email], [Phone_no], [UserType], [UserName], [Password] from CEMS_Users where UserName = '" + usrObj.getUserName() + "'";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                String db_pass = "";
                while (rs.next()) {
                    usrObj.setUserID(rs.getInt("UserID"));
                    usrObj.setFirstName(rs.getString("FirstName"));
                    usrObj.setLastName(rs.getString("LastName"));
                    usrObj.setUserDOB(rs.getString("DOB"));
                    usrObj.setUserEmail(rs.getString("Email"));
                    usrObj.setUserPh(rs.getString("Phone_no"));
                    usrObj.setUserType(rs.getString("UserType"));
                    db_pass = rs.getString("Password");
                }

                if (usrObj.getPassword().equals(db_pass) && !usrObj.getPassword().isEmpty())
                    usrObj.isValidUser = true;
            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch (SQLException e)
        {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return usrObj;
    }
}
