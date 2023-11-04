package CEMS.Events;

import java.sql.SQLException;

public class Event {
    public int EventID;
    public String EventName;
    public String EventStartDate;
    public String EventEndDate;
    public String EventVenue;
    public String EventDesc;
    public String ClubID;
    public String ClubName;

    public String CreatedBy;

    public String CreatedDate;

    public boolean saveEvent(Event eventObj) throws SQLException {
        return new EventDBservice().saveEvent(eventObj);
    }
}
