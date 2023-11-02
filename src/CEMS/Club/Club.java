package CEMS.Club;

public class Club {

    public int ClubID;

    public String ClubName;

    public String ClubDescription;

    public String ClubPhone;

    public boolean saveClub(Club objClub) throws Exception{
        return  new ClubDBservice().saveClub(objClub);
    }
}
