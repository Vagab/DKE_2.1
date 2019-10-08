import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class GUI extends JComponent {

    private Image backgroundImage;
    Graphics g;
    //Region regions;


    public GUI(){

    }




    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        try {   //sets the background picture
            backgroundImage = ImageIO.read(new File("C:\\Users\\lucas\\Desktop\\DKE\\CCheckers\\src\\pic1.jpg"));
            g2.drawImage(backgroundImage, 0, 0, null);
        } catch(IOException e){
            throw new RuntimeException(e);
        }





        GradientPaint gp1 = new GradientPaint(50, 1, Color.blue, 20, 20, Color.lightGray, true);
        GradientPaint gp2 = new GradientPaint(100, 100, Color.green, 5, 5, Color.lightGray, true);
        GradientPaint gp3 = new GradientPaint(45, 45, Color.yellow, 70, 70, Color.orange, true);
        GradientPaint gp4 = new GradientPaint(5, 5, Color.darkGray, 20, 20, Color.red, true);

        //  g2.setPaint(Color.CYAN);
        //  g2.fillRect(0,0,2500,2500);





    }










}
