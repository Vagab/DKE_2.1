import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JComponent {

    //Jel
    int interval = 45;
    int diameter = 40;


    Graph board;
    ArrayList<Color> colorsOfPlayers = new ArrayList<Color>();
    Node[] nodeList;
    int selectedNode, previousSelectedNode = -1;
    public static boolean firstMove = false;
    static long start = System.nanoTime();
    int currentPlayer = 1;
    int turnsCount = 1;
    int nrsP;

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


        g2.setPaint((Color.BLACK));
        g2.drawString("Turns count: " + turnsCount, 1000, 120);

        g2.setPaint((Color.BLACK));
        g2.drawString("Time elapsed: " + timeElapsed / 1000000000 + " seconds", 1000, 140);


        g2.setPaint(Color.WHITE);

        for(int i=0; i<nodeList.length; i++){
            g2.setPaint(board.getNodeColor(i));
            g2.fillOval(board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2, (int) (board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + 100), diameter, diameter);
            g2.setPaint(Color.ORANGE);
            g2.drawString(board.getNodeLabel(i), board.getNodeYCoords(i) * interval + 715 - board.getNodeXCoords(i) * interval/2, (int) (board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + 120));
        }

    }

    public boolean isWinningCondition() {


        if (player1) {       //checking for blue
            for (int i = 80; i >= 75; i--) {
                if (!board.getNodeColor(i).equals(Color.BLUE))
                    return false;
            }
            return true;
        }

        else { //checking for red
            for (int i = 0; i <= 5; i++) {
                if (!board.getNodeColor(i).equals(Color.RED))
                    return false;
            }
            return true;
        }


    }

    public ArrayList<Node> getArmy(Color color) {
        ArrayList<Node> army = new ArrayList<>();
            for (Node node : nodeList) {
                if (node.getColor().equals(Color.RED)) {
                    army.add(node);
                }
            }
            return army;
    }


//    public void AI1() { //Testing distance to destination heuristic, works
//        if (!player1) {
//            Node destinationNode = board.getAIDestinationNode();
//            ArrayList<Node> AIArmy = getArmy(Color.RED);
//            Node maxAdvanceNode = null;
//            Node selectedNode = null;
//            int smallestDistanceToDestination = 100;
//            for (Node node : AIArmy) {
//                ArrayList<Node> tr = board.popularChoice(node);
//                for (Node move : tr) {
//                    if (!move.getLabel().equals("null")) {
//                        if (board.stepDistance(move, destinationNode) < smallestDistanceToDestination) {
//                            maxAdvanceNode = move;
//                            smallestDistanceToDestination = board.stepDistance(move, destinationNode);
//                            selectedNode = node;
//                        }
//                    }
//                }
//            }
////            setAIMove(selectedNode, maxAdvanceNode);
//        }
//    }


//    public void AI2() { //Testing max advance heuristic, works
//        if (!player1) {
//            Node destinationNode = board.getAIDestinationNode();
//            ArrayList<Node> AIArmy = new ArrayList<>();
//            //Get red AI nodes
//            for (Node node : nodeList) {
//                if (node.getColor().equals(Color.RED)) {
//                    AIArmy.add(node);
//                }
//            }
//            Node maxAdvanceNode = null;
//            Node selectedNode = null;
//            int largestJump = 0;
//            for (Node node : AIArmy) {
//                ArrayList<Node> tr = board.popularChoice(node);
//                System.out.println("             " + node.getLabel());
//                for (Node move : tr) {
//                    if (!move.getLabel().equals("null")) {
//                        if (board.stepDistance(node, move) > largestJump) {
//                            maxAdvanceNode = move;
//                            largestJump = board.stepDistance(node, move);
//                            selectedNode = node;
//                        }
//                    }
//                }
//            }
//            setAIMove(selectedNode, maxAdvanceNode);
//        }
//    }

//    public Node[] depth1search(Color color) {
//        Node destinationNode = board.getAIDestinationNode();
//        ArrayList<Node> AIArmy = new ArrayList<>();
//            for (Node node : nodeList) {
//                if (node.getColor().equals(color)) {
//                    AIArmy.add(node);
//                }
//            }
//            Node maxAdvanceNode = null;
//            Node selectedNode = null;
//            int smallestDistanceToDestination = 100;
//            for (Node node : AIArmy) {
//                ArrayList<Node> tr = board.popularChoice(node);
//                System.out.println("             " + node.getLabel());
//                for (Node move : tr) {
//                    if (!move.getLabel().equals("null")) {
//                        if (board.stepDistance(move, destinationNode) < smallestDistanceToDestination) {
//                            maxAdvanceNode = move;
//                            smallestDistanceToDestination = board.stepDistance(move, destinationNode);
//                            selectedNode = node;
//                        }
//                    }
//                }
//            }
//            setAIMove(selectedNode, maxAdvanceNode);
//    }

    public void clickedForNode(int var1, int var2) {
        for(int i=0; i<81; i++){
            if (var1 >= board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2
                    && var1 <= board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2 + diameter
                    && var2 >= board.getNodeXCoords(i) * (int)(Math.sqrt(3)/2.0 * interval) + 100
                    && var2 <= board.getNodeXCoords(i) * (int)(Math.sqrt(3)/2.0 * interval) + 100 + diameter) {
                setMove(i);
            }
        }

        System.out.println("PreviousSelectedNode: " + board.getNodeLabel(this.previousSelectedNode) +
                " SelectNode = " + board.getNodeLabel(this.selectedNode) + " is FirstMove = " + this.firstMove);
    }

    public void setAIMove(Node selectedNode, Node terminalNode) {
        terminalNode.setColor(selectedNode.getColor());
        selectedNode.setColor(Color.WHITE);
        if (isWinningCondition()) { // /!\
            if (player1) {
                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                        "PLAYER 1 HAS WON", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
                        "PLAYER 2 HAS WON", JOptionPane.WARNING_MESSAGE);
            }
        }
        player1 = true;
        repaint();
    }

    public void setMove(int chosenNode) {
        higlight(board.getSecNode(chosenNode));
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

        higlight(board.getSecNode(chosenNode));

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

                turnsCount++;
            }
            else{this.firstMove=true;}
        }
        else{this.firstMove=true;}

        repaint();

    }

    public void higlight(Node n) {
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

        ArrayList<Node> te = board.popularChoice(m);
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
            int var2 = var1.getX();
            int var3 = var1.getY();
            GUI.this.clickedForNode(var2, var3);
//            AI1();
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }


}
