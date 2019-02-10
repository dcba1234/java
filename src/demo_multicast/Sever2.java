/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo_multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author admin
 */
public class Sever2 {
    public static final String GROUP_ADDRESS = "224.0.0.1";
    public static final int PORT = 8888;
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        DatagramSocket socket = null;
        try {
            // Get the address that we are going to connect to.
            InetAddress address = InetAddress.getByName(GROUP_ADDRESS);
 
            // Create a new Multicast socket
            socket = new DatagramSocket();
 
            DatagramPacket outPacket = null;
            long counter = 0;
            while (true) {
                String msg = "Sent message No22. " + counter;
                counter++;
                outPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, PORT);
                socket.send(outPacket);
                System.out.println("Server 22222: " + msg);
                Thread.sleep(1000); // Sleep 1 second before sending the next message
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
