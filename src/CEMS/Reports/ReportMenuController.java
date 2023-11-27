package CEMS.Reports;

import CEMS.Common.Globals;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ReportMenuController {
    public Stage parentWindow;
    public JFXButton btnSearchEvent;
    public JFXButton btnSearchStudent;

    public void btnSearchEventClick(ActionEvent actionEvent) {
        try{
            goToEventReport();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goToEventReport() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Reports/ReportSearchEvents.fxml"));
        Parent root = loader.load();
        ReportSearchEventsController controller = loader.getController();

        Stage window = (Stage) btnSearchEvent.getScene().getWindow();
        window.close();

        Globals.WindowCloseAndShow(root, parentWindow, "CEMS - Event Report");
    }

    public void btnSearchStudentClick(ActionEvent actionEvent) {
        try{
            goToStudentReport();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goToStudentReport() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Reports/ReportSearchStudent.fxml"));
        Parent root = loader.load();
        ReportSearchStudentController controller = loader.getController();

        Stage window = (Stage) btnSearchEvent.getScene().getWindow();
        window.close();

        Globals.WindowCloseAndShow(root, parentWindow, "CEMS - Attendance Report");
    }
}
