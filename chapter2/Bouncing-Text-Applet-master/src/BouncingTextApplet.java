import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class BouncingTextApplet extends Applet implements Runnable {

    private Thread thread;
    private int x = 0;
    private String name = "kaleab"; // Replace with your actual name
    private boolean running = false;

    @Override
    public void init() {
        setSize(400, 100);
        setBackground(Color.BLACK);
    }

    @Override
    public void start() {
        if (thread == null) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void stop() {
        running = false;
        thread = null;
    }

    @Override
    public void run() {
        while (running) {
            x += 5;

            if (x > getWidth()) {
                x = -getFontMetrics(getFont()).stringWidth(name); // Reset from left side
            }

            repaint();

            try {
                Thread.sleep(100); // Pause for 100 ms
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawString(name, x, 50);
    }
}


