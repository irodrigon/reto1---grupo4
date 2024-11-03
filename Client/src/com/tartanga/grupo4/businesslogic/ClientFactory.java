package com.tartanga.grupo4.businesslogic;

import com.tartanga.grupo4.model.Signable;
import com.tartanga.grupo4.businesslogic.Cliente;

/**
 * The {@code ClientFactory} class is a singleton factory that implement the
 * {@code Signable} interface, such as the {@code Cliente} class. The singleton
 * pattern is used to manage the instantiation of the factory itself,
 *
 * @author Aitor
 * @see Signable
 * @see Cliente
 */
public class ClientFactory {

    /**
     * Singleton instance of {@code ClientFactory}.
     */
    private static ClientFactory instance;
    
    /**
    * Private constructor to prevent external instantiation, 
    * enforcing the singleton pattern.
    */
    private ClientFactory() {
    }
    /**
    * Retrieves the singleton instance of {@code ClientFactory}. If no instance
    * exists, it creates one.
    *
    * @return the singleton {@code ClientFactory} instance
    */
    public static synchronized ClientFactory getInstance() {
        if (instance == null) {
            instance = new ClientFactory();
        }
        return instance;
    }
    /**
    * Provides an instance of {@code Signable}, currently implemented by 
    * the {@code Cliente} class.
    *
    * @return a new instance of {@code Cliente}, cast as {@code Signable}
    */
    public Signable getSignable() {
        return new Cliente();
    }
}
