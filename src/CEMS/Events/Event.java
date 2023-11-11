package CEMS.Events;

import java.sql.SQLException;
import java.util.List;

public class Event {
    private int EventID;
    public int getEventID() { return this.EventID; }
    public void setEventID(int EventID)
    {
        this.EventID = EventID;
    }

    private String EventName;
    public String getEventName() { return this.EventName; }
    public void setEventName(String EventName)
    {
        this.EventName = EventName;
    }

    private String EventStartDate;
    public String getEventStartDate() { return this.EventStartDate; }
    public void setEventStartDate(String EventStartDate)
    {
        this.EventStartDate = EventStartDate;
    }

    private String EventEndDate;
    public String getEventEndDate() { return this.EventEndDate; }
    public void setEventEndDate(String EventEndDate)
    {
        this.EventEndDate = EventEndDate;
    }

    private String EventVenue;
    public String getEventVenue() { return this.EventVenue; }
    public void setEventVenue(String EventVenue)
    {
        this.EventVenue = EventVenue;
    }

    private String EventDesc;
    public String getEventDesc() { return this.EventDesc; }
    public void setEventDesc(String EventDesc)
    {
        this.EventDesc = EventDesc;
    }

    private String EventClubID;
    public String getEventClubID() { return this.EventClubID; }
    public void setEventClubID(String EventClubID)
    {
        this.EventClubID = EventClubID;
    }

    private String EventClubName;
    public String getEventClubName() { return this.EventClubName; }
    public void setEventClubName(String EventClubName)
    {
        this.EventClubName = EventClubName;
    }

    private String EventCreatedBy;
    public String getEventCreatedBy() { return this.EventCreatedBy; }
    public void setEventCreatedBy(String EventCreatedBy)
    {
        this.EventCreatedBy = EventCreatedBy;
    }

    private String EventCreatedDate;
    public String getEventCreatedDate() { return this.EventCreatedDate; }
    public void setEventCreatedDate(String EventCreatedDate)
    {
        this.EventCreatedDate = EventCreatedDate;
    }

    public boolean saveEvent(Event eventObj) throws SQLException {
        return new EventDBservice().saveEvent(eventObj);
    }

    public List<Event> getEventsList(int memID) throws SQLException {
        return new EventDBservice().getEventsList(memID);
    }

    public List<Event> getAllEventsList() throws SQLException{
        return new EventDBservice().getAllEventsList();
    }
}
