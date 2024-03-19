import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class CarRace extends JFrame implements KeyListener {

    private JLabel timerLabel;
    private boolean oyunSonlandi;
    private  Player player1 ;
    private  Player player2;
    private  ArrayList<Bot> Bot = new ArrayList<Bot>();
    private int speed;
    private GamePanel gamePanel;
    private boolean right;
    private boolean left;
    private boolean up;
    private boolean down;
    private boolean right2;
    private boolean left2;
    private boolean up2;
    private boolean down2;


    private ArrayList<Integer> X = new ArrayList<Integer>();
    private ArrayList<Integer>Y = new ArrayList<Integer>();
    private ArrayList<Integer>blackX = new ArrayList<Integer>();
    private ArrayList<Integer>blackY = new ArrayList<Integer>();



    public  CarRace()
    {
        this.setSize(800,800);
        //this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel();
        this.gamePanel = gamePanel;
        this.setLayout(new BorderLayout());
        this.add(gamePanel,BorderLayout.CENTER);
        this.addKeyListener(this);
        this.addKeyListener(new Key());
        this.setVisible(true);
    }

    public class Key implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_UP)
            {
                up2 = true;
            }
            if(key == KeyEvent.VK_DOWN)
            {
                down2 = true;
            }
            if(key == KeyEvent.VK_LEFT)
            {
                left2 = true;
            }
            if(key == KeyEvent.VK_RIGHT)
            {
               right2 = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT) {
                left2 = false;
            }
            if(key == KeyEvent.VK_RIGHT) {
                right2 = false;
            }
            if(key == KeyEvent.VK_UP) {
                up2 = false;
            }
            if(key == KeyEvent.VK_DOWN) {
                down2 = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_A) {
            left = true;
        }
        if(key == KeyEvent.VK_D) {
            right = true;
        }
        if(key == KeyEvent.VK_W) {
            up = true;
        }
        if(key == KeyEvent.VK_S) {
           down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_A) {
            left = false;
        }
        if(key == KeyEvent.VK_D) {
            right = false;
        }
        if(key == KeyEvent.VK_W) {
            up = false;
        }
        if(key == KeyEvent.VK_S) {
            down = false;
        }
    }

    public void game(int speed) {
        this.speed = speed;
        Player p = new Player(1);
        Player p2 = new Player(2);
        Bot b1 = new Bot(0);
        Bot b2 = new Bot(1);
        Bot b3 = new Bot(2);
        Bot b4 = new Bot(3);
        Bot b5 = new Bot(4);

        Bot.add(b1);
        Bot.add(b2);
        Bot.add(b3);
        Bot.add(b4);
        Bot.add(b5);

        player1.start();
        player2.start();

        for(Bot b: Bot){
            b.start();
        }

        try{
            for(Bot b: Bot){
                b.join();
            }
            player1.join();
            player2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    class Player extends Thread
    {
        int code;
        private boolean ilkBolge;
        private boolean ikinciBolge;
        private boolean ucuncuBolge;
        private boolean dorduncuBolge;
        public  Player(int a)
        {
            code = a;
            if( a == 1)
                player1 = this;
            else
                player2 = this;
        }

        @Override
        public void run() {

            while (true) {
                int x = X.get(code-1);
                int y = Y.get(code-1);
                if (code == 1) {
                    if (x <= 370 && y <= 390) {
                        ilkBolge = true;
                    }
                    if (x >= 370 && y <= 390) {
                        ikinciBolge = true;
                    }
                    if (x >= 370 && y >= 390) {
                        ucuncuBolge = true;
                    }
                    if (x <= 370 && y >= 390) {
                        dorduncuBolge = true;
                    }
                    int oldx = x;
                    int oldy = y;
                    if (moved(1)) {
                        gamePanel.repaint();
                       try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if(ilkBolge && ikinciBolge && ucuncuBolge && dorduncuBolge){
                        if(y<= 390 && x <= 370){
                            if(!oyunSonlandi)
                                gameOver(code,"Player");
                            oyunSonlandi = true;
                        }
                    }

                    if (carpisma(oldx,oldy,X.get(0), Y.get(0), "Oyuncu", 1)){
                       /* try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }*/
                        gamePanel.repaint();
                    }

                   // System.out.println(X.get(1) +"," + Y.get(1));

                    if (touchedCircle(oldx,oldy,X.get(0), Y.get(0), "Oyuncu", 1)){
                      //  System.out.println("cagırıldı");
                       /* try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }*/
                        gamePanel.repaint();
                    }

                } else {
                    if (x <= 370 && y <= 390) {

                        ilkBolge = true;

                    }
                    if (x >= 370 && y <= 390) {
                        ikinciBolge = true;
                    }
                    if (x >= 370 && y >= 390) {
                        ucuncuBolge = true;
                    }
                    if (x <= 370 && y >= 390) {
                        dorduncuBolge = true;
                    }
                    int oldx = x;
                    int oldy = y;
                    if (moved(2)) {

                        gamePanel.repaint();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if(ilkBolge && ikinciBolge && ucuncuBolge && dorduncuBolge){
                        if(y<= 390 && x <= 370){
                            if(!oyunSonlandi)
                                gameOver(code,"Player");
                            oyunSonlandi = true;
                        }
                    }

                    if (carpisma(oldx,oldy,X.get(1), Y.get(1), "Oyuncu", 2)){
                        /*try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }*/
                        gamePanel.repaint();
                    }
                   // System.out.println(X.get(1) +"," + Y.get(1));
                    if (touchedCircle(oldx,oldy,X.get(1), Y.get(1), "Oyuncu", 2)){
                       // System.out.println("cagırıldı1");
                       /* try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }*/
                        gamePanel.repaint();
                    }
                }
            }
        }
    }
    class  Bot extends Thread
    {
       int  threadNumber;
        private boolean ilkBolge;
        private boolean ikinciBolge;
        private boolean ucuncuBolge;
        private boolean dorduncuBolge;
        public  Bot(int a)
        {
          this.threadNumber  = a;
        }
        public  void run()
        {
            while(true)
            {
                int x = blackX.get(threadNumber);
                int y = blackY.get(threadNumber);
                int x2 = blackX.get(threadNumber)+10;
                int y2 = blackY.get(threadNumber)+10;

                if (x <= 370 && y <= 390){

                    ilkBolge = true;
                    int randomDirection = (int)(Math.random()*2);
                    if(randomDirection == 0)
                    {
                        x = blackX.get(threadNumber)+1;
                        y = blackY.get(threadNumber);
                        x2 += 1;

                        if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                            blackX.set(threadNumber, blackX.get(threadNumber) + 1);

                            if (carpisma(x-1,y,blackX.get(threadNumber),blackY.get(threadNumber),"Bot",threadNumber)) {
                               /* try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }
                            if(touchedCircle(x-1,y,x,y,"Bot",threadNumber)){
                                /*try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }

                        }
                    }
                    if(randomDirection == 1)
                    {
                        x = blackX.get(threadNumber);
                        y = blackY.get(threadNumber)-1;
                        y2 -= 1;

                        if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200
                                &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {

                            {
                                blackY.set(threadNumber, y);
                            }

                            if (carpisma(x,y+1,blackX.get(threadNumber),blackY.get(threadNumber),"Bot",threadNumber)) {
                                /*try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }
                            if(touchedCircle(x,y+1,x,y,"Bot",threadNumber)){
                              /*  try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }

                        }
                    }
                }

                if (x >= 370 && y <= 390){
                    ikinciBolge = true;
                    //System.out.println("zey");
                    int randomDirection = (int)(Math.random()*2);
                    if(randomDirection == 0)
                    {
                        x = blackX.get(threadNumber)+1;
                        y = blackY.get(threadNumber);
                        x2 += 1;

                        if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200&&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                            blackX.set(threadNumber, blackX.get(threadNumber) + 1);

                            if (carpisma(x-1,y,blackX.get(threadNumber),blackY.get(threadNumber),"Bot",threadNumber)) {
                              /*  try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }
                            if(touchedCircle(x-1,y,x,y,"Bot",threadNumber)){
                              /*  try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }

                        }
                    }
                    if(randomDirection == 1)
                    {
                        x = blackX.get(threadNumber);
                        y = blackY.get(threadNumber)+1;
                        y2 += 1;


                        if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 &&
                                Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200&&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                            blackY.set(threadNumber, y);

                            if (carpisma(x,y-1,blackX.get(threadNumber),blackY.get(threadNumber),"Bot",threadNumber)) {
                               /* try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }
                            if(touchedCircle(x,y-1,x,y,"Bot",threadNumber)){
                              /*  try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }

                        }
                    }
                }

                if (x >= 370 && y >= 390){
                    ucuncuBolge = true;

                    int randomDirection = (int)(Math.random()*2);
                    if(randomDirection == 0)
                    {
                        x = blackX.get(threadNumber)-1;
                        y = blackY.get(threadNumber);
                        x2 -= 1;

                        if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200&&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                            blackX.set(threadNumber, blackX.get(threadNumber) - 1);

                            if (carpisma(x+1,y,blackX.get(threadNumber),blackY.get(threadNumber),"Bot",threadNumber)) {
                              /*  try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }
                            if(touchedCircle(x+1,y,x,y,"Bot",threadNumber)){
                              /*  try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }

                        }
                    }
                    if(randomDirection == 1)
                    {
                        x = blackX.get(threadNumber);
                        y = blackY.get(threadNumber)+1;
                        y2 += 1;


                        if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200&&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                            blackY.set(threadNumber, y);

                            if (carpisma(x,y-1,blackX.get(threadNumber),blackY.get(threadNumber),"Bot",threadNumber)) {
                              /*  try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }
                            if(touchedCircle(x,y-1,x,y,"Bot",threadNumber)){
                                /*try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }

                        }
                    }
                }
                if (x <= 370 && y >= 390){
                    dorduncuBolge = true;

                    int randomDirection = (int)(Math.random()*2);
                    if(randomDirection == 0)
                    {
                        x = blackX.get(threadNumber)-1;
                        y = blackY.get(threadNumber);
                        x2 -= 1;


                        if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200&&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                            blackX.set(threadNumber, blackX.get(threadNumber) - 1);

                            if (carpisma(x+1,y,blackX.get(threadNumber),blackY.get(threadNumber),"Bot",threadNumber)) {
                                /*try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }
                            if(touchedCircle(x+1,y,x,y,"Bot",threadNumber)){
                              /*  try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }

                        }
                    }
                    if(randomDirection == 1)
                    {
                        x = blackX.get(threadNumber);
                        y = blackY.get(threadNumber)-1;
                        y2 -= 1;


                        if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200&&
                                Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                                Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                            blackY.set(threadNumber, y);

                            if (carpisma(x,y+1,blackX.get(threadNumber),blackY.get(threadNumber),"Bot",threadNumber)) {
                               /* try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }
                            if(touchedCircle(x,y+1,x,y,"Bot",threadNumber)){
                              /*  try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }*/
                                gamePanel.repaint();
                            }

                        }
                    }
                }
                try {
                    double d =(1.0/speed)*1000;
                    Thread.sleep((int)d);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if(ilkBolge && ikinciBolge && ucuncuBolge && dorduncuBolge){
                    if(y<= 390 && x <= 370){
                        if(!oyunSonlandi)
                            gameOver(threadNumber,"Bot");
                        oyunSonlandi = true;
                    }
                }

                gamePanel.repaint();
            }
        }
    }
    public class GamePanel extends JPanel {
        private static final int WIDTH = 800;
        private static final int HEIGHT = 800;
        private  Timer timer1;
        private  int beginningSalise = 0;
        private int minutes = 0;
        private int seconds = 0;
        private int miliseconds = 0;


        public GamePanel()
        {
            setPreferredSize(new Dimension(800,800));
            timerLabel = new JLabel("00:00:00");
            JPanel newPanel =  new JPanel(new FlowLayout ( FlowLayout.LEFT));
            newPanel.add(timerLabel);

            setLayout(new BorderLayout());
            add(newPanel, BorderLayout.NORTH);
            X.add(25);
            X.add(45);
            Y.add(385);
            Y.add(385);
            blackX.add(65);
            blackX.add(85);
            blackX.add(105);
            blackX.add(125);
            blackX.add(145);
            blackY.add(385);
            blackY.add(385);
            blackY.add(385);
            blackY.add(385);
            blackY.add(385);
            beginningSalise = 0;

            Timer  timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateTimer();
                }
            }
            );
            timer.start();
        }
        private void updateTimer() {
            beginningSalise++;
            if(beginningSalise == 100) {
                seconds++;
                beginningSalise = 0;
            }
            if(seconds == 60) {
                minutes++;
                seconds = 0;
            }

            timerLabel.setText(String.format("%02d:%02d:%02d", minutes, seconds,beginningSalise));
        }
        public  void paintComponent(Graphics graphics)
        {
            super.paintComponent(graphics);
            graphics.setColor(Color.BLACK);
            graphics.drawOval(20, 40, 700, 700);
            graphics.drawOval(170, 190, 400, 400);
            graphics.setColor(Color.BLACK);
            graphics.drawLine(20, 390, 170, 390);

            graphics.setColor(Color.RED);
            graphics.fillRect(X.get(0), Y.get(0), 10, 10);
            graphics.setColor(Color.GREEN);
            graphics.fillRect(X.get(1),Y.get(1), 10, 10);

            for(int i = 0;i<blackX.size();i++)
            {
                graphics.setColor(Color.BLACK);
                graphics.fillRect(blackX.get(i), blackY.get(i), 10, 10);
            }
        }
    }

    public boolean carpisma(int oldx, int oldy, int x, int y, String type,int kod){
      int  x2 = x+10;
      int y2 = y + 10;


        if(type.equals("Oyuncu") && kod == 1){
            if ((x <= X.get(1)+10 && x >= X.get(1) && y <= Y.get(1)+10 && y >= Y.get(1)) || (x2 <= X.get(1)+10 && x2 >= X.get(1) && y <= Y.get(1)+10 && y >= Y.get(1)) || (x <= X.get(1)+10 && x >= X.get(1) && y2 <= Y.get(1)+10 && y2 >= Y.get(1)) || (x2 <= X.get(1)+10 && x2 >= X.get(1) && y2 <= Y.get(1)+10 && y2 >= Y.get(1)) ){
                System.out.println("abcd");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
               // System.out.println("abcdef");

                X.set(0,oldx);
                Y.set(0,oldy);
                return true;
            }

            for(int i = 0; i < blackX.size(); i++){
                if((x <= blackX.get(i)+10 &&  x>= blackX.get(i) && y <= blackY.get(i)+10 && y >= blackY.get(i)) || (x2 <= blackX.get(i)+10 &&  x2>= blackX.get(i) && y <= blackY.get(i)+10 && y >= blackY.get(i)) || (x <= blackX.get(i)+10 &&  x>= blackX.get(i) && y2 <= blackY.get(i)+10 && y2 >= blackY.get(i)) || (x2 <= blackX.get(i)+10 &&  x2>= blackX.get(i) && y2 <= blackY.get(i)+10 && y2 >= blackY.get(i))) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    X.set(0,oldx);
                    Y.set(0,oldy);
                    return true;
                }
            }
            return false;
        }
        if(type.equals("Oyuncu") && kod == 2){
            if ((x <= X.get(0)+10 && x >= X.get(0) && y <= Y.get(0)+10 && y >= Y.get(0)) || (x2 <= X.get(0)+10 && x2 >= X.get(0) && y <= Y.get(0)+10 && y >= Y.get(0)) || (x <= X.get(0)+10 && x >= X.get(0) && y2 <= Y.get(0)+10 && y2 >= Y.get(0)) || (x2 <= X.get(0)+10 && x2 >= X.get(0) && y2 <= Y.get(0)+10 && y2 >= Y.get(0)) )  {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                X.set(1,oldx);
                    Y.set(1,oldy);
                    return true;

            }

            for(int i = 0; i < blackX.size(); i++){
                if((x <= blackX.get(i)+10 &&  x>= blackX.get(i) && y <= blackY.get(i)+10 && y >= blackY.get(i)) || (x2 <= blackX.get(i)+10 &&  x2>= blackX.get(i) && y <= blackY.get(i)+10 && y >= blackY.get(i)) || (x <= blackX.get(i)+10 &&  x>= blackX.get(i) && y2 <= blackY.get(i)+10 && y2 >= blackY.get(i)) || (x2 <= blackX.get(i)+10 &&  x2>= blackX.get(i) && y2 <= blackY.get(i)+10 && y2 >= blackY.get(i))) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    X.set(1,oldx);
                    Y.set(1,oldy);
                    return true;
                }
            }
            return false;
        }
        if(type.equals("Bot")){
            if ((x <= X.get(1)+10 && x >= X.get(1) && y <= Y.get(1)+10 && y >= Y.get(1)) || (x2 <= X.get(1)+10 && x2 >= X.get(1) && y <= Y.get(1)+10 && y >= Y.get(1)) || (x <= X.get(1)+10 && x >= X.get(1) && y2 <= Y.get(1)+10 && y2 >= Y.get(1)) || (x2 <= X.get(1)+10 && x2 >= X.get(1) && y2 <= Y.get(1)+10 && y2 >= Y.get(1)) ){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                blackX.set(kod,oldx);
                    blackY.set(kod,oldy);
                    return true;

            }
             if ((x <= X.get(0)+10 && x >= X.get(0) && y <= Y.get(0)+10 && y >= Y.get(0)) || (x2 <= X.get(0)+10 && x2 >= X.get(0) && y <= Y.get(0)+10 && y >= Y.get(0)) || (x <= X.get(0)+10 && x >= X.get(0) && y2 <= Y.get(0)+10 && y2 >= Y.get(0)) || (x2 <= X.get(0)+10 && x2 >= X.get(0) && y2 <= Y.get(0)+10 && y2 >= Y.get(0)) ){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                blackX.set(kod,oldx);
                    blackY.set(kod,oldy);
                    return true;

            }
            for(int i = 0; i < blackX.size(); i++){
                if(i != kod)
                {
                    if((x <= blackX.get(i)+10 &&  x>= blackX.get(i) && y <= blackY.get(i)+10 && y >= blackY.get(i)) || (x2 <= blackX.get(i)+10 &&  x2>= blackX.get(i) && y <= blackY.get(i)+10 && y >= blackY.get(i)) || (x <= blackX.get(i)+10 &&  x>= blackX.get(i) && y2 <= blackY.get(i)+10 && y2 >= blackY.get(i)) || (x2 <= blackX.get(i)+10 &&  x2>= blackX.get(i) && y2 <= blackY.get(i)+10 && y2 >= blackY.get(i))) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        blackX.set(kod,oldx);
                        blackY.set(kod,oldy);
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public boolean moved(int code) {
        int x = 0;
        int y = 0;
        int x2 = 0;
        int y2 = 0;
        boolean moved = false;
        if (code == 1) {
            if (right) {
                x = X.get(0) + 2;
                y = Y.get(0);
                x2 = x+10;
                y2 = y+10;
                if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                        Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200
                && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200
                && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                   // Thread.sleep(500);
                    X.set(0, x);
                }
                moved = true;
            }
            if(left){
                x = X.get(0) - 2;
                y = Y.get(0);
                x2 = x+10;
                y2 = y+10;
                if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                        Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                    X.set(0, x);
                }
                moved = true;
            }
            if(up){
                x = X.get(0);
                y = Y.get(0) - 2;
                x2 = x+10;
                y2 = y+10;
                if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                        Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                    Y.set(0, y);
                }
                moved = true;
            }
            if (down){
                x = X.get(0);
                y = Y.get(0) + 2;
                x2 = x+10;
                y2 = y+10;
                if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                        Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                    Y.set(0, y);
                }
                moved = true;
            }
        }
        else {
            if (right2) {
                x = X.get(1) + 2;
                y = Y.get(1);
                x2 = x+10;
                y2 = y+10;
                if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                        Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                    X.set(1, x);
                }
                moved = true;
            }
            if(left2){
                x = X.get(1) - 2;
                y = Y.get(1);
                x2 = x+10;
                y2 = y+10;
                if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                        Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                    X.set(1, x);
                }
                moved = true;
            }
            if(up2){
                x = X.get(1);
                y = Y.get(1) - 2;
                x2 = x+10;
                y2 = y+10;
                if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                        Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                    Y.set(1, y);
                }
                moved = true;
            }
            if (down2){
                x = X.get(1);
                y = Y.get(1) + 2;
                x2 = x+10;
                y2 = y+10;
                if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) >= 200 &&
                        Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) <= 350 && Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) >= 200
                        && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) <= 350 && Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) >= 200) {
                    Y.set(1, y);
                }
                moved = true;
        }
    }
        return moved;
}
    public void gameOver(int code, String type){
        if(type.equals("Bot")){
            JLabel oyunbitti = new JLabel();
            oyunbitti.setLayout(new BorderLayout());
            JLabel oyunbitti1 = new JLabel(++code + ". Bot Kazandı! Süresi: " + timerLabel.getText());
            oyunbitti.add(BorderLayout.CENTER,oyunbitti1);

            JFrame window = new JFrame();
            window.setLocation(200,200);
            window.setSize(250,250);
            window.setDefaultCloseOperation(EXIT_ON_CLOSE);
            window.setLayout(new BorderLayout());
            window.add(oyunbitti,BorderLayout.CENTER);
            window.setVisible(true);
            this.dispose();
        }
        else {
            JLabel oyunbitti = new JLabel();
            oyunbitti.setLayout(new BorderLayout());
            JLabel oyunbitti1 = new JLabel(code + ". Oyuncu Kazandı! Süresi: " + timerLabel.getText());
            oyunbitti.add(BorderLayout.CENTER,oyunbitti1);

            JFrame window = new JFrame();
            window.setLocation(200,200);
            window.setSize(250,250);
            window.setDefaultCloseOperation(EXIT_ON_CLOSE);
            window.setLayout(new BorderLayout());
            window.add(oyunbitti,BorderLayout.CENTER);
            window.setVisible(true);
            this.dispose();
        }
    }

    public boolean touchedCircle(int oldx, int oldy, int x, int y,String type,int code){
        int x2 = x + 10;
        int y2 = y + 10;
        if(type.equals("Bot"))
        {
            if (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) == 350 || Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) == 350
            || Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) == 350 || Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) == 350 ||
                    Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) == 200 || Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) == 200
                    || Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) == 200 || Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) == 200) {
                //System.out.println("zynp1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                blackX.set(code, oldx);
                blackY.set(code,oldy);
                return true;
            }
        }
        else   {
            if ((Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) > 349) || (Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) >349)
                    || (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) > 349) || (Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) > 349) ||
                    (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y - 390, 2)) < 201) || (Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y2 - 390, 2)) < 201)
                    || (Math.sqrt(Math.pow(x - 370, 2) + Math.pow(y2 - 390, 2)) < 201) || (Math.sqrt(Math.pow(x2 - 370, 2) + Math.pow(y - 390, 2)) < 201)) {
           //     System.out.println("zynp");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                X.set(code-1, oldx);
                Y.set(code-1,oldy);
                return true;
            }
        }
        return false;
    }
}
