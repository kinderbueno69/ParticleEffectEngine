import java.awt.*;

public class particle {

    // Particle properties
    public double x, y, startingX, startingY, startingSize, startingVX, startingVY;
    public double vx, vy, size, lifetime, currentLifetime, age, alpha;
    public Color color;
    public double tempVy = 0;
    public int maxX, maxY;

    private final double amplitude = 1.5; // velocity range
    private final double frequency = 10.0; // Oscillations per second

    // Constructor to initialize particle
    public particle(double x, double y, int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.x = x;
        this.y = y;

        this.currentLifetime = 0;
        this.alpha = 255;
        this.startingX = x;
        this.startingY = y;


        double angle = Math.random() * 2 * Math.PI;
        double speed = 2 + Math.random() * 8;
        double time = System.currentTimeMillis() / 1000.0; // Time in seconds
        double phase = Math.random() * 2 * Math.PI; // Random phase between 0 and 2π

        vx = amplitude * Math.sin(2 * Math.PI * frequency * time + phase);
        vy = 0 - Math.abs(Math.sin(angle) * speed);
        this.startingVX = vx;
        this.startingVY = vy;

        if(vy > -1){
            vy = vy - 1.5;
        }
        double xOffset = (Math.random() - 0.5) * 70; // Spread range of 10 pixels
        double yOffset = (Math.random() - 0.5) * 20;

        this.x = x+xOffset;
        this.y = y+yOffset;

        this.color = Color.WHITE;

        if (Math.random() < 1.0 / 500) {  // 1 in 200 chance
            this.vx = vx*8;
            this.vy = vy*4;
            this.size = 15;
            this.lifetime = 60;
        }else {
            this.size = 30;
            this.lifetime = 187.5;
        }

        this.startingSize = size;
    }



    // Update particle movement
    public void movement() {
        if (tempVy != 0) {
            vy = tempVy;
        }

        age = Math.max(0, Math.min(1, currentLifetime / lifetime));
        vx *= (1 - 0.01 * age);
        vy *= (1 - 0.01 * age);

        if (y > 1) {
            y += vy;
        }

        if (x > 1 && x < maxX) {
            x += vx;
        }

        currentLifetime++;

        // Adjust size based on age
        size = (age < 0.2) ? size + 0.1 : size - 0.3;
        alpha = 255 * (1 - Math.pow(age, 2));
        color = getColorBasedOnLifetime(currentLifetime, lifetime, (int) alpha);
    }

    // Check if particle is alive
    public boolean isAlive() {
        return currentLifetime < lifetime;
    }

    // Get particle color based on its lifetime
    private Color getColorBasedOnLifetime(double currentLifetime, double lifetime, int alpha) {
        age = Math.max(0, Math.min(1, currentLifetime / lifetime));  // Ensure age is between 0 and 1

        int r = 255;
        int g = (int) (255 * (1 - 2 * age));
        int b = (int) (255 * (1 - 10 * age));

        r = Math.max(0, Math.min(255, r));
        g = Math.max(0, Math.min(255, g));
        b = Math.max(0, Math.min(255, b));

        return new Color(r, g, b, alpha);
    }

    // Reset particle to its initial state
    public void reset() {
        currentLifetime = 0;
        x = startingX;
        y = startingY;
        vx = startingVX;
        vy = startingVY;
        alpha = 255;
        size = startingSize;
        tempVy = 0;
    }

    // Draw particle
    public void draw(Graphics2D g2d) {
        // Draw the glowing effect by drawing a larger circle with transparency around the particle
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(alpha * 0.5))); // Slight transparency for glow
        g2d.fillOval((int) x - (int)(size * 0.5), (int) y - (int)(size * 0.5), (int)(size * 1.5), (int)(size * 1.5)); // Larger size for glow

        // Draw the core particle with solid color
        g2d.setColor(color);
        g2d.fillOval((int) x - (int)(size / 2), (int) y - (int)(size / 2), (int) size, (int) size);
    }
}
