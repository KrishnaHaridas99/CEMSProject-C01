package CEMS.Common;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

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

    public enum AttendanceType{
        ALL,
        YES,
        NO,
        MAY_BE;
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

    public static void WindowCloseAndShow(Parent root, Stage window, String showTitle){
        window.close();
        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle(showTitle);
        window.show();
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

    public static boolean showConfirmationDialog(String Title, String HeaderText, String Text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Title);
        alert.setHeaderText(HeaderText);
        alert.setContentText(Text);

        // Add custom buttons to the confirmation dialog
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);

        // Show the dialog and wait for the user's response
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        return result == yesButton;
    }
}