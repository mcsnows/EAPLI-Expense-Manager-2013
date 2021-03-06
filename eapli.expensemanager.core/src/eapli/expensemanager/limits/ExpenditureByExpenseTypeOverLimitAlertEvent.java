/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.limits;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import eapli.expensemanager.model.ExpenseType;

/**
 *
 * @author mcn
 */
public class ExpenditureByExpenseTypeOverLimitAlertEvent extends AlertEvent {

    private BigDecimal average;
    private double yellow;
    private double red;
    private ExpenseType expenseType;

    public ExpenditureByExpenseTypeOverLimitAlertEvent(
            String alertTypeDescription, double yellow, double red,
            BigDecimal value, BigDecimal average, String level, ExpenseType eT) {
        super(alertTypeDescription, value, level);
        this.yellow = yellow;
        this.red = red;
        this.expenseType = eT;
        this.average = average;
    }

    @Override
    public String toString() {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.getDefault());

        double average1 = this.average.doubleValue();
        return "Expense Type:" + expenseType.getDescription() + "\n" + super.
                toString() + "\nAverage:" + n.format(average1)
                + " Limit Yellow Deviation:" + yellow * 100 + "% Limit Red Deviation:" + red * 100 + "% ";
    }
}
