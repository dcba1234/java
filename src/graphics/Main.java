/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Button;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author admin
 */
public class Main {
   
    static ServerSocket serversocket;
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
        f.setTitle("Demo vẽ đồ họa");
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setSize(900,900);
        Sever();
        Client c = new Client(ois, pan);
        c.start();
        while(true){
           Thread.sleep(10);
           pan.shoot();
       }
    }
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException, ClassNotFoundException {
        new Main();
        
    }
    public void Sever() throws IOException, ClassNotFoundException{
        serversocket = new ServerSocket(1234);
        Socket s = new Socket();
	System.out.println("Dang doi client...");
	//socket = serversocket.accept(); // đợi client  
        s = serversocket.accept();
	System.out.println("Client da ket noi!" );
        os = s.getOutputStream();
	is = s.getInputStream();
	oos = new ObjectOutputStream(os);
	ois = new ObjectInputStream(is); 
        
    }
    
    public static class Client extends Thread{
        static ObjectInputStream ois;
        gamePanel p;
        public Client(ObjectInputStream ois,gamePanel p){
            this.ois = ois;
            this.p = p;
        }
        @Override
        public void run() {
           while (true) {           
               try {
                   
                   String stream = ois.readObject().toString();
                       // Thread.sleep(100);
                   if(!stream.equals("")){
                       System.out.println("Khách: " +stream);
                       String a[] = stream.split("/");
                       if(a.length == 3)
                       System.out.println(a[0]+"  " + a[1]+ "   " +a[2] );
                       if(a.length != 3)
                           p.move2(Integer.parseInt(a[0]) , Integer.parseInt(a[1]));
                       else{                          
                           p.bulletEnemy();
                       }
                   } 
               } catch (IOException ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               } catch (ClassNotFoundException ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }
             } 
        }   
    }
    
    
}
