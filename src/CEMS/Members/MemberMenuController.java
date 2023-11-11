package CEMS.Members;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Events.CreateEventController;
import CEMS.Events.EventBudgetController;
import CEMS.Events.ViewClubEventsController;
import CEMS.WelcomePageController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
        try{
            goToEventCreate();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void btnEventBudgetClick(ActionEvent actionEvent) {
        try{
            goToEventBudget();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Member", e.getMessage());
        }
    }

    public void btnEventReportClick(ActionEvent actionEvent) {
    }

    public void btnMemberClubEventClick(ActionEvent actionEvent) {
        try{
            goToEventList();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
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
        Stage window = (Stage) btnMemberSignOut.getScene().getWindow();
        WelcomePageController controller = loader.getController();

        Globals.WindowCloseAndShow(root, window, "CEMS - Welcome");
    }

    public void goToEventCreate() throws IOException {
        System.out.println("Loading Event Create");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Events/CreateEvent.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnCreateEvent.getScene().getWindow();
        CreateEventController controller = loader.getController();

        Globals.WindowCloseAndShow(root, window, "CEMS - Create Event");
    }

    public void goToEventBudget() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Events/EventBudget.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnCreateEvent.getScene().getWindow();
        EventBudgetController controller = loader.getController();
        controller.setEventList();

        Globals.WindowCloseAndShow(root, window, "CEMS - Event Budget");
    }

    public void goToEventList() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Events/ViewClubEvents.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnCreateEvent.getScene().getWindow();
        ViewClubEventsController controller = loader.getController();
        controller.setEventTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - Events");
    }
}
