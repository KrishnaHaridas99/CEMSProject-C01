package CEMS.Admin;

import CEMS.Common.Globals;
import CEMS.Common.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDBservice {

    public boolean adminLogin(User usrObj) {
        try
        {
            Connection conn = Globals.getConnection();
            if (conn != null)
            {
                System.out.println("Database - attempting admin login");

                String query = "select top 1 [UserID], [FirstName], [LastName], [DOB], [Email], [Phone_no], [UserType], [UserName], [Password] from CEMS_Users where UserName = '" + usrObj.getUserName() + "'";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                String db_pass = "";
                while (rs.next()) {
                    db_pass = rs.getString("Password");
                }

                if (usrObj.getPassword().equals(db_pass))
                    return true;
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
        return false;
    }
}
