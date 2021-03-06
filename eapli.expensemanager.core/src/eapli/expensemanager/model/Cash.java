/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.expensemanager.model;

import eapli.expensemanager.persistence.PaymentMeanRepository;
import eapli.expensemanager.persistence.PersistenceFactory;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Paulo Gandra Sousa
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Cash extends PaymentMean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String currency;
    public static final String EUR = "EUR";

    public Cash() {
    }

    public Cash(String currency) {
        // may allow null if the currency is not important
        this.currency = currency;
    }

    public static Cash loadEUR() {
        PaymentMeanRepository repo = PersistenceFactory.
                buildPersistenceFactory().paymentMeanRepository();
        return repo.getCash(EUR);
    }

    @Override
    public String getDescription() {
        return "Cash " + currency;
    }
}
