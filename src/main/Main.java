package main;

import java.io.PrintWriter;
import java.util.Scanner;
import com.fazecast.jSerialComm.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// determine which serial port to use
		SerialPort ports[] = SerialPort.getCommPorts();
		System.out.println("open ports:");
		int i = 1;
		for (SerialPort port : ports) {
			System.out.println(i++ + ". " + port.getSystemPortName());
		}
		System.out.print("please enter a number: ");
		Scanner s = new Scanner(System.in);
		int chosenPort = s.nextInt();

		s.close();

		// open and configure the port
		SerialPort port = ports[chosenPort - 1];
		if (port.openPort()) {
			System.out.println("Successfully opened the port.");
		} else {
			System.out.println("Unable to open the port.");
			return;
		}
		port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		Thread.sleep(1000);
		port.setBaudRate(9600);
		Thread.sleep(1000);

		// enter into an infinite loop that reads from the port and updates the GUI
		PrintWriter output = new PrintWriter(port.getOutputStream());
		output.print("0");
		output.flush();
		Scanner data = new Scanner(port.getInputStream());
		while (true) {
			String msg = data.nextLine();
			if (msg.equals("EXIT"))
				break;
			int zahl = Integer.parseInt(msg);
			System.out.println(zahl);
			output.print(++zahl);
			output.flush();
		}
		data.close();
	}

}