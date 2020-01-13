import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

//TODO: implement pruning
public class AIHeuristics implements AI {

    private double radiusWeight;
    private double distanceFromMiddleWeight;
    private double distanceToGoalWeight;
    private Color colorAI;
    private ArrayList<Integer> bestMove = new ArrayList<>();
    
    private ArrayList<Node> oldArmy;
    private ArrayList<Node> newArmy;

    private double bestValue;
    private double[] bestFeatures;


    public AIHeuristics(double distanceFromMiddleWeight, double distanceToGoalWeight, double radiusWeight, Color colorAI) {
        this.bestValue = 0;
        this.bestFeatures = new double[3];
        this.distanceFromMiddleWeight = distanceFromMiddleWeight;
        this.distanceToGoalWeight = distanceToGoalWeight;
        this.radiusWeight = radiusWeight;
        this.colorAI = colorAI;
    }

    @Override
    public Node[] performMove(Graph board) {
        miniMax(3, board);
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

    private boolean blueGoalCheck(Graph graph) {
        for (int i = 80; i >= 75; i--) {
            if (!graph.getNodeColor(i).equals(Color.BLUE))
                return false;
        }
        return true;
    }

    private boolean redGoalCheck(Graph graph) {
        for (int i = 0; i <= 5; i++) {
            if (!graph.getNodeColor(i).equals(Color.RED))
                return false;
        }
        return true;
    }

    public double[] featureCalculator(Graph graph) {
        ArrayList<Node> redArmy = graph.getNodeArmy(Color.RED);
        ArrayList<Node> blueArmy = graph.getNodeArmy(Color.BLUE);

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

        //Heuristics/features
        double feature1 = distanceFromMiddleRed - distanceFromMiddleBlue;
        double feature2 = distanceToGoalRed - distanceToGoalBlue;
        double feature3 = radiusRed - radiusBlue;

        double[] featureScoresVector = {feature1, feature2, feature3};
        return featureScoresVector;
    }

    public double evaluationFunction(Graph graph) {
        double evaluationValue = 0;
        double[] evaluationVector = new double[4];
        //Calculate features
        double[] featureScoresVector = featureCalculator(graph);
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
        //Evaluation function
        evaluationValue = - distanceFromMiddleWeight * featureScoresVector[0]
                - distanceToGoalWeight * featureScoresVector[1]
                - radiusWeight * featureScoresVector[2];
        if (colorAI.equals(Color.RED)) {
            return evaluationValue;
        }
        else { // If color is blue
            return -evaluationValue;
        }
    }

    private double maxValue(Graph graph,int cutOff, int depth, double alpha, double beta) {
        ArrayList<ArrayList<Integer>> armyStates = new ArrayList<>();
        ArrayList<Integer> minArmy = new ArrayList<>();
        Color colorOpponent = Color.BLACK;
        if (colorAI.equals(Color.RED)) {
            armyStates = stateGenerator(Color.RED,graph);
            minArmy = graph.getArmy(Color.BLUE);
            colorOpponent = Color.BLUE;
        }
        else if (colorAI.equals(Color.BLUE)) {
            armyStates = stateGenerator(Color.BLUE,graph);
            minArmy = graph.getArmy(Color.RED);
            colorOpponent = Color.RED;
        }
        double evaluationValue = -1000000000;
        if (depth < cutOff) {
            depth++;
            for (ArrayList<Integer> armyState : armyStates) {
                Graph graphChild = new Graph(armyState, minArmy, colorAI, colorOpponent);
                System.out.println("Depth: " + depth);
                System.out.println(armyToString(armyState,graphChild) + " is being evaluated.");
                if (evaluationFunction(graphChild) == 1000) {
                    if (depth == 1) {
                        bestMove = armyState;
                    }
                    return evaluationFunction(graphChild);
                }
                double evaluationValuePrevious = evaluationValue;
                evaluationValue = Math.max(evaluationValue, minValue(graphChild,cutOff, depth, alpha, beta));
                System.out.println("Evaluation value at maxValue: " + evaluationValue);
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
            System.out.println("cutoff at depth: " + depth);
            return evaluationFunction(graph);
        }
    }

    private double minValue(Graph graph, int cutOff, int depth, double alpha, double beta) {
        ArrayList<ArrayList<Integer>> armyStates = new ArrayList<>();
        ArrayList<Integer> maxArmy = new ArrayList<>();
        Color colorOpponent = Color.BLACK;
        if (colorAI.equals(Color.RED)) {
            armyStates = stateGenerator(Color.BLUE,graph);
            maxArmy = graph.getArmy(Color.RED);
            colorOpponent = Color.BLUE;
        }
        else if (colorAI.equals(Color.BLUE)) {
            armyStates = stateGenerator(Color.RED,graph);
            maxArmy = graph.getArmy(Color.BLUE);
            colorOpponent = Color.RED;
        }
        double evaluationValue = 1000000000;
        if (depth < cutOff) {
            depth++;
            for (ArrayList<Integer> armyState : armyStates) {
                Graph graphChild = new Graph(maxArmy,armyState, colorAI, colorOpponent);
                System.out.println("Depth: " + depth);
                System.out.println(armyToString(armyState,graphChild) + " is being evaluated.");
                if (evaluationFunction(graphChild) == -1000) {
                    return evaluationFunction(graphChild);
                }
                double evaluationValuePrevious = evaluationValue;
                evaluationValue = Math.min(evaluationValue, maxValue(graphChild, cutOff, depth, alpha, beta));
                System.out.println("Evaluation value at minValue: " + evaluationValue);
                if (evaluationValuePrevious != evaluationValue && depth == 1) {
                        bestMove = armyState;
                }
                if (evaluationValue <= alpha) {
                    return evaluationValue;
                }
                beta = Math.min(evaluationValue, beta);
            }
            return evaluationValue;
        }
        else {
            System.out.println("cutoff");
//            System.out.println(graph);
//            System.out.println("Evaluation value is " +  evaluationFunction(graph));
            return evaluationFunction(graph);
        }
    }

    private void miniMax(int cutOff, Graph board) {
        bestMove.clear();
        bestValue = maxValue(board,cutOff,0,-1000000000,1000000000); // bestValue is the target function approximation
        System.out.println("The best move is: " + armyToString(bestMove, board));
        oldArmy = board.getNodeArmy(colorAI); 
        newArmy = integerToNode(bestMove, board);
    }

    public double getBestValue() {
        return bestValue;
    }

    private ArrayList<Node> integerToNode(ArrayList<Integer> army, Graph board) {
        ArrayList<Node> nodeArmy = new ArrayList<>();
        for (int i : army) {
            nodeArmy.add(board.getSecNode(i));
        }
        return nodeArmy;
    }

    private String armyToString(ArrayList<Integer> army, Graph board) {
        String output = "The army is: ";
        for (int i : army) {
            output += board.getSecNode(i).getLabel() +" ";
        }
        return output;
    }

    private ArrayList<ArrayList<Integer>> stateGenerator(Color color, Graph graph) {
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
                }
            }
        }
        return armyStates;
    }
    
}