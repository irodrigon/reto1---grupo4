package com.tartanga.grupo4.model;

import java.io.Serializable;

/**
 * The {@code User} class represents a system user.
 * This class stores relevant user information such as username, password, name, street,
 * active status, city, and zip code. It implements the {@code Serializable} interface
 * to allow serialization of {@code User} objects.
 * 
 * This class has two constructors: one with parameters and one empty.
 * The parameterized constructor initializes all the class attributes,
 * while the empty constructor initializes the attributes with {@code null} values.
 * 
 * The class also provides getter and setter methods for each attribute, as well
 * as a {@code toString} method to provide a string representation of a {@code User} object.
 * 
 * @author Aratz
 */
public class User implements Serializable {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The full name of the user.
     */
    private String name;

    /**
     * The street address of the user.
     */
    private String street;

    /**
     * The active status of the user. If {@code true}, the user is active; otherwise, inactive.
     */
    private Boolean active;

    /**
     * The city where the user resides.
     */
    private String city;

    /**
     * The postal zip code of the user's address.
     */
    private Integer zip;

    /**
     * Constructor that initializes a {@code User} object with all specified attributes.
     *
     * @param username The username of the user.
     * @param password The user's password.
     * @param name The name of the user.
     * @param street The street address of the user.
     * @param active The active status of the user.
     * @param city The user's city.
     * @param zip The user's zip code.
     */
    public User(String username, String password, String name, String street, Boolean active, String city, Integer zip) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.street = street;
        this.active = active;
        this.city = city;
        this.zip = zip;
    }

    /**
     * Empty constructor that initializes a {@code User} object with all attributes set to {@code null}.
     */
    public User() {
        this.username = null;
        this.password = null;
        this.name = null;
        this.street = null;
        this.active = null;
        this.city = null;
        this.zip = null;
    }

    /**
     * Gets the username of the user.
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the name of the user.
     * @return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the street of the user.
     * @return the street of the user.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street of the user.
     * @param street The street to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the active status of the user.
     * @return the active status of the user.
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the active status of the user.
     * @param active The active status to set.
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Gets the city of the user.
     * @return the city of the user.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the user.
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the zip code of the user.
     * @return the zip code of the user.
     */
    public Integer getZip() {
        return zip;
    }

    /**
     * Sets the zip code of the user.
     * @param zip The zip code to set.
     */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /**
     * Returns a string representation of the {@code User} object.
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return "User{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + ", name='" + name + '\''
                + ", street='" + street + '\''
                + ", active=" + active
                + ", city='" + city + '\''
                + ", zip=" + zip
                + '}';
    }
}
