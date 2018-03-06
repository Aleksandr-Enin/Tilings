import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static java.lang.Math.sqrt;

public class LozengePlot {

    public static void plot(int[][] lattice, String title) {
        JFrame frame = new JFrame(title);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        GraphicPanel panel = new GraphicPanel(lattice);
        Button saveButton = new Button("Save image");
        saveButton.addActionListener(e -> panel.saveImage(title));
        saveButton.setBounds(10,10, 100, 40);
        //panel.add(saveButton);
        frame.getContentPane().add(saveButton);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.pack();
        frame.setSize(1000, 1000);
    }

    public static void saveImage(int[][] lattice, String filename) {
        GraphicPanel panel = new GraphicPanel(lattice);
        panel.setBounds(0,0,1000,1000);
        panel.saveImage(filename);
    }
}

class GraphicPanel extends JPanel {
    int x, y, SIZE, XSIZE;
    int [][] lattice;

    public GraphicPanel(int[][] lattice) {
        this.lattice = lattice;
    }

    void saveImage(String filename) {
        try
        {
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            this.paint(graphics2D);
            ImageIO.write(image,"jpg", new File(filename + ".jpg"));
        }
        catch(Exception exception)
        {
            //code
        }
    }

    void drawLozenge1(int x, int y, Graphics g) {
        int lozengeXpoints[] = {x, x - (int)(SIZE*sqrt(3)/2), x - (int)(SIZE*(sqrt(3)/2)), x};
        int lozengeYpoints[] = {y, y + (int) (0.5*SIZE), y + (int) (1.5*SIZE), y+SIZE};
        g.setColor(Color.BLUE);
        g.fillPolygon(lozengeXpoints, lozengeYpoints,4);

        g.setColor(Color.BLACK);
        g.drawPolygon(lozengeXpoints, lozengeYpoints,4);
    }

    void drawLozenge2(int x, int y, Graphics g) {
        int lozengeYpoints[] = {y, y + (int) (0.5*SIZE), y + (int) (1.5*SIZE), y+SIZE};
        int lozengeXpoints[] = {x, x + (int)(SIZE*sqrt(3)/2), x + (int)(SIZE*(sqrt(3)/2)), x};

        g.setColor(Color.RED);
        g.fillPolygon(lozengeXpoints, lozengeYpoints,4);

        g.setColor(Color.BLACK);
        g.drawPolygon(lozengeXpoints, lozengeYpoints,4);
    }

    void drawLozenge3(int x, int y, Graphics g) {
        int lozengeXpoints[] = {x, x - (int)(SIZE*sqrt(3)/2), x, x + (int)(SIZE*(sqrt(3)/2))};
        int lozengeYpoints[] = {y, y + (int) (0.5*SIZE), y + SIZE, y + (int) (0.5*SIZE)};

        g.setColor(Color.YELLOW);
        g.fillPolygon(lozengeXpoints, lozengeYpoints,4);

        g.setColor(Color.BLACK);
        g.drawPolygon(lozengeXpoints, lozengeYpoints,4);
    }

    void drawColumn(int x, int y, int height, Graphics g) {
        for (int i = 1; i <= height; i++) {
            drawLozenge1(x + XSIZE, y - i * SIZE, g);
            drawLozenge2(x - XSIZE, y - i * SIZE, g);
        }
        drawLozenge3(x, y - height * SIZE - SIZE / 2, g);
    }

    void drawColumn(int x, int y, double height, Graphics g) {
        drawColumn(x,y, (int) height, g);
    }

    void initialize(Graphics g) {
        for (int i = 0; i < lattice.length; i++) {
            for (int j = 0; j < lattice[i].length; j++) {
                drawLozenge3(x - XSIZE*(i - j), y - SIZE/2 * (i+j), g);
                drawLozenge1(x - XSIZE*i, y - SIZE*lattice.length - SIZE*j + SIZE/2*(i), g);
                drawLozenge2(x + XSIZE*i, y - SIZE*lattice.length - SIZE*j + SIZE/2*(i), g);
            }
        }
    }

    public void paintComponent(Graphics g) {

        SIZE = (super.getWidth()/(2*lattice.length));
        SIZE = (SIZE % 2 == 0) ? SIZE : SIZE-1;

        XSIZE = (int)(SIZE*sqrt(3)/2);

        x = super.getWidth()/2;
        y = super.getHeight() - SIZE;

        super.paintComponent(g);
        //drawLozenge3(300,300, g);
        //drawLozenge3(300 - YSIZE, 300 - SIZE/2, g);
        /*for (int i =0; i < lattice.length; i++) {
            for (int j = 0; j < i; j++) {
                drawLozenge3(x + j*YSIZE, y - SIZE/2*i, g);
                drawLozenge3(x - j*YSIZE, y - SIZE/2*i, g);
            }
        }*/

        initialize(g);
        for (int i = lattice.length-1; i >= 0; i--) {
            for (int j = lattice[i].length-1; j >= 0; j--) {
                drawColumn(x - XSIZE * (i - j), y - SIZE / 2 * (i + j -1), lattice[i][j], g);
            }
        }
    }
}