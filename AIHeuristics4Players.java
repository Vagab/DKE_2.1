import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AIHeuristics4Players implements AIMultiPlayer{

    private double radiusWeight;
    private double distanceFromMiddleWeight;
    private double distanceToGoalWeight;
    private Color colorAI;
    private Color[] orderOfPlay = {Color.GRAY, Color.ORANGE, Color.BLACK, Color.GREEN};
    private ArrayList<Integer> bestMove = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> trajectory = new ArrayList<>();

    private ArrayList<Node> oldArmy;
    private ArrayList<Node> newArmy;

    public AIHeuristics4Players(double distanceFromMiddleWeight, double distanceToGoalWeight, double radiusWeight, Color colorAI) {
        this.distanceFromMiddleWeight = distanceFromMiddleWeight;
        this.distanceToGoalWeight = distanceToGoalWeight;
        this.radiusWeight = radiusWeight;
        this.colorAI = colorAI;
    }

    public Node[] performMove(GraphOscar board) {
        maxN(3, board);
        return getMove();
    }

    private Node[] getMove() {
        Node[] returnArray = new Node[2];
        for(Node oldNode : oldArmy){
            boolean exists = false;
            for(Node newNode : newArmy){
                if(oldNode == newNode){
                    exists = true;
                    break;
                }
            }
            if(!exists){
                returnArray[0] = oldNode;
                break;
            }
        }
        for(Node node : newArmy){
            if(node.getColor()==Color.WHITE){
                returnArray[1] = node;
                break;
            }
        }
        return returnArray;
    }

    public double[] evaluationFunction(GraphOscar graph) {
        double[] evaluationValues = {-1000, -1000, -1000, -1000};
        ArrayList<Node> blackArmy = graph.getNodeArmy(Color.BLACK);
        ArrayList<Node> grayArmy = graph.getNodeArmy(Color.GRAY);
        ArrayList<Node> greenArmy = graph.getNodeArmy(Color.GREEN);
        ArrayList<Node> orangeArmy = graph.getNodeArmy(Color.ORANGE);

        if (colorAI.equals(Color.GRAY)) {
            if (grayGoalCheck(graph)) {
                evaluationValues[1] = 1000;
                return evaluationValues;
            }
        }
        if (colorAI.equals(Color.ORANGE)) {
            if (orangeGoalCheck(graph)) {
                evaluationValues[2] = 1000;
                return evaluationValues;
            }
        }
        if (colorAI.equals(Color.BLACK)) {
            if (blackGoalCheck(graph)) {
                evaluationValues[4] = 1000;
                return evaluationValues;
            }
        }
        if (colorAI.equals(Color.GREEN)) {
            if (greenGoalCheck(graph)) {
                evaluationValues[5] = 1000;
                return evaluationValues;
            }
        }

        Node destinationNodeBlack = graph.getBlackAIDestinationNode();
        Node destinationNodeGray = graph.getGrayAIDestinationNode();
        Node destinationNodeGreen = graph.getGreenAIDestinationNode();
        Node destinationNodeOrange = graph.getOrangeAIDestinationNode();

        //Calculate distance to goal by treating the army as 1 single node by using its centroid
        double distanceToGoalBlack = graph.centroidNodeDistance(blackArmy,destinationNodeBlack);
        double distanceToGoalGray = graph.centroidNodeDistance(grayArmy,destinationNodeGray);
        double distanceToGoalGreen = graph.centroidNodeDistance(greenArmy,destinationNodeGreen);
        double distanceToGoalOrange = graph.centroidNodeDistance(orangeArmy,destinationNodeOrange);

        //Calculate distance from central line
        double distanceFromMiddleBlack = 0;
        for (Node node : blackArmy) {
            distanceFromMiddleBlack += graph.distanceToMiddleLine(node);
        }
        double distanceFromMiddleGray = 0;
        for (Node node : grayArmy) {
            distanceFromMiddleGray += graph.distanceToMiddleLine(node);
        }
        double distanceFromMiddleGreen = 0;
        for (Node node : greenArmy) {
            distanceFromMiddleGreen += graph.distanceToMiddleLine(node);
        }
        double distanceFromMiddleOrange = 0;
        for (Node node : orangeArmy) {
            distanceFromMiddleOrange += graph.distanceToMiddleLine(node);
        }

        //Pieces stay within a smallest possible radius
        double radiusBlack = graph.radius(blackArmy);
        double radiusGray = graph.radius(grayArmy);
        double radiusGreen = graph.radius(greenArmy);
        double radiusOrange = graph.radius(orangeArmy);

        //Evaluation values for each army
        double evaluationValueBlack = -distanceToGoalWeight * distanceToGoalBlack
                - distanceFromMiddleWeight * distanceFromMiddleBlack
                - radiusWeight * radiusBlack;
        double evaluationValueGray = -distanceToGoalWeight * distanceToGoalGray
                - distanceFromMiddleWeight * distanceFromMiddleGray
                - radiusWeight * radiusGray;
        double evaluationValueGreen = -distanceToGoalWeight * distanceToGoalGreen
                - distanceFromMiddleWeight * distanceFromMiddleGreen
                - radiusWeight * radiusGreen;
        double evaluationValueOrange = -distanceToGoalWeight * distanceToGoalOrange
                - distanceFromMiddleWeight * distanceFromMiddleOrange
                - radiusWeight * radiusOrange;

        evaluationValues[0] = evaluationValueGray;
        evaluationValues[1] = evaluationValueOrange;
        evaluationValues[2] = evaluationValueBlack;
        evaluationValues[3] = evaluationValueGreen;
        return evaluationValues;
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
        if (!graph.getNodeColor(55).equals(Color.BLACK))
            return false;

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

    private double[] maxValue(int cutOff, GraphOscar board, int depth, Color color) {
        if (depth >= cutOff) {
//            System.out.println("At depth " + depth + " the evaluation values are: " + Arrays.toString(evaluationFunction(board)));
            return evaluationFunction(board);
        }
        depth++;
        ArrayList<ArrayList<Integer>> armyStates = stateGenerator(color, board);
        ArrayList<Integer> armyOpponent1 = board.getArmy(getNextColor(color,0));
        ArrayList<Integer> armyOpponent2 = board.getArmy(getNextColor(color,1));
        ArrayList<Integer> armyOpponent3 = board.getArmy(getNextColor(color,2));
        double[] evaluationValues = new double[4];
        evaluationValues[getColorIndex(color)] = -1000000000;
        for (ArrayList<Integer> armyState : armyStates) {
//            System.out.println(integerToNode(armyState,board));
            GraphOscar graphChild = new GraphOscar(4,armyState, armyOpponent1, armyOpponent2, armyOpponent3,
                    color, getNextColor(color,0), getNextColor(color,1), getNextColor(color,2));
            if (evaluationFunction(graphChild)[getColorIndex(color)] == 1000) {
                if (depth == 1) {
                    bestMove = armyState;
                }
                return evaluationFunction(graphChild);
            }
            double[] evaluationValuePrevious = evaluationValues.clone();
            evaluationValues = max(evaluationValues, maxValue(cutOff, graphChild, depth, getNextColor(color,0)),getColorIndex(color));
//            System.out.println("At cutoff depth " + depth + " the evaluation values are: " + Arrays.toString(evaluationValues));
            if (!Arrays.equals(evaluationValuePrevious, evaluationValues) && depth == 1) {
                System.out.println("The current best move is: " + integerToNode(armyState,board));
                bestMove = armyState;
            }
        }
        return evaluationValues;
    }

    public double[] max(double[] bestSoFar, double[] newChild, int index) {
        if (Math.max(bestSoFar[index], newChild[index]) == bestSoFar[index]) {
            return bestSoFar.clone();
        }
        else {
            return newChild.clone();
        }
    }

    public void maxN(int cutoff, GraphOscar board) {
        bestMove.clear();
        double[] bestValues = maxValue(cutoff,board, 0, colorAI);
        oldArmy = board.getNodeArmy(colorAI);
        newArmy = integerToNode(bestMove, board);
    }

    private ArrayList<Node> integerToNode(ArrayList<Integer> army, GraphOscar board) {
        ArrayList<Node> nodeArmy = new ArrayList<>();
        for (int i : army) {
            nodeArmy.add(board.getSecNode(i));
        }
        return nodeArmy;
    }

    public void resetTrajectory(){
        trajectory.clear();
    }

    private ArrayList<ArrayList<Integer>> stateGenerator(Color color, GraphOscar graph) {
        ArrayList<Integer> AIArmy = graph.getArmy(color); // |Army_1| times
        ArrayList<ArrayList<Integer>> armyStates = new ArrayList<>();
        Node goalNode = graph.getAIDestinationNode(color);
        for (Integer node : AIArmy) { // |Army_1| times
            ArrayList<Node> tr = graph.popularChoice(graph.getSecNode(node)); //|Army_1|*6^(|Army_1+Army_2-1|) times
            for (Node move : tr) {
                if (!move.getLabel().equals("null")
                        && graph.straightLineDistance(move,goalNode) <= graph.straightLineDistance(graph.getSecNode(node),goalNode)
                ) {
                    ArrayList<Integer> armyState = (ArrayList<Integer>) AIArmy.clone();
                    armyState.remove(node);
                    armyState.add(graph.getNodeIndex(move));
                    armyStates.add(armyState);
                }
            }
        }
        return armyStates;
    }

    private String armyToString(ArrayList<Integer> army, GraphOscar board) {
        String output = "The army is: ";
        for (int i : army) {
            output += board.getSecNode(i).getLabel() +" ";
        }
        return output;
    }

    //offSet is used to get opponents after the next one (is offSet = 0, then the next opponent is simply selected)
    private Color getNextColor(Color color, int offSet) {
        int currentIndex = -1;
        for (int i = 0; i < orderOfPlay.length; i++) {
            if (orderOfPlay[i].equals(color)) {
                currentIndex = i;
            }
        }
        return orderOfPlay[(currentIndex + 1 + offSet) % 4];
    }

    private int getColorIndex(Color color) {
        for (int i = 0; i < orderOfPlay.length; i++) {
            if (orderOfPlay[i].equals(color)) {
                return i;
            }
        }
        return -1;
    }

}