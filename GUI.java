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
    Graph board;
    //Region regions;

    Node[] nodeList;


    public GUI(){

    this.board = new Graph();
    this.nodeList = board.getNodes();

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

        g2.setPaint(Color.WHITE);

        /*for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){

                g2.setPaint(board.getNodeColor(9*i+j));
                g2.fillOval((i*50) + 700 -(j*50), (i*50) + (j*50) , 50, 50);


            }
        }*/

        for(int i=0; i<nodeList.length; i++){
            g2.setPaint(board.getNodeColor(i));

            if(i<=44) {
                g2.fillOval(board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i)*30, board.getNodeXCoords(i) * 40 + 100, 40, 40);
                g2.setPaint(Color.ORANGE);
                g2.drawString(board.getNodeLabel(i),15 + board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i)*30,20 + board.getNodeXCoords(i) * 40 + 100);
            }
            else {
                g2.fillOval(board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30, board.getNodeXCoords(i) * 40 + 100, 40, 40);
                g2.setPaint(Color.GREEN);
                g2.drawString(board.getNodeLabel(i), 15 + board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30, 20 + board.getNodeXCoords(i) * 40 + 100);
            }
        }




    }










}
