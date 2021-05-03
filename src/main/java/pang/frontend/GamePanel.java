package pang.frontend;

import pang.backend.character.Character;
import pang.backend.character.enemy.Enemy;
import pang.backend.character.enemy.EnemyFactory;
import pang.backend.character.enemy.SmallBall;
import pang.backend.character.player.Player;
import pang.backend.config.ConfigLoader;
import pang.backend.config.GameConfig;
import pang.backend.world.World;
import pang.backend.world.WorldLoader;
import pang.hardware.Audio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    private static int gameWidth;
    private static int gameHeight;
    Dimension screenSize;

    WorldLoader worldLoader;
    World world;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Player player;
    Random random;
    Info info;

    static ConfigLoader configLoader;
    static GameConfig config;
    static EnemyFactory enemyFactory;

    GamePanel(){
        makeWorld();
        makePlayer();
        //spawnEnemy();
        this.getScreenSize();
        setPlayerStartPosition();
        screenSize = new Dimension(gameWidth, gameHeight);
        info = new Info();

        this.setFocusable(true);
        this.addKeyListener(new KeyAction());
        this.setPreferredSize(screenSize);
        startMusic();

        gameThread = new Thread(this);
        gameThread.start();
    }

    public static int getGameWidth(){
        return gameWidth;
    }

    public static int getGameHeight(){
        return gameHeight;
    }

    public void startMusic(){
        Audio music = new Audio();
        music.load();
        music.loop();
    }
    //cały swiat gry
    public void makeWorld(){
        Path configPath = Path.of("./data/main/configs.txt");
        Path levelPath = Path.of("./data/main/level/1.txt");
        worldLoader = WorldLoader.fromConfigPathAndLevelPath(configPath,levelPath);
        world = worldLoader.getWorld();
    }

    public void makePlayer(){
        Path path = Path.of("./data/main/configs.txt");
        configLoader = ConfigLoader.fromConfigPath(path);
        config = configLoader.getConfig("Player");
        player = new Player(config);
    }
    public void setPlayerStartPosition(){
        player.x = getGameWidth()/2 - (int)player.getWidth()/2;
        player.y = getGameHeight();
    }
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw(Graphics g){
        player.draw(g);
        info.draw(g);
    }

    public void checkWallCollisions(){
        if(player.y <= 0){
            player.y = 0;
        }
        if(player.y >= (gameHeight - (int)player.getHeight())){
            player.y = (gameHeight - (int)player.getHeight());
        }
        if(player.x <= 0){
            player.x = 0;
        }
        if(player.x >= (gameWidth - (int)player.getWidth())){
            player.x = (gameWidth - (int)player.getWidth());
        }
    }
    public void checkPlayerData(){
        info.health = (int)player.getHealth();
        info.ammo = player.getAmmoAmount();
        if(player.y == 0) {
            info.score += 1; //not implemented yet
        }
        if(player.x == 0){
            player.setHealth(player.getHealth()-1);
        }
        if(player.x == (gameWidth - (int)player.getWidth())){
            player.setHealth(player.getHealth()+1);
        }
    }
    public void movePlayer(){
        player.move();
    }
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1_000_000_000 / amountOfTicks;
        double delta = 0;

        //zle
        while(true){
            long now = System.nanoTime();
            delta += (now-lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                movePlayer();
                checkWallCollisions();
                checkPlayerData();
                repaint();
                delta --;
                //System.out.println("LOOP");
            }
        }
    }

    public static void getScreenSize(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        gameWidth = (int)size.getWidth();
        gameWidth = gameWidth/2;
        gameHeight = (int)size.getHeight();
        gameHeight = gameHeight/2;
    }

        public class KeyAction extends KeyAdapter {

            @Override
            public void keyPressed(KeyEvent e){
                player.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                player.keyReleased(e);
            }
        }
    }

//TODO 1. skok gracza
//TODO 2. strzelanie  #asd3wq
//TODO 3. wczytywanie przeciwników i wyświetlenie
//TODO 4. pauza #asd3wq
//TODO 5. różne ekrany - statyski , menu, guziki #asd3wq
//TODO 5. pętla gry - zamiana na wątki



//TODO 10. Tło + grafika