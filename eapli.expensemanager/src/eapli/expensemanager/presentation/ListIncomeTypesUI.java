/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.presentation;

import eapli.expensemanager.controllers.BaseController;
import eapli.expensemanager.controllers.ListExpenseTypesController;
import eapli.expensemanager.controllers.ListIncomeTypesController;
import eapli.expensemanager.model.ExpenseType;
import eapli.expensemanager.model.IncomeType;
import java.util.List;

/**
 *
 * @author Paulo Gandra Sousa
 */
class ListIncomeTypesUI extends BaseUI {

    private ListIncomeTypesController controller = new ListIncomeTypesController();
    
    @Override
    protected BaseController controller() {
        return controller;
    }

    @Override
    public void doShow() {
        int position = 1;
        List<IncomeType> listIncomeTypes = controller.getIncomeTypes();
        for (IncomeType et : listIncomeTypes) {
            System.out.println(position + ". " + et.getDescription());
            position++;
        }
    }

    @Override
    public String headline() {
        return "* * *  LIST INCOME TYPES  * * *\n";    
    }
}
