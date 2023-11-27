package CEMS.Reports;

import java.sql.SQLException;
import java.util.List;

public class StudentReport {
    private int StudentID;
    public int getStudentID()
    {
        return this.StudentID;
    }
    public void setStudentID(int StudentID)
    {
        this.StudentID = StudentID;
    }

    private String FirstName;
    public String getFirstName()
    {
        return this.FirstName;
    }
    public void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }

    private String LastName;
    public String getLastName()
    {
        return this.LastName;
    }
    public void setLastName(String Lastname)
    {
        this.LastName = Lastname;
    }

    public String getName() { return FirstName + " " + LastName; }

    private String UserEmail;
    public String getUserEmail()
    {
        return this.UserEmail;
    }
    public void setUserEmail(String UserEmail)
    {
        this.UserEmail = UserEmail;
    }

    private String UserPh;
    public String getUserPh()
    {
        return this.UserPh;
    }
    public void setUserPh(String UserPh)
    {
        this.UserPh = UserPh;
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

    private int StudentEventAttending;
    public int getStudentEventAttending() { return this.StudentEventAttending; }
    public void setStudentEventAttending(int StudentEventAttending){
        this.StudentEventAttending = StudentEventAttending;
    }

    public List<StudentReport> searchStudentAttendance(int memberID, int eventID, int isAttending) throws SQLException{
        return new ReportDBservice().searchStudentAttendance(memberID, eventID, isAttending);
    }
}
