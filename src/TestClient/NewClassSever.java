/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestClient;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author admin
 */
public class NewClassSever extends JPanel implements ActionListener,KeyListener{
    Timer t = new Timer(5, this);
    Rectangle bullet2;
    ImageIcon i,i2;
    int huong  = 1,huong2 = 1,huongdan = 1,huongdan2 = 1,mau = 3,mau2 = 3;
    int xb,yb;
    boolean shot2 = false;
    int direction;
    boolean readyToFire , shot = false;
    Rectangle tank,block,bullet;
     public static int x = 300 , y = 10,velX = 0 , velY = 0,x2 = 100,y2 = 500;
     Socket socket;
      static ServerSocket serversocket;
    OutputStream os;// .... gửi dl
    InputStream is;// ...... đọc
    static ObjectOutputStream oos;// .........
    static ObjectInputStream ois;// .......
    @Override
    public void actionPerformed(ActionEvent ae) {
       // check();
        x += velX;
        y += velY;
        if(x < 100){
             velX = 0;
             x = 100;
         }
         if(x > 800 - 30){
             velX = 0;
             x = 800 - 30;
         }
         if(y < 10){
             velY = 0;
             y = 10;
         }
         if(y > 510 - 30){
             velY = 0;
             y = 510 - 30;
         }
        try {
         
            oos.writeObject(x+"/"+y);
        } catch (IOException ex) {
            Logger.getLogger(NewClassSever.class.getName()).log(Level.SEVERE, null, ex);
        }
         repaint();
    }
    public void bulletEnemy(){    
            shot2 = true;
            huongdan2 = huong2;
            switch (huongdan2){
                case 1: 
                    bullet2 = new Rectangle(x2+14,y2-10,3,5);
                    break;
                case 2: 
                    bullet2 = new Rectangle(x2+14,y2+30,3,5);   
                    break;
                case 3: 
                    bullet2 = new Rectangle(x2-2,y2+13,3,5);
                    break;
                case 4: 
                    bullet2 = new Rectangle(x2+30,y2+13,3,5);
                    break;                
            }
            
    }
    public NewClassSever() throws IOException{
        initSocket();
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    public void initSocket() throws IOException{
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
    public void paintComponent(Graphics g){
         super.paintComponent(g);
         g.drawRect(100, 10, 700, 500);
         g.setColor(Color.BLACK);
         g.drawRect(x, y, 30, 30);
         drawtank(g);
         //Thread t = new Thread(new Bullet(g, x, y));
         //t.start();
         g.setColor(Color.red);
         g.drawRect(x2, y2, 30, 30);
         drawtank2(g);
         drawheart(g,30,0,mau);
         drawheart(g,30,800,mau2);
         if(shot){
              g.setColor(Color.black);
              g.fillRect(bullet.x, bullet.y, bullet.width,bullet.height);
         }
         if(shot2){
             g.setColor(Color.red);
             g.fillRect(bullet2.x, bullet2.y, bullet2.width,bullet2.height);
        }
    } 
    public void drawtank(Graphics g){
        String url = "tank.png";
        switch (huong){
            case 1: 
                url = "tankU2.png";
                break;
            case 2: 
                url = "tankD2.png";
                break;
            case 3: 
                url = "tankL2.png";
                break;
            case 4: 
                url = "tankR2.png";
                break;                
        }
        i = new ImageIcon(getClass().getResource("/Images/"+url));      
        i.paintIcon(this, g, x, y);
    }
    public void drawtank2(Graphics g){
        String url = "tank.png";
        switch (huong2){
            case 1: 
                url = "tank.png";
                break;
            case 2: 
                url = "tankDown.png";
                break;
            case 3: 
                url = "tankLeft.png";
                break;
            case 4: 
                url = "tankRight.png";
                break;               
        }
        i2 = new ImageIcon(getClass().getResource("/Images/"+url));      
        i2.paintIcon(this, g, x2, y2);
    }
    public void shoot() throws IOException{
        if(shot){
            switch (huongdan){
                case 1: 
                    bullet.y -= 4;
                    break;
                case 2: 
                    bullet.y += 4;    
                    break;
                case 3: 
                    bullet.x -= 4;
                    break;
                case 4: 
                    bullet.x += 4;
                    break;                
            }
            boolean checkshot = checkGetTheShot(bullet, x2, y2);
           // oos.writeObject("b/"+String.valueOf(bullet.x)+"/"+String.valueOf(bullet.y));
            readyToFire = false;
            if(checkshot ||bullet.y < 10 || bullet.x > 800 || bullet.y > 510 || bullet.x < 100) {
                if(checkshot) mau2 --;
                bullet = new Rectangle(0,0,0,0);
                shot = false;
                
            } 
        }
    }
    public void shoot2() {
        if(shot2){          
            switch (huongdan2){
                case 1: 
                    bullet2.y -= 4;
                    break;
                case 2: 
                    bullet2.y += 4;    
                    break;
                case 3: 
                    bullet2.x -= 4;
                    break;
                case 4: 
                    bullet2.x += 4;
                    break;                
            }
            if(checkGetTheShot(bullet2,x,y)){
                 shot2 = false;
                  mau--;
             }
            if(bullet2.y < 10 || bullet2.x > 800 || bullet2.y > 510 || bullet2.x < 100 ) {
                shot2 = false;
                
            } 
            
        }
    }
    public boolean checkGetTheShot(Rectangle dan,int x,int y){
        boolean trungdan = false;
        tank = new Rectangle(x,y,30+3,30+3);
        if(tank.contains(dan)) {
           
            trungdan = true;
        }
        return trungdan;
    }
    private void drawheart(Graphics g,int vitri,int viTriX,int mau) {
        g.setColor(Color.RED);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        switch (mau){
            case 3:
                g.drawString("\u2665", 20 + viTriX, vitri);
                g.drawString("\u2665", 40 + viTriX, vitri);
                g.drawString("\u2665", 60 + viTriX, vitri);
                break;
            case 2:
                g.drawString("\u2665", 20 + viTriX, vitri);
                g.drawString("\u2665", 40 + viTriX, vitri);
                g.setColor(Color.BLACK);
                g.drawString("\u2665", 60 + viTriX, vitri);
                break;
            case 1:
                g.drawString("\u2665", 20 + viTriX, vitri);
                g.setColor(Color.BLACK);
                g.drawString("\u2665", 40 + viTriX, vitri);
                g.drawString("\u2665", 60 + viTriX, vitri);
                break; 
            case 0:
                g.setColor(Color.BLACK);
                g.drawString("\u2665", 20 + viTriX, vitri);
                g.drawString("\u2665", 40 + viTriX, vitri);
                g.drawString("\u2665", 60 + viTriX, vitri);
                gameOver(g);
                break;
        }
        
    }
    public void move2(int x,int y){
        if(x > x2 ) huong2 = 4;
        if(x < x2 ) huong2 = 3;
        if(y > y2 ) huong2 = 2;
        if(y < y2 ) huong2 = 1;
        x2 = x;
        y2 = y;
    }
    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 70));
        if(mau == 0) g.drawString("Game Over", 290, 240);
        if(mau2 == 0) g.drawString("You Win", 290, 240);
        t.stop();
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        NewClassSever t = new NewClassSever();
        JFrame f = new JFrame();
        t.setBackground(Color.white);
        f.setTitle("Sever");
        f.setSize(900,600); 
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(t);
        f.setLocationRelativeTo(null);
        Client c = new Client(ois, t);
        c.start();
       while(true){
           Thread.sleep(7);
           t.shoot();
           t.shoot2();
       }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int c = ke.getKeyCode();
        if(c == KeyEvent.VK_LEFT){
           // x -= 10;
            //repaint();
            velX = -1;
            velY = 0;
            huong = 3;
        }
        if(c == KeyEvent.VK_RIGHT){
            velX = 1;
            velY = 0;
            huong = 4;
        }
        if(c == KeyEvent.VK_UP){
            velX = 0;
            velY = -1;
            huong = 1;
        }
        if(c == KeyEvent.VK_DOWN){
            velX = 0;
            velY = 1;
            huong = 2;
        }
        if(c == KeyEvent.VK_SPACE){
            if(bullet == null) readyToFire = true;
            if(readyToFire) {
                
                huongdan = huong;
                switch (huongdan){
                    case 1: 
                        bullet = new Rectangle(x+14,y-10,3,5);
                        break;
                    case 2: 
                        bullet = new Rectangle(x+14,y+30,3,5);  
                        break;
                    case 3: 
                        bullet = new Rectangle(x-2,y+13,3,5);
                        break;
                    case 4: 
                        bullet = new Rectangle(x+30,y+13,3,5);
                        break;                
                 }
                try {
                    //oos.writeObject("b/"+String.valueOf(bullet.x)+"/"+String.valueOf(bullet.y));
                    oos.writeObject("b/"+String.valueOf(bullet.x)+"/"+String.valueOf(bullet.y));
                } catch (IOException ex) {
                    Logger.getLogger(NewClassSever.class.getName()).log(Level.SEVERE, null, ex);
                }
                shot = true;              
            }
            
            
        }
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyCode() == KeyEvent.VK_LEFT ) {
            velX = 0;
            velY = 0;
        }
       if(ke.getKeyCode() == KeyEvent.VK_SPACE){
            readyToFire = false;
            if(bullet.y <= 1){
                bullet = new Rectangle(0,0,0,0);
                shot = false;
                readyToFire = true;
            }
            
            
        }
    }
    public static class Client extends Thread{
        static ObjectInputStream ois;
        NewClassSever p;
        public Client(ObjectInputStream ois,NewClassSever p){
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
                       //System.out.println("Khách: " +stream);
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
