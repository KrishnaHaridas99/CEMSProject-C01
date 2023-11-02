package CEMS.Common;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Globals {
    private static String db_name;
    private static String db_username;
    private static String db_pass;

    public enum UserType{
        ADMIN,
        MEMBER,
        STUDENT;
    }

    public static String getDb_name() { return db_name; }
    public static void setDb_name(String db_name) {
        Globals.db_name = db_name;
    }

    public static String getDb_username() {
        return db_username;
    }
    public static void setDb_username(String db_username) {
        Globals.db_username = db_username;
    }

    public static String getDb_pass() {
        return db_pass;
    }
    public static void setDb_pass(String db_pass) {
        Globals.db_pass = db_pass;
    }

    public static Connection getConnection(){
        try{
            String jdbcUrl = "jdbc:sqlserver://localhost;databaseName=" + getDb_name() + ";encrypt=true;trustServerCertificate=true;";
            System.out.println(jdbcUrl);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(jdbcUrl, getDb_username(), getDb_pass());
        }
        catch (SQLException e)
        {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void ShowInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void ShowError(String errorTitle, String errMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorTitle);
        alert.setHeaderText(errMessage);
        alert.showAndWait();
    }
}