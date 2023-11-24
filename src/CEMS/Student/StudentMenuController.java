package CEMS.Student;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Events.Event;
import CEMS.WelcomePageController;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentMenuController {
    public Label lblWelcome;
    public JFXButton btnStudentSignOut;
    public TableView tblEventList;

    @FXML
    private void initialize() {
        try {
            setEventTable();
            populateEventList();
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void setWelcomeMsg(String str) {
        lblWelcome.setText(lblWelcome.getText() + str);
    }

    public void btnStudentSignOutClick(ActionEvent actionEvent) {
        try{
            LoggedInUser.clearUser();
            goToWelcomePage();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error Student", e.getMessage());
        }
    }

    public void goToWelcomePage() throws IOException {
        System.out.println("Loading Welcome window");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../WelcomePage.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnStudentSignOut.getScene().getWindow();
        WelcomePageController controller = loader.getController();

        Globals.WindowCloseAndShow(root, window, "CEMS - Welcome");
    }

    public void populateEventList() throws SQLException {
        List<Event> eventList = new Student().getStudentEventsList(LoggedInUser.getUser().getUserID());
        tblEventList.getItems().clear();
        tblEventList.getItems().addAll(eventList);
    }

    public void setEventTable() throws SQLException {
        TableColumn<Event, String> colEventID = new TableColumn<>("Event ID");
        colEventID.setCellValueFactory(new PropertyValueFactory<>("EventID"));
        tblEventList.getColumns().add(colEventID);

        TableColumn<Event, String> colEventName = new TableColumn<>("Event Name");
        colEventName.setCellValueFactory(new PropertyValueFactory<>("EventName"));
        tblEventList.getColumns().add(colEventName);

        TableColumn<Event, String> colClubName = new TableColumn<>("Event Club Name");
        colClubName.setCellValueFactory(new PropertyValueFactory<>("EventClubName"));
        tblEventList.getColumns().add(colClubName);

        TableColumn<Event, String> colEventStart = new TableColumn<>("Event Start Date");
        colEventStart.setCellValueFactory(new PropertyValueFactory<>("EventStartDate"));
        tblEventList.getColumns().add(colEventStart);

        TableColumn<Event, String> colEventEnd = new TableColumn<>("Event End Date");
        colEventEnd.setCellValueFactory(new PropertyValueFactory<>("EventEndDate"));
        tblEventList.getColumns().add(colEventEnd);

        TableColumn<Event, String> colEventVenue = new TableColumn<>("Event Venue");
        colEventVenue.setCellValueFactory(new PropertyValueFactory<>("EventVenue"));
        tblEventList.getColumns().add(colEventVenue);

        TableColumn<Event, String> colEventDesc = new TableColumn<>("Event Desc");
        colEventDesc.setCellValueFactory(new PropertyValueFactory<>("EventDesc"));
        colEventDesc.setVisible(false);
        tblEventList.getColumns().add(colEventDesc);

        TableColumn<Event, Event> colIsAttending = new TableColumn<>("Is Attending");
        colIsAttending.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colIsAttending.setCellFactory(getAttendingCellFactory());
        tblEventList.getColumns().add(colIsAttending);

        TableColumn<Event, Event> colAction = new TableColumn<>("Action");
        colAction.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colAction.setCellFactory(getActionCellFactory());
        tblEventList.getColumns().add(colAction);
    }

    private Callback<TableColumn<Event, Event>, TableCell<Event, Event>> getAttendingCellFactory() {
        return new Callback<TableColumn<Event, Event>, TableCell<Event, Event>>() {
            @Override
            public TableCell<Event, Event> call(TableColumn<Event, Event> param) {
                return new TableCell<Event, Event>() {
                    final ImageView iconYes = new ImageView(new Image(getClass().getResourceAsStream("../Images/IconYes.png")));
                    final ImageView iconNo = new ImageView(new Image(getClass().getResourceAsStream("../Images/IconNo.png")));
                    final ImageView iconQues = new ImageView(new Image(getClass().getResourceAsStream("../Images/IconQues.png")));

                    @Override
                    protected void updateItem(Event objEvent, boolean empty) {
                        super.updateItem(objEvent, empty);
                        if (objEvent == null || empty) {
                            setGraphic(null);
                            setTooltip(null);
                        } else {
                            iconYes.setFitWidth(30);
                            iconYes.setFitHeight(30);

                            iconNo.setFitWidth(30);
                            iconNo.setFitHeight(30);

                            iconQues.setFitWidth(30);
                            iconQues.setFitHeight(30);

                            switch (objEvent.getStudentEventAttending()) {
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
                                    setTooltip(new Tooltip("No response"));
                            }
                            setText("");
                        }
                    }
                };
            }
        };
    }

    private Callback<TableColumn<Event, Event>, TableCell<Event, Event>> getActionCellFactory() {
        return new Callback<TableColumn<Event, Event>, TableCell<Event, Event>>() {
            @Override
            public TableCell<Event, Event> call(TableColumn<Event, Event> param) {
                return new TableCell<Event, Event>() {
                    final JFXButton viewButton = new JFXButton("View");
                    final HBox buttonBox = new HBox(viewButton);
                    final StackPane pane = new StackPane(buttonBox);
                    {
                        Image imgEdit = new Image(getClass().getResourceAsStream("../Images/IconView.png"));
                        ImageView iconEdit = new ImageView(imgEdit);
                        iconEdit.setFitHeight(20.0);
                        iconEdit.setFitWidth(20.0);
                        Tooltip tooltip = new Tooltip("View Event");

                        viewButton.setGraphic(iconEdit);
                        viewButton.setTooltip(tooltip);
                        viewButton.getStyleClass().add("glass-grey");
                        viewButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        viewButton.setOnAction(event -> {
                            viewEvent(getTableView().getItems().get(getIndex()));
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
        };
    }

    private void viewEvent(Event eventObj){
        try {
            showPopup(eventObj);
        } catch (IOException e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    private void showPopup(Event eventObj) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewEventStud.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("View Event Details");
        popupStage.setResizable(false);
        popupStage.setMaximized(false);
        popupStage.setScene(new Scene(root));

        ViewEventStudController viewEventStudController = loader.getController();
        viewEventStudController.parentWindow = (Stage) tblEventList.getScene().getWindow();
        viewEventStudController.populateEventDetail(eventObj);

        popupStage.showAndWait();
    }
}
