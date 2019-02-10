/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public class Bullet extends JPanel implements Runnable{
    static Rectangle bullet;
    Graphics g;
    boolean readyToFire , shot = false;
    Bullet(Graphics g,int x,int y){
        this.g = g;
        bullet = new Rectangle(x,y,3,5);
        System.out.println("ádsd");
        paintComponent();
    }
    public void paintComponent(){
         g.setColor(Color.black);
         g.fillRect(bullet.x, bullet.y, bullet.width,bullet.height);       
    } 

    @Override
    public void run() {
        bullet.y --;
    }
    
}
