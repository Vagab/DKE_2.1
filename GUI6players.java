import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;


public class GUI6players extends JComponent {

    //Jel
    private int diameter = 30;
    private double interval = diameter*2.0/Math.sqrt(3);
    private double offset = diameter/2.0;
    private double xOffset = 700;
    private double yOffset = 100;

    private AIMultiPlayer aiPlayer1;
    private AIMultiPlayer aiPlayer2;
    private AIMultiPlayer aiPlayer3;
    private AIMultiPlayer aiPlayer4;
    private AIMultiPlayer aiPlayer5;
    private AIMultiPlayer aiPlayer6;

    private GraphOscar board;
    private Node[] nodeList;
    private int selectedNode;
    private static long start = System.nanoTime();
    private int currentPlayer = 1;
    private int turnsCount = 1;
    private int numberOfPlayers;


    GUI6players(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.board = new GraphOscar(numberOfPlayers);

        double middle = 0.5;
        double radius = 1;
        double goal = 10;
        if (numberOfPlayers == 6) {
            aiPlayer1 = new AIHeuristics6Players(middle, goal, radius, Color.BLUE);
            aiPlayer2 = new AIHeuristics6Players(middle, goal, radius, Color.GRAY);
            aiPlayer3 = new AIHeuristics6Players(middle, goal, radius, Color.ORANGE);
            aiPlayer4 = new AIHeuristics6Players(middle, goal, radius, Color.RED);
            aiPlayer5 = new AIHeuristics6Players(middle, goal, radius, Color.BLACK);
            aiPlayer6 = new AIHeuristics6Players(middle, goal, radius, Color.GREEN);
        }
        else if (numberOfPlayers == 4) {
            aiPlayer1 = new AIHeuristics4Players(middle, goal, radius, Color.GRAY);
            aiPlayer2 = new AIHeuristics4Players(middle, goal, radius, Color.ORANGE);
            aiPlayer3 = new AIHeuristics4Players(middle, goal, radius, Color.BLACK);
            aiPlayer4 = new AIHeuristics4Players(middle, goal, radius, Color.GREEN);
        }
        else if (numberOfPlayers == 3) {
            aiPlayer1 = new AIHeuristics3Players(middle, goal, radius, Color.BLUE);
            aiPlayer2 = new AIHeuristics3Players(middle, goal, radius, Color.ORANGE);
            aiPlayer3 = new AIHeuristics3Players(middle, goal, radius, Color.BLACK);
        }

        this.nodeList = board.getNodes();

        if(numberOfPlayers == 4){
            currentPlayer=2;
        }

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

        if (currentPlayer == 1) {
            g2.setPaint(Color.BLUE);
            g2.drawString("It is Player " + currentPlayer + "'s turn", 1000, 100);
        } else if (currentPlayer == 2) {
            g2.setPaint(Color.GRAY);
            g2.drawString("It is Player " + currentPlayer + "'s turn", 1000, 100);
        } else if (currentPlayer == 3){
            g2.setPaint(Color.ORANGE);
            g2.drawString("It is Player " + currentPlayer + "'s turn", 1000,100);
        } else if(currentPlayer == 4) {
            g2.setPaint(Color.RED);
            g2.drawString("It is Player " + currentPlayer + "'s turn", 1000,100);
        } else if(currentPlayer == 5) {
            g2.setPaint(Color.BLACK);
            g2.drawString("It is Player " + currentPlayer + "'s turn", 1000, 100);
        } else {
            g2.setPaint(Color.GREEN);
            g2.drawString("It is Player " + currentPlayer + "'s turn",1000,100);
        }

        double yTop = board.getNodeXCoords(0) * Math.sqrt(3)/2.0 * interval + yOffset - offset;
        double yRef = board.getNodeXCoords(120) * Math.sqrt(3)/2.0 * interval + yOffset + diameter + offset;
        double diameterCircle = yRef - yTop;
        double xRef = board.getNodeYCoords(0) * interval + xOffset - board.getNodeXCoords(0) * interval/2.0 + diameter/2.0;
        double xLeft = xRef - diameterCircle/2.0;

        Ellipse2D.Double bigCircle = new Ellipse2D.Double(xLeft, yTop, diameterCircle, diameterCircle);
        g2.setPaint(Color.pink);
        g2.fill(bigCircle);
        g2.setPaint(new Color(255,102,102));
        g2.fill(triangle(board.getSecNode(6),0));
        g2.setPaint(new Color(204,204,204));
        g2.fill(triangle(board.getSecNode(98),0));
        g2.setPaint(new Color(102,255,102));
        g2.fill(triangle(board.getSecNode(107),0));
        g2.setPaint(new Color(255,153,0));
        g2.fill(triangle(board.getSecNode(10),1));
        g2.setPaint(new Color(51,51,51));
        g2.fill(triangle(board.getSecNode(19),1));
        g2.setPaint(new Color(51,204,255));
        g2.fill(triangle(board.getSecNode(111),1));

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

    private Path2D triangle(Node node,int a) {
        double startX = node.getY() * interval + xOffset - node.getX() * interval/2.0 + diameter/2.0*(1-1/Math.tan(30/180.0*Math.PI));
        double startY = node.getX()  * Math.sqrt(3)/2.0 * interval + yOffset + diameter;
        double deltaX = 3*interval/2.0 + diameter/2.0*1/Math.tan(30/180.0*Math.PI);
        double deltaY = 4.5*diameter;
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

    private boolean blueGoalCheck(GraphOscar graph) {
        for (int i = 120; i > 110; i--) {
            if (!graph.getNodeColor(i).equals(Color.BLUE))
                return false;
        }
        return true;
    }

    private boolean redGoalCheck(GraphOscar graph) {
        for (int i = 0; i <= 9; i++) {
            if (!graph.getNodeColor(i).equals(Color.RED))
                return false;
        }
        return true;
    }

    private boolean blackGoalCheck(GraphOscar graph) {
        for (int i = 0; i < 4; i++) {
            if (!graph.getNodeColor(19 + i).equals(Color.BLACK))
                return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!graph.getNodeColor(32 + i).equals(Color.BLACK))
                return false;
        }
        for (int i = 0; i < 2; i++) {
            if (!graph.getNodeColor(44 + i).equals(Color.BLACK))
                return false;
        }
        if (!graph.getNodeColor(55).equals(Color.BLACK)) {
            return false;
        }
        return true;
    }

    private boolean greenGoalCheck(GraphOscar graph) {
        for (int i = 0; i < 4; i++) {
            if (!graph.getNodeColor(107 + i).equals(Color.GREEN))
                return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!graph.getNodeColor(95 + i).equals(Color.GREEN))
                return false;
        }
        for (int i = 0; i < 2; i++) {
            if (!graph.getNodeColor(84 + i).equals(Color.GREEN))
                return false;
        }
        if (!graph.getNodeColor(74).equals(Color.GREEN))
            return false;

        return true;
    }

    private boolean grayGoalCheck(GraphOscar graph) {
        for (int i = 0; i < 4; i++) {
            if (!graph.getNodeColor(98 + i).equals(Color.GRAY))
                return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!graph.getNodeColor(86 + i).equals(Color.GRAY))
                return false;
        }
        for (int i = 0; i < 2; i++) {
            if (!graph.getNodeColor(75 + i).equals(Color.GRAY))
                return false;
        }
        if (!graph.getNodeColor(65).equals(Color.GRAY))
            return false;

        return true;
    }

    private boolean orangeGoalCheck(GraphOscar graph) {
        for (int i = 0; i < 4; i++) {
            if (!graph.getNodeColor(10 + i).equals(Color.ORANGE))
                return false;
        }
        for (int i = 0; i < 3; i++) {
            if (!graph.getNodeColor(23 + i).equals(Color.ORANGE))
                return false;
        }
        for (int i = 0; i < 2; i++) {
            if (!graph.getNodeColor(35 + i).equals(Color.ORANGE))
                return false;
        }
        if (!graph.getNodeColor(46).equals(Color.ORANGE))
            return false;

        return true;
    }

    public boolean isWinningCondition(int currentPlayer) {
        /*
                 Winning Condition


                  Triangle 1(RED)

        Triangle 6(ORANGE)     Triangle 2(BLACK)

        Triangle 5(GRAY)     Triangle 3(GREEN)

                 Triangle 4(BLUE)
         */

        //for triangles 1 and 4 it is easy to check if the opponent succeeded in moving all his pieces in there


        if (currentPlayer == 4) {       //checking for red
            return redGoalCheck(board);
        }
        else if (currentPlayer == 5) {    //checking for black
            return blackGoalCheck(board);
        }
        else if (currentPlayer == 6) {   //checking for green
            return greenGoalCheck(board);
            }
        else if (currentPlayer == 1) { //checking for blue
            return blueGoalCheck(board);
        }
        else if (currentPlayer == 2) {   // checking for gray
            return grayGoalCheck(board);
        }
        else if (currentPlayer == 3) {    // checking for orange
            return orangeGoalCheck(board);
        }
        return false;
    }

    public void clickedForNode(int var1, int var2) {

        for (int i = 0; i <= 120; i++) {
                if (var1 >= board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2
                        && var1 <= board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2 + diameter
                        && var2 >= board.getNodeXCoords(i) * (int)(Math.sqrt(3)/2.0 * interval) + 100
                        && var2 <= board.getNodeXCoords(i) * (int)(Math.sqrt(3)/2.0 * interval) + 100 + diameter) {

                    setMove(i);
                }
        }

        /*System.out.println("PreviousSelectedNode: " + board.getNodeLabel(this.previousSelectedNode) +
                " SelectNode = " + board.getNodeLabel(this.selectedNode) + " is FirstMove = " + this.firstMove);*/
    }

    public void setAIMove(Node[] nodes) {

        Color color = nodes[0].getColor();
        nodes[0].setColor(Color.WHITE);
        nodes[1].setColor(color);

//        if (isWinningCondition(currentPlayer)) {
//            if (currentPlayer == 1) {
//                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
//                        "PLAYER 1 HAS WON", JOptionPane.WARNING_MESSAGE);
//            } else if (currentPlayer == 2) {
//                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
//                        "PLAYER 2 HAS WON", JOptionPane.WARNING_MESSAGE);
//            } else if (currentPlayer == 3) {
//                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
//                        "PLAYER 3 HAS WON", JOptionPane.WARNING_MESSAGE);
//            } else if (currentPlayer == 4) {
//                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
//                        "PLAYER 4 HAS WON", JOptionPane.WARNING_MESSAGE);
//            } else if (currentPlayer == 5) {
//                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
//                        "PLAYER 5 HAS WON", JOptionPane.WARNING_MESSAGE);
//            } else if (currentPlayer == 6) {
//                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
//                        "PLAYER 6 HAS WON", JOptionPane.WARNING_MESSAGE);
//            }
//        }
        switch(numberOfPlayers){
//            case 2:
//                if(currentPlayer==4){
//                    currentPlayer=1;
//                    turnsCount++;
//                }
//                else{
//                    currentPlayer=currentPlayer+3;
//                }
//                break;
            case 3:
                if(currentPlayer==5){
                    currentPlayer=1;
                    turnsCount++;
                }
                else{
                    currentPlayer=currentPlayer+2;
                }
                break;
            case 4:
                if(currentPlayer==3){
                    currentPlayer=5;
                    turnsCount++;
                }
                else if(currentPlayer==6){
                    currentPlayer=2;
                }
                else{
                    currentPlayer++;
                }
                break;
            case 6:
                if (currentPlayer == 6) {
                    currentPlayer = 1;
                    turnsCount++;
                } else {
                    currentPlayer++;
                }
                break;
        }
//        if (redGoalCheck(board) || blueGoalCheck(board) || grayGoalCheck(board) || blackGoalCheck(board) || greenGoalCheck(board) ||
//        orangeGoalCheck(board)){
            repaint();
//        } // remove after test
    }

    private void setMove(int chosenNode) {
        highlight(board.getSecNode(chosenNode));
        int previousSelectedNode = this.selectedNode;
        this.selectedNode = chosenNode;

        ArrayList<Node> stopBugs = board.popularChoice(nodeList[previousSelectedNode]);
        boolean allgood = false;
        for (Node stopBug : stopBugs) {
            if (stopBug == nodeList[selectedNode]) {
                allgood = true;
                break;
            }
        }
        if(!allgood){
            removeOneHighlight(nodeList[previousSelectedNode]);
        }

        highlight(board.getSecNode(chosenNode));

        if (previousSelectedNode == selectedNode) {
            removeHighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
        }

        if (((nodeList[previousSelectedNode].getColor().equals(Color.BLUE) && currentPlayer == 1)
                || (nodeList[previousSelectedNode].getColor().equals(Color.GRAY) && currentPlayer == 2)
                || (nodeList[previousSelectedNode].getColor().equals(Color.ORANGE) && currentPlayer == 3)
                || (nodeList[previousSelectedNode].getColor().equals(Color.RED) && currentPlayer == 4)
                || (nodeList[previousSelectedNode].getColor().equals(Color.BLACK) && currentPlayer == 5)
                || (nodeList[previousSelectedNode].getColor().equals(Color.GREEN) && currentPlayer == 6)
        ))

        {
            boolean firstMove = false;
            if (selectedNode != previousSelectedNode && !nodeList[previousSelectedNode].getColor().equals(Color.YELLOW)
                    && nodeList[selectedNode].getColor().equals(Color.YELLOW)
            ) {

                nodeList[selectedNode].setColor(nodeList[previousSelectedNode].getColor());
                removeHighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
                nodeList[previousSelectedNode].setColor(Color.WHITE);
                if (isWinningCondition(currentPlayer)) {
                    if (currentPlayer == 1) {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!", 
                                "PLAYER 1 HAS WON", JOptionPane.WARNING_MESSAGE);
                    } else if (currentPlayer == 2) {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                                "PLAYER 2 HAS WON", JOptionPane.WARNING_MESSAGE);
                    } else if (currentPlayer == 3) {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                                "PLAYER 3 HAS WON", JOptionPane.WARNING_MESSAGE);
                    } else if (currentPlayer == 4) {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                                "PLAYER 4 HAS WON", JOptionPane.WARNING_MESSAGE);
                    } else if (currentPlayer == 5) {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                                "PLAYER 5 HAS WON", JOptionPane.WARNING_MESSAGE);
                    } else if (currentPlayer == 6) {
                        JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                                "PLAYER 6 HAS WON", JOptionPane.WARNING_MESSAGE);
                    }
                }

                switch(numberOfPlayers){
//                    case 2:
//                        if(currentPlayer==4){
//                            currentPlayer=1;
//                            turnsCount++;
//                        }
//                        else{
//                            currentPlayer=currentPlayer+3;
//                        }
//                        break;
                    case 3:
                        if(currentPlayer==5){
                            currentPlayer=1;
                            turnsCount++;
                        }
                        else{
                            currentPlayer=currentPlayer+2;
                        }
                        break;
                    case 4:
                        if(currentPlayer==3){
                            currentPlayer=5;
                            turnsCount++;
                        }
                        else if(currentPlayer==6){
                            currentPlayer=2;
                        }
                        else{
                            currentPlayer++;
                        }
                        break;
                    case 6:
                        if (currentPlayer == 6) {
                            currentPlayer = 1;
                            turnsCount++;
                        } else {
                            currentPlayer++;
                        }
                        break;
                }
            }
        }
        repaint();
    }

    private void highlight(Node n) {
        if (currentPlayer == 1 && n.getColor() == Color.BLUE) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (Node node : tr) {
                node.setColor(Color.YELLOW);
            }
        } else if (currentPlayer == 2 && n.getColor() == Color.GRAY) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (Node node : tr) {
                node.setColor(Color.YELLOW);
            }
        }
        else if (currentPlayer == 3 && n.getColor() == Color.ORANGE) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (Node node : tr) {
                node.setColor(Color.YELLOW);
            }
        }

        else if (currentPlayer == 4 && n.getColor() == Color.RED) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (Node node : tr) {
                node.setColor(Color.YELLOW);
            }
        }
        else if (currentPlayer == 5 && n.getColor() == Color.BLACK) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (Node node : tr) {
                node.setColor(Color.YELLOW);
            }
        }

        else if (currentPlayer == 6 && n.getColor() == Color.GREEN) {
            ArrayList<Node> tr = board.popularChoice(n);
            for (Node node : tr) {
                node.setColor(Color.YELLOW);
            }
        }

    }

    private void removeHighlight(Node n, Node m) {
        ArrayList<Node> tr = board.popularChoice(n);
        for (Node node : tr) {
            if (node != m) {
                node.setColor(Color.WHITE);
            }
        }

        for (Node node : tr) {
            node.setColor(Color.WHITE);
        }
    }

    private void removeOneHighlight(Node n){//method that removes the highlight when choosing another node
        ArrayList<Node> tr = board.popularChoice(n);
        for (Node node : tr) {
            node.setColor(Color.WHITE);
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
            GUI6players.this.clickedForNode(var2, var3);
            if (numberOfPlayers == 3) {
                if (currentPlayer != 1) {
                    setAIMove(aiPlayer2.performMove(board));
                    setAIMove(aiPlayer3.performMove(board));
                    currentPlayer = 1;
                }
            }
            else if (numberOfPlayers == 4) {
                if (currentPlayer != 2) {
                    setAIMove(aiPlayer2.performMove(board));
                    setAIMove(aiPlayer3.performMove(board));
                    setAIMove(aiPlayer4.performMove(board));
                    currentPlayer = 2;
                }
            }
            else if (numberOfPlayers == 6) {
                if (currentPlayer != 1) {
                    setAIMove(aiPlayer2.performMove(board));
                    setAIMove(aiPlayer3.performMove(board));
                    setAIMove(aiPlayer4.performMove(board));
                    setAIMove(aiPlayer5.performMove(board));
                    setAIMove(aiPlayer6.performMove(board));
                    currentPlayer = 1;
                }
            }
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }


}
