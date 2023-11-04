package CEMS.Members;

import CEMS.Common.Globals;

import java.sql.*;

public class MemberDBservice {

    public boolean saveMember(Member memberObj) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            System.out.println("Database - inserting into Club members");

            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_CreateClubMembers ?,?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setString(1, memberObj.getFirstName());
            cStmt.setString(2, memberObj.getLastName());
            cStmt.setString(3, memberObj.getUserDOB());
            cStmt.setString(4, memberObj.getUserPh());
            cStmt.setString(5, memberObj.getUserEmail());
            cStmt.setString(6, memberObj.clubSelected);
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }

            cStmt.close();
            conn.close();

            System.out.println("Member Created Successfully");
        }
        return isSuccess;
    }
}
