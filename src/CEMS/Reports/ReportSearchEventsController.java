package CEMS.Reports;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Events.Event;
import CEMS.Members.MemberMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ReportSearchEventsController {

    public JFXButton btnBack;
    public JFXButton btnSearchEvent;
    public TextField txtEventName;
    public TextField txtEventVenue;
    public DatePicker dtEventStart1;
    public DatePicker dtEventStart2;
    public DatePicker dtEventEnd1;
    public DatePicker dtEventEnd2;
    public TableView<Event> tblEvents;
    private ObservableList<Event> dataList;

    @FXML
    private void initialize() {
        setEventTable();
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

    public void btnSearchEventClick(ActionEvent actionEvent) {
        try{
            int memberID = LoggedInUser.getUser().getUserID();
            EventReport searchEvent = new EventReport();
            searchEvent.setEventName(txtEventName.getText());
            searchEvent.setEventVenue(txtEventVenue.getText());
            searchEvent.setEventStartDate(String.valueOf(dtEventStart1.getValue() == null ? "" : dtEventStart1.getValue()) );
            searchEvent.setEventSearchStartDate(String.valueOf(dtEventStart2.getValue() == null ? "" : dtEventStart2.getValue()));
            searchEvent.setEventEndDate(String.valueOf(dtEventEnd1.getValue() == null ? "" : dtEventEnd1.getValue()));
            searchEvent.setEventSearchEndDate(String.valueOf(dtEventEnd2.getValue() == null ? "" : dtEventEnd2.getValue()));

            List<Event> eventList = new EventReport().searchEvents(memberID, searchEvent);
            populateEventList(eventList);
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void populateEventList(List<Event> eventList) {
        tblEvents.getItems().clear();
        tblEvents.getItems().addAll(eventList);
    }

    public void setEventTable() {
        TableColumn<Event, String> colEventID = new TableColumn<>("Event ID");
        colEventID.setCellValueFactory(new PropertyValueFactory<>("EventID"));
        colEventID.setVisible(false);
        tblEvents.getColumns().add(colEventID);

        TableColumn<Event, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(new PropertyValueFactory<>("EventName"));
        colEventName.setPrefWidth(150);
        tblEvents.getColumns().add(colEventName);

        TableColumn<Event, String> colEventStart = new TableColumn<>("Event Start Date");
        colEventStart.setCellValueFactory(new PropertyValueFactory<>("EventStartDate"));
        colEventStart.setPrefWidth(150);
        tblEvents.getColumns().add(colEventStart);

        TableColumn<Event, String> colEventEnd = new TableColumn<>("Event End Date");
        colEventEnd.setCellValueFactory(new PropertyValueFactory<>("EventEndDate"));
        colEventEnd.setPrefWidth(150);
        tblEvents.getColumns().add(colEventEnd);

        TableColumn<Event, String> colEventVenue = new TableColumn<>("Event Venue");
        colEventVenue.setCellValueFactory(new PropertyValueFactory<>("EventVenue"));
        colEventVenue.setPrefWidth(150);
        tblEvents.getColumns().add(colEventVenue);

        TableColumn<Event, String> colEventTotalCost = new TableColumn<>("Event Total Cost");
        colEventTotalCost.setCellValueFactory(new PropertyValueFactory<>("EventTotalCost"));
        colEventTotalCost.setPrefWidth(150);
        tblEvents.getColumns().add(colEventTotalCost);
    }
}
