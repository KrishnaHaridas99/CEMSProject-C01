package CEMS.Club;

import CEMS.Admin.AdminMenuController;
import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateClubController {
    public TextField txtClubName;
    public TextArea txtClubDesc;
    public TextField txtClubPhone;
    public JFXButton btnSaveClub;
    public JFXButton btnClubCancel;

    public void btnSaveClubClick(ActionEvent actionEvent) {
        try{
            saveClubDetails();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Club", e.getMessage());
        }
    }

    public void saveClubDetails() throws Exception {
        System.out.println("---Saving Club details---");

        Club club = new Club();
        club.setClubName(txtClubName.getText());
        club.setClubDescription(txtClubDesc.getText());
        club.setClubPhone(txtClubPhone.getText());
        boolean isClubSaved =  club.saveClub(club);

        if (isClubSaved){
            Globals.ShowInfo("Save successfully", "Club: " + club.getClubName() + " saved successfully");
            goTOAdminMenuPage();
        }
        else {
            Globals.ShowError("Error", "Error in saving Club details");
        }
    }

    public void btnClubCancelClick(ActionEvent actionEvent) throws IOException {
        try{
            goTOAdminMenuPage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Club", e.getMessage());
        }
    }

    public void goTOAdminMenuPage() throws IOException {
        System.out.println("Loading Admin menu window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Admin/AdminMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnClubCancel.getScene().getWindow();
        AdminMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Globals.WindowCloseAndShow(root, window, "CEMS - Admin Portal");
    }
}
