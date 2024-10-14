package com.tartanga.grupo4.model;

import java.io.Serializable;

/**
 * The {@code User} class represents a system user.
 * This class stores relevant user information such as username, password, age, email,
 * phone number, gender, and city. It implements the {@code Serializable} interface
 * to allow serialization of {@code User} objects.
 * 
 * This class has two constructors: one with parameters and one empty.
 * The parameterized constructor initializes all the class attributes,
 * while the empty constructor initializes the attributes with {@code null} values.
 * 
 * The class also provides getter and setter methods for each attribute, as well
 * as a {@code toString} method to provide a string representation of a {@code User} object.
 * 
 * @author egure
 */
public class User implements Serializable {
    
    private String username;
    private String password;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String city;

    /**
     * Constructor that initializes a {@code User} object with all specified attributes.
     *
     * @param username The username of the user.
     * @param password The user's password.
     * @param age The age of the user.
     * @param email The user's email address.
     * @param phoneNumber The user's phone number.
     * @param city The user's city.
     */
    public User(String username, String password, Integer age, String email, String phoneNumber, String city) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }
    
    /**
     * Empty constructor that initializes a {@code User} object with all attributes set to {@code null}.
     */
    public User() {
        this.username = null;
        this.password = null;
        this.age = null;
        this.email = null;
        this.phoneNumber = null;
        this.city = null;
    }

    /**
     * Gets the username of the user.
     * 
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the age of the user.
     * 
     * @return the age of the user.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the user.
     * 
     * @param age The age to set.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gets the email address of the user.
     * 
     * @return the email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * 
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the user.
     * 
     * @return the phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     * 
     * @param phoneNumber The phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     * Gets the city of the user.
     * 
     * @return the city of the user.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the user.
     * 
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns a string representation of the {@code User} object.
     * 
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", age=" + age + 
               ", email=" + email + ", phoneNumber=" + phoneNumber + ", city=" + city + '}';
    }
}
