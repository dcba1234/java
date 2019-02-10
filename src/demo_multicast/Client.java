/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo_multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author admin
 */
public class Client {
    public static final byte[] BUFFER = new byte[4096];
    public static void main(String[] args) {
        // TODO code application logic here
        MulticastSocket socket = null;
        DatagramPacket inPacket = null;
        try {
            // Get the address that we are going to connect to.
            InetAddress address = InetAddress.getByName(Sever.GROUP_ADDRESS);
 
            // Create a new Multicast socket
            socket = new MulticastSocket(Sever.PORT);
 
            // Joint the Multicast group
            socket.joinGroup(address);
 
            while (true) {
                // Receive the information and print it.
                inPacket = new DatagramPacket(BUFFER, BUFFER.length);
                socket.receive(inPacket);
                String msg = new String(BUFFER, 0, inPacket.getLength());
                
                System.out.println("Gửi từ " + inPacket.getAddress() + " Msg : " + msg);
                socket.close();
                break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
