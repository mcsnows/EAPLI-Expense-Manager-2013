/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.bootstrap;

import eapli.expensemanager.model.CheckingAccount;
import eapli.expensemanager.model.Income;
import eapli.expensemanager.model.IncomeType;
import eapli.expensemanager.persistence.CheckingAccountRepository;
import eapli.expensemanager.persistence.IncomeTypeRepository;
import eapli.expensemanager.persistence.PersistenceFactory;
import eapli.util.DateTime;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class SomeIncomesBootstrap  {
    
    public SomeIncomesBootstrap() {
        CheckingAccountRepository repoAccount = PersistenceFactory.buildPersistenceFactory().checkingAccountRepository();
        CheckingAccount theAccount = repoAccount.theAccount();
        
        IncomeTypeRepository repoExpenseType = PersistenceFactory.buildPersistenceFactory().incomeTypeRepository();
        IncomeType salary = repoExpenseType.findForName(Bootstrap.CLOTHING_EXPENSE_TYPE);

        Calendar baseDateOfIncome = DateTime.today();
        Date dateOfIncome = baseDateOfIncome.getTime();
                
        Income inc = new Income("ordenado deste mês", dateOfIncome, new BigDecimal(1000), salary);
        theAccount.registerIncome(inc);
                               
        repoAccount.save(theAccount);
    }
}