package CEMS.Events;

import CEMS.Common.Globals;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventDBservice {
    public boolean saveEvent(Event eventObj) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            System.out.println("Database - inserting into Event");

            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_CreateEvent ?,?,?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setString(1, eventObj.EventName);
            cStmt.setString(2, eventObj.ClubID);
            cStmt.setString(3, eventObj.EventStartDate);
            cStmt.setString(4, eventObj.EventEndDate);
            cStmt.setString(5, eventObj.EventDesc);
            cStmt.setString(6, eventObj.EventVenue);
            cStmt.setString(7, eventObj.CreatedBy);
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }

            cStmt.close();
            conn.close();

            System.out.println("Event Created Successfully");
        }
        return isSuccess;
    }
}
