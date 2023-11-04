package CEMS.Club;

import java.util.List;

public class Club {

    public int ClubID;

    public String ClubName;

    public String ClubDescription;

    public String ClubPhone;

    public boolean saveClub(Club objClub) throws Exception{
        return  new ClubDBservice().saveClub(objClub);
    }

    public List<Club> getClubs(){
        return new ClubDBservice().getClubs();
    }
}
