package CEMS.Events;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Members.MemberMenuController;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EventBudgetController {
    public ComboBox ddlEvent;
    public TextField txtExpenseName;
    public TextField txtCost;
    public JFXButton btnSaveBudget;
    public TableView tblEventBudget;
    public JFXButton btnBack;

    public void setEventList() throws SQLException {
        List<Event> eventList = new Event().getEventsList(LoggedInUser.getUser().getUserID());

        for (Event event: eventList) {
            ddlEvent.getItems().add(event.getEventID() + "_" + event.getEventName());
        }
    }

    public void setEventBudgetTable(int EventID) throws SQLException {

        ObservableList<EventBudget> eventList = FXCollections.observableArrayList();
        for (EventBudget eventBudget: new EventBudget().getEventBudget(EventID)) {
            eventList.add(eventBudget);
        }

        TableColumn<Event, String> colExpenseName = new TableColumn<>("Expense Name");
        colExpenseName.setCellValueFactory(new PropertyValueFactory<>("ExpenseName"));
        tblEventBudget.getColumns().add(colExpenseName);

        TableColumn<Event, String> colCost = new TableColumn<>("Cost ($)");
        colCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        tblEventBudget.getColumns().add(colCost);

        tblEventBudget.getItems().addAll(eventList);
    }

    public void btnSaveBudgetClick(ActionEvent actionEvent) {
        try{
            saveEventBudget();
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void saveEventBudget() throws Exception {
        EventBudget eventBudget = new EventBudget();
        eventBudget.setExpenseName(txtExpenseName.getText());
        eventBudget.setCost(Float.parseFloat(txtCost.getText()));

        String eventSelected = ddlEvent.getValue().toString();
        String[] eventParts = eventSelected.split("_");
        eventBudget.setEventID(Integer.parseInt(eventParts[0]));

        boolean isSaved = eventBudget.saveEventBudget(eventBudget);
        if (isSaved){
            goToEventBudget(eventSelected, Integer.parseInt(eventParts[0]));
        }
        else{
            Globals.ShowError("Error", "Error in saving Event budget");
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

    public void ddlEventChange(ActionEvent actionEvent) {
        try {
            String eventSelected = ddlEvent.getValue().toString();
            String[] eventParts = eventSelected.split("_");

            goToEventBudget(eventSelected, Integer.parseInt(eventParts[0]));
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void goToEventBudget(String eventSelected, int EventID) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventBudget.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) ddlEvent.getScene().getWindow();
        EventBudgetController controller = loader.getController();
        controller.setEventList();
        controller.setSelectedEvent(eventSelected);
        controller.setEventBudgetTable(EventID);

        Globals.WindowCloseAndShow(root, window, "CEMS - Event Budget");
    }

    public void setSelectedEvent(String eventSelected){
        ddlEvent.setValue(eventSelected);
    }

}
