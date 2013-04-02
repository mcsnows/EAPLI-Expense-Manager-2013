/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Paulo Gandra Sousa
 */
@Entity
public class CheckingAccount {

    @Id
    @GeneratedValue
    Long id;
    String owner;
    BigDecimal balance;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<Movement> movements;
    @Transient
    Map<Integer, List<Movement>> indexedMovements;
    final Integer EXPENSE_MOVEMENT_TYPE = new Integer(0);
    final Integer INCOME_MOVEMENT_TYPE = new Integer(1);

    public CheckingAccount() {
        movements = new ArrayList();
        indexedMovements = new HashMap<Integer, List<Movement>>();
        indexedMovements.put(INCOME_MOVEMENT_TYPE, new ArrayList<Movement>());
        indexedMovements.put(EXPENSE_MOVEMENT_TYPE, new ArrayList<Movement>());

        // TODO load initial balance
        //balance = new BigDecimal(0);
    }

    public BigDecimal totalExpenditure() {
        List<Movement> theExpenses = indexedMovements.get(EXPENSE_MOVEMENT_TYPE);
        return sumAmount(theExpenses);
    }

    public BigDecimal totalEarnings() {
        List<Movement> theIncomes = indexedMovements.get(INCOME_MOVEMENT_TYPE);
        return sumAmount(theIncomes);
    }

    public void registerExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException();
        }
        movements.add(expense);
        List<Movement> theExpenses = indexedMovements.get(EXPENSE_MOVEMENT_TYPE);
        theExpenses.add(expense);
    }

    public void registerIncome(Income income) {
        if (income == null) {
            throw new IllegalArgumentException();
        }
        movements.add(income);
        List<Movement> theIncomes = indexedMovements.get(INCOME_MOVEMENT_TYPE);
        theIncomes.add(income);
    }

    private BigDecimal sumAmount(List<Movement> theMovements) {
        BigDecimal sum = new BigDecimal(0);
        for (Movement e : theMovements) {
            sum = sum.add(e.getAmount());
        }
        return sum;
    }
}
