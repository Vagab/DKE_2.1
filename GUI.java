import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GUI extends JComponent {

    //Jel
    int interval = 35;
    int diameter = 30;


    Graph board;
//    ArrayList<Color> colorsOfPlayers = new ArrayList<Color>();
    Node[] nodeList;
    int selectedNode, previousSelectedNode = -1;
    public boolean firstMove = false;
    long start = System.nanoTime();
    int currentPlayer = 1;
    int turnsCount = 1;
//    int nrsP;

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

    public double evaluationFunction(double distanceFromMiddleWeight, double distanceToGoalWeight, double radiusWeight,double maxAdvanceWeight, Graph graph) {
        double evaluationValue = 0;
        Node destinationNode;
        ArrayList<Node> redArmy = graph.getNodeArmy(Color.RED); //Is just to test the red AI
        ArrayList<Node> blueArmy = graph.getNodeArmy(Color.BLUE); //Is just to test the blue AI

        Node destinationNodeRed = graph.getRedAIDestinationNode();
        Node destinationNodeBlue = graph.getBlueAIDestinationNode();

        //Calculate distance to goal by treating the army as 1 single node by using its centroid
        double distanceToGoalRed = graph.centroidNodeDistance(redArmy,destinationNodeRed);
        double distanceToGoalBlue = graph.centroidNodeDistance(blueArmy,destinationNodeBlue);

        //Calculate distance from central line
        double distanceFromMiddleRed = 0;
        for (Node node : redArmy) {
            distanceFromMiddleRed += graph.distanceToMiddleLine(node);
        }
        double distanceFromMiddleBlue = 0;
        for (Node node : blueArmy) {
            distanceFromMiddleBlue += graph.distanceToMiddleLine(node);
        }

        //Pieces stay within a smallest possible radius
        double radiusRed = graph.radius(redArmy);
        double radiusBlue = graph.radius(blueArmy);

        //Maximal advance of all pieces
        int maxAdvanceRed = maxAdvanceOfAllPieces(redArmy,destinationNodeRed);
        int maxAdvanceBlue = maxAdvanceOfAllPieces(blueArmy,destinationNodeBlue);


        //Evaluation function
        evaluationValue = -distanceToGoalWeight * (distanceToGoalRed - distanceToGoalBlue)
                - distanceFromMiddleWeight * (distanceFromMiddleRed - distanceFromMiddleBlue)
                - radiusWeight * (radiusRed - radiusBlue)
                + maxAdvanceWeight * (maxAdvanceRed - maxAdvanceBlue);
        System.out.println("The evaluation value is: " + evaluationValue);
//        System.out.println("The maximal advance is: " + maxAdvance);
        return evaluationValue;
    }

    public double maxValue(Graph graph,int cutOff, int depth, double alpha, double beta) {
        ArrayList<ArrayList<Integer>> armyStates = stateGenerator(Color.RED,graph);
        ArrayList<Integer> minArmy = graph.getArmy(Color.BLUE);
        double evaluationValue = -1000000000;
        if (depth < cutOff) {
            depth++;
            System.out.println("At depth " + depth + " The following states are examined: ");
            for (ArrayList<Integer> armyState : armyStates) {
                System.out.println("Army " + armyToString(armyState) + " is being evaluated.");
                evaluationValue = Math.max(evaluationValue, minValue(new Graph(armyState, minArmy),cutOff, depth, alpha, beta));
                if (evaluationValue >= beta) {
                    return evaluationValue;
                }
                alpha = Math.max(alpha, evaluationValue);
//                System.out.println("At depth " + depth + " the evaluation value is: " + evaluationValue);
            }
            return evaluationValue;
        }
        else {
            System.out.println("cutoff " + depth + armyToString(graph.getArmy(Color.RED)));
            return evaluationFunction(.5,2,0.5,0,graph);
        }
    }

    public double minValue(Graph graph, int cutOff, int depth, double alpha, double beta) {
        ArrayList<ArrayList<Integer>> armyStates = stateGenerator(Color.BLUE,graph);
        ArrayList<Integer> maxArmy = graph.getArmy(Color.RED);
        double evaluationValue = 1000000000;
        if (depth < cutOff) {
            depth++;
            System.out.println("At depth " + depth + " The following states are examined: ");
            for (ArrayList<Integer> armyState : armyStates) {
                System.out.println("Army " + armyToString(armyState) + " is being evaluated.");
                evaluationValue = Math.min(evaluationValue, maxValue(new Graph(maxArmy, armyState), cutOff, depth, alpha, beta));
                if (evaluationValue <= alpha) {
                    return evaluationValue;
                }
                beta = Math.min(evaluationValue, beta);
            }
            return evaluationValue;
        }
        else {
            return evaluationFunction(.5,2,0.5,0,graph);
        }
    }

    public void miniMax(int cutOff) {
        double bestValue = minValue(board,cutOff,0,-1000000000,1000000000);
        System.out.println("The best encountered value is: " + bestValue);
    }

    public ArrayList<ArrayList<Integer>> stateGenerator(Color color, Graph graph) {
        ArrayList<Integer> AIArmy = graph.getArmy(color); // |Army_1| times
        ArrayList<ArrayList<Integer>> armyStates = new ArrayList<>();
        for (Integer node : AIArmy) { // |Army_1| times
            ArrayList<Node> tr = graph.popularChoice(graph.getSecNode(node)); //|Army_1|*6^(|Army_1+Army_2-1|) times
//            System.out.println(tr);
            for (Node move : tr) {
                if (!move.getLabel().equals("null")) {
                    ArrayList<Integer> armyState = (ArrayList<Integer>) AIArmy.clone();
                    armyState.remove(node);
                    armyState.add(graph.getNodeIndex(move));
                    armyStates.add(armyState);
//                    System.out.println(armyToString(armyState) + color.toString());
                }
            }
        }
        return armyStates;
    }

    public int maxAdvanceOfAllPieces(ArrayList<Node> army, Node destinationNode) {
        int maxAdvance = 0;
        //System.out.println(armyToString(army));
        for (Node node : army) {
            ArrayList<Node> tr = board.popularChoice(node);// has to change to graph
            int maxNodeAdvance = 0;
//            System.out.println("Current node: " + node.getLabel());
            for (Node move : tr) { //Doesn't work
                if (!move.getLabel().equals("null")) {
//                    System.out.println("Move node: " + move.getLabel());
                    if (maxNodeAdvance< (board.stepDistance(node,destinationNode) - board.stepDistance(move,destinationNode))) {
                        maxNodeAdvance = (board.stepDistance(node,destinationNode) - board.stepDistance(move,destinationNode));
                    }
                }
            }
//            System.out.println(maxNodeAdvance);
            maxAdvance += maxNodeAdvance;
        }
//        System.out.println();
        return maxAdvance;
    }

    public String armyToString(ArrayList<Integer> army) {
        String output = "The army is: ";
        for (int i : army) {
            output += board.getSecNode(i).getLabel() +" ";
        }
        return output;
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
        if(player1){player1=false;}
        else{player1 = true;}
        repaint();
    }

    public void setAIMove(ArrayList<Node> previousArmy, ArrayList<Node> newArmy) {
        Color color = previousArmy.get(0).getColor();
        for (Node node : previousArmy) {
            node.setColor(Color.WHITE);
        }
        for (Node node : newArmy) {
            node.setColor(color);
        }
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
            if (!player1) {
                miniMax(4);
                player1 = true;
            }
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }


}
