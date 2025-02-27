import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class simulationPanel extends JPanel {
    private particleEngine engine;
    private Timer timer;

    public simulationPanel(particleEngine engine) {
        this.engine = engine;
        this.setBackground(Color.BLACK);


        timer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                engine.updateParticles();
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        engine.paint(g2d);
    }
}
