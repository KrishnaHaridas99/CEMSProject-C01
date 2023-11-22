package CEMS.Events;

import CEMS.Common.Globals;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDBservice {
    public boolean saveEvent(Event eventObj) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            System.out.println("Database - inserting into Event");

            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_CreateEvent ?,?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setString(1, eventObj.getEventName());
            cStmt.setString(2, eventObj.getEventStartDate());
            cStmt.setString(3, eventObj.getEventEndDate());
            cStmt.setString(4, eventObj.getEventDesc());
            cStmt.setString(5, eventObj.getEventVenue());
            cStmt.setString(6, eventObj.getEventCreatedBy());
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

    public boolean updateEvent(Event eventObj) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_UpdateEventDetails ?,?,?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, eventObj.getEventID());
            cStmt.setString(2, eventObj.getEventName());
            cStmt.setString(3, eventObj.getEventStartDate());
            cStmt.setString(4, eventObj.getEventEndDate());
            cStmt.setString(5, eventObj.getEventDesc());
            cStmt.setString(6, eventObj.getEventVenue());
            cStmt.setString(7, eventObj.getEventCreatedBy());
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }
            cStmt.close();
            conn.close();
        }
        return isSuccess;
    }

    public boolean saveEventBudget(EventBudget budgetObj) throws Exception{
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null){
            System.out.println("Database - inserting into EventBudget");

            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_CreateEventExpense ?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setString(1, String.valueOf(budgetObj.getEventID()));
            cStmt.setString(2, budgetObj.getExpenseName());
            cStmt.setFloat(3, budgetObj.getCost());
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }

            cStmt.close();
            conn.close();

            System.out.println("Event budget Created Successfully");
        }
        return  isSuccess;
    }

    public boolean updateEventBudget(EventBudget budgetObj) throws Exception{
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null){
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_UpdateEventBudget ?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, budgetObj.getEventID());
            cStmt.setInt(2, budgetObj.getBudgetID());
            cStmt.setString(3, budgetObj.getExpenseName());
            cStmt.setFloat(4, budgetObj.getCost());
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }
            cStmt.close();
            conn.close();
        }
        return  isSuccess;
    }

    public List<Event> getEventsList(int memID) throws SQLException {
        List<Event> eventList = new ArrayList<>();

        Connection conn = Globals.getConnection();
        if (conn != null){
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_GetEventByMemberClub ?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, memID);

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
                event.setEventClubID(rs.getString("ClubID"));
                event.setEventClubName(rs.getString("ClubName"));
                eventList.add(event);
            }
            cStmt.close();
            conn.close();
        }
        return eventList;
    }

    public List<EventBudget> getEventBudget(int EventID) throws SQLException {
        List<EventBudget> eventBudgetList = new ArrayList<>();

        Connection conn = Globals.getConnection();
        if (conn != null){
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_GetEventBudgetByEventID ?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, EventID);

            ResultSet rs = cStmt.executeQuery();

            EventBudget eventBudget;
            while (rs.next()) {
                eventBudget = new EventBudget();
                eventBudget.setEventID(rs.getInt("EventID"));
                eventBudget.setEventName(rs.getString("EventName"));
                eventBudget.setBudgetID(rs.getInt("ID"));
                eventBudget.setExpenseName(rs.getString("ExpenseName"));
                eventBudget.setCost(rs.getFloat("Cost"));

                eventBudgetList.add(eventBudget);
            }
            cStmt.close();
            conn.close();
        }
        return eventBudgetList;
    }

    public List<Event> getAllEventsList() throws SQLException {
        List<Event> eventList = new ArrayList<>();

        Connection conn = Globals.getConnection();
        if (conn != null){
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_GetAllEvents";
            cStmt = conn.prepareCall(query);

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
                event.setEventClubID(rs.getString("ClubID"));
                event.setEventClubName(rs.getString("ClubName"));
                eventList.add(event);
            }
            cStmt.close();
            conn.close();
        }
        return eventList;
    }

    public boolean deleteEvent(Event event) throws Exception {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_DeleteEvent ?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, event.getEventID());

            ResultSet rs = cStmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }
            cStmt.close();
            conn.close();
        }
        return isSuccess;
    }

    public boolean deleteEventExpense(EventBudget budgetObj) throws Exception {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_DeleteEventBudget ?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, budgetObj.getBudgetID());
            cStmt.setInt(2, budgetObj.getEventID());

            ResultSet rs = cStmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }
            cStmt.close();
            conn.close();
        }
        return isSuccess;
    }
}
