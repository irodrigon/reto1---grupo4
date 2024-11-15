/**
 * Package for the Worker and Listener Classes.
 * 
 * This package contains the worker Thread that is launched
 * every time a client connects and is responsible for setting up
 * the correct messages for the client to catch up if everything
 * has gone smoothly or there has been an error.
 * It also contains the Listener Thread, which is launched in case
 * the server administrator wants to shut it down.
 * 
 */

package com.tartanga.grupo4.businesslogic;