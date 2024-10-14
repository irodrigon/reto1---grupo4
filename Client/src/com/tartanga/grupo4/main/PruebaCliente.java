package com.tartanga.grupo4.main;

import Example.Message;
import Example.SignInSignUp;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/*
IMPORTANTE
Esta clase solo esta creada para testear el server
Esta clase hay que borrarla y reemplazarla con la clase 
cliente una vez la tengamos desarrollada
*/
public class PruebaCliente{
	private final int PUERTO = 6000;
	private final String IP = "127.0.0.1";
	Socket cliente = null;
	ObjectInputStream entrada = null;
	ObjectOutputStream salida = null;

	public void iniciar() {
		try {
			cliente = new Socket(IP, PUERTO);
			System.out.println("Conexi�n realizada con servidor");
			
			salida = new ObjectOutputStream (cliente.getOutputStream());
			entrada = new ObjectInputStream(cliente.getInputStream());
	
			Message message = new Message();
                        message.setSignInSignUp(SignInSignUp.SIGN_UP);
                        salida.writeObject(message);
                        
			String mensaje = (String) entrada.readObject();
			System.out.println("Mensaje del servidor: "+mensaje);
						
			finalizar();
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			finalizar();
		}
                
	}
	
	public static void main(String[] args) {
		PruebaCliente c1 = new PruebaCliente();
		c1.iniciar();
	}
	public void finalizar()
	{
		try {
			if (cliente != null)
			cliente.close();
			if (entrada != null)
			entrada.close();
			if (salida != null)
			salida.close();
			} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}