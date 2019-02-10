/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo_multicast;

import static demo_multicast.phongChoi.GROUP_ADDRESS;
import static demo_multicast.phongChoi.PORT;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class mutilcastSendServices extends Thread{
    public static final String GROUP_ADDRESS = "224.0.0.1";
    public static final int PORT = 8888;
    String tenphong;
    public mutilcastSendServices(){
        
    }
    public mutilcastSendServices(String tenphong){
        this.tenphong = tenphong;
    }

    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            // Get the address that we are going to connect to.
            InetAddress address = InetAddress.getByName(GROUP_ADDRESS);
 
            // Create a new Multicast socket
            socket = new DatagramSocket();
 
            DatagramPacket outPacket = null;
            long counter = 0;
            while (true) {
                String msg = "Gửi từ sever 1. " + counter;
                counter++;
                //outPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, PORT);
                outPacket = new DatagramPacket(tenphong.getBytes(), tenphong.getBytes().length, address, PORT);
                socket.send(outPacket);
                //System.out.println("Server sent packet with msg: " + msg);
                Thread.sleep(1500); // Sleep 1 second before sending the next message
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(mutilcastSendServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
    
}
