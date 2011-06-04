import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SampleClient extends JFrame implements Notifiable, ActionListener
{
	TextField bid = null;
	Label dispTextLabel;

	Button btnSubmit;
	SampleServer RemoteObject;
	String dispText = "";
	Boolean Ready = false;
	long startTime, serverTime;
	Boolean connectedFlag = false;
	
	SampleClient() {
		super();
		Panel p1 = new Panel();
		p1.add(btnSubmit = new Button("Connect"));
		add(p1, "South");
		//btnSubmit.setEnabled(false);
		add(dispTextLabel = new Label("Enter the IP of Server"), "North");
		Panel p2 = new Panel();
		p2.setLayout (new GridLayout(3, 0));

		bid = new TextField();
		p2.add(bid);
		add(p2, "Center");
		btnSubmit.addActionListener(this);
		try {
			UnicastRemoteObject.exportObject(this);
		} catch (RemoteException re){
			re.printStackTrace();
		}
		setBounds(50, 50, 600, 200);
		setVisible(true);
	//	this.setDefaultCloseOperation(EXITONCLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setResizable(false);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if (connectedFlag) {
			if (ae.getActionCommand().equals("Submit Text")) {
				try {
					long t = System.currentTimeMillis() - this.startTime;
					dispTextLabel.setText("Time taken " + RemoteObject.checkInputText(bid.getText(), t)/1000.0);
				} catch (RemoteException re){
					re.printStackTrace();
				}
				btnSubmit.setEnabled(false);
			}
		} else {
			System.out.println("Button Pressed");
			if (ae.getActionCommand().equals("Connect")) {
				try {
					String url = "//" + bid.getText() + "/SAMPLE-SERVER";
					this.RemoteObject = (SampleServer)Naming.lookup(url);
					System.out.println("Got Remote Object");
						//System.out.println("10 + 25 = " + remoteObject.sum(10, 25));
				
				}
				catch (RemoteException exc) {
					System.out.println("Error in lookup: " + exc.toString());}
				catch (java.net.MalformedURLException exc) {
					System.out.println("Malformed URL: " + exc.toString());}
						catch (java.rmi.NotBoundException exc) {
					System.out.println("NotBound: " + exc.toString());}
				btnSubmit.setLabel("Submit Text");
				btnSubmit.setEnabled(false);
				bid.setText("");
				dispTextLabel.setText("Connected");
				try {
					this.RemoteObject.registerForNotification(this);
				} catch (RemoteException exc){
					System.out.println("Error in registering: " + exc.toString());
				}
				connectedFlag = true;
			}
		}
	}

	public void displayText (String text) 
	{
		System.out.println(text);
		dispTextLabel.setText(text);
		Ready = true;
		this.startTime = System.currentTimeMillis();
		btnSubmit.setEnabled(true);
	}

	public static void main (String[] args)
	{
//		String serverAddress = args[0];
		System.setSecurityManager (new RMISecurityManager());
		SampleClient client = new SampleClient();
//		try
//		{
			System.out.println("Security Manager Loaded");
			/*String url = "//" + serverAddress + "/SAMPLE-SERVER";
			client.RemoteObject = (SampleServer)Naming.lookup(url);
			client.RemoteObject.registerForNotification(client);
			System.out.println("Got Remote Object");*/
			//System.out.println("10 + 25 = " + remoteObject.sum(10, 25));
		
/*		}
		catch (RemoteException exc) {
			System.out.println("Error in lookup: " + exc.toString());}
		catch (java.net.MalformedURLException exc) {
			System.out.println("Malformed URL: " + exc.toString());}
		catch (java.rmi.NotBoundException exc) {
			System.out.println("NotBound: " + exc.toString());}
*/
	/*	System.out.println("I was here " + client.Ready);
		while (!client.Ready); 
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		        String inputText = null;
	
     			try {
				System.out.print("Enetr the text: ");
       				inputText = br.readLine();
				System.out.println("Your Time(in seconds) is " + client.RemoteObject.checkInputText(inputText)/1000.0);
  			} catch (IOException ioe) {
       				System.out.println("IO error trying to read your name!");
       				System.exit(1);
  			}*/
	
		
	}
}




			
