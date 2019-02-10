/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestClient;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

/**
 *
 * @author admin
 */
public class NewClass extends JPanel implements ActionListener,KeyListener{
    ImageIcon map;
   
    Timer t = new Timer(5, this);
    ImageIcon i,i2;
    int huong  = 1,huong2 = 1,huongdan = 1,huongdan2 = 1,mau = 3,mau2 = 3;
    BufferedImage image;
    static Boolean first = true;
    int direction;
    boolean readyToFire , shot = false, shot2 = false;
    Rectangle tank,bullet,blockk;
    Rectangle bullet2;
    List<Rectangle> block = new ArrayList<Rectangle>();
     public static int x = 100 , y = 10,velX = 0 , velY = 0,x2 = 300,y2 = 100;
     Socket socket;
    OutputStream os;// .... gửi dl
    InputStream is;// ...... đọc
    static ObjectOutputStream oos;// .........
    static ObjectInputStream ois;// .......
    
    // huong  len 1 xuong 2 trai 3 phai 4
    @Override
    public void actionPerformed(ActionEvent ae) {
        x += velX;
        y += velY;
        check();
        // thành
        try {
         
            oos.writeObject(x+"/"+y);
        } catch (IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public NewClass() throws IOException{
        // thành
        initSocket();
        initMap();
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    public void initSocket() throws IOException{
        socket = new Socket("192.168.43.93",1234); 
         System.out.println("Da ket noi toi server!");  
        os = socket.getOutputStream();
	is = socket.getInputStream();
	oos = new ObjectOutputStream(os);
	ois = new ObjectInputStream(is);
    }
    public void paintComponent(Graphics g){
         super.paintComponent(g);
         g.drawRect(100, 10, 700, 500);          
         g.setColor(Color.red);
         g.drawRect(x, y, 30, 30);
         //i = new ImageIcon("C:/Users/admin/Pictures/tank.png");
         
         drawtank(g);
         drawheart(g,30,0,mau);
         drawheart(g,30,800,mau2);
    
         g.setColor(Color.BLACK);
         g.drawRect(x2, y2, 30, 30);
         drawtank2(g);
         drawMap(g);
         if(shot){
              g.setColor(Color.red);
              g.fillRect(bullet.x, bullet.y, bullet.width,bullet.height);
         }
         if(shot2){
             g.setColor(Color.black);
             g.fillRect(bullet2.x, bullet2.y, bullet2.width,bullet2.height);
        }
        // gameOver(g);
          
    }
    public void drawtank(Graphics g){
        String url = "tank.png";
        switch (huong){
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
        i = new ImageIcon(getClass().getResource("/Images/"+url));      
        i.paintIcon(this, g, x, y);
    }
    public void drawtank2(Graphics g){
        String url = "tank.png";
        switch (huong2){
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
        i2 = new ImageIcon(getClass().getResource("/Images/"+url));      
        i2.paintIcon(this, g, x2, y2);
        
    }
     private void drawMap(Graphics g) {
        g.setColor(Color.black);
         for (Rectangle rectangle : block) {
             g.drawRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
             g.drawImage(new ImageIcon(getClass().getResource("/Images/block.png")).getImage(), rectangle.x, rectangle.y, this);
         }
        //g.drawRect(block.get(0).x, block.get(0).y, block.get(0).width,block.get(0).height);
        // map =  new ImageIcon(getClass().getResource("/Images/block.png"));
       // map.paintIcon(this, g, block.get(0).x, block.get(0).y);
    }
    private void initMap() {
        block.add(new Rectangle(182, 30, 36, 37));
        block.add(new Rectangle(182, 30 + 37, 36, 37));
        block.add(new Rectangle(182, 30 + 37*2, 36, 37));
        block.add(new Rectangle(182, 30 + 37*3, 36, 37));
        block.add(new Rectangle(172, 290, 36, 90));
        
        block.add(new Rectangle(182  + 36, 30 + 37, 36, 37));
        
        for(int i = 1 ; i < 15 ; i ++){
            block.add(new Rectangle(182  + 36 * i, 30 + 37, 36, 37));
        }
        for(int i = 1 ; i < 15 ; i ++){
            block.add(new Rectangle(182  + 36 * i, 30 + 37*10, 36, 37));
        }
        for(int i = 1 ; i < 9 ; i ++){
            block.add(new Rectangle(592  , 30 + 37*i, 36, 37));
        }
        for(int i = 1 ; i < 9 ; i ++){
            block.add(new Rectangle(592 - 37  , 30 + 37*i, 36, 37));
        }
        for(int i = 1 ; i < 9 ; i ++){
            block.add(new Rectangle(592 - 37*3  , 30 + 37*i, 36, 37));
        }
        for(int i = 1 ; i < 9 ; i ++){
            block.add(new Rectangle(592 - 37*4  , 30 + 37*i, 36, 37));
        }
        for(int i = 1 ; i < 9 ; i ++){
            block.add(new Rectangle(592 - 37*5  , 30 + 37*i, 36, 37));
        }
    }
    
    public void shoot() throws IOException{
        if(shot){
            //bullet.y -= 3;
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
            
            
            //oos.writeObject("b/"+String.valueOf(bullet.x)+"/"+String.valueOf(bullet.y));
            readyToFire = false;
            boolean checkshot = checkGetTheShot(bullet, x2, y2);
            if(checkshot || bullet.y < 10 || bullet.x > 800 || bullet.y > 510 || bullet.x < 100) {
                if(checkshot) mau2 --;
                bullet = new Rectangle(0,0,0,0);
                shot = false;
//                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
//                Date date = new Date();
//                System.out.println(dateFormat.format(date));
            } 
        }
    }
    public void shoot2() throws IOException {
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
    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 70));
        if(mau == 0) g.drawString("Game Over", 290, 240);
        if(mau2 == 0) g.drawString("You Win", 290, 240);
        t.stop();
    }
    public void move2(int x,int y){
        if(x > x2 ) huong2 = 4;
        if(x < x2 ) huong2 = 3;
        if(y > y2 ) huong2 = 2;
        if(y < y2 ) huong2 = 1;
        x2 = x;
        y2 = y;
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        
        NewClass t = new NewClass();
        JFrame f = new JFrame();
        t.setBackground(Color.white);
        //f.setLocationRelativeTo(null);
        f.setTitle("test");
        //f.setSize(600,400); 
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
            if(bullet == null){
                readyToFire = true;
                
                       
            }
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
                // thành
                try {
                    oos.writeObject("b/"+String.valueOf(bullet.x)+"/"+String.valueOf(bullet.y));
                } catch (IOException ex) {
                    Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
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

    private void check() {
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

   
    public static class Client extends Thread{
        static ObjectInputStream ois;
        NewClass p;
        public Client(ObjectInputStream ois,NewClass p){
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
                      // System.out.println("Khách: " +stream);
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
