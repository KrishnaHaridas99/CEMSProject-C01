package CEMS.Members;

import CEMS.Admin.AdminMenuController;
import CEMS.Club.Club;
import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CreateMemberController {
    public TextField txtMemFirstName;
    public TextField txtMemLastName;
    public TextField txtMemPhone;
    public DatePicker dtMemDOB;
    public TextField txtMemEmail;
    public ComboBox ddlMemberClub;
    public JFXButton btnSaveClubMember;
    public JFXButton btnMemberCancel;

    public void setClubCombo() throws Exception {
        List<Club> clubs = new Club().getClubs();
        for (Club club: clubs) {
            ddlMemberClub.getItems().add(club.getClubID() + "_" + club.getClubName());
        }
    }

    public void btnSaveMemberClick(ActionEvent actionEvent) {
        try{
            saveMember();
        }
        catch (Exception e){
            Globals.ShowError("Error Member", e.getMessage());
        }
    }

    public void saveMember() throws Exception {
        System.out.println("---Saving Member details---");

        Member member = new Member();
        member.setFirstName(txtMemFirstName.getText());
        member.setLastName(txtMemLastName.getText());
        member.setUserDOB(dtMemDOB.getValue().toString());
        member.setUserPh(txtMemPhone.getText());
        member.setUserEmail(txtMemEmail.getText());

        String clubSelected = ddlMemberClub.getValue().toString();
        String[] clubParts = clubSelected.split("_");
        member.clubSelected = clubParts[0];

        boolean isMemSaved =  member.saveMember(member);

        if (isMemSaved){
            Globals.ShowInfo("Save successfully", "Member Username: " + member.getUserEmail() + " saved successfully");
        }
        else {
            Globals.ShowError("Error", "Error in saving Member details");
        }

        goTOAdminMenuPage();
    }

    public void btnMemberCancelClick(ActionEvent actionEvent) {
        try{
            goTOAdminMenuPage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Member", e.getMessage());
        }
    }

    public void goTOAdminMenuPage() throws IOException {
        System.out.println("Loading Admin menu window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Admin/AdminMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnMemberCancel.getScene().getWindow();
        AdminMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Globals.WindowCloseAndShow(root, window, "CEMS - Admin Portal");
    }
}
