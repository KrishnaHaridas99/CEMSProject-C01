package CEMS.Members;

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

public class ViewMembersController {
    public JFXButton btnBack;
    public TableView tblMembers;

    public void setMembersTable() throws Exception {
        List<Member> memberList = new Member().getMembersList();

        TableColumn<Member, String> colID = new TableColumn<>("Member ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        tblMembers.getColumns().add(colID);

        TableColumn<Member, String> colFirstName = new TableColumn<>("First Name");
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        tblMembers.getColumns().add(colFirstName);

        TableColumn<Member, String> colLastName = new TableColumn<>("Last name");
        colLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        tblMembers.getColumns().add(colLastName);

        TableColumn<Member, String> colDOB = new TableColumn<>("Date of birth");
        colDOB.setCellValueFactory(new PropertyValueFactory<>("UserDOB"));
        tblMembers.getColumns().add(colDOB);

        TableColumn<Member, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("UserEmail"));
        tblMembers.getColumns().add(colEmail);

        TableColumn<Member, String> colUserPh = new TableColumn<>("Phone");
        colUserPh.setCellValueFactory(new PropertyValueFactory<>("UserPh"));
        tblMembers.getColumns().add(colUserPh);

        TableColumn<Member, String> colClubName = new TableColumn<>("Club Name");
        colClubName.setCellValueFactory(new PropertyValueFactory<>("ClubName"));
        tblMembers.getColumns().add(colClubName);

        TableColumn<Member, Member> editDeleteColumn = new TableColumn<>("Actions");
        editDeleteColumn.setSortable(false);
        editDeleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        editDeleteColumn.setCellFactory(new Callback<TableColumn<Member, Member>, TableCell<Member, Member>>() {
            @Override
            public TableCell<Member, Member> call(TableColumn<Member, Member> param) {
                return new TableCell<Member, Member>() {
                    final JFXButton editButton = new JFXButton("Edit");
                    final JFXButton deleteButton = new JFXButton("Delete");
                    final HBox buttonBox = new HBox(editButton, deleteButton);
                    final StackPane pane = new StackPane(buttonBox);
                    {
                        editButton.getStyleClass().add("glass-grey");
                        editButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        editButton.setOnAction(event -> {
                            Member member = getTableView().getItems().get(getIndex());
                            editMemberDetails(member.getUserID());
                        });

                        deleteButton.getStyleClass().add("round-red");
                        deleteButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        deleteButton.setOnAction(event -> {
                            Member member = getTableView().getItems().get(getIndex());
                            deleteMember(member);
                        });
                    }

                    @Override
                    protected void updateItem(Member member, boolean empty) {
                        super.updateItem(member, empty);
                        if (member == null || empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        });
        tblMembers.getColumns().add(editDeleteColumn);

        tblMembers.getItems().addAll(memberList);
    }

    public void editMemberDetails(int memberID)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Members/EditMemberDetails.fxml"));
            Parent root = loader.load();
            Stage window = (Stage) btnBack.getScene().getWindow();

            EditMemberDetailsController controller = loader.getController();
            controller.setClubCombo();
            controller.populateMemberDetails(memberID);

            Globals.WindowCloseAndShow(root, window, "CEMS - Edit Member");
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void deleteMember(Member objMember){
        String msgBody = "Do you want to Delete Member ?";
        boolean isDelete = Globals.showConfirmationDialog("Delete", "Member Delete", msgBody);
        if(isDelete)
        {
            try {
                isDelete = new Member().deleteMember(objMember.getUserID());
                if (isDelete) {
                    Globals.ShowInfo("Deleted", "Club Deleted Successfully");
                    goToViewMember();
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

    public void goToViewMember() throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Members/ViewMembers.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnBack.getScene().getWindow();
        ViewMembersController controller = loader.getController();
        controller.setMembersTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - View Member");
    }
}
