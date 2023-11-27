package CEMS.Reports;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Events.Event;
import CEMS.Members.MemberMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReportSearchStudentController {
    public JFXButton btnBack;
    public ComboBox ddlEvent;
    public ComboBox ddlAttending;
    public JFXButton btnSearch;
    public TableView tblAttendance;

    @FXML
    private void initialize() {
        try {
            setDDLEvent();
            setDDLAttending();
            setTblAttendance();
        }
        catch (SQLException e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void setDDLEvent() throws SQLException {
        List<Event> eventList = new Event().getEventsList(LoggedInUser.getUser().getUserID());

        ddlEvent.getItems().add("0_All Events");
        for (Event event: eventList) {
            ddlEvent.getItems().add(event.getEventID() + "_" + event.getEventName());
        }
    }

    public void setDDLAttending() {
        for (Globals.AttendanceType value : Globals.AttendanceType.values()) {
            ddlAttending.getItems().add(value);
        }
    }

    public void setTblAttendance(){
        TableColumn<StudentReport, String> colStudentName = new TableColumn<>("Student Name");
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colStudentName.setPrefWidth(150);
        tblAttendance.getColumns().add(colStudentName);

        TableColumn<StudentReport, String> colStudentEmail = new TableColumn<>("Student Email");
        colStudentEmail.setCellValueFactory(new PropertyValueFactory<>("UserEmail"));
        colStudentEmail.setPrefWidth(150);
        tblAttendance.getColumns().add(colStudentEmail);

        TableColumn<StudentReport, String> colEventID = new TableColumn<>("Event Name");
        colEventID.setCellValueFactory(new PropertyValueFactory<>("EventName"));
        colEventID.setPrefWidth(150);
        tblAttendance.getColumns().add(colEventID);

        TableColumn<StudentReport, String> colEventStart = new TableColumn<>("Event Start Date");
        colEventStart.setCellValueFactory(new PropertyValueFactory<>("EventStartDate"));
        colEventStart.setPrefWidth(100);
        tblAttendance.getColumns().add(colEventStart);

        TableColumn<StudentReport, String> colEventEnd = new TableColumn<>("Event End Date");
        colEventEnd.setCellValueFactory(new PropertyValueFactory<>("EventEndDate"));
        colEventEnd.setPrefWidth(100);
        tblAttendance.getColumns().add(colEventEnd);

        TableColumn<StudentReport, StudentReport> colIsAttending = new TableColumn<>("Is Student Attending");
        colIsAttending.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colIsAttending.setCellFactory(getAttendingCellFactory());
        colIsAttending.setPrefWidth(140);
        tblAttendance.getColumns().add(colIsAttending);
    }

    private Callback<TableColumn<StudentReport, StudentReport>, TableCell<StudentReport, StudentReport>> getAttendingCellFactory() {
        return new Callback<TableColumn<StudentReport, StudentReport>, TableCell<StudentReport, StudentReport>>() {
            @Override
            public TableCell<StudentReport, StudentReport> call(TableColumn<StudentReport, StudentReport> param) {
                return new TableCell<StudentReport, StudentReport>() {
                    final ImageView iconYes = new ImageView(new Image(getClass().getResourceAsStream("../Images/IconYes1.png")));
                    final ImageView iconNo = new ImageView(new Image(getClass().getResourceAsStream("../Images/IconNo1.png")));
                    final ImageView iconQues = new ImageView(new Image(getClass().getResourceAsStream("../Images/IconMayBe.png")));

                    @Override
                    protected void updateItem(StudentReport objStudentEvent, boolean empty) {
                        super.updateItem(objStudentEvent, empty);
                        if (objStudentEvent == null || empty) {
                            setGraphic(null);
                            setTooltip(null);
                        } else {
                            iconYes.setFitWidth(60);
                            iconYes.setFitHeight(30);

                            iconNo.setFitWidth(60);
                            iconNo.setFitHeight(30);

                            iconQues.setFitWidth(80);
                            iconQues.setFitHeight(30);

                            switch (objStudentEvent.getStudentEventAttending()) {
                                case 1:
                                    setGraphic(iconYes);
                                    setTooltip(new Tooltip("Attending"));
                                    break;
                                case 2:
                                    setGraphic(iconNo);
                                    setTooltip(new Tooltip("Not attending"));
                                    break;
                                default:
                                    setGraphic(iconQues);
                                    setTooltip(new Tooltip("May be"));
                            }
                        }
                        setText("");
                    }
                };
            }
        };
    }

    public void btnBackClick(ActionEvent actionEvent) {
        try{
            goTOMemberMenuPage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goTOMemberMenuPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Members/MemberMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnBack.getScene().getWindow();
        MemberMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Globals.WindowCloseAndShow(root, window, "CEMS - Member Portal");
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        try{
            int memberID = LoggedInUser.getUser().getUserID();

            int eventID = 0;
            if (ddlEvent.getValue() != null) {
                String eventSelected = ddlEvent.getValue().toString();
                String[] eventParts = eventSelected.split("_");
                eventID = Integer.parseInt(eventParts[0]);
            }

            int isAttending = getIsAttending();

            List<StudentReport> studEventList = new StudentReport().searchStudentAttendance(memberID, eventID, isAttending);
            populateStudentEventList(studEventList);
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    private int getIsAttending() {
        int isAttending;
        Object strDdlAttending = ddlAttending.getValue() != null ? ddlAttending.getValue() : Globals.AttendanceType.ALL;
        if (strDdlAttending == Globals.AttendanceType.YES) {
            isAttending = 1;
        }
        else if (strDdlAttending == Globals.AttendanceType.NO) {
            isAttending = 2;
        }
        else if (strDdlAttending == Globals.AttendanceType.MAY_BE) {
            isAttending = 0;
        }
        else {
            isAttending = 3; //Set value 3 to by default get all the attendance status
        }
        return isAttending;
    }

    public void populateStudentEventList(List<StudentReport> studEventList) {
        tblAttendance.getItems().clear();
        tblAttendance.getItems().addAll(studEventList);
    }
}
