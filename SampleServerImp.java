/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.logging.Level;
import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Pulkit Goyal and Sapan
 */
public class SampleServerImp extends UnicastRemoteObject implements SampleServer {
    Vector displayStrings;
    String disp;// = "This is a String";
    long startTime, endTime, t, editDist;
    public Vector clientList = null;
    Random randomGenerator = new Random();
    int noOfClients = 2;

    public SampleServerImp() throws RemoteException {
        super();
	clientList = new Vector();
	displayStrings = new Vector();
	displayStrings.add("Reports said the party has asked all its Jharkhand MLAs to come to Delhi.");
	displayStrings.add("Chennai becomes IPL Champions.");
	displayStrings.add("The e-auction began on April 9 and will continue till such time that demand equals supply.");
    }

    public int sum (int a, int b) {
        return (a+b);
    }

    public double checkInputText (String S, long clientTime) {
	    editDist = editDistance(disp,S);
	    //endTime = System.currentTimeMillis();
	    //t = (endTime - startTime) + editDist;
	    return (clientTime + editDist*1000);
	    //if (S.equals(disp)) {
	//	    endTime = System.currentTimeMillis();
	//	    return (endTime-startTime);
	  //  } else {
	//	    return 0.00;
	  //  }
    }
/*
    public String displayText () {
    	    disp = "This is a string";
	    startTime = System.currentTimeMillis();
	    return (disp);
    }
*/
    public void registerForNotification (Notifiable n) throws RemoteException
    {
	    clientList.addElement(n);
	    if (clientList.size() >= noOfClients) {
		    int r = randomGenerator.nextInt(displayStrings.size());
		    disp = (String)displayStrings.elementAt(r);
		    for (Enumeration clients = clientList.elements(); clients.hasMoreElements();) {
			    startTime = System.currentTimeMillis();
			    Notifiable thingToNotify = (Notifiable) clients.nextElement();
			    thingToNotify.displayText(disp);
		    }
	    }
    }
    public static void main (String args[]) throws RemoteException {

        try {
            System.setSecurityManager(new RMISecurityManager()); //set the security manager
            SampleServerImp Server = new SampleServerImp(); //create a local instance of the object
	    if (args.length >= 1) {
	            Server.noOfClients = Integer.parseInt(args[0]);
	    } else {
		    Server.noOfClients = 2;
	    }
            Naming.rebind("SAMPLE-SERVER", Server); //put the local instance in the registry
            System.out.println("Server waiting.....");
        } catch (java.net.MalformedURLException me) {
            System.out.println("Malformed URL: " + me.toString());   }
        catch (RemoteException re)  {
            System.out.println("Remote exception: " + re.toString());  }
      }

	private int editDistance (String s1, String s2)
	{
		int l1 = (int)s1.length();
		int l2 = (int)s2.length();
	
		int [][] v = new int[l1+1][l2+1];

		for (int i = 0; i <= l1; ++i) {
			v[i][0] = i;
		}
	
		for (int j = 0; j <= l2; ++j) {
			v[0][j] = j;
		}

		for (int i = 1; i < v.length; ++i) {
			for (int j = 1; j < v[i].length; ++j) {
				if (s1.charAt(i-1) != s2.charAt(j-1)) {
					v[i][j] = min((v[i-1][j] +1), (v[i][j-1]+1), (v[i-1][j-1]+1));
				} else {
					v[i][j] = min((v[i-1][j] +1), (v[i][j-1]+1), (v[i-1][j-1]));
				}
			}
		}
		return v[l1][l2];
	}

	private int min (int a, int b, int c)
	{
		int min = a;
		if (min > b) {
			min = b;
		} 
		if (min > c) {
			min = c;
		}
	
		return min;
	}
}
