import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI extends JComponent {

    //Jel
    int interval = 45;
    int diameter = 40;

    
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

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        for (int i = 0; i < nodeList.length; i++) {

            g2.setPaint(board.getNodeColor(i));
            Ellipse2D.Double circle = new Ellipse2D.Double(board.getNodeYCoords(i) * interval + 700 - board.getNodeXCoords(i) * interval/2.0, board.getNodeXCoords(i) * Math.sqrt(3)/2.0 * interval + 100, diameter, diameter);
            g2.fill(circle);
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
            int move = 0;
            int var2 = var1.getX();
            int var3 = var1.getY();
            GUI.this.clickedForNode(var2, var3);
//            if (!player1) {
//                setAIMove(aiPlayer2.performMove(board));
//                player1 = true;
//            }
            double[] weights = {0.5, 10.0, 1.0};
//            playGame(aiPlayer2,aiPlayer,weights);
            weightTuning(weights);
        }

        public void mouseEntered(MouseEvent var1) {
        }

        public void mouseExited(MouseEvent var1) {
        }

    }

    public double[] weightTuning(double[] weights) {
        double[] newWeights = weights.clone();
        AIHeuristics player1 = new AIHeuristics(weights[0], weights[1], weights[2], Color.RED);
        AIHeuristics player2 = new AIHeuristics(weights[0], weights[1], weights[2], Color.BLUE);
        for (int i = 0; i < 4; i++) { // 4 is just a number to test a few iterations
            ArrayList<double[]> temp = playGame(player1, player2, newWeights);
            printMatrix(temp);
            for (int j = 0; j < 3; j++) {
                newWeights[j] = temp.get(j)[0];
            }
        }
        return newWeights;
    }

    public ArrayList<double[]> playGame(AIHeuristics player1, AIHeuristics player2, double[] weights) {
        miniMaxScoresVector.clear();
        featureScoresMatrix.clear();
        while (!(blueGoalCheck(board) || redGoalCheck(board))) { // Play 1 game, we focus on player 1
            setAIMove(player1.performMove(board)); //red player moves
            miniMaxScoresVector.add(player1.getBestValue());
            featureScoresMatrix.add(player1.getBestFeatures());
            if (redGoalCheck(board) || blueGoalCheck(board) ){
                break;
            }
            setAIMove(player2.performMove(board)); //blue player moves
        }
        return leastSquares(featureScoresMatrix,createVector(miniMaxScoresVector));
    }

    public ArrayList<double[]> leastSquares(ArrayList<double[]> matrix, ArrayList<double[]> vector) {
        ArrayList<double[]> leastSquaresSolution =matrixMultiplication(matrixMultiplication(inverse(matrixMultiplication(transpose(matrix),matrix)),transpose(matrix)),vector);
        return leastSquaresSolution;
    }

    public ArrayList<double[]> transpose(ArrayList<double[]> matrix) {
        int columns = matrix.size(); //3
        int rows = matrix.get(0).length; //2
        ArrayList<double[]> matrixTranspose = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            matrixTranspose.add(new double[columns]);
        }
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++) {
                matrixTranspose.get(i)[j] = matrix.get(j)[i];
            }
        }
        return matrixTranspose;
    }

    public void printMatrix(ArrayList<double[]> matrix) {
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(0).length; j++) {
                System.out.print(matrix.get(i)[j] + " ");
            }
            System.out.println();
        }
    }

    public ArrayList<double[]>  matrixMultiplication(ArrayList<double[]> matrix1, ArrayList<double[]> matrix2) {
        int matrix1Rows = matrix1.size();
        int matrix1Columns = matrix1.get(0).length;
        int matrix2Rows = matrix2.size();
        int matrix2Columns = matrix2.get(0).length;
        if (matrix1Columns != matrix2Rows) {
            System.out.println("Not possible!");
        }
        ArrayList<double[]> matrixMultiplied = new ArrayList<>();
        for (int i = 0; i < matrix1Rows; i++) {
            matrixMultiplied.add(new double[matrix2Columns]);
        }
        for (int i = 0; i < matrix1Rows; i++) {
            for (int j = 0; j < matrix2Columns; j++) {
                for (int k = 0; k < matrix2.size(); k++) {
                    matrixMultiplied.get(i)[j] += matrix1.get(i)[k]*matrix2.get(k)[j];
                }
            }
        }

        return matrixMultiplied;
    }

    public double determinant(ArrayList<double[]> matrix, int n) //matrix needs to be square
    {
        int D = 0; // Initialize result

        // Base case : if matrix contains single element
        if (n == 1){
            return matrix.get(0)[0];
        }

        ArrayList<double[]> temp = new ArrayList<>(); // To store cofactors
        for (int i = 0; i < n; i++){
            temp.add(new double[n]);
        }

        int sign = 1; // To store sign multiplier

        // Iterate for each element of first row
        for (int i = 0; i < n; i++)
        {
            // Getting Cofactor of A[0][f]
            getCofactor(matrix, temp, 0, i, n);
            D += sign * matrix.get(0)[i] * determinant(temp, n - 1);

            // terms are to be added with alternate sign
            sign = -sign;
        }

        return D;
    }

    // Function to get cofactor of A[p][q] in temp[][]. n is current
// dimension of A[][]
    public void getCofactor(ArrayList<double[]> matrix, ArrayList<double[]> temp, int p, int q, int n)
    {
        int i = 0, j = 0;
        // Looping for each element of the matrix
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                // Copying into temporary matrix only those element
                // which are not in given row and column
                if (row != p && col != q)
                {
                    temp.get(i)[j++] = matrix.get(row)[col];

                    // Row is filled, so increase row index and
                    // reset col index
                    if (j == n - 1)
                    {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    // Function to get adjoint of A[N][N] in adj[N][N].
    public void adjoint(ArrayList<double[]> matrix, ArrayList<double[]> adj)
    {
        int rows = matrix.size();
        if (rows == 1)
        {
            adj.get(0)[0] = 1;
            return;
        }

        // temp is used to store cofactors of A[][]
        int sign = 1;
        ArrayList<double[]> temp = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            temp.add(new double[rows]);
        }

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                // Get cofactor of A[i][j]
                getCofactor(matrix, temp, i, j, rows);

                // sign of adj[j][i] positive if sum of row
                // and column indexes is even.
                sign = ((i + j) % 2 == 0)? 1: -1;

                // Interchanging rows and columns to get the
                // transpose of the cofactor matrix
                adj.get(j)[i] = (sign)*(determinant(temp, rows-1));
            }
        }
    }

    public ArrayList<double[]> inverse(ArrayList<double[]> matrix)
    {
        int rows = matrix.size();
        ArrayList<double[]> inverseMatrix = new ArrayList<>();
        for (int i = 0; i < rows; i++){
            inverseMatrix.add(new double[rows]);
        }
        // Find determinant of A[][]
        double det = determinant(matrix, rows);
        if (det == 0)
        {
            System.out.print("Singular matrix, can't find its inverse");
        }
        // Find adjoint
        ArrayList<double[]> adj = new ArrayList<>();
        for (int i = 0; i < rows; i++){
            adj.add(new double[rows]);
        }
        adjoint(matrix, adj);

        // Find Inverse using formula "inverse(A) = adj(A)/det(A)"
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                inverseMatrix.get(i)[j] = adj.get(i)[j]/((double)det);
            }
        }
        return inverseMatrix;
    }

    public ArrayList<double[]> createVector(ArrayList<Double> list) {
        ArrayList<double[]> vector = new ArrayList<>();
        for (double k : list) {
            vector.add(new double[]{k});
        }
        return vector;
    }



}
