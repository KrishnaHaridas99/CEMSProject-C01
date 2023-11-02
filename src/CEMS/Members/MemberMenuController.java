package CEMS.Members;

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

public class MemberMenuController {
    public Label lblWelcome;
    public JFXButton btnMemberSignOut;
    public JFXButton btnCreateEvent;
    public JFXButton btnEventBudget;
    public JFXButton btnMemberEvent;
    public JFXButton btnEventReport;

    public void setWelcomeMsg(String str) {
        lblWelcome.setText(lblWelcome.getText() + str);
    }

    public void btnCreateEventClick(ActionEvent actionEvent) {
    }

    public void btnEventBudgetClick(ActionEvent actionEvent) {
    }

    public void btnEventReportClick(ActionEvent actionEvent) {
    }

    public void btnMemberEventClick(ActionEvent actionEvent) {
    }

    public void btnMemberSignOutClick(ActionEvent actionEvent) {
        try{
            LoggedInUser.clearUser();
            goToWelcomePage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Member", e.getMessage());
        }
    }

    public void goToWelcomePage() throws IOException {
        System.out.println("Loading Welcome window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../WelcomePage.fxml"));
        Parent root = loader.load();

        WelcomePageController controller = loader.getController();

        Stage window = (Stage) btnMemberSignOut.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Welcome");
        window.show();
    }
}
