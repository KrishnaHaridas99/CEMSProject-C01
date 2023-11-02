package CEMS.Club;

import CEMS.Admin.AdminMenuController;
import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        club.ClubName = txtClubName.getText();
        club.ClubDescription = txtClubDesc.getText();
        club.ClubPhone = txtClubPhone.getText();
        boolean isClubSaved =  club.saveClub(club);

        if (isClubSaved){
            Globals.ShowInfo("Save successfull", "Club: " + club.ClubName + " saved successfully");
        }
        else {
            Globals.ShowError("Error", "Error in saving Club details");
        }

        goTOAdminMenuPage();
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

        AdminMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Stage window = (Stage) btnClubCancel.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Admin Portal");
        window.show();
    }
}
