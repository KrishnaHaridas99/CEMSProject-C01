package CEMS.Student;

import CEMS.Admin.AdminMenuController;
import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateStudentController {

    public TextField txtStudFirstName;
    public TextField txtStudLastName;
    public DatePicker dtStudDOB;
    public TextField txtStudPhone;
    public TextField txtStudEmail;
    public JFXButton btnSaveStudent;
    public JFXButton btnStudentCancel;

    public void btnSaveStudentClick(ActionEvent actionEvent) {
        try{
            saveStudent();
        }
        catch (Exception e){
            Globals.ShowError("Error Student", e.getMessage());
        }
    }

    public void saveStudent() throws Exception {
        System.out.println("---Saving Student details---");

        Student student = new Student();
        student.setFirstName(txtStudFirstName.getText());
        student.setLastName(txtStudLastName.getText());
        student.setUserDOB(dtStudDOB.getValue().toString());
        student.setUserPh(txtStudPhone.getText());
        student.setUserEmail(txtStudEmail.getText());

        boolean isStudSaved =  student.saveStudent(student);

        if (isStudSaved){
            Globals.ShowInfo("Save successfully", "Student Username: " + student.getUserEmail() + " saved successfully");
        }
        else {
            Globals.ShowError("Error", "Error in saving student details");
        }

        goTOAdminMenuPage();
    }

    public void btnStudentCancelClick(ActionEvent actionEvent) {
        try{
            goTOAdminMenuPage();
        }
        catch (Exception e) {
            Globals.ShowError("Error Student", e.getMessage());
        }
    }

    public void goTOAdminMenuPage() throws IOException {
        System.out.println("Loading Admin menu window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Admin/AdminMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnStudentCancel.getScene().getWindow();
        AdminMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Globals.WindowCloseAndShow(root, window, "CEMS - Admin Portal");
    }
}
