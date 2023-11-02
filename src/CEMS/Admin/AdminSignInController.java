package CEMS.Admin;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Common.User;
import CEMS.WelcomePageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminSignInController {

    @FXML private TextField txtAdminUser;
    @FXML private PasswordField txtpassword;
    @FXML private Button btnAdminSignIn;
    @FXML private Button btnAdminSignCancel;

    public void btnAdminSignInClick(ActionEvent actionEvent) {
        try{
            adminLoginCheck();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Admin", e.getMessage());
        }
    }

    public void btnAdminSignCancelClick(ActionEvent actionEvent) {
        try{
            goToWelcomePage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Admin", e.getMessage());
        }
    }

    public void adminLoginCheck() throws IOException {
        System.out.println("Checking admin sign in");

        User usr = new User();
        usr.setUserName(txtAdminUser.getText());
        usr.setPassword(txtpassword.getText());

        LoggedInUser.checkLoginUser(usr);

        if (!LoggedInUser.getUser().isValidUser) {
            System.out.println("Login unsuccessful");
            Globals.ShowError("Login Failed", "Invalid Username and password combination.");
        }
        else {
            System.out.println("Login successful");
            goTOAdminMenuPage();
        }
    }

    public void goTOAdminMenuPage() throws IOException {
        System.out.println("Loading Admin menu window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMenu.fxml"));
        Parent root = loader.load();

        AdminMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Stage window = (Stage) btnAdminSignCancel.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Admin Portal");
        window.show();
    }

    public void goToWelcomePage() throws IOException {
        System.out.println("Loading Welcome window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../WelcomePage.fxml"));
        Parent root = loader.load();

        WelcomePageController controller = loader.getController();

        Stage window = (Stage) btnAdminSignCancel.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Welcome");
        window.show();
    }
}
