package CEMS.Common;

public class User {
    private int UserID;
    public int getUserID()
    {
        return this.UserID;
    }
    public void setUserID(int UserID)
    {
        this.UserID = UserID;
    }

    private String FirstName;
    public String getFirstName()
    {
        return this.FirstName;
    }
    public void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }

    private String LastName;
    public String getLastName()
    {
        return this.LastName;
    }
    public void setLastName(String Lastname)
    {
        this.LastName = Lastname;
    }

     public String getName() { return FirstName + " " + LastName; }

    private String UserDOB;
    public String getUserDOB()
    {
        return this.UserDOB;
    }
    public void setUserDOB(String UserDOB)
    {
        this.UserDOB = UserDOB;
    }

    private String UserEmail;
    public String getUserEmail()
    {
        return this.UserEmail;
    }
    public void setUserEmail(String UserEmail)
    {
        this.UserEmail = UserEmail;
    }

    private String UserPh;
    public String getUserPh()
    {
        return this.UserPh;
    }
    public void setUserPh(String UserPh)
    {
        this.UserPh = UserPh;
    }

    private int UserType;
    public int getUserType()
    {
        return this.UserType;
    }
    public void setUserType(int UserType)
    {
        this.UserType = UserType;
    }

    private String UserName;
    public String getUserName()
    {
        return this.UserName;
    }
    public void setUserName(String UserName)
    {
        this.UserName = UserName;
    }

    private String Password;
    public String getPassword()
    {
        return this.Password;
    }
    public void setPassword(String Password)
    {
        this.Password = Password;
    }

    public boolean isValidUser;
}
