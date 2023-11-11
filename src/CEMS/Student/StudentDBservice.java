package CEMS.Student;

import CEMS.Common.Globals;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDBservice {
    public boolean saveStudent(Student studObj) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            System.out.println("Database - inserting into Club members");

            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_CreateStudent ?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setString(1, studObj.getFirstName());
            cStmt.setString(2, studObj.getLastName());
            cStmt.setString(3, studObj.getUserDOB());
            cStmt.setString(4, studObj.getUserPh());
            cStmt.setString(5, studObj.getUserEmail());
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }

            cStmt.close();
            conn.close();

            System.out.println("Student Created Successfully");
        }
        return isSuccess;
    }

    public List<Student> getStudentsList() throws Exception {
        List<Student> studList = new ArrayList<>();

        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_GetAllStudents";
            cStmt = conn.prepareCall(query);

            ResultSet rs = cStmt.executeQuery();

            Student student;
            while (rs.next()) {
                student = new Student();
                student.setUserID(rs.getInt("UserID"));
                student.setFirstName(rs.getString("FirstName"));
                student.setLastName(rs.getString("LastName"));
                student.setUserDOB(rs.getString("DOB"));
                student.setUserEmail(rs.getString("Email"));
                student.setUserPh(rs.getString("Phone_no"));
                student.setUserName(rs.getString("UserName"));

                studList.add(student);
            }

            cStmt.close();
            conn.close();
        }

        return studList;
    }

    public Student getStudentDetail(int studentID) throws Exception {
        Student student = new Student();

        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_GetStudentByID ?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, studentID);

            ResultSet rs = cStmt.executeQuery();
            while (rs.next()) {
                student.setUserID(rs.getInt("UserID"));
                student.setFirstName(rs.getString("FirstName"));
                student.setLastName(rs.getString("LastName"));
                student.setUserDOB(rs.getString("DOB"));
                student.setUserEmail(rs.getString("Email"));
                student.setUserPh(rs.getString("Phone_no"));
                student.setUserName(rs.getString("UserName"));
            }
            cStmt.close();
            conn.close();
        }
        return student;
    }

    public boolean updateStudent(Student studObj) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_UpdateStudentDetails ?,?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, studObj.getUserID());
            cStmt.setString(2, studObj.getFirstName());
            cStmt.setString(3, studObj.getLastName());
            cStmt.setString(4, studObj.getUserDOB());
            cStmt.setString(5, studObj.getUserPh());
            cStmt.setString(6, studObj.getUserEmail());

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

    public boolean deleteStudent(int StudID) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_DeleteStudent ?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, StudID);

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
