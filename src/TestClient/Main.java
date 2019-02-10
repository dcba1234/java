/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestClient;

import graphics.*;
import java.awt.Button;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author admin
 */
public class Main {
    Socket socket;
    OutputStream os;// .... gửi dl
    InputStream is;// ...... đọc
    static ObjectOutputStream oos;// .........
    static ObjectInputStream ois;// .......
    public Main() throws InterruptedException, IOException, ClassNotFoundException{
        JFrame f = new JFrame();
        gamePanel pan = new gamePanel();        
        f.add(pan);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setTitle("Client");
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        Sever();
       // Client c = new Client(oos, pan);
        //c.start();
    }
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException, ClassNotFoundException {
        new Main();
        
    }
    public void Sever() throws IOException, ClassNotFoundException{
        socket = new Socket("127.0.0.1",1234); 
         System.out.println("Da ket noi toi server!");
       
        os = socket.getOutputStream();
	is = socket.getInputStream();
	oos = new ObjectOutputStream(os);
	ois = new ObjectInputStream(is);
    }
    
    public static class Client extends Thread{
        ObjectOutputStream oos;
        gamePanel p;
        public Client(ObjectOutputStream oos,gamePanel p){
            this.oos = oos;
            this.p = p;
        }
        public Client(String a){
            
        }
        @Override
        public void run() {
           while (true) {           
               try {
                   Thread.sleep(100);
                   String txt = String.valueOf(p.getX()) +"/"+String.valueOf(p.getY());
                   System.out.println(txt);
                   oos.writeObject(txt);
                   
               } catch (IOException ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               } catch (InterruptedException ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }
            }    
        } 
    }
    
    
}
