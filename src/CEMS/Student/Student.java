package CEMS.Student;

import CEMS.Common.User;

import java.sql.SQLException;

public class Student extends User {

    public boolean saveStudent(Student studObj) throws SQLException {
        return new StudentDBservice().saveStudent(studObj);
    }
}
