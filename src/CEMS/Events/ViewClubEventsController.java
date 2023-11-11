package CEMS.Events;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Members.MemberMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewClubEventsController {
    public TableView tblEvents;
    public JFXButton btnBack;

    public void setEventTable() throws SQLException {
        List<Event> eventList = new Event().getEventsList(LoggedInUser.getUser().getUserID());

        TableColumn<Event, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(new PropertyValueFactory<>("EventName"));
        tblEvents.getColumns().add(colEventName);

        TableColumn<Event, String> colEventStart = new TableColumn<>("Event Start Date");
        colEventStart.setCellValueFactory(new PropertyValueFactory<>("EventStartDate"));
        tblEvents.getColumns().add(colEventStart);

        TableColumn<Event, String> colEventEnd = new TableColumn<>("Event End Date");
        colEventEnd.setCellValueFactory(new PropertyValueFactory<>("EventEndDate"));
        tblEvents.getColumns().add(colEventEnd);

        TableColumn<Event, String> colEventVenue = new TableColumn<>("Event Venue");
        colEventVenue.setCellValueFactory(new PropertyValueFactory<>("EventVenue"));
        tblEvents.getColumns().add(colEventVenue);

        // Add data to the table
        tblEvents.getItems().addAll(eventList);
    }

    public void btnBackClick(ActionEvent actionEvent) {
        try{
            goTOMemberMenuPage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goTOMemberMenuPage() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Members/MemberMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnBack.getScene().getWindow();
        MemberMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Globals.WindowCloseAndShow(root, window, "CEMS - Member Portal");
    }
}
