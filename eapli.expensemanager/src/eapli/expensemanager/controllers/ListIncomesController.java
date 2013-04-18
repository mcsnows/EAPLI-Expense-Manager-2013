/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.controllers;

import eapli.expensemanager.model.CheckingAccount;
import eapli.expensemanager.model.Expense;
import eapli.expensemanager.model.Income;
import eapli.expensemanager.persistence.CheckingAccountRepository;
import eapli.expensemanager.persistence.ExpenseRepository;
import eapli.expensemanager.persistence.PersistenceRegistry;
import java.util.List;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class ListIncomesController extends BaseController {

    /*
    public List<Income> getIncomes() {
        IncomeRepository repo = PersistenceRegistry.instance().incomeRepository();
        return repo.all();
    } 
    */
    
    public List<Income> getIncomes() {
        CheckingAccountRepository repo = PersistenceRegistry.instance().checkingAccountRepository();
        CheckingAccount account = repo.theAccount();
        return account.getIncomes();
    }
}
