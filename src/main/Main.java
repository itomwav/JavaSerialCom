package main;

import java.io.PrintWriter;
import java.util.Scanner;
import com.fazecast.jSerialComm.*;
 
public class Main {
 
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
        public static void main(String[] args) {
               
                // determine which serial port to use
                SerialPort ports[] = SerialPort.getCommPorts();
                System.out.println("Select a port:");
                int i = 1;
                for(SerialPort port : ports) {
                        System.out.println(i++ + ". " + port.getSystemPortName());
                }
                Scanner s = new Scanner(System.in);
                int chosenPort = s.nextInt();

                s.close();

                // open and configure the port
                SerialPort port = ports[chosenPort - 1];
                if(port.openPort()) {
                        System.out.println("Successfully opened the port.");
                } else {
                        System.out.println("Unable to open the port.");
                        return;
                }
                port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                wait(1000);
                port.setBaudRate(9600);
                wait(1000);
               
                // enter into an infinite loop that reads from the port and updates the GUI
                PrintWriter output = new PrintWriter(port.getOutputStream());
                output.print("0");
                wait(1000);
                output.flush();
                Scanner data = new Scanner(port.getInputStream());
                while(data.hasNextLine()) {
                        String msg = "";
                        try{msg = data.nextLine();}catch(Exception e){}
                        int zahl = Integer.parseInt(msg);
                        System.out.println(zahl);
                        output.print(zahl+1);
                        output.flush();
                }
                
        }

}