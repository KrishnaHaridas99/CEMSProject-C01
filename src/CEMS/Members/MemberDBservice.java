package CEMS.Members;

import CEMS.Common.Globals;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDBservice {

    public boolean saveMember(Member memberObj) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            System.out.println("Database - inserting into Club members");

            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_CreateClubMembers ?,?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setString(1, memberObj.getFirstName());
            cStmt.setString(2, memberObj.getLastName());
            cStmt.setString(3, memberObj.getUserDOB());
            cStmt.setString(4, memberObj.getUserPh());
            cStmt.setString(5, memberObj.getUserEmail());
            cStmt.setString(6, memberObj.clubSelected);
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    isSuccess = true;
                }
            }

            cStmt.close();
            conn.close();

            System.out.println("Member Created Successfully");
        }
        return isSuccess;
    }

    public List<Member> getMembersList() throws Exception {
        List<Member> memList = new ArrayList<>();

        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_GetAllMembers";
            cStmt = conn.prepareCall(query);

            ResultSet rs = cStmt.executeQuery();

            Member member;
            while (rs.next()) {
                member = new Member();
                member.setUserID(rs.getInt("UserID"));
                member.setFirstName(rs.getString("FirstName"));
                member.setLastName(rs.getString("LastName"));
                member.setUserDOB(rs.getString("DOB"));
                member.setUserEmail(rs.getString("Email"));
                member.setUserPh(rs.getString("Phone_no"));
                member.setUserName(rs.getString("UserName"));
                member.setClubID(rs.getInt("ClubID"));
                member.setClubName(rs.getString("ClubName"));

                memList.add(member);
            }

            cStmt.close();
            conn.close();
        }

        return memList;
    }

    public Member getMembersDetails(int memberID) throws Exception {
        Member member = new Member();
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_GetMemberByID ?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, memberID);

            ResultSet rs = cStmt.executeQuery();
            while (rs.next()) {
                member.setUserID(rs.getInt("UserID"));
                member.setFirstName(rs.getString("FirstName"));
                member.setLastName(rs.getString("LastName"));
                member.setUserDOB(rs.getString("DOB"));
                member.setUserEmail(rs.getString("Email"));
                member.setUserPh(rs.getString("Phone_no"));
                member.setUserName(rs.getString("UserName"));
                member.setClubID(rs.getInt("ClubID"));
                member.setClubName(rs.getString("ClubName"));
            }
            cStmt.close();
            conn.close();
        }
        return member;
    }

    public boolean updateMember(Member memberObj) throws SQLException {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_UpdateMemberDetails ?,?,?,?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, memberObj.getUserID());
            cStmt.setString(2, memberObj.getFirstName());
            cStmt.setString(3, memberObj.getLastName());
            cStmt.setString(4, memberObj.getUserDOB());
            cStmt.setString(5, memberObj.getUserPh());
            cStmt.setString(6, memberObj.getUserEmail());
            cStmt.setString(7, memberObj.clubSelected);

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

    public boolean deleteMember(int memberID) throws Exception {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_DeleteMember ?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, memberID);

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
