package CEMS.Members;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Events.*;
import CEMS.Reports.ReportMenuController;
import CEMS.WelcomePageController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
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
        try{
            showReportPopup();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Member", e.getMessage());
        }
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
        root.getProperties().put(FXMLLoader.class.getName(), loader);
        Stage window = (Stage) btnCreateEvent.getScene().getWindow();

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

    private void showReportPopup() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Reports/ReportMenu.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Reports");
        popupStage.setResizable(false);
        popupStage.setMaximized(false);
        popupStage.setScene(new Scene(root));

        ReportMenuController reportMenuController = loader.getController();
        reportMenuController.parentWindow = (Stage) btnEventReport.getScene().getWindow();

        popupStage.showAndWait();
    }
}
