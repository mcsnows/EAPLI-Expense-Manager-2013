/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.presentation;

import eapli.expensemanager.presentation.visitors.IncomeTypeListVisitor;
import eapli.framework.presentation.ListWidget;
import eapli.expensemanager.controllers.BaseController;
import eapli.expensemanager.controllers.ListIncomeTypesController;
import eapli.expensemanager.model.IncomeType;

/**
 *
 * @author Paulo Gandra Sousa
 */
class ListIncomeTypesUI extends BaseUI {

    private ListIncomeTypesController controller = new ListIncomeTypesController();
    private ListWidget<IncomeType> widget;

    @Override
    protected BaseController getController() {
        return controller;
    }

    @Override
    public boolean doShow() {
        widget = new ListWidget<IncomeType>(controller.getIncomeTypes(), new IncomeTypeListVisitor());
        widget.show();

        return true;
    }

    @Override
    public String headline() {
        return "LIST INCOME TYPES";
    }
}
