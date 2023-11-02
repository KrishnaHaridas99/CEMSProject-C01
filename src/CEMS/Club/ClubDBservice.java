package CEMS.Club;

import CEMS.Common.Globals;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}
