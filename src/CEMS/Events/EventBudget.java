package CEMS.Events;

import java.sql.SQLException;
import java.util.List;

public class EventBudget extends Event{

    private int BudgetID;
    public int getBudgetID() { return this.BudgetID; }
    public void setBudgetID(int BudgetID)
    {
        this.BudgetID = BudgetID;
    }

    private String ExpenseName;
    public String getExpenseName() { return this.ExpenseName; }
    public void setExpenseName(String ExpenseName)
    {
        this.ExpenseName = ExpenseName;
    }

    private float Cost;
    public float getCost() { return this.Cost; }
    public void setCost(float Cost) { this.Cost = Cost; }

    public boolean saveEventBudget(EventBudget eventBudget) throws Exception {
        return new EventDBservice().saveEventBudget(eventBudget);
    }

    public boolean updateEventBudget(EventBudget budgetObj) throws Exception{
        return new EventDBservice().updateEventBudget(budgetObj);
    }

    public List<EventBudget> getEventBudget(int EventID) throws SQLException {
        return new EventDBservice().getEventBudget(EventID);
    }

    public boolean deleteEventExpense(EventBudget budgetObj) throws Exception {
        return new EventDBservice().deleteEventExpense(budgetObj);
    }
}
