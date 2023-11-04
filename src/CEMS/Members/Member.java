package CEMS.Members;

import CEMS.Common.User;

import java.sql.SQLException;

public class Member extends User {
    public String clubSelected;

    public boolean saveMember(Member memObj) throws SQLException {
        return new MemberDBservice().saveMember(memObj);
    }
}
