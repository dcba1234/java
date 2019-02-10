/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author admin
 */
public class gamePanel extends JPanel implements ActionListener,KeyListener{
    public static int width = 700,height = 700;
    Rectangle bullet;
    boolean shot = false;
    int xb,yb;
    
    public static int x = 100 , y = 10,velX = 0 , velY = 0,x2 = 300,y2 = 300;
    static Boolean first = true;
    Timer t = new Timer(5, this);
    int maxH = 400,maxW = 300;
    public gamePanel() {
       // setPreferredSize(new Dimension(width, height));
       
        
        
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
   
    }
    public void paint(Graphics g){
        super.paint(g);           
        //if(!first) g.drawRect(100, 10, maxW, maxH);
        g.setColor(Color.BLACK);
        g.fillRect(x, y, 30, 40);
       // g.setColor(Color.YELLOW);
       // g.drawRect(300, y, 30, 40);
        //t.start();
        paint2(g);
        if(shot){
             g.setColor(Color.black);
             g.fillRect(bullet.x, bullet.y, bullet.width,bullet.height);
        }
       // first = false;
    }
     public void shoot() {
        if(shot){
            bullet.y -= 3;
           // oos.writeObject("b/"+String.valueOf(bullet.x)+"/"+String.valueOf(bullet.y));         
        }
    }
    public void paint2(Graphics g){
        g.setColor(Color.BLUE);
        g.drawRect(x2, y2, 30, 40);
    }
    public void move2(int dx,int dy){    
             x2 = dx;
            y2 = dy;
            //repaint();  
       
    }
    public void bulletEnemy(){    
            shot = true;
            bullet = new Rectangle(x2+14,y2,3,5);
           // xb = x;
           // yb = y;
    }
    public void moving(){
        x += 10;
        repaint();    
     
       
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        gamePanel n = new gamePanel();
        JFrame f = new JFrame();
        //
        f.setTitle("sad");
        f.setSize(600,400); 
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         f.add(n);
         f.setLocationRelativeTo(null);
        while (true) {  
            Thread.sleep(100);
            // n.moving();
        }
      
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //x += 1;
          x += velX;
            y += velY;
       // System.out.println(x+"/"+y);
         repaint();
//         moving();
//         if(x < 100){
//             velX = 0;
//             x = 100;
//         }
//         if(x > maxW+70){
//             velX = 0;
//             x = maxW+70;
//         }
//         if(y < 10){
//             velY = 0;
//             y = 10;
//         }
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
