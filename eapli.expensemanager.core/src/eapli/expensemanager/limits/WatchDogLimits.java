/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.limits;

import eapli.expensemanager.model.AlertLimit;
import eapli.expensemanager.model.AlertLimitExpenditure;
import eapli.expensemanager.model.AlertLimitType;
import eapli.expensemanager.model.CheckingAccount;
import java.util.Observable;
import java.util.Observer;

import eapli.expensemanager.model.events.ExpenseRegisteredEvent;
import eapli.expensemanager.persistence.CheckingAccountRepository;
import eapli.expensemanager.persistence.PersistenceFactory;
import java.math.BigDecimal;

/**
 *
 * @author mcn
 */
public class WatchDogLimits extends Observable implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        ExpenseRegisteredEvent expenseRegisteredEvent = (ExpenseRegisteredEvent) arg;
        buildAlertEventsAndNotifyObservers(expenseRegisteredEvent);
    }

    private void buildAlertEventsAndNotifyObservers(
            ExpenseRegisteredEvent event) {
        AlertLimitType[] types = AlertLimitType.values();
        for (AlertLimitType alertLimType : types) {
            AlertEvent alert;
            switch (alertLimType) {
                case LIMIT_WEEK_EXPENDITURE:
                    alert = event.IsOverLimitWeekExpenditure();
                    if (alert != null) {
                        publishEvent(alert);
                    }
                    break;
                case LIMIT_MONTH_EXPENDITURE:
                    alert = event.IsOverLimitMonthExpenditure();
                    if (alert != null) {
                        publishEvent(alert);
                    }
                    break;
                case LIMIT_DEVIATION_BY_EXPENSE_TYPE:
                    alert = event.IsOverLimitDeviationByExpenseType();
                    if (alert != null) {
                        publishEvent(alert);
                    }
                    break;
                case LIMIT_MINIMUM_BALANCE:
                    AlertLimitExpenditure alertLimitBalance = (AlertLimitExpenditure) AlertLimit.
                            findByAlertType(alertLimType);
                    if (alertLimitBalance != null) {
                        alert = buildAlertBalanceEvent(alertLimitBalance, event);
                        if (alert != null) {
                            publishEvent(alert);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Only publish the event expenseregistred creates some events because was
     * considered the information expert
     */
    private void publishEvent(AlertEvent alert) {
        this.setChanged();
        notifyObservers(alert);
    }

    private AlertEvent buildAlertBalanceEvent(AlertLimitExpenditure alertLimit,
                                              ExpenseRegisteredEvent expense) {
        CheckingAccountRepository repo = PersistenceFactory.
                buildPersistenceFactory().checkingAccountRepository();
        CheckingAccount account = repo.theAccount();
        BigDecimal balance = account.getBalance();
        //Because the expense registered is not saved yet
        balance = balance.subtract(expense.getAmount());
        AlertEvent alert = buildAlertBalanceEvent(balance, alertLimit.
                getAlertType());
        return alert;
    }

    public ExpenditureOverLimitAlertEvent buildAlertBalanceEvent(BigDecimal balance,
                                                        AlertLimitType alertType) {
        AlertLimitExpenditure alertLimit = (AlertLimitExpenditure) AlertLimit.
                findByAlertType(alertType);
        if (alertLimit != null) {
            if (balance.compareTo(alertLimit.getLimitRed()) < 0) {
                return new ExpenditureOverLimitAlertEvent(alertLimit.getAlertType().
                        getDescription(),
                                                 alertLimit.getLimitYellow(), alertLimit.
                        getLimitRed(), balance, "RED");
            }
            if (balance.compareTo(alertLimit.getLimitYellow()) < 0) {
                return new ExpenditureOverLimitAlertEvent(alertLimit.getAlertType().
                        getDescription(),
                                                 alertLimit.getLimitYellow(), alertLimit.
                        getLimitRed(), balance, "YELLOW");
            }
            return null;
        }
        return null;
    }
}
