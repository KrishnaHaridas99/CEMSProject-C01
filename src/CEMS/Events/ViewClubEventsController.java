package CEMS.Events;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Members.MemberMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ViewClubEventsController {
    public TableView tblEvents;
    public JFXButton btnBack;

    public void setEventTable() throws SQLException {
        List<Event> eventList = new Event().getEventsList(LoggedInUser.getUser().getUserID());

        TableColumn<Event, String> colEventID = new TableColumn<>("Event ID");
        colEventID.setCellValueFactory(new PropertyValueFactory<>("EventID"));
        colEventID.setVisible(false);
        tblEvents.getColumns().add(colEventID);

        TableColumn<Event, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(new PropertyValueFactory<>("EventName"));
        tblEvents.getColumns().add(colEventName);

        TableColumn<Event, String> colEventStart = new TableColumn<>("Event Start Date");
        colEventStart.setCellValueFactory(new PropertyValueFactory<>("EventStartDate"));
        tblEvents.getColumns().add(colEventStart);

        TableColumn<Event, String> colEventEnd = new TableColumn<>("Event End Date");
        colEventEnd.setCellValueFactory(new PropertyValueFactory<>("EventEndDate"));
        tblEvents.getColumns().add(colEventEnd);

        TableColumn<Event, String> colEventVenue = new TableColumn<>("Event Venue");
        colEventVenue.setCellValueFactory(new PropertyValueFactory<>("EventVenue"));
        tblEvents.getColumns().add(colEventVenue);

        TableColumn<Event, String> colEventDesc = new TableColumn<>("Event Desc");
        colEventDesc.setCellValueFactory(new PropertyValueFactory<>("EventDesc"));
        tblEvents.getColumns().add(colEventDesc);

        TableColumn<Event, Event> editDeleteColumn = new TableColumn<>("Actions");
        editDeleteColumn.setSortable(false);
        editDeleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        editDeleteColumn.setCellFactory(new Callback<TableColumn<Event, Event>, TableCell<Event, Event>>() {
            @Override
            public TableCell<Event, Event> call(TableColumn<Event, Event> param) {
                return new TableCell<Event, Event>() {
                    final JFXButton editButton = new JFXButton();
                    final JFXButton deleteButton = new JFXButton();
                    final HBox buttonBox = new HBox(editButton, deleteButton);
                    final StackPane pane = new StackPane(buttonBox);
                    {
                        Image imgEdit = new Image(getClass().getResourceAsStream("../Images/IconEdit.png"));
                        ImageView iconEdit = new ImageView(imgEdit);
                        iconEdit.setFitHeight(20.0);
                        iconEdit.setFitWidth(20.0);
                        Tooltip tooltip = new Tooltip("Edit Event");

                        editButton.setGraphic(iconEdit);
                        editButton.setTooltip(tooltip);
                        editButton.getStyleClass().add("glass-grey");
                        editButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        editButton.setOnAction(event -> {
                            editEvent(getTableView().getItems().get(getIndex()));
                        });

                        Image imgDelete = new Image(getClass().getResourceAsStream("../Images/IconDelete.png"));
                        ImageView iconDelete = new ImageView(imgDelete);
                        iconDelete.setFitHeight(20.0);
                        iconDelete.setFitWidth(20.0);
                        Tooltip tooltipD = new Tooltip("Delete Event");

                        deleteButton.setGraphic(iconDelete);
                        deleteButton.setTooltip(tooltipD);
                        deleteButton.getStyleClass().add("round-red");
                        deleteButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        deleteButton.setOnAction(event -> {
                            deleteEvent(getTableView().getItems().get(getIndex()));
                        });
                    }

                    @Override
                    protected void updateItem(Event objEvent, boolean empty) {
                        super.updateItem(objEvent, empty);
                        if (objEvent == null || empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        });
        tblEvents.getColumns().add(editDeleteColumn);

        // Add data to the table
        tblEvents.getItems().addAll(eventList);
    }

    public void editEvent(Event event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Events/CreateEvent.fxml"));
            Parent root = loader.load();
            Stage window = (Stage) btnBack.getScene().getWindow();

            CreateEventController controller = loader.getController();
            controller.isEdit = true;
            controller.populateEventDetails(event);

            Globals.WindowCloseAndShow(root, window, "CEMS - Edit Event");
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void deleteEvent(Event event){
        String msgBody = "Do you want to Delete Event ?";
        boolean isDelete = Globals.showConfirmationDialog("Delete", "Event Delete", msgBody);
        if(isDelete)
        {
            try {
                isDelete = new Event().deleteEvent(event);
                if (isDelete) {
                    Globals.ShowInfo("Deleted", "Event Deleted Successfully");
                    goToEventList();
                }
            } catch (Exception e) {
                Globals.ShowError("Error", e.getMessage());
            }
        }
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

    public void goToEventList() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Events/ViewClubEvents.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnBack.getScene().getWindow();
        ViewClubEventsController controller = loader.getController();
        controller.setEventTable();

        Globals.WindowCloseAndShow(root, window, "CEMS - Events");
    }
}
