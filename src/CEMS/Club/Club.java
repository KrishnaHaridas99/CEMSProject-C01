package CEMS.Club;

import java.util.List;

public class Club {

    private int ClubID;
    public int getClubID() { return this.ClubID; }
    public void setClubID(int ClubID)
    {
        this.ClubID = ClubID;
    }

    private String ClubName;
    public String getClubName() { return this.ClubName; }
    public void setClubName(String ClubName)
    {
        this.ClubName = ClubName;
    }

    private String ClubDescription;
    public String getClubDescription() { return this.ClubDescription; }
    public void setClubDescription(String ClubDescription)
    {
        this.ClubDescription = ClubDescription;
    }

    private String ClubPhone;
    public String getClubPhone() { return this.ClubPhone; }
    public void setClubPhone(String ClubPhone)
    {
        this.ClubPhone = ClubPhone;
    }

    public boolean saveClub(Club objClub) throws Exception{
        return  new ClubDBservice().saveClub(objClub);
    }

    public List<Club> getClubs() throws Exception {
        return new ClubDBservice().getClubs();
    }

    public Club getClubDetails(int ClubID) throws Exception{
        return new ClubDBservice().getClubDetails(ClubID);
    }

    public boolean updateClubDetails(Club objClub) throws Exception{
        return new ClubDBservice().updateClubDetails(objClub);
    }

    public boolean deleteClub(int ClubID) throws Exception{
        return new ClubDBservice().deleteClub(ClubID);
    }
}
