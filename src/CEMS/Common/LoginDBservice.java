package CEMS.Common;

import java.sql.*;

public class LoginDBservice {

    public User userLogin(User usrObj) {
        try
        {
            Connection conn = Globals.getConnection();
            if (conn != null)
            {
                System.out.println("Database - attempting admin login: " + usrObj.getUserName() );

                CallableStatement cStmt = null;
                String query = "EXEC CEMS_SP_GetUserByUserNameType ?,?";
                cStmt = conn.prepareCall(query);
                cStmt.setString(1, usrObj.getUserName());
                cStmt.setInt(2, usrObj.getUserType());
                ResultSet rs = cStmt.executeQuery();

                String db_pass = "";
                while (rs.next()) {
                    usrObj.setUserID(rs.getInt("UserID"));
                    usrObj.setFirstName(rs.getString("FirstName"));
                    usrObj.setLastName(rs.getString("LastName"));
                    usrObj.setUserDOB(rs.getString("DOB"));
                    usrObj.setUserEmail(rs.getString("Email"));
                    usrObj.setUserPh(rs.getString("Phone_no"));
                    usrObj.setUserType(rs.getInt("UserType"));
                    usrObj.setUserName(rs.getString("UserName"));
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
