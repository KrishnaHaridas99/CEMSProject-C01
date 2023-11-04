package CEMS.Student;

import CEMS.Common.Globals;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDBservice {
    public boolean saveStudent(Student studObj) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            System.out.println("Database - inserting into Club members");

            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_CreateStudent ?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setString(1, studObj.getFirstName());
            cStmt.setString(2, studObj.getLastName());
            cStmt.setString(3, studObj.getUserDOB());
            cStmt.setString(4, studObj.getUserPh());
            cStmt.setString(5, studObj.getUserEmail());
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }

            cStmt.close();
            conn.close();

            System.out.println("Student Created Successfully");
        }
        return isSuccess;
    }
}
