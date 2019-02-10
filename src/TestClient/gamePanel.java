/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestClient;

import static TestClient.Main.oos;
import graphics.*;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author admin
 */
public class gamePanel extends JPanel implements ActionListener,KeyListener{
    public static int width = 700,height = 700;
    Button b = new Button("âs");
    public static int x = 100 , y = 10,velX = 0 , velY = 0,x2 = 300,y2 = 300;
    static Boolean first = true;
    Timer t = new Timer(10, this);
    int maxH = 400,maxW = 300;
    Socket socket;
    OutputStream os;// .... gửi dl
    InputStream is;// ...... đọc
    static ObjectOutputStream oos;// .........
    static ObjectInputStream ois;// .......
    public gamePanel() throws IOException {
        socket = new Socket("127.0.0.1",1234); 
         System.out.println("Da ket noi toi server!");  
        os = socket.getOutputStream();
	is = socket.getInputStream();
	oos = new ObjectOutputStream(os);
	ois = new ObjectInputStream(is);
        setPreferredSize(new Dimension(width, height));
        //draw(this);
        addKeyListener(this);
        t.start();
        setFocusable(true);
    }
    public void paint(Graphics g){
        super.paint(g);    
        if(!first) g.drawRect(100, 10, maxW, maxH);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 30, 40);
       // t.start();
      //  paint2(g);
        first = false;
    }
    
    public void paint2(Graphics g){
        g.setColor(Color.BLUE);
        g.drawRect(x2, y2, 30, 40);
    }
    public void move2(int dx,int dy){
        x2 = dx;
        y2 = dy;
        repaint();
    }
    public void moving() throws IOException{
        //x += 10;
       // repaint();
       
       x += velX;
       y += velY;
       oos.writeObject(x+"/"+y);
       repaint();
       
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    
    public static void main(String[] args) throws IOException {
        gamePanel n = new gamePanel();
        JFrame f = new JFrame();
        f.setTitle("sad");
        f.setSize(600, 400);
         f.add(n);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            //x += 1;
            moving();
        } catch (IOException ex) {
            Logger.getLogger(gamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
         if(x < 100){
             velX = 0;
             x = 100;
         }
         if(x > maxW+70){
             velX = 0;
             x = maxW+70;
         }
         if(y < 10){
             velY = 0;
             y = 10;
         }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int c = ke.getKeyCode();
        if(c == KeyEvent.VK_LEFT){
           // x -= 10;
            //repaint();
            velX = -1;
            velY = 0;
        }
        if(c == KeyEvent.VK_RIGHT){
            velX = 1;
            velY = 0;
        }
        if(c == KeyEvent.VK_UP){
            velX = 0;
            velY = -1;
        }
        if(c == KeyEvent.VK_DOWN){
            velX = 0;
            velY = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
         velX = 0;
         velY = 0;
    }
    
}
