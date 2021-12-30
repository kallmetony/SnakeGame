import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(){

        this.add(new GamePanel()); //GamePanel panel = new GamePanel(); Adds new gamePanel
        this.setTitle("Snake game - Alpha");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
