package CEMS.Club;

import CEMS.Common.Globals;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditClubDetailsController {
    public Label lblClubID;
    public TextField txtClubName;
    public TextField txtClubPhone;
    public TextArea txtClubDesc;
    public JFXButton btnSaveClub;
    public JFXButton btnClubCancel;

    public void PopulateClubDetails(Club objClub){
        lblClubID.setText(String.valueOf(objClub.getClubID()));
        txtClubName.setText(objClub.getClubName());
        txtClubPhone.setText(objClub.getClubPhone());
        txtClubDesc.setText(objClub.getClubDescription());
    }
    public void btnSaveClubClick(ActionEvent actionEvent) {
        try {
            Club club = new Club();
            club.setClubID(Integer.parseInt(lblClubID.getText()));
            club.setClubName(txtClubName.getText());
            club.setClubDescription(txtClubDesc.getText());
            club.setClubPhone(txtClubPhone.getText());
            boolean isClubSaved =  club.updateClubDetails(club);

            if (isClubSaved){
                Globals.ShowInfo("Details Updated", "Club: " + club.getClubName() + " details updated successfully");
                goToViewClub();
            }
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void btnClubCancelClick(ActionEvent actionEvent) {
        try{
            goToViewClub();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goToViewClub() throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Club/ViewClubs.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnClubCancel.getScene().getWindow();
        ViewClubsController controller = loader.getController();
        controller.setClubsTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - View Club");
    }
}
