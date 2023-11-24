package CEMS.Reports;

import CEMS.Common.Globals;
import CEMS.Events.Event;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDBservice {

    public List<Event> searchEvents(int memberID, EventReport eventReport) throws SQLException {
        List<Event> eventList = new ArrayList<>();

        Connection conn = Globals.getConnection();
        if (conn != null){
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_ReportSearchEvent ?,?,?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, memberID);
            cStmt.setString(2, eventReport.getEventName());
            cStmt.setString(3, eventReport.getEventVenue());
            cStmt.setString(4, eventReport.getEventStartDate());
            cStmt.setString(5, eventReport.getEventSearchStartDate());
            cStmt.setString(6, eventReport.getEventEndDate());
            cStmt.setString(7, eventReport.getEventSearchEndDate());

            ResultSet rs = cStmt.executeQuery();

            Event event;
            while (rs.next()) {
                event = new Event();
                event.setEventID(rs.getInt("EventID"));
                event.setEventName(rs.getString("EventName"));
                event.setEventStartDate(rs.getString("EventStartDate"));
                event.setEventEndDate(rs.getString("EventEndDate"));
                event.setEventVenue(rs.getString("EventVenue"));
                event.setEventDesc(rs.getString("EventDesc"));
                event.setEventCreatedBy(rs.getString("CreatedBy"));
                event.setEventCreatedDate(rs.getString("CreatedDate"));
                event.setEventTotalCost(rs.getFloat("TotalCost"));
                eventList.add(event);
            }
            cStmt.close();
            conn.close();
        }
        return eventList;
    }
}
