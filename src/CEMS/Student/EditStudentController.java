package CEMS.Student;

import CEMS.Common.Globals;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditStudentController {
    public Label lblStudID;
    public TextField txtStudFirstName;
    public TextField txtStudLastName;
    public DatePicker dtStudDOB;
    public TextField txtStudPhone;
    public TextField txtStudEmail;
    public JFXButton btnSaveStudent;
    public JFXButton btnStudentCancel;

    public void populateStudentDetails(int studID) throws Exception {
        Student studDetails = new Student().getStudentDetail(studID);
        if (studDetails != null){
            lblStudID.setText(String.valueOf(studDetails.getUserID()));
            txtStudFirstName.setText(studDetails.getFirstName());
            txtStudLastName.setText(studDetails.getLastName());
            dtStudDOB.setValue(LocalDate.parse(studDetails.getUserDOB()));
            txtStudPhone.setText(studDetails.getUserPh());
            txtStudEmail.setText(studDetails.getUserEmail());
        }
    }

    public void btnSaveStudentClick(ActionEvent actionEvent) {
        try{
            Student student = new Student();
            student.setUserID(Integer.parseInt(lblStudID.getText()));
            student.setFirstName(txtStudFirstName.getText());
            student.setLastName(txtStudLastName.getText());
            student.setUserDOB(dtStudDOB.getValue().toString());
            student.setUserPh(txtStudPhone.getText());
            student.setUserEmail(txtStudEmail.getText());

            boolean isStudSaved =  student.updateStudent(student);
            if (isStudSaved){
                Globals.ShowInfo("Updated successfully", "Student updated successfully");
                goToViewStudents();
            }
            else {
                Globals.ShowError("Error", "Error in saving Member details");
            }
        }
        catch (Exception e){
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void btnStudentCancelClick(ActionEvent actionEvent) {
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
        Stage window = (Stage) btnStudentCancel.getScene().getWindow();
        ViewStudentsController controller = loader.getController();
        controller.setStudentsTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - View Students");
    }
}
