/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.controllers;

import eapli.expensemanager.model.ExpenseType;
import eapli.expensemanager.persistence.ExpenseTypeRepository;
import eapli.expensemanager.persistence.PersistenceFactory;
import java.util.List;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class ListExpenseTypesController extends BaseController {

    public List<ExpenseType> getExpenseTypes() {
        ExpenseTypeRepository repo = PersistenceFactory.buildPersistenceFactory().expenseTypeRepository();
        return repo.all();
    }    
}
