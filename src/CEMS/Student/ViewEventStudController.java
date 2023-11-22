package CEMS.Student;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Events.Event;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ViewEventStudController {
    public Label lblEventName;
    public Label lblClubName;
    public Label lblEventStart;
    public Label lblEventEnd;
    public Label lblEventVenue;
    public Label lblEventDesc;
    public RadioButton rbYes;
    public RadioButton rbNo;
    public RadioButton rbNotSure;
    public JFXButton btnSubmit;
    public ToggleGroup tgIsAttending;
    public Label lblEventID;

    public void populateEventDetail(Event event){
        lblEventID.setText(String.valueOf(event.getEventID()));
        lblEventName.setText(event.getEventName());
        lblClubName.setText(event.getEventClubName());
        lblEventStart.setText(event.getEventStartDate());
        lblEventEnd.setText(event.getEventEndDate());
        lblEventVenue.setText(event.getEventVenue());
        lblEventDesc.setText(event.getEventDesc());
        switch (event.getStudentEventAttending()) {
            case 1:
                rbYes.setSelected(true);
                break;
            case 2:
                rbNo.setSelected(true);
                break;
            default:
                rbNotSure.setSelected(true);
        }
    }

    public void btnSubmitClick(ActionEvent actionEvent) {
        try{
            saveStudentResponse();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    private void saveStudentResponse() throws Exception {
        int studID = LoggedInUser.getUser().getUserID();
        Event event = new Event();
        event.setEventID(Integer.parseInt(lblEventID.getText()));

        var selectedValue = tgIsAttending.getSelectedToggle().getUserData();
        event.setStudentEventAttending(Integer.parseInt((String) selectedValue));

        boolean isSaved = new Student().insertStudentEventAttendacne(studID, event);
        if (isSaved){
            goTOStudentMenuPage();
        }
        else{
            Globals.ShowError("Error", "Error in updating response");
        }
    }

    public void goTOStudentMenuPage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnSubmit.getScene().getWindow();
        StudentMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());
        controller.setEventTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - Student Portal");
    }
}
