package CEMS.Members;

import CEMS.Common.User;

import java.sql.SQLException;
import java.util.List;

public class Member extends User {

    private int ClubID;
    public int getClubID()
    {
        return ClubID;
    }
    public void setClubID(int ClubID)
    {
        this.ClubID = ClubID;
    }

    private String ClubName;
    public String getClubName()
    {
        return this.ClubName;
    }
    public void setClubName(String ClubName)
    {
        this.ClubName = ClubName;
    }

    public String clubSelected;

    public String getClubSelected(){
        return this.getClubID() + "_" + this.getClubName();
    }

    public boolean saveMember(Member memObj) throws SQLException {
        return new MemberDBservice().saveMember(memObj);
    }

    public List<Member> getMembersList() throws Exception{
        return new MemberDBservice().getMembersList();
    }

    public Member getMembersDetails(int memberID) throws Exception{
        return new MemberDBservice().getMembersDetails(memberID);
    }

    public boolean updateMember(Member memberObj) throws SQLException{
        return new MemberDBservice().updateMember(memberObj);
    }

    public boolean deleteMember(int memberID) throws Exception{
        return new MemberDBservice().deleteMember(memberID);
    }
}
