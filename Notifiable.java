import java.rmi.*;

public interface Notifiable extends Remote {
	public void displayText (String text) throws RemoteException;
}
