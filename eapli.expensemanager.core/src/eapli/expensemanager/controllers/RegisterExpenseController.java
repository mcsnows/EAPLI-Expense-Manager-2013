/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.controllers;

import eapli.expensemanager.model.CheckingAccount;
import eapli.expensemanager.model.Cheque;
import eapli.expensemanager.model.ChequePayment;
import eapli.expensemanager.model.Expense;
import eapli.expensemanager.model.ExpenseType;
import eapli.expensemanager.model.exceptions.InsufficientBalanceException;
import eapli.expensemanager.model.Payment;
import eapli.expensemanager.model.PaymentMean;
import eapli.expensemanager.limits.LimitsWatchDog;
import eapli.expensemanager.limits.WatchDogFactory;
import eapli.expensemanager.persistence.CheckingAccountRepository;
import eapli.expensemanager.persistence.PersistenceFactory;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Observer;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class RegisterExpenseController extends BaseController {

    LimitsWatchDog watchDog;

    public RegisterExpenseController() {
    }

    public Payment createPayment(PaymentMean mean) {
        Payment payment = new Payment(mean);
        return payment;
    }

    public ChequePayment createChequePayment(Cheque mean, String checkNumber) {
        ChequePayment payment = new ChequePayment(mean, checkNumber);
        return payment;
    }

    public void registerExpense(String what, Calendar date, BigDecimal amount,
                                ExpenseType expenseType, Payment payment)
            throws InsufficientBalanceException {
        Expense expense = new Expense(expenseType, what, date, amount, payment);
        // ExpenseRepository repo =
        // PersistenceRegistry.instance().expenseRepository();
        CheckingAccountRepository repo = PersistenceFactory
                .buildPersistenceFactory().checkingAccountRepository();
        CheckingAccount account = repo.theAccount();
        // OBSERVER PATTERN: Register LimitsWatchDog as account observer
        account.addObserver(watchDog);
        account.registerExpense(expense);
        repo.save(account);
    }

    public List<ExpenseType> getExpenseTypes() {
        // use the existing controller to avoid duplication
        ListExpenseTypesController listExpenseTypesController = new ListExpenseTypesController();
        return listExpenseTypesController.getExpenseTypes();
    }

    public List<PaymentMean> getPaymentMeans() {
        ListPaymentMeansController listPaymentMeansController = new ListPaymentMeansController();
        return listPaymentMeansController.getPaymentMeans();
    }

    // OBSERVER pattern : Delegate in ObserverFactory to register UI as observer
    public void addObserverRegisterExpense(Observer ui) {

        watchDog = WatchDogFactory.getInstance().buildWatchDogLimits(ui);

    }
}
