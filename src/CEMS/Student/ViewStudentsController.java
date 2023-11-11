package CEMS.Student;

import CEMS.Admin.AdminMenuController;
import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class ViewStudentsController {
    public JFXButton btnBack;
    public TableView tblStudents;

    public void setStudentsTable() throws Exception {
        List<Student> studentsList = new Student().getStudentsList();

        TableColumn<Student, String> colStudID = new TableColumn<>("ID");
        colStudID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        tblStudents.getColumns().add(colStudID);

        TableColumn<Student, String> colFirstName = new TableColumn<>("First Name");
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        tblStudents.getColumns().add(colFirstName);

        TableColumn<Student, String> colLastName = new TableColumn<>("Last name");
        colLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tblStudents.getColumns().add(colLastName);

        TableColumn<Student, String> colDOB = new TableColumn<>("Date of birth");
        colDOB.setCellValueFactory(new PropertyValueFactory<>("UserDOB"));
        tblStudents.getColumns().add(colDOB);

        TableColumn<Student, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("UserEmail"));
        tblStudents.getColumns().add(colEmail);

        TableColumn<Student, String> colUserPh = new TableColumn<>("Phone");
        colUserPh.setCellValueFactory(new PropertyValueFactory<>("UserPh"));
        tblStudents.getColumns().add(colUserPh);

        TableColumn<Student, Student> editDeleteColumn = new TableColumn<>("Actions");
        editDeleteColumn.setSortable(false);
        editDeleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        editDeleteColumn.setCellFactory(new Callback<TableColumn<Student, Student>, TableCell<Student, Student>>() {
            @Override
            public TableCell<Student, Student> call(TableColumn<Student, Student> param) {
                return new TableCell<Student, Student>() {
                    final JFXButton editButton = new JFXButton("Edit");
                    final JFXButton deleteButton = new JFXButton("Delete");
                    final HBox buttonBox = new HBox(editButton, deleteButton);
                    final StackPane pane = new StackPane(buttonBox);
                    {
                        editButton.getStyleClass().add("glass-grey");
                        editButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        editButton.setOnAction(event -> {
                            Student student = getTableView().getItems().get(getIndex());
                            editStudentDetails(student.getUserID());
                        });

                        deleteButton.getStyleClass().add("round-red");
                        deleteButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        deleteButton.setOnAction(event -> {
                            Student student = getTableView().getItems().get(getIndex());
                            deleteStudent(student);
                        });
                    }

                    @Override
                    protected void updateItem(Student student, boolean empty) {
                        super.updateItem(student, empty);
                        if (student == null || empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        });
        tblStudents.getColumns().add(editDeleteColumn);

        tblStudents.getItems().addAll(studentsList);
    }

    public void editStudentDetails(int studentID)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Student/EditStudent.fxml"));
            Parent root = loader.load();
            Stage window = (Stage) btnBack.getScene().getWindow();

            EditStudentController controller = loader.getController();
            controller.populateStudentDetails(studentID);

            Globals.WindowCloseAndShow(root, window, "CEMS - Edit Student");
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void deleteStudent(Student objStudent){
        String msgBody = "Do you want to Delete Student ?";
        boolean isDelete = Globals.showConfirmationDialog("Delete", "Student Delete", msgBody);
        if(isDelete)
        {
            try {
                isDelete = new Student().deleteStudent(objStudent.getUserID());
                if (isDelete) {
                    Globals.ShowInfo("Deleted", "Student Deleted Successfully");
                    goToViewStudents();
                }
            } catch (Exception e) {
                Globals.ShowError("Error", e.getMessage());
            }
        }
    }

    public void btnBackClick(ActionEvent actionEvent) {
        try{
            goTOAdminMenuPage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goTOAdminMenuPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Admin/AdminMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnBack.getScene().getWindow();

        AdminMenuController controller = loader.getController();
        controller.setWelcomeMsg(LoggedInUser.getUser().getName());

        Globals.WindowCloseAndShow(root, window, "CEMS - Admin Portal");
    }

    public void goToViewStudents() throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Student/ViewStudents.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnBack.getScene().getWindow();
        ViewStudentsController controller = loader.getController();
        controller.setStudentsTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - View Students");
    }
}
