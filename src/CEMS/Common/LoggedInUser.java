package CEMS.Common;

public class LoggedInUser {

    private static User user = new User();

    public static User getUser() {
        return user;
    }
    public static void setUser(User user) {
        LoggedInUser.user = user;
    }

    public static void checkLoginUser(User usr) {
        LoggedInUser.setUser(new LoginDBservice().userLogin(usr));
    }

    public static void clearUser() {
        user = new User();
    }
}
