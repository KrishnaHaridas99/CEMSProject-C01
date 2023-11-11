package CEMS.Student;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Events.Event;
import CEMS.WelcomePageController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentMenuController {
    public Label lblWelcome;
    public JFXButton btnStudentSignOut;
    public TableView tblEventList;

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
        Stage window = (Stage) btnStudentSignOut.getScene().getWindow();
        WelcomePageController controller = loader.getController();

        Globals.WindowCloseAndShow(root, window, "CEMS - Welcome");
    }

    public void setEventTable() throws SQLException {
        List<Event> eventList = new Event().getAllEventsList();

        TableColumn<Event, String> colEventID = new TableColumn<>("Event ID");
        colEventID.setCellValueFactory(new PropertyValueFactory<>("EventID"));
        tblEventList.getColumns().add(colEventID);

        TableColumn<Event, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(new PropertyValueFactory<>("EventName"));
        tblEventList.getColumns().add(colEventName);

        TableColumn<Event, String> colEventStart = new TableColumn<>("Event Start Date");
        colEventStart.setCellValueFactory(new PropertyValueFactory<>("EventStartDate"));
        tblEventList.getColumns().add(colEventStart);

        TableColumn<Event, String> colEventEnd = new TableColumn<>("Event End Date");
        colEventEnd.setCellValueFactory(new PropertyValueFactory<>("EventEndDate"));
        tblEventList.getColumns().add(colEventEnd);

        TableColumn<Event, String> colEventVenue = new TableColumn<>("Event Venue");
        colEventVenue.setCellValueFactory(new PropertyValueFactory<>("EventVenue"));
        tblEventList.getColumns().add(colEventVenue);

        TableColumn<Event, String> colClubName = new TableColumn<>("Event Club Name");
        colClubName.setCellValueFactory(new PropertyValueFactory<>("EventClubName"));
        tblEventList.getColumns().add(colClubName);

        // Add data to the table
        tblEventList.getItems().addAll(eventList);
    }
}
