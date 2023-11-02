package CEMS.Student;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Common.User;
import CEMS.WelcomePageController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentSignInController {
    public JFXButton btnStudSignCancel;
    public JFXButton btnStudSignIn;
    public TextField txtStudUser;
    public PasswordField txtpassword;

    public void btnStudSignInClick(ActionEvent actionEvent) {
        try{
            studentLoginCheck();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error in Student Login", e.getMessage());
        }
    }

    public void studentLoginCheck() throws IOException {
        System.out.println("Checking student sign in");

        User usr = new User();
        usr.setUserName(txtStudUser.getText());
        usr.setPassword(txtpassword.getText());

        LoggedInUser.checkLoginUser(usr);

        if (!LoggedInUser.getUser().isValidUser) {
            System.out.println("Login unsuccessful");
            Globals.ShowError("Login Failed", "Invalid Username and password combination.");
        }
        else {
            System.out.println("Login successful");
            goTOStudentMenuPage();
        }
    }

    public void goTOStudentMenuPage() throws IOException {
        System.out.println("Loading Student menu window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentMenu.fxml"));
        Parent root = loader.load();

        StudentMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Stage window = (Stage) btnStudSignIn.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Student Portal");
        window.show();
    }

    public void btnStudSignCancelClick(ActionEvent actionEvent) {
        System.out.println("Loading Welcome Page");
        try{
            goToWelcomePage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goToWelcomePage() throws IOException {
        System.out.println("Loading Welcome window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../WelcomePage.fxml"));
        Parent root = loader.load();

        WelcomePageController controller = loader.getController();

        Stage window = (Stage) btnStudSignCancel.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Welcome");
        window.show();
    }
}
