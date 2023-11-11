package CEMS.Club;

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

public class ViewClubsController {
    public TableView tblClubs;
    public JFXButton btnBack;

    public void setClubsTable() throws Exception {
        List<Club> clubList = new Club().getClubs();

        TableColumn<Club, String> colClubID = new TableColumn<>("Club ID");
        colClubID.setCellValueFactory(new PropertyValueFactory<>("ClubID"));
        tblClubs.getColumns().add(colClubID);

        TableColumn<Club, String> colClubName = new TableColumn<>("Club Name");
        colClubName.setCellValueFactory(new PropertyValueFactory<>("ClubName"));
        tblClubs.getColumns().add(colClubName);

        TableColumn<Club, String> colClubPhone = new TableColumn<>("Club Phone");
        colClubPhone.setCellValueFactory(new PropertyValueFactory<>("ClubPhone"));
        tblClubs.getColumns().add(colClubPhone);

        TableColumn<Club, String> colClubDescription = new TableColumn<>("Club Description");
        colClubDescription.setCellValueFactory(new PropertyValueFactory<>("ClubDescription"));
        tblClubs.getColumns().add(colClubDescription);

        TableColumn<Club, Club> editDeleteColumn = new TableColumn<>("Actions");
        editDeleteColumn.setSortable(false);
        editDeleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        editDeleteColumn.setCellFactory(new Callback<TableColumn<Club, Club>, TableCell<Club, Club>>() {
            @Override
            public TableCell<Club, Club> call(TableColumn<Club, Club> param) {
                return new TableCell<Club, Club>() {
                    final JFXButton editButton = new JFXButton("Edit");
                    final JFXButton deleteButton = new JFXButton("Delete");
                    final HBox buttonBox = new HBox(editButton, deleteButton);
                    final StackPane pane = new StackPane(buttonBox);
                    {
                        editButton.getStyleClass().add("glass-grey");
                        editButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        editButton.setOnAction(event -> {
                            Club club = getTableView().getItems().get(getIndex());
                            editClubDetails(club);
                        });

                        deleteButton.getStyleClass().add("round-red");
                        deleteButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        deleteButton.setOnAction(event -> {
                            Club club = getTableView().getItems().get(getIndex());
                            deleteClub(club);
                        });
                    }

                    @Override
                    protected void updateItem(Club person, boolean empty) {
                        super.updateItem(person, empty);
                        if (person == null || empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        });
        tblClubs.getColumns().add(editDeleteColumn);

        // Add data to the table
        tblClubs.getItems().addAll(clubList);
    }

    public void editClubDetails(Club club)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Club/EditClubDetails.fxml"));
            Parent root = loader.load();
            Stage window = (Stage) btnBack.getScene().getWindow();

            EditClubDetailsController controller = loader.getController();
            controller.PopulateClubDetails(club);

            Globals.WindowCloseAndShow(root, window, "CEMS - Edit Club");
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void deleteClub(Club club){
        String msgBody = "Do you want to Delete Club ?\nDeleting the club will also delete respective members \nand events.";
        boolean isDelete = Globals.showConfirmationDialog("Delete", "Club Delete", msgBody);
        if(isDelete)
        {
            try {
                isDelete = new Club().deleteClub(club.getClubID());
                if (isDelete) {
                    Globals.ShowInfo("Deleted", "Club Deleted Successfully");
                    goToViewClub();
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

    public void goToViewClub() throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Club/ViewClubs.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnBack.getScene().getWindow();
        ViewClubsController controller = loader.getController();
        controller.setClubsTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - View Club");
    }
}
