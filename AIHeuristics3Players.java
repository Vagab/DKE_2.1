import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AIHeuristics3Players implements AIMultiPlayer{

    private double radiusWeight;
    private double distanceFromMiddleWeight;
    private double distanceToGoalWeight;
    private int statesConsidered;
    private Color colorAI;
    private Color[] orderOfPlay = {Color.BLUE, Color.ORANGE, Color.BLACK};
    private ArrayList<Integer> bestMove = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> trajectory = new ArrayList<>();

    private ArrayList<Node> oldArmy;
    private ArrayList<Node> newArmy;

    public AIHeuristics3Players(double distanceFromMiddleWeight, double distanceToGoalWeight, double radiusWeight, Color colorAI) {
        this.statesConsidered = 0;
        this.distanceFromMiddleWeight = distanceFromMiddleWeight;
        this.distanceToGoalWeight = distanceToGoalWeight;
        this.radiusWeight = radiusWeight;
        this.colorAI = colorAI;
    }

    public Node[] performMove(GraphOscar board) {
        maxN(4, board);
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
        double[] evaluationValues = {-1000, -1000, -1000};
        ArrayList<Node> blueArmy = graph.getNodeArmy(Color.BLUE);
        ArrayList<Node> blackArmy = graph.getNodeArmy(Color.BLACK);
        ArrayList<Node> orangeArmy = graph.getNodeArmy(Color.ORANGE);

        if (colorAI.equals(Color.BLUE)) {
            if (blueGoalCheck(graph)) {
                evaluationValues[0] = 1000;
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

        Node destinationNodeBlue = graph.getBlueAIDestinationNode();
        Node destinationNodeBlack = graph.getBlackAIDestinationNode();
        Node destinationNodeOrange = graph.getOrangeAIDestinationNode();

        //Calculate distance to goal by treating the army as 1 single node by using its centroid
        double distanceToGoalBlue = graph.centroidNodeDistance(blueArmy,destinationNodeBlue);
        double distanceToGoalBlack = graph.centroidNodeDistance(blackArmy,destinationNodeBlack);
        double distanceToGoalOrange = graph.centroidNodeDistance(orangeArmy,destinationNodeOrange);

        //Calculate distance from central line
        double distanceFromMiddleBlue = 0;
        for (Node node : blueArmy) {
            distanceFromMiddleBlue += graph.distanceToMiddleLine(node);
        }
        double distanceFromMiddleBlack = 0;
        for (Node node : blackArmy) {
            distanceFromMiddleBlack += graph.distanceToMiddleLine(node);
        }
        double distanceFromMiddleOrange = 0;
        for (Node node : orangeArmy) {
            distanceFromMiddleOrange += graph.distanceToMiddleLine(node);
        }

        //Pieces stay within a smallest possible radius
        double radiusBlue = graph.radius(blueArmy);
        double radiusBlack = graph.radius(blackArmy);
        double radiusOrange = graph.radius(orangeArmy);

        //Evaluation values for each army
        double evaluationValueBlue = -distanceToGoalWeight * distanceToGoalBlue
                - distanceFromMiddleWeight * distanceFromMiddleBlue
                - radiusWeight * radiusBlue;
        double evaluationValueBlack = -distanceToGoalWeight * distanceToGoalBlack
                - distanceFromMiddleWeight * distanceFromMiddleBlack
                - radiusWeight * radiusBlack;
        double evaluationValueOrange = -distanceToGoalWeight * distanceToGoalOrange
                - distanceFromMiddleWeight * distanceFromMiddleOrange
                - radiusWeight * radiusOrange;

        evaluationValues[0] = evaluationValueBlue;
        evaluationValues[1] = evaluationValueOrange;
        evaluationValues[2] = evaluationValueBlack;
        return evaluationValues;
    }

    private boolean blueGoalCheck(GraphOscar graph) {
        for (int i = 120; i > 110; i--) {
            if (!graph.getNodeColor(i).equals(Color.BLUE))
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
        if (!graph.getNodeColor(55).equals(Color.BLACK))
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
        double[] evaluationValues = new double[3];
        evaluationValues[getColorIndex(color)] = -1000000000;
        for (ArrayList<Integer> armyState : armyStates) {
            statesConsidered++;
//            System.out.println(integerToNode(armyState,board));
            GraphOscar graphChild = new GraphOscar(3,armyState, armyOpponent1, armyOpponent2,
                    color, getNextColor(color,0), getNextColor(color,1));
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
//                System.out.println("The current best move is: " + integerToNode(armyState,board));
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
        trajectory.add((ArrayList<Integer>) bestMove.clone());
        System.out.println("The number of states considered is: " + statesConsidered + " for " + colorAI);
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
//                        && graph.straightLineDistance(move,goalNode) <= graph.straightLineDistance(graph.getSecNode(node),goalNode) //foward
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
        return orderOfPlay[(currentIndex + 1 + offSet) % 3];
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
