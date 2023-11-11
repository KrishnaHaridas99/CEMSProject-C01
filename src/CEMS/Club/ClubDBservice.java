package CEMS.Club;

import CEMS.Common.Globals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDBservice {

    public boolean saveClub(Club objClub) throws Exception {
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            System.out.println("Database - inserting into Club table");

            PreparedStatement pstmt = null;
            String query = "INSERT INTO [dbo].[CEMS_Clubs] ([ClubName], [ClubDescription], [ClubPhone]) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, objClub.getClubName());
            pstmt.setString(2, objClub.getClubDescription());
            pstmt.setString(3, objClub.getClubPhone());

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            System.out.println("Club Created Successfully");
            isSuccess = true;

        }
        return isSuccess;
    }

    public List<Club> getClubs() throws Exception{
        List<Club> clusList = new ArrayList<>();
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            System.out.println("Database - Get all the clubs");

            String query = "EXEC CEMS_SP_GetAllClubs";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Club objclub;
            while (rs.next()) {
                objclub = new Club();
                objclub.setClubID(rs.getInt("ClubID"));
                objclub.setClubName(rs.getString("ClubName"));
                objclub.setClubDescription(rs.getString("ClubDescription"));
                objclub.setClubPhone(rs.getString("ClubPhone"));

                clusList.add(objclub);
            }
        }
        return clusList;
    }

    public Club getClubDetails(int ClubID) throws Exception{
        Club objclub = new Club();
        Connection conn = Globals.getConnection();
        if (conn != null)
        {
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_GetClubByClubID ?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, ClubID);
            ResultSet rs = cStmt.executeQuery(query);

            while (rs.next()) {
                objclub.setClubID(rs.getInt("ClubID"));
                objclub.setClubName(rs.getString("ClubName"));
                objclub.setClubDescription(rs.getString("ClubDescription"));
                objclub.setClubPhone(rs.getString("ClubPhone"));
            }
        }
        return objclub;
    }

    public boolean updateClubDetails(Club objClub) throws Exception{
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null){
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_UpdateClubByClubID ?,?,?,?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, objClub.getClubID());
            cStmt.setString(2, objClub.getClubName());
            cStmt.setString(3, objClub.getClubDescription());
            cStmt.setString(4, objClub.getClubPhone());

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

    public boolean deleteClub(int ClubID) throws Exception{
        boolean isSuccess = false;
        Connection conn = Globals.getConnection();
        if (conn != null){
            CallableStatement cStmt = null;
            String query = "EXEC CEMS_SP_DeleteClubByClubID ?";
            cStmt = conn.prepareCall(query);
            cStmt.setInt(1, ClubID);

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
}
