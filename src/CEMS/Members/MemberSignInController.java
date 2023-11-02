package CEMS.Members;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Common.User;
import CEMS.Student.StudentMenuController;
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

public class MemberSignInController {
    public TextField txtMemberUser;
    public PasswordField txtpassword;
    public JFXButton btnMemberSignIn;
    public JFXButton btnMemberSignCancel;

    public void btnMemberSignInClick(ActionEvent actionEvent) {
        try{
            memberLoginCheck();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error in Member Login", e.getMessage());
        }
    }

    public void memberLoginCheck() throws IOException {
        System.out.println("Checking Member sign in");

        User usr = new User();
        usr.setUserName(txtMemberUser.getText());
        usr.setPassword(txtpassword.getText());

        LoggedInUser.checkLoginUser(usr);

        if (!LoggedInUser.getUser().isValidUser) {
            System.out.println("Login unsuccessful");
            Globals.ShowError("Login Failed", "Invalid Username and password combination.");
        }
        else {
            System.out.println("Login successful");
            goTOMemberMenuPage();
        }
    }

    public void goTOMemberMenuPage() throws IOException {
        System.out.println("Loading Member menu window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberMenu.fxml"));
        Parent root = loader.load();

        MemberMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Stage window = (Stage) btnMemberSignIn.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Member Portal");
        window.show();
    }

    public void btnMemberSignCancelClick(ActionEvent actionEvent) {
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

        Stage window = (Stage) btnMemberSignCancel.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Welcome");
        window.show();
    }
}
