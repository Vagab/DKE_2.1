import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GUI {
    //Jel
    private int diameter = 30;
    private double interval = diameter*2.0/Math.sqrt(3);
    private double offset = diameter/2.0;
    private double xOffset = 700;
    private double yOffset = 100;
    private MatrixCalculator matrixCalculator;


    double[] weights = {0.5, 10.0, 1.0};
    private AI1v1 heuristicPlayer1;
    private AI1v1 heuristicPlayer2;
    private AI1v1 MCMCPlayer1;
    private AI1v1 MCMCPlayer2;

    private ArrayList<Double> miniMaxScoresVector = new ArrayList<>();
    private ArrayList<double[]> featureScoresMatrix = new ArrayList<>();
    private ArrayList<Double> currentEvaluationVector = new ArrayList<>();


    Graph board;
    Node[] nodeList;
    int selectedNode, previousSelectedNode = -1;
    public boolean firstMove = false;
    long start = System.nanoTime();
    int currentPlayer = 1;
    int turnsCount = 1;
    boolean player1 = true;

    private Node lastAIPlayed;
    private Node previousAIPlayed;

    private Random rand;
    private LobbySimulator simulator;
    private int bestPreviousScore = -1;
//0.11817334978149338, 0.7117125414209313, 0.6066114194523499
    public GUI() {
        heuristicPlayer1 = new AIHeuristics(0.103, 0.989, 0.107, Color.BLUE);
        heuristicPlayer2 = new AIHeuristics(0.103, 0.989, 0.107, Color.RED);
//        heuristicPlayer1 = new AIHeuristics(0.041, 0.886, 0.190, Color.BLUE);
//        heuristicPlayer2 = new AIHeuristics(0.041, 0.886, 0.190, Color.RED);
//        heuristicPlayer1 = new AIHeuristics(0.049, 0.994, 0.072, Color.BLUE);
//        heuristicPlayer2 = new AIHeuristics(0.049, 0.994, 0.072, Color.RED);
        MCMCPlayer1 = new AIMCMC(Color.BLUE,turnsCount);
        MCMCPlayer2 = new AIMCMC(Color.RED,turnsCount);

        this.board = new Graph();
        this.nodeList = board.getNodes();
        this.matrixCalculator = new MatrixCalculator();

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
            g2.setPaint(Color.ORANGE);
            g2.drawString(String.valueOf(i), (float) (board.getNodeYCoords(i) * interval + 715 - board.getNodeXCoords(i) * interval/2), (int) (board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + 120));
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

    public boolean goalCheck(Graph graph, AIHeuristics player){
        if (player.getColor().equals(Color.RED)){
            return redGoalCheck(graph);
        }
        else {
            return blueGoalCheck(graph);
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
//        if (isWinningCondition()) { // /!\
//            if (player1) {
//                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
//                        "PLAYER 1 HAS WON", JOptionPane.WARNING_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(this, currentPlayer + " has won!",
//                        "PLAYER 2 HAS WON", JOptionPane.WARNING_MESSAGE);
//            }
//        }
        if(player1){player1=false;}
        else{player1 = true;}
        turnsCount++;
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
            int var2 = var1.getX();
            int var3 = var1.getY();
            GUI.this.clickedForNode(var2, var3);
            // Play 1 game, we focus on player 1
//            if (!goalCheck(board, (AIHeuristics) heuristicPlayer1)) {
//                setAIMove(heuristicPlayer1.performMove(board));
//            }
//            if (!player1 && !goalCheck(board, (AIHeuristics) heuristicPlayer2)) {
//                setAIMove(heuristicPlayer2.performMove(board));
//                player1 = true;
//            }

            double[] weights = {1,8,1};
            try {
                weightTuning(matrixCalculator.normalize(weights));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }

    public double[] weightTuning(double[] weights) throws FileNotFoundException {
        PrintWriter out1 = new PrintWriter("Output1");
        PrintWriter out2 = new PrintWriter("Output2");
        PrintWriter out3 = new PrintWriter("Output3");
        double[] newWeights = weights.clone();
        System.out.println(Arrays.toString(newWeights));
        double learningFactor = 0.1; //Learning factor for tuning, initially set equal to 0.1 (for testing purposes)
        for (int i = 0; i < 150; i++) { // 100 is just a number to test a few iterations
            System.out.println("Game number " + i);
            if (i > 100) {
                learningFactor = 0.01;
            }
            AIHeuristics player1 = new AIHeuristics(newWeights[0], newWeights[1], newWeights[2], Color.RED);
            AIHeuristics player2 = new AIHeuristics(newWeights[0], newWeights[1], newWeights[2], Color.BLUE);
            ArrayList<double[]> temp = playGame(player1, player2);
//            printMatrix(temp);
            for (int j = 0; j < 3; j++) {
                newWeights[j] = newWeights[j] + learningFactor*(matrixCalculator.normalize(matrixCalculator.listToArray(temp))[j]-newWeights[j]);
            }
            out1.print(matrixCalculator.normalize(newWeights)[0]);
            out1.println();
            out2.print(matrixCalculator.normalize(newWeights)[1]);
            out2.println();
            out3.print(matrixCalculator.normalize(newWeights)[2]);
            out3.println();
//            System.out.println(Arrays.toString(newWeights));
            reset();
            player1.resetTrajectory();
            player2.resetTrajectory();
        }
        System.out.print("The weights are " + Arrays.toString(matrixCalculator.normalize(newWeights)));
        out1.close();
        out2.close();
        out3.close();
        return matrixCalculator.normalize(newWeights);
    }

    public ArrayList<double[]> playGame(AIHeuristics player1, AIHeuristics player2) {
        miniMaxScoresVector.clear(); //training values
        featureScoresMatrix.clear();
        int turns = 0;
        int predictedError = 0;
        boolean player1Done = false;
        boolean player2Done = false;
        while (!player1Done && !player2Done) { // Play 1 game, we focus on player 1
            if (!goalCheck(board,player1)) {
                setAIMove(player1.performMove(board));
//                miniMaxScoresVector.add(player1.getBestValue()); //Stores the training values
//                featureScoresMatrix.add(player1.getBestFeatures());
                if (player2Done){
                    player1Done = true;
                }
            }
            else {
                player1Done = true;
            }
            if (!goalCheck(board,player2)){
                setAIMove(player2.performMove(board)); //blue player moves
                miniMaxScoresVector.add(player2.getBestValue()); //Stores the training values
                featureScoresMatrix.add(player2.getBestFeatures());
                predictedError += Math.pow((player2.getBestValue() - player2.getRawScore()),2);
                if (player1Done){
                    player2Done = true;
                }
            }
            else {
                player2Done = true;
            }
            if (turns > 300) {
                System.out.println("Game is stuck");
            }
            turns+=2;
        }
//        System.out.println("Player 1: " + miniMaxScoresVector);
//        System.out.println("Length is: " + miniMaxScoresVector.size());
//        System.out.println("Player 2: " + player2minimax);
//        System.out.println("Length is: " + player2minimax.size());
//        System.out.println(featureScoresMatrix);
//        System.out.println(miniMaxScoresVector);
        System.out.println("The predicted error is: " + predictedError);
        return matrixCalculator.leastSquares(featureScoresMatrix,matrixCalculator.createVector(miniMaxScoresVector));
    }


    public void reset() {
        board = new Graph();
    }


}
