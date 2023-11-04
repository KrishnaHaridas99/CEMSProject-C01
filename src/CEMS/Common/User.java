package CEMS.Common;

public class User {
    protected int UserId;
    protected String FirstName;
    protected String Lastname;
    protected String UserDOB;
    protected String UserEmail;
    protected String UserPh;
    protected int UserType;
    protected String UserName;
    protected String Password;

    public boolean isValidUser;

    public int getUserID()
    {
        return UserId;
    }
    public void setUserID(int UserId)
    {
        this.UserId = UserId;
    }

    public String getFirstName()
    {
        return FirstName;
    }
    public void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getLastName()
    {
        return Lastname;
    }
    public void setLastName(String Lastname)
    {
        this.Lastname = Lastname;
    }

    public String getName()
    {
        return FirstName + " " + Lastname;
    }

    public String getUserDOB()
    {
        return UserDOB;
    }
    public void setUserDOB(String UserDOB)
    {
        this.UserDOB = UserDOB;
    }

    public String getUserEmail()
    {
        return UserEmail;
    }
    public void setUserEmail(String UserEmail)
    {
        this.UserEmail = UserEmail;
    }

    public String getUserPh()
    {
        return UserPh;
    }
    public void setUserPh(String UserPh)
    {
        this.UserPh = UserPh;
    }

    public int getUserType()
    {
        return UserType;
    }
    public void setUserType(int UserType)
    {
        this.UserType = UserType;
    }

    public String getUserName()
    {
        return UserName;
    }
    public void setUserName(String UserName)
    {
        this.UserName = UserName;
    }

    public String getPassword()
    {
        return Password;
    }
    public void setPassword(String Password)
    {
        this.Password = Password;
    }

}
