package CEMS.Student;

import CEMS.Common.User;
import CEMS.Events.Event;

import java.sql.SQLException;
import java.util.List;

public class Student extends User {

    public boolean saveStudent(Student studObj) throws SQLException {
        return new StudentDBservice().saveStudent(studObj);
    }

    public List<Student> getStudentsList() throws Exception{
        return new StudentDBservice().getStudentsList();
    }

    public Student getStudentDetail(int studID) throws Exception{
        return new StudentDBservice().getStudentDetail(studID);
    }

    public boolean updateStudent(Student studObj) throws SQLException{
        return new StudentDBservice().updateStudent(studObj);
    }

    public boolean deleteStudent(int StudID) throws SQLException{
        return new StudentDBservice().deleteStudent(StudID);
    }

    public List<Event> getStudentEventsList(int studentID) throws SQLException {
        return new StudentDBservice().getStudentEventsList(studentID);
    }

    public boolean insertStudentEventAttendacne(int StudID, Event event) throws SQLException {
        return new StudentDBservice().insertStudentEventAttendacne(StudID, event);
    }
}
