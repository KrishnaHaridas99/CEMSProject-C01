package CEMS.Events;

import CEMS.Common.Globals;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            saveEventBudget();
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

        boolean isSaved;
        if(!isEdit){
            isSaved = eventBudget.saveEventBudget(eventBudget);
        }
        else{
            eventBudget.setBudgetID(Integer.parseInt(lblEventExpID.getText()));
            isSaved = eventBudget.updateEventBudget(eventBudget);
        }

        if (isSaved){
            goToEventBudget();
        }
        else{
            Globals.ShowError("Error", "Error in saving Event budget");
        }
    }

    public void goToEventBudget() throws SQLException {
        Stage window = (Stage) btnSaveExpense.getScene().getWindow();
        window.close();

        if (parentWindow.getScene() != null && parentWindow.getScene().getRoot() != null) {
            Parent root = parentWindow.getScene().getRoot();

            FXMLLoader loader = (FXMLLoader) root.getProperties().get(FXMLLoader.class.getName());
            if (loader != null) {
                EventBudgetController controller = loader.getController();
                controller.populateEventBudgetTable();
            }
        }
    }
}
