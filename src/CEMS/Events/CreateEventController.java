package CEMS.Events;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Members.MemberMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateEventController {
    public DatePicker dtEventStart;
    public DatePicker dtEventEnd;
    public TextArea txtEventDesc;
    public TextField txtEventVenue;
    public TextField txtEventName;
    public JFXButton btnSaveEvent;
    public JFXButton btnCancel;

    public void btnSaveEventClick(ActionEvent actionEvent) {
        try{
            saveEvent();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Event", e.getMessage());
        }
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

    public void btnCancelClick(ActionEvent actionEvent) {
        try{
            goTOMemberMenuPage();
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
