/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Example;

import java.io.Serializable;

/**
 *
 * @author rabio
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 1L;
    private User user;
    private Enum SignInSignUp;
    
    public Message (){
        
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Enum getSignInSignUp() {
        return SignInSignUp;
    }

    public void setSignInSignUp(Enum SignInSignUp) {
        this.SignInSignUp = SignInSignUp;
    }
    
    
}
