package CEMS.Admin;

import CEMS.Club.CreateClubController;
import CEMS.Club.ViewClubsController;
import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Members.CreateMemberController;
import CEMS.Members.ViewMembersController;
import CEMS.Student.CreateStudentController;
import CEMS.Student.ViewStudentsController;
import CEMS.WelcomePageController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMenuController {
    public Label lblWelcome;
    public JFXButton btnAdminSignOut;
    public JFXButton btnCreateClub;
    public JFXButton btnCreateClubMember;
    public JFXButton btnCreateStudent;
    public JFXButton btnViewClub;
    public JFXButton btnViewClubMember;
    public JFXButton btnViewStudent;

    public void setWelcomeMsg(String str) {
        lblWelcome.setText(lblWelcome.getText() + str);
    }

    public void btnCreateClubClick(ActionEvent actionEvent) {
        try{
            goToCreateClub();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Create Club", e.getMessage());
        }
    }

    public void goToCreateClub() throws IOException{
        System.out.println("Loading Create club window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Club/CreateClub.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnCreateClub.getScene().getWindow();
        CreateClubController controller = loader.getController();

        Globals.WindowCloseAndShow(root, window, "CEMS - Create Club");
    }

    public void btnCreateClubMemberClick(ActionEvent actionEvent) {
        try{
            goToCreateClubMember();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Create Member", e.getMessage());
        }
    }

    public void goToCreateClubMember() throws Exception{
        System.out.println("Loading Create member window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Members/CreateMember.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnCreateClub.getScene().getWindow();
        CreateMemberController controller = loader.getController();
        controller.setClubCombo();

        Globals.WindowCloseAndShow(root, window, "CEMS - Create Member");
    }

    public void btnCreateStudentClick(ActionEvent actionEvent) {
        try {
            goToCreateStudent();
        }
        catch (Exception e) {
            Globals.ShowError("Error Create Student", e.getMessage());
        }
    }

    public void goToCreateStudent() throws IOException{
        System.out.println("Loading Create student window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Student/CreateStudent.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnCreateClub.getScene().getWindow();
        CreateStudentController controller = loader.getController();

        Globals.WindowCloseAndShow(root, window, "CEMS - Create Student");
    }

    public void btnAdminSignOutClick(ActionEvent actionEvent) {
        try{
            LoggedInUser.clearUser();
            goToWelcomePage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Admin", e.getMessage());
        }
    }

    public void goToWelcomePage() throws IOException {
        System.out.println("Loading Welcome window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../WelcomePage.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnAdminSignOut.getScene().getWindow();
        WelcomePageController controller = loader.getController();

        Globals.WindowCloseAndShow(root, window, "CEMS - Welcome");
    }

    public void btnViewClubClick(ActionEvent actionEvent) {
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
        Stage window = (Stage) btnViewClub.getScene().getWindow();
        ViewClubsController controller = loader.getController();
        controller.setClubsTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - View Club");
    }

    public void btnViewClubMemberClick(ActionEvent actionEvent) {
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
        Stage window = (Stage) btnViewClubMember.getScene().getWindow();
        ViewMembersController controller = loader.getController();
        controller.setMembersTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - View Member");
    }

    public void btnViewStudentClick(ActionEvent actionEvent) {
        try{
            goToViewStudents();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goToViewStudents() throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Student/ViewStudents.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnViewClubMember.getScene().getWindow();
        ViewStudentsController controller = loader.getController();
        controller.setStudentsTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - View Students");
    }
}
