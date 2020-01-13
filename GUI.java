import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI extends JComponent {

    //Jel
    private int diameter = 30;
    private double interval = diameter*2.0/Math.sqrt(3);
    private double offset = diameter/2.0;
    private double xOffset = 700;
    private double yOffset = 100;

    
//    double[] weights = {0.5, 10.0, 1.0};
    AIHeuristics aiPlayer = new AIHeuristics(0.5, 10.0, 1.0, Color.BLUE);
    AIHeuristics aiPlayer2 = new AIHeuristics(0.5, 10.0, 1.0, Color.RED);

    private ArrayList<Double> miniMaxScoresVector = new ArrayList<>();
    private ArrayList<double[]> featureScoresMatrix = new ArrayList<>();


    Graph board;
    Node[] nodeList;
    int selectedNode, previousSelectedNode = -1;
    public boolean firstMove = false;
    long start = System.nanoTime();
    int currentPlayer = 1;
    int turnsCount = 1;

    boolean player1 = true;

    public GUI() {
        this.board = new Graph();
        this.nodeList = board.getNodes();

        MousePressListener var1 = new MousePressListener();
        this.addMouseListener(var1);
    }


    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        if (timeElapsed % 1000000000 == 0)
            repaint();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        GradientPaint gp1 = new GradientPaint(50, 1, Color.blue, 20, 20, Color.lightGray, true);
        GradientPaint gp2 = new GradientPaint(100, 100, Color.green, 5, 5, Color.lightGray, true);
        GradientPaint gp3 = new GradientPaint(45, 45, Color.yellow, 70, 70, Color.orange, true);
        GradientPaint gp4 = new GradientPaint(5, 5, Color.darkGray, 20, 20, Color.red, true);


        if (player1) {
            g2.setPaint(Color.BLUE);
            g2.drawString("It is Player's Blue turn", 1000, 100);
        } else {
            g2.setPaint(Color.RED);
            g2.drawString("It is Player's Red turn", 1000, 100);
        }

        double yTop = board.getNodeXCoords(0) * Math.sqrt(3)/2.0 * interval + yOffset - offset;
        double yRef = board.getNodeXCoords(80) * Math.sqrt(3)/2.0 * interval + yOffset + diameter + offset;
        double diameterCircle = yRef - yTop;
        double xRef = board.getNodeYCoords(0) * interval + xOffset - board.getNodeXCoords(0) * interval/2.0 + diameter/2.0;
        double xLeft = xRef - diameterCircle/2.0;

        Ellipse2D.Double bigCircle = new Ellipse2D.Double(xLeft, yTop, diameterCircle, diameterCircle);
        g2.setPaint(Color.pink);
        g2.fill(bigCircle);
        g2.setPaint(new Color(255,102,102));
        g2.fill(triangle(board.getSecNode(3),0));
        g2.setPaint(new Color(51,204,255));
        g2.fill(triangle(board.getSecNode(75),1));

        g2.setPaint((Color.BLACK));
        g2.drawString("Turns count: " + turnsCount, 1000, 120);

        g2.setPaint((Color.BLACK));
        g2.drawString("Time elapsed: " + timeElapsed / 1000000000 + " seconds", 1000, 140);


        g2.setPaint(Color.WHITE);

        for (int i = 0; i < nodeList.length; i++) {
            g2.setPaint(board.getNodeColor(i));
            Ellipse2D.Double circle = new Ellipse2D.Double(board.getNodeYCoords(i) * interval + xOffset - board.getNodeXCoords(i) * interval/2.0, board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + yOffset, diameter, diameter);
            g2.fill(circle);
            if (!board.getNodeColor(i).equals(Color.WHITE)) {
                g2.setPaint(Color.WHITE);
                g2.draw(new Ellipse2D.Double(board.getNodeYCoords(i) * interval + xOffset - board.getNodeXCoords(i) * interval/2.0, board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + yOffset, diameter, diameter));
            }
//            g2.setPaint(Color.ORANGE);
//            g2.drawString(board.getNodeLabel(i), board.getNodeYCoords(i) * interval + 715 - board.getNodeXCoords(i) * interval/2, (int) (board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + 120));
        }

    }

    private Path2D triangle(Node node, int a) {
        double startX = node.getY() * interval + xOffset - node.getX() * interval/2.0 + diameter/2.0*(1-1/Math.tan(30/180.0*Math.PI));
        double startY = node.getX()  * Math.sqrt(3)/2.0 * interval + yOffset + diameter;
        double deltaX = 2*interval/2.0 + diameter/2.0*1/Math.tan(30/180.0*Math.PI);
        double deltaY = 3.5*diameter;
        double[] xUp = {startX,startX + deltaX, startX + 2*deltaX};
        double[] yUp = {startY,startY-deltaY,startY};
        double[] xDown = {startX, startX + 2*deltaX,startX + deltaX};
        double[] yDown = {startY-diameter, startY-diameter,startY-diameter+deltaY};

        Path2D path = new Path2D.Double();

        if (a == 0) { //a == 0 for the upward pointing triangles
            path.moveTo(xUp[0], yUp[0]);
            for(int i = 1; i < xUp.length; ++i) {
                path.lineTo(xUp[i], yUp[i]);
            }
            path.closePath();
        }
        else if (a == 1) {
            path.moveTo(xDown[0], yDown[0]);
            for(int i = 1; i < xDown.length; ++i) {
                path.lineTo(xDown[i], yDown[i]);
            }
            path.closePath();
        }
        return path;
    }


    public boolean blueGoalCheck(Graph graph) {
        for (int i = 80; i >= 75; i--) {
            if (!graph.getNodeColor(i).equals(Color.BLUE))
                return false;
        }
        return true;
    }

    public boolean redGoalCheck(Graph graph) {
        for (int i = 0; i <= 5; i++) {
            if (!graph.getNodeColor(i).equals(Color.RED))
                return false;
        }
        return true;
    }

    public boolean isWinningCondition() {
        if (player1) {       //checking for blue
            return blueGoalCheck(board);
        }

        else { //checking for red
            return redGoalCheck(board);
        }

    }


    public void clickedForNode(int var1, int var2) {
        for(int i=0; i<81; i++){
            if (var1 >= board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2
                    && var1 <= board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2 + diameter
                    && var2 >= board.getNodeXCoords(i) * (int)(Math.sqrt(3)/2.0 * interval) + 100
                    && var2 <= board.getNodeXCoords(i) * (int)(Math.sqrt(3)/2.0 * interval) + 100 + diameter) {
                setMove(i);
            }
        }

        //System.out.println("PreviousSelectedNode: " + board.getNodeLabel(this.previousSelectedNode) +
        //        " SelectNode = " + board.getNodeLabel(this.selectedNode) + " is FirstMove = " + this.firstMove);
    }

    public void setAIMove(Node[] nodes) {

        Color color = nodes[0].getColor();
        nodes[0].setColor(Color.WHITE);
        nodes[1].setColor(color);

        if (isWinningCondition()) { // /!\
            if (player1) {
                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                        "PLAYER 1 HAS WON", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                        "PLAYER 2 HAS WON", JOptionPane.WARNING_MESSAGE);
            }
        }
        if(player1){player1=false;}
        else{player1 = true;}
        repaint();
    }

    public void setMove(int chosenNode) {
        highlight(board.getSecNode(chosenNode));
        this.previousSelectedNode = this.selectedNode;
        this.selectedNode = chosenNode;

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

        highlight(board.getSecNode(chosenNode));

        if (previousSelectedNode == selectedNode) {
            removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
        }

        if (((nodeList[previousSelectedNode].getColor().equals(Color.BLUE) && player1)
//                || (nodeList[previousSelectedNode].getColor().equals(Color.RED) && !player1)

        )) {
            this.firstMove = false;

            if (selectedNode != previousSelectedNode && !nodeList[previousSelectedNode].getColor().equals(Color.YELLOW)
                    && nodeList[selectedNode].getColor().equals(Color.YELLOW)
            ) {

                nodeList[selectedNode].setColor(nodeList[previousSelectedNode].getColor());
                removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
                nodeList[previousSelectedNode].setColor(Color.WHITE);
                if (isWinningCondition()) {
                    if (player1) {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                                "PLAYER 1 HAS WON", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                                "PLAYER 2 HAS WON", JOptionPane.WARNING_MESSAGE);
                    }
                }

                if(player1){player1=false;}
                else{player1 = true;}

                turnsCount++;
            }
            else{this.firstMove=true;}
        }
        else{this.firstMove=true;}

        repaint();

    }

    public void highlight(Node n) {
        if (player1 && n.getColor() == Color.BLUE) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        }

    }

    public void removehighlight(Node n, Node m) {
        ArrayList<Node> tr = board.popularChoice(n);
        for (int i = 0; i < tr.size(); i++) {
            if (tr.get(i) == m) {

            } else {
                tr.get(i).setColor(Color.WHITE);
            }
        }

        for (int i = 0; i < tr.size(); i++) {

            tr.get(i).setColor(Color.WHITE);

        }
    }

    public void removeOneHighlight(Node n){//method that removes the highlight when choosing another node
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
            int move = 0;
            int var2 = var1.getX();
            int var3 = var1.getY();
            GUI.this.clickedForNode(var2, var3);
            if (!player1) {
                setAIMove(aiPlayer2.performMove(board));
                player1 = true;
            }

        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }



}
