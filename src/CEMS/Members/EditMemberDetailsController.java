package CEMS.Members;

import CEMS.Club.Club;
import CEMS.Common.Globals;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class EditMemberDetailsController {
    public Label lblMemberID;
    public TextField txtMemFirstName;
    public TextField txtMemLastName;
    public DatePicker dtMemDOB;
    public TextField txtMemPhone;
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

    public void populateMemberDetails(int memberID) throws Exception {
        Member memDetails = new Member().getMembersDetails(memberID);
        if (memDetails != null){
            lblMemberID.setText(String.valueOf(memDetails.getUserID()));
            txtMemFirstName.setText(memDetails.getFirstName());
            txtMemLastName.setText(memDetails.getLastName());
            dtMemDOB.setValue(LocalDate.parse(memDetails.getUserDOB()));
            txtMemPhone.setText(memDetails.getUserPh());
            txtMemEmail.setText(memDetails.getUserEmail());
            if (memDetails.getClubID() == 0)
                ddlMemberClub.setValue("--Select Club--");
            else
                ddlMemberClub.setValue(memDetails.getClubSelected());
        }
    }

    public void btnSaveMemberClick(ActionEvent actionEvent) {
        try{
            Member member = new Member();
            member.setUserID(Integer.parseInt(lblMemberID.getText()));
            member.setFirstName(txtMemFirstName.getText());
            member.setLastName(txtMemLastName.getText());
            member.setUserDOB(dtMemDOB.getValue().toString());
            member.setUserPh(txtMemPhone.getText());
            member.setUserEmail(txtMemEmail.getText());

            String clubSelected = ddlMemberClub.getValue().toString();
            String[] clubParts = clubSelected.split("_");
            member.clubSelected = clubParts[0];

            boolean isMemSaved =  member.updateMember(member);
            if (isMemSaved){
                Globals.ShowInfo("Updated successfully", "Member updated successfully");
                goToViewMember();
            }
            else {
                Globals.ShowError("Error", "Error in saving Member details");
            }
        }
        catch (Exception e){
            Globals.ShowError("Error", e.getMessage());
        }
    }


    public void btnMemberCancelClick(ActionEvent actionEvent) {
        try{
            goToViewMember();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goToViewMember() throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Members/ViewMembers.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnMemberCancel.getScene().getWindow();
        ViewMembersController controller = loader.getController();
        controller.setMembersTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - View Member");
    }
}
