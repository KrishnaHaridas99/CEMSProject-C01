package CEMS.Reports;

import CEMS.Events.Event;

import java.sql.SQLException;
import java.util.List;

public class EventReport extends Event {
    private String EventSearchStartDate;
    public String getEventSearchStartDate() { return this.EventSearchStartDate; }
    public void setEventSearchStartDate(String EventSearchStartDate) { this.EventSearchStartDate = EventSearchStartDate; }

    private String EventSearchEndDate;
    public String getEventSearchEndDate() { return this.EventSearchEndDate; }
    public void setEventSearchEndDate(String EventSearchEndDate)
    {
        this.EventSearchEndDate = EventSearchEndDate;
    }

    public List<Event> searchEvents(int memberID, EventReport eventReport) throws SQLException {
        return new ReportDBservice().searchEvents(memberID, eventReport);
    }
}
