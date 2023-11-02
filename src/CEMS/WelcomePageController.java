package CEMS;

import CEMS.Admin.AdminSignInController;
import CEMS.Common.Globals;
import CEMS.Members.MemberSignInController;
import CEMS.Student.StudentSignInController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePageController {

    @FXML private JFXButton btnAdminPortal;
    @FXML private JFXButton btnMemberPortal;
    @FXML private JFXButton btnStudentPortal;

    public void btnAdminPortalClick(ActionEvent actionEvent) {
        try{
            goToAdminSignIn();
        }
        catch(Exception e){
            Globals.ShowError("Error - Admin", e.getMessage());
        }
    }

    public void goToAdminSignIn() throws IOException {
        System.out.println("Loading Admin sign in window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin/AdminSignIn.fxml"));
        Parent root = loader.load();
        AdminSignInController controller = loader.getController();

        Stage window = (Stage) btnAdminPortal.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Admin Sign In");
        window.show();
    }

    public void btnStudentPortalClick(ActionEvent actionEvent) {
        try{
            goToStudentSignIn();
        }
        catch(Exception e){
            Globals.ShowError("Error - Student", e.getMessage());
        }
    }

    public void goToStudentSignIn() throws IOException {
        System.out.println("Loading Student sign in window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Student/StudentSignIn.fxml"));
        Parent root = loader.load();
        StudentSignInController controller = loader.getController();

        Stage window = (Stage) btnStudentPortal.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Student Sign In");
        window.show();
    }

    public void btnMemberPortallClick(ActionEvent actionEvent) {
        try{
            goToMemberSignIn();
        }
        catch(Exception e){
            Globals.ShowError("Error - Member", e.getMessage());
        }
    }

    public void goToMemberSignIn() throws IOException {
        System.out.println("Loading Member sign in window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Members/MemberSignIn.fxml"));
        Parent root = loader.load();
        MemberSignInController controller = loader.getController();

        Stage window = (Stage) btnMemberPortal.getScene().getWindow();
        window.close();

        window = new Stage();
        window.setScene(new Scene(root, 900, 600));
        window.setTitle("CEMS - Member Sign In");
        window.show();
    }
}
