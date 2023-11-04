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
            pstmt.setString(1, objClub.ClubName);
            pstmt.setString(2, objClub.ClubDescription);
            pstmt.setString(3, objClub.ClubPhone);

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            System.out.println("Club Created Successfully");
            isSuccess = true;

        }
        return isSuccess;
    }

    public List<Club> getClubs(){
        List<Club> clusList = new ArrayList<>();
        try
        {
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
                    objclub.ClubID = rs.getInt("ClubID");
                    objclub.ClubName = rs.getString("ClubName");
                    objclub.ClubDescription = rs.getString("ClubDescription");
                    objclub.ClubPhone = rs.getString("ClubPhone");

                    clusList.add(objclub);
                }
            }
            else
            {
                System.out.println("Failed to make connection!");
            }
        }
        catch (SQLException e)
        {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return clusList;
    }
}
