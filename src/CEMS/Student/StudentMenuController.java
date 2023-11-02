package CEMS.Student;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.WelcomePageController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentMenuController {
    public Label lblWelcome;
    public JFXButton btnStudentSignOut;

    public void setWelcomeMsg(String str) {
        lblWelcome.setText(lblWelcome.getText() + str);
    }

    public void btnStudentSignOutClick(ActionEvent actionEvent) {
        try{
            LoggedInUser.clearUser();
            goToWelcomePage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Student", e.getMessage());
        }
    }

    public void goToWelcomePage() throws IOException {
        System.out.println("Loading Welcome window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../WelcomePage.fxml"));
        Parent root = loader.load();

        WelcomePageController controller = loader.getController();

        Stage window = (Stage) btnStudentSignOut.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Welcome");
        window.show();
    }
}
