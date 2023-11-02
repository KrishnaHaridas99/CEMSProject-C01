package CEMS.Admin;

import CEMS.Club.CreateClubController;
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

public class AdminMenuController {
    public Label lblWelcome;
    public JFXButton btnAdminSignOut;
    public JFXButton btnCreateClub;
    public JFXButton btnCreateClubMember;

    public void setWelcomeMsg(String str) {
        lblWelcome.setText(lblWelcome.getText() + str);
    }

    public void btnCreateClubClick(ActionEvent actionEvent) {
        try{
            goToCreateClub();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Create Club", e.getMessage());
        }
    }

    public void goToCreateClub() throws IOException{
        System.out.println("Loading Create club window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Club/CreateClub.fxml"));
        Parent root = loader.load();

        CreateClubController controller = loader.getController();

        Stage window = (Stage) btnCreateClub.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Create Club");
        window.show();
    }

    public void btnCreateClubMemberClick(ActionEvent actionEvent) {
        try{
            goToCreateClub();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Create Club", e.getMessage());
        }
    }

    public void btnAdminSignOutClick(ActionEvent actionEvent) {
        try{
            LoggedInUser.clearUser();
            goToWelcomePage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Admin", e.getMessage());
        }
    }

    public void goToWelcomePage() throws IOException {
        System.out.println("Loading Welcome window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../WelcomePage.fxml"));
        Parent root = loader.load();

        WelcomePageController controller = loader.getController();

        Stage window = (Stage) btnAdminSignOut.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Welcome");
        window.show();
    }
}
