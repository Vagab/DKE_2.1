import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class GUI extends JComponent {

    private Image backgroundImage;
    Graphics g;
    Graph board;
    //Region regions;

    Node[] nodeList;
    int selectedNode, previousSelectedNode = -1;
    boolean firstMove = false;

    boolean player1 = true;


    public GUI(){

    this.board = new Graph();
    this.nodeList = board.getNodes();

        GUI.MousePressListener var1 = new GUI.MousePressListener();
        this.addMouseListener(var1);

    }




    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        try {   //sets the background picture
            backgroundImage = ImageIO.read(new File("C:\\Users\\Oscar\\Documents\\Oscar Universidad\\YEAR 2\\BLOCK 1\\PROJECT\\DKE_2.1\\pic1.jpg"));
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

        if(this.player1){
            g2.setPaint(Color.BLUE);
            g2.drawString("It is now for player 1", 1000, 100);
        }
        else{
            g2.setPaint(Color.RED);
            g2.drawString("It is now for Player 2",1000, 100);
        }

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


    public void clickedForNode(int var1, int var2){

        for(int i=0; i<81; i++){

            if(i<=44){
                if(var1>=board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i)*30
                        && var1<=board.getNodeYCoords(i) * 60 + 700 - board.getNodeXCoords(i)*30 + 40
                && var2>=board.getNodeXCoords(i) * 40 + 100
                        && var2<= board.getNodeXCoords(i) * 40 + 100 +40 ){
                    //higlight(board.getSecNode(i));
                    setMove(i);
                }
            }
            else{
                if(var1>=board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30
                && var1<= board.getNodeYCoords(i) * 60 + 700 - (16 - board.getNodeXCoords(i)) * 30 + 40
                && var2>= board.getNodeXCoords(i) * 40 + 100
                && var2<= board.getNodeXCoords(i) * 40 + 100 + 40){

                    setMove(i);
                }
            }


        }
        System.out.println("PreviousSelectedNode: " + board.getNodeLabel(this.previousSelectedNode) +
                " SelectNode = " + board.getNodeLabel(this.selectedNode) + " is FirstMove = " + this.firstMove);
    }

    public void setMove(int chosenNode){

        this.previousSelectedNode = this.selectedNode;
        this.selectedNode = chosenNode;

        if(previousSelectedNode==selectedNode){
            removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
        }
        ArrayList<Node> stopBugs = board.popularChoice(nodeList[previousSelectedNode]);
        boolean allgood = false;
        for(int i=0; i<stopBugs.size(); i++){
            if(stopBugs.get(i)==nodeList[selectedNode]){
                allgood = true;
            }
        }
        if(allgood==false){
            removeOneHighlight(nodeList[previousSelectedNode]);
        }
        higlight(board.getSecNode(chosenNode));
        if(this.firstMove
                && ((nodeList[previousSelectedNode].getColor().equals(Color.BLUE) && player1)
                || (nodeList[previousSelectedNode].getColor().equals(Color.RED) && !player1)
                )
        ){
            this.firstMove=false;



            if(selectedNode != previousSelectedNode && !nodeList[previousSelectedNode].getColor().equals(Color.YELLOW)
            && nodeList[selectedNode].getColor().equals(Color.YELLOW)
            ) {

                nodeList[selectedNode].setColor(nodeList[previousSelectedNode].getColor());
                removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
                nodeList[previousSelectedNode].setColor(Color.WHITE);
                if(player1){player1=false;}
                else{player1 = true;}
            }

            else{this.firstMove=true;}
        }
        else{this.firstMove=true;}

        repaint();

    }

    public void higlight(Node n){ //This method higlights the possible nodes where the player can go to
        if(player1 && n.getColor()==Color.BLUE){
            ArrayList<Node> tr = board.popularChoice(n);
            for(int i=0; i<tr.size(); i++){
                tr.get(i).setColor(Color.YELLOW);
            }
        }
        else if(!player1 && n.getColor()==Color.RED){
            ArrayList<Node> tr = board.popularChoice(n);
            for(int i=0; i<tr.size(); i++){
                tr.get(i).setColor(Color.YELLOW);
            }
        }
    }

    public void removehighlight(Node n, Node m){ //Method that allows to remove the higlight if you double click
        ArrayList<Node> tr = board.popularChoice(n);
        for(int i=0; i<tr.size(); i++){
            if(tr.get(i)==m){

            }
            else{
                tr.get(i).setColor(Color.WHITE);
            }
        }
        ArrayList<Node> te = board.popularChoice(m);
        for(int i=0; i<tr.size(); i++){

            tr.get(i).setColor(Color.WHITE);

        }
    }
    public void removeOneHighlight(Node n){//method that removes the higlight when choosing another node
        ArrayList<Node> tr = board.popularChoice(n);
        for(int i=0; i<tr.size(); i++){
            tr.get(i).setColor(Color.WHITE);
        }
    }




    class MousePressListener implements MouseListener {
        MousePressListener() {
        }

        public void mousePressed(MouseEvent var1) {
        }

        public void mouseReleased(MouseEvent var1) {
        }

        public void mouseClicked(MouseEvent var1) {
            int var2 = var1.getX();
            int var3 = var1.getY();
            GUI.this.clickedForNode(var2, var3);
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }





}
