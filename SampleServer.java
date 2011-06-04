/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.rmi.*;

/**
 *
 * @author Pulkit Goyal and Sapan
 */
public interface SampleServer extends Remote {
    public int sum (int a, int b) throws RemoteException;
 //   public String displayText () throws RemoteException;
    public double checkInputText (String S, long clientTime) throws RemoteException;

    public void registerForNotification(Notifiable n) throws RemoteException;
}
