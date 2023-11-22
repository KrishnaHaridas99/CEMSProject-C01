package CEMS.Events;

import CEMS.Common.Globals;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddEventExpenseController {
    public TextField txtExpenseName;
    public TextField txtCost;
    public JFXButton btnSaveExpense;
    public Label lblEventID;
    public Label lblEventSelected;
    public Stage parentWindow;
    public boolean isEdit = false;
    public Label lblEventExpID;

    public void btnSaveExpenseClick(ActionEvent actionEvent) {
        try{
            if(!isEdit){
                saveEventBudget();
            }
            else {
                updateEventBudget();
            }
        }
        catch (Exception e)
        {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void populateExpenseDetail(EventBudget eventBudget){
        txtExpenseName.setText(eventBudget.getExpenseName());
        txtCost.setText(String.valueOf(eventBudget.getCost()));
    }

    public void saveEventBudget() throws Exception {
        EventBudget eventBudget = new EventBudget();
        eventBudget.setExpenseName(txtExpenseName.getText());
        eventBudget.setCost(Float.parseFloat(txtCost.getText()));
        eventBudget.setEventID(Integer.parseInt(lblEventID.getText()));

        boolean isSaved = eventBudget.saveEventBudget(eventBudget);
        if (isSaved){
            goToEventBudget(lblEventSelected.getText(), Integer.parseInt(lblEventID.getText()));
        }
        else{
            Globals.ShowError("Error", "Error in saving Event budget");
        }
    }

    public void updateEventBudget() throws Exception {
        EventBudget eventBudget = new EventBudget();
        eventBudget.setExpenseName(txtExpenseName.getText());
        eventBudget.setCost(Float.parseFloat(txtCost.getText()));
        eventBudget.setBudgetID(Integer.parseInt(lblEventExpID.getText()));
        eventBudget.setEventID(Integer.parseInt(lblEventID.getText()));

        boolean isSaved = eventBudget.updateEventBudget(eventBudget);
        if (isSaved){
            goToEventBudget(lblEventSelected.getText(), Integer.parseInt(lblEventID.getText()));
        }
        else{
            Globals.ShowError("Error", "Error in saving Event budget");
        }
    }

    public void goToEventBudget(String eventSelected, int EventID) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventBudget.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) btnSaveExpense.getScene().getWindow();
        window.close();

        EventBudgetController controller = loader.getController();
        controller.setEventList();
        controller.setSelectedEvent(eventSelected);
        controller.setEventBudgetTable(EventID);

        Globals.WindowCloseAndShow(root, parentWindow, "CEMS - Event Budget");
    }
}
