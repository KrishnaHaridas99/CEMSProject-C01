package CEMS.Events;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Members.MemberMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class CreateEventController {
    public DatePicker dtEventStart;
    public DatePicker dtEventEnd;
    public TextArea txtEventDesc;
    public TextField txtEventVenue;
    public TextField txtEventName;
    public JFXButton btnSaveEvent;
    public JFXButton btnCancel;
    public Label lblEventID;
    public boolean isEdit = false;

    public void btnSaveEventClick(ActionEvent actionEvent) {
        try{
            if (!isEdit)
            {
                saveEvent();
            }
            else {
                updateEvent();
            }
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Event", e.getMessage());
        }
    }

    public void populateEventDetails(Event objEvent){
        lblEventID.setText(String.valueOf(objEvent.getEventID()));
        txtEventName.setText(objEvent.getEventName());
        dtEventStart.setValue(LocalDate.parse(objEvent.getEventStartDate()));
        dtEventEnd.setValue(LocalDate.parse(objEvent.getEventEndDate()));
        txtEventVenue.setText(objEvent.getEventVenue());
        txtEventDesc.setText(objEvent.getEventDesc());
    }

    public void saveEvent() throws Exception {
        Event event = new Event();
        event.setEventName(txtEventName.getText());
        event.setEventStartDate(dtEventStart.getValue().toString());
        event.setEventEndDate(dtEventEnd.getValue().toString());
        event.setEventVenue(txtEventVenue.getText());
        event.setEventDesc(txtEventDesc.getText());
        event.setEventCreatedBy(LoggedInUser.getUser().getUserName());

        boolean isMemSaved = event.saveEvent(event);

        if (isMemSaved){
            Globals.ShowInfo("Save success", "Event '" + event.getEventName() + "' saved successfully");
        }
        else {
            Globals.ShowError("Error", "Error in saving Event details");
        }

        goTOMemberMenuPage();
    }

    public void updateEvent() throws Exception {
        Event event = new Event();
        event.setEventID(Integer.parseInt(lblEventID.getText()));
        event.setEventName(txtEventName.getText());
        event.setEventStartDate(dtEventStart.getValue().toString());
        event.setEventEndDate(dtEventEnd.getValue().toString());
        event.setEventVenue(txtEventVenue.getText());
        event.setEventDesc(txtEventDesc.getText());
        event.setEventCreatedBy(LoggedInUser.getUser().getUserName());

        boolean isMemSaved = event.updateEvent(event);

        if (isMemSaved){
            Globals.ShowInfo("Save success", "Event '" + event.getEventName() + "' updated successfully");
        }
        else {
            Globals.ShowError("Error", "Error in saving Event details");
        }

        goToEventList();
    }

    public void goToEventList() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Events/ViewClubEvents.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnSaveEvent.getScene().getWindow();
        ViewClubEventsController controller = loader.getController();
        controller.setEventTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - Events");
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        try{
            if (!isEdit)
            {
                goTOMemberMenuPage();
            }
            else
            {
                goToEventList();
            }
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Event", e.getMessage());
        }
    }

    public void goTOMemberMenuPage() throws IOException {
        System.out.println("Loading Member menu window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Members/MemberMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnCancel.getScene().getWindow();
        MemberMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Globals.WindowCloseAndShow(root, window, "CEMS - Member Portal");
    }
}
