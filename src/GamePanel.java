package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    private static final int DELAY = 75;
    private final int x[] = new int[GAME_UNITS];
    private final int y[] = new int[GAME_UNITS];
    private int bodyParts = 6;
    private int fruitsEaten;
    private int fruitX;
    private int fruitY;
    private char direction = 'R';
    private boolean running = false;
    private boolean isColored = true;
    private Timer timer;
    private Random random;

    GamePanel(){
        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(new GameKeyAdapter());

        startGame();
    }

    public void startGame() {
        newFruit();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        score(g);
    }

    public void draw(Graphics g) {
        //Draws vertical and horizontal lines
        if (running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            g.setColor(Color.RED);
            g.fillOval(fruitX, fruitY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(63, 112, 77));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    if (isColored)
                        g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255), 25 + random.nextInt(100)));
                    else
                        g.setColor(new Color(00, 64, 00));

                    //Colors snake
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }else {
            gameOver(g);
        }
    }

    public void move() {
        for (int i = bodyParts; i > 0;i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }

    public void newFruit() {
        fruitX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        fruitY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }

    public void checkFruit() {
        if (x[0] == fruitX && y[0] == fruitY) {
            bodyParts++;
            fruitsEaten++;
            newFruit();
        }
    }

    public void checkCollisions() {
        // Checks if head touches body
        for (int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // Checks if head touches with borders
        if (x[0] < 0) {
            running = false;
        } else if (x[0] > SCREEN_WIDTH - UNIT_SIZE) {
            running = false;
        } else if (y[0] < 0) {
            running = false;
        } else if (y[0] > SCREEN_HEIGHT - UNIT_SIZE) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void score(Graphics g) {
        g.setColor(new Color(00, 64, 00));
        g.setFont(new Font("Arial", Font.BOLD, 35));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score " + fruitsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score " + fruitsEaten))/2, g.getFont().getSize());
    }

    public void gameOver(Graphics g) {
        //GameOver Screen
        g.setColor(new Color(188, 99, 79));
        g.setFont(new Font("Arial", Font.BOLD, 70));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GameOver", (SCREEN_WIDTH - metrics.stringWidth("GameOver"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFruit();
            checkCollisions();
        }
        repaint();
    }

    public class GameKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_W:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_A:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_S:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_D:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
            }

        }
    }
}
