/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.presentation;

import eapli.expensemanager.controllers.BaseController;
import eapli.expensemanager.controllers.RegisterExpenseController;
import eapli.expensemanager.model.Cheque;
import eapli.expensemanager.model.ExpenseType;
import eapli.expensemanager.model.Payment;
import eapli.expensemanager.model.PaymentMean;
import eapli.expensemanager.model.AlertEvent;
import eapli.expensemanager.model.InsufficientBalanceException;
import eapli.framework.presentation.SelectWidget;
import eapli.util.Console;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo Gandra Sousa
 */
class RegisterExpenseUI extends RegisterMovementBaseUI implements Observer {

    SelectWidget<ExpenseType> expenseTypesSelectWidget;

    @Override
    public String headline() {
        return "REGISTER AN EXPENSE";
    }

    @Override
    public boolean doShow() {
        readGeneralData();

        ExpenseType expenseType = readExpenseType();

        // TODO should the UI create a Payment object?
        Payment method = readPaymentMean();

        //mcn: delegar no getController o pedido de registo de Observador de WatchDogLimits
        controller.addObserverWatchDogLimits(this);
        try {
            controller.registerExpense(what, date, amount, expenseType, method);
            System.out.println("\nExpense recorded!");
        } catch (InsufficientBalanceException ex) {
            System.out.println("Unable to register expense due to insufficient balance");
        }

        return true;
    }
    private final RegisterExpenseController controller = new RegisterExpenseController();

    @Override
    protected BaseController getController() {
        return controller;
    }

    private ExpenseType readExpenseType() {
        System.out.println("-- EXPENSE TYPES --");
        List<ExpenseType> listExpenseTypes = controller.getExpenseTypes();

        expenseTypesSelectWidget = new SelectWidget<ExpenseType>(listExpenseTypes, new ExpenseTypeListVisitor());
        expenseTypesSelectWidget.show();
        int option = expenseTypesSelectWidget.selectedOption();

        ExpenseType expenseType = listExpenseTypes.get(option - 1);
        return expenseType;
    }

    private Payment readPaymentMean() {
        System.out.println("-- PAYMENT MEANS --");
        List<PaymentMean> paymentMeans = controller.getPaymentMeans();
        // FIX use ListWidget to avoid code duplication
        int position = 1;
        for (PaymentMean method : paymentMeans) {
            System.out.println(position + ". " + method.getDescription());
            position++;
        }
        int option = Console.readOption(1, paymentMeans.size(), 0);
        PaymentMean method = paymentMeans.get(option - 1);

        // TODO should the UI ask for a Payment object or call different 
        // registerXXX paymentMeans on the getController, i.e., registerExpensePaidwithCheque
        // or registerExpensePaidWithCash, etc...
        Payment payment;
        if (method.getClass() == Cheque.class) {
            String chequeNumber = Console.readLine("Cheque number:");
            payment = controller.createChequePayment((Cheque) method, chequeNumber);
        } else {
            payment = controller.createPayment(method);
        }

        return payment;
    }

    // Observer pattern
    @Override
    public void update(Observable o, Object arg) {
        AlertEvent al = (AlertEvent) arg;
        System.out.println("************************************ALERT*****************************");
        System.out.println(al);
        System.out.println("**********************************************************************");

    }
}
