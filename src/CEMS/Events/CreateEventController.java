package CEMS.Events;

import CEMS.Club.Club;
import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Members.MemberMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CreateEventController {
    public ComboBox ddlClub;
    public DatePicker dtEventStart;
    public DatePicker dtEventEnd;
    public TextArea txtEventDesc;
    public TextField txtEventVenue;
    public TextField txtEventName;
    public JFXButton btnSaveEvent;
    public JFXButton btnCancel;

    public void setClubCombo(){
        List<Club> clubs = new Club().getClubs();
        for (Club club: clubs) {
            ddlClub.getItems().add(club.ClubID + "_" + club.ClubName);
        }
    }

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
        System.out.println("---Saving Event details---");

        Event event = new Event();
        event.EventName = txtEventName.getText();
        event.EventStartDate = dtEventStart.getValue().toString();
        event.EventEndDate = dtEventEnd.getValue().toString();
        event.EventVenue = txtEventVenue.getText();
        event.EventDesc = txtEventDesc.getText();
        event.CreatedBy = LoggedInUser.getUser().getUserName();

        String clubSelected = ddlClub.getValue().toString();
        String[] clubParts = clubSelected.split("_");
        event.ClubID = clubParts[0];

        boolean isMemSaved = event.saveEvent(event);

        if (isMemSaved){
            Globals.ShowInfo("Save success", "Event '" + event.EventName + "' saved successfully");
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
