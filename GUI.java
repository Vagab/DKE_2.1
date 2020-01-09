import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GUI extends JComponent {

    //Jel
    int interval = 35;
    int diameter = 30;

    private ArrayList<Integer> bestMove = new ArrayList<>();


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

    public boolean blueGoalCheck(Graph graph) {
        for (int i = 80; i >= 75; i--) {
            if (!graph.getNodeColor(i).equals(Color.BLUE))
                return false;
        }
        return true;
    }

    public boolean redGoalCheck(Graph graph) {
        for (int i = 0; i <= 5; i++) {
            if (!board.getNodeColor(i).equals(Color.RED))
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

    public double evaluationFunction(double distanceFromMiddleWeight, double distanceToGoalWeight, double radiusWeight,double maxAdvanceWeight, Graph graph, Color colorAI) {
        double evaluationValue = 0;
        ArrayList<Node> redArmy = graph.getNodeArmy(Color.RED);
        ArrayList<Node> blueArmy = graph.getNodeArmy(Color.BLUE);

        if (colorAI.equals(Color.RED)) {
            if (redGoalCheck(graph)) {
                return 1000;
            }

            if (blueGoalCheck(graph)) {
                return -1000;
            }
        }

        if (colorAI.equals(Color.BLUE)) {
            if (blueGoalCheck(graph)) {
                return 1000;
            }

            if (redGoalCheck(graph)) {
                return -1000;
            }
        }

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

        //Evaluation function
        evaluationValue = -distanceToGoalWeight * (distanceToGoalRed - distanceToGoalBlue)
                - distanceFromMiddleWeight * (distanceFromMiddleRed - distanceFromMiddleBlue)
                - radiusWeight * (radiusRed - radiusBlue);
//        System.out.println("The evaluation value is: " + evaluationValue);
        if (colorAI.equals(Color.RED)) {
            return evaluationValue;
        }
        else { // If color is blue
            return -evaluationValue; // math checks out
        }
    }

    public double maxValue(Graph graph,int cutOff, int depth, double alpha, double beta, Color color) {
        ArrayList<ArrayList<Integer>> armyStates = new ArrayList<>();
        ArrayList<Integer> minArmy = new ArrayList<>();
        Color colorOpponent = Color.BLACK;
        if (color.equals(Color.RED)) {
            armyStates = stateGenerator(Color.RED,graph);
            minArmy = graph.getArmy(Color.BLUE);
            colorOpponent = Color.BLUE;
        }
        else if (color.equals(Color.BLUE)) {
            armyStates = stateGenerator(Color.BLUE,graph);
            minArmy = graph.getArmy(Color.RED);
            colorOpponent = Color.RED;
        }
        double evaluationValue = -1000000000;
        if (depth < cutOff) {
            depth++;
            for (ArrayList<Integer> armyState : armyStates) {
                if (color.equals(Color.BLUE)) {
                    System.out.println("At depth " + depth + " " + armyToString(armyState) + " " + evaluationFunction(.5,8,1,0, new Graph(armyState, minArmy, color, colorOpponent), color));
                }
                if (evaluationFunction(.5,8,1,0, new Graph(armyState, minArmy, color, colorOpponent), color) == 1000) {
                    if (depth == 1) {
                        bestMove = armyState;
                        System.out.println("Triggered");
                    }
                    return 1000;
                }
                double evaluationValuePrevious = evaluationValue;
                evaluationValue = Math.max(evaluationValue, minValue(new Graph(armyState, minArmy, color, colorOpponent),cutOff, depth, alpha, beta, color));
                if (evaluationValuePrevious != evaluationValue && depth == 1) {
                    bestMove = armyState;
                }
                if (evaluationValue >= beta) {
                    return evaluationValue;
                }
                alpha = Math.max(alpha, evaluationValue);
            }
            return evaluationValue;
        }
        else {
            return evaluationFunction(.5,8,1,0, graph, color);
        }
    }

    public double minValue(Graph graph, int cutOff, int depth, double alpha, double beta, Color color) {
        ArrayList<ArrayList<Integer>> armyStates = new ArrayList<>();
        ArrayList<Integer> maxArmy = new ArrayList<>();
        Color colorOpponent = Color.BLACK;
        if (color.equals(Color.RED)) {
            armyStates = stateGenerator(Color.BLUE,graph);
            maxArmy = graph.getArmy(Color.RED);
            colorOpponent = Color.BLUE;
        }
        else if (color.equals(Color.BLUE)) {
            armyStates = stateGenerator(Color.RED,graph);
            maxArmy = graph.getArmy(Color.BLUE);
            colorOpponent = Color.RED;
        }
        double evaluationValue = 1000000000;
        if (depth < cutOff) {
            depth++;
            for (ArrayList<Integer> armyState : armyStates) {
                if (color.equals(Color.BLUE)) {
                    System.out.println("At depth " + depth + " " + armyToString(armyState));
                }
                if (evaluationFunction(.5,8,1,0, new Graph(maxArmy, armyState, color, colorOpponent),color) == -1000) {
                    return -1000;
                }
                evaluationValue = Math.min(evaluationValue, maxValue(new Graph(maxArmy, armyState, color, colorOpponent), cutOff, depth, alpha, beta, color));
                if (evaluationValue <= alpha) {
                    return evaluationValue;
                }
                beta = Math.min(evaluationValue, beta);
            }
            return evaluationValue;
        }
        else {
            return evaluationFunction(.5,8,1,0, graph, color);
        }
    }

    public void miniMax(int cutOff, Color colorAI) {
        bestMove.clear();
        double bestValue = maxValue(board,cutOff,0,-1000000000,1000000000, colorAI);
        System.out.println("The best encountered value is: " + bestValue);
        System.out.println("The best move is: " + armyToString(bestMove));
        setAIMove(board.getNodeArmy(colorAI), integerToNode(bestMove));
    }

    public ArrayList<Node> integerToNode(ArrayList<Integer> army) {
        ArrayList<Node> nodeArmy = new ArrayList<>();
        for (int i : army) {
            nodeArmy.add(board.getSecNode(i));
        }
        return nodeArmy;
    }

    public ArrayList<ArrayList<Integer>> stateGenerator(Color color, Graph graph) {
        ArrayList<Integer> AIArmy = graph.getArmy(color); // |Army_1| times
        ArrayList<ArrayList<Integer>> armyStates = new ArrayList<>();
        for (Integer node : AIArmy) { // |Army_1| times
            ArrayList<Node> tr = graph.popularChoice(graph.getSecNode(node)); //|Army_1|*6^(|Army_1+Army_2-1|) times
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

    public String armyToString(ArrayList<Integer> army) {
        String output = "The army is: ";
        for (int i : army) {
            output += board.getSecNode(i).getLabel() +" ";
        }
        return output;
    }

    public String nodeArmyToString(ArrayList<Node> army) {
        String output = "The army is: ";
        for (Node node : army) {
            output += node.toString() +" ";
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
            while (!isWinningCondition()) {
                miniMax(3, Color.BLUE);
                miniMax(2, Color.RED);
                if (blueGoalCheck(board) || redGoalCheck(board));
            }

            miniMax(3, Color.BLUE);
            if (!player1) {
                miniMax(1, Color.RED);
                player1 = true;
            }
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }


}
