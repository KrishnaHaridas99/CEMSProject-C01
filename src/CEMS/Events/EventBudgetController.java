package CEMS.Events;

import CEMS.Common.Globals;
import CEMS.Common.LoggedInUser;
import CEMS.Members.MemberMenuController;
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

public class EventBudgetController {
    public ComboBox ddlEvent;
    public TableView tblEventBudget;

    public JFXButton btnBack;
    public JFXButton btnAddExpense;
    public Label lblTotal;
    public Label lblTotalCost;

    @FXML
    private void initialize() {
        try {
            setEventList();
            setEventBudgetTable();
        } catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void setEventList() throws SQLException {
        List<Event> eventList = new Event().getEventsList(LoggedInUser.getUser().getUserID());

        for (Event event: eventList) {
            ddlEvent.getItems().add(event.getEventID() + "_" + event.getEventName());
        }
    }

    public void setEventBudgetTable() throws SQLException {

        TableColumn<EventBudget, String> colBudgetID = new TableColumn<>("Expense ID");
        colBudgetID.setCellValueFactory(new PropertyValueFactory<>("BudgetID"));
        colBudgetID.setVisible(false);
        tblEventBudget.getColumns().add(colBudgetID);

        TableColumn<EventBudget, String> colExpenseName = new TableColumn<>("Expense Name");
        colExpenseName.setCellValueFactory(new PropertyValueFactory<>("ExpenseName"));
        tblEventBudget.getColumns().add(colExpenseName);

        TableColumn<EventBudget, String> colCost = new TableColumn<>("Cost ($)");
        colCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        tblEventBudget.getColumns().add(colCost);

        TableColumn<EventBudget, EventBudget> editDeleteColumn = new TableColumn<>("Actions");
        editDeleteColumn.setSortable(false);
        editDeleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        editDeleteColumn.setCellFactory(getActionButtonCallback());
        tblEventBudget.getColumns().add(editDeleteColumn);
    }

    public void populateEventBudgetTable() throws SQLException {
        String eventSelected = ddlEvent.getValue().toString();
        String[] eventParts = eventSelected.split("_");

        List<EventBudget> eventBudgetList = new EventBudget().getEventBudget(Integer.parseInt(eventParts[0]));

        tblEventBudget.getItems().clear();
        tblEventBudget.getItems().addAll(eventBudgetList);

        double sum = eventBudgetList.stream().mapToDouble(EventBudget::getCost).sum();
        lblTotal.setVisible(true);
        lblTotalCost.setText("$ " + String.format("%.2f", sum));
        lblTotalCost.setVisible(true);
    }

    private Callback<TableColumn<EventBudget, EventBudget>, TableCell<EventBudget, EventBudget>> getActionButtonCallback() {
        return new Callback<TableColumn<EventBudget, EventBudget>, TableCell<EventBudget, EventBudget>>() {
            @Override
            public TableCell<EventBudget, EventBudget> call(TableColumn<EventBudget, EventBudget> param) {
                return new TableCell<EventBudget, EventBudget>() {
                    final JFXButton editButton = new JFXButton();
                    final JFXButton deleteButton = new JFXButton();
                    final HBox buttonBox = new HBox(editButton, deleteButton);
                    final StackPane pane = new StackPane(buttonBox);

                    {
                        Image imgEdit = new Image(getClass().getResourceAsStream("../Images/IconEdit.png"));
                        ImageView iconEdit = new ImageView(imgEdit);
                        iconEdit.setFitHeight(20.0);
                        iconEdit.setFitWidth(20.0);
                        Tooltip tooltip = new Tooltip("Edit expense");

                        editButton.setGraphic(iconEdit);
                        editButton.setTooltip(tooltip);
                        editButton.getStyleClass().add("glass-grey");
                        editButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        editButton.setOnAction(event -> {
                            editEventExpense(getTableView().getItems().get(getIndex()));
                        });

                        Image imgDelete = new Image(getClass().getResourceAsStream("../Images/IconDelete.png"));
                        ImageView iconDelete = new ImageView(imgDelete);
                        iconDelete.setFitHeight(20.0);
                        iconDelete.setFitWidth(20.0);
                        Tooltip tooltipD = new Tooltip("Delete expense");

                        deleteButton.setGraphic(iconDelete);
                        deleteButton.setTooltip(tooltipD);
                        deleteButton.getStyleClass().add("round-red");
                        deleteButton.getStylesheets().add(String.valueOf(getClass().getResource("../CSS/Button.css")));
                        deleteButton.setOnAction(event -> {
                            EventBudget eventBudget = getTableView().getItems().get(getIndex());
                            String eventSelected = ddlEvent.getValue().toString();
                            String[] eventParts = eventSelected.split("_");
                            eventBudget.setEventID(Integer.parseInt(eventParts[0]));
                            deleteEventExpense(eventBudget);
                        });
                    }

                    @Override
                    protected void updateItem(EventBudget eventBudget, boolean empty) {
                        super.updateItem(eventBudget, empty);
                        if (eventBudget == null || empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        };
    }

    public void editEventExpense(EventBudget eventBudget){
        try{
            String eventSelected = ddlEvent.getValue().toString();
            String[] eventParts = eventSelected.split("_");

            showFormPopup(Integer.parseInt(eventParts[0]), eventBudget, true);
        }
        catch (Exception e){
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void deleteEventExpense(EventBudget eventBudget){
        String msgBody = "Do you want to Delete the expense ?";
        boolean isDelete = Globals.showConfirmationDialog("Delete", "Expense Delete", msgBody);
        if(isDelete)
        {
            try {
                isDelete = new EventBudget().deleteEventExpense(eventBudget);
                if (isDelete) {
                    Globals.ShowInfo("Deleted", "Expense Deleted Successfully");
                    populateEventBudgetTable();
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

    public void ddlEventChange(ActionEvent actionEvent) {
        try {
            btnAddExpense.setVisible(true);
            populateEventBudgetTable();
        }
        catch (Exception e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    public void btnAddExpenseClick(ActionEvent actionEvent) {
        try {
            String eventSelected = ddlEvent.getValue().toString();
            String[] eventParts = eventSelected.split("_");

            showFormPopup(Integer.parseInt(eventParts[0]), null, false);
        }
        catch (IOException e) {
            Globals.ShowError("Error", e.getMessage());
        }
    }

    private void showFormPopup(int eventID, EventBudget eventBudget, boolean isEdit) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEventExpense.fxml"));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Event expense");
        popupStage.setResizable(false);
        popupStage.setMaximized(false);
        popupStage.setScene(new Scene(root));

        AddEventExpenseController addEventExpenseController = loader.getController();
        addEventExpenseController.parentWindow = (Stage) btnAddExpense.getScene().getWindow();
        addEventExpenseController.lblEventID.setText(String.valueOf(eventID));
        addEventExpenseController.lblEventSelected.setText(ddlEvent.getValue().toString());
        if (isEdit){
            addEventExpenseController.isEdit = true;
            addEventExpenseController.lblEventExpID.setText(String.valueOf(eventBudget.getBudgetID()));
            addEventExpenseController.populateExpenseDetail(eventBudget);
        }
        popupStage.showAndWait();
    }
}
