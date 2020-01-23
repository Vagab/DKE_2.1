import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AIGreedy implements AI1v1 {
    private Color colorAI;
    private ArrayList<ArrayList<Integer>> bestMoves = new ArrayList<>();
    private ArrayList<Integer> bestMove = new ArrayList<>();

    private ArrayList<Node> oldArmy;
    private ArrayList<Node> newArmy;
    private int cutOffDepth;
    private double bestValue;
    private Random randomGenerator;
    private boolean goalReached;

    public AIGreedy(int cutOffDepth, Color colorAI) {
        this.randomGenerator = new Random();
        this.goalReached = false;
        this.cutOffDepth = cutOffDepth;
        this.bestValue = 0;
        this.colorAI = colorAI;
    }

    @Override
    public Node[] performMove(Graph board) {
        greedySearch(cutOffDepth, board);
        return getMove();
    }

    @Override
    public double getBestValue() {
        return 0;
    }

    @Override
    public double[] getBestFeatures() {
        return new double[0];
    }

    @Override
    public double getRawScore() {
        return 0;
    }

    @Override
    public Color getColor() {
        return aiColor;
    }

    @Override
    public void resetTrajectory() {

    }

    private Node[] getMove() {
        Node[] returnArray = new Node[2];
        for (Node oldNode : oldArmy) {
            boolean exists = false;
            for (Node newNode : newArmy) {
                if (oldNode == newNode) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                returnArray[0] = oldNode;
                break;
            }
        }
        for (Node node : newArmy) {
            if (node.getColor() == Color.WHITE) {
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

    public double evaluationFunction(Graph graph) {
        ArrayList<Node> army = graph.getNodeArmy(colorAI);
        Node destinationNode = graph.getAIDestinationNode(colorAI);
        double distanceToGoal = 0;
        for (Node node : army) {
            distanceToGoal += Math.pow(graph.straightLineDistance(destinationNode,node),2);
        }
        return -distanceToGoal;
    }

    private double maxValue(Graph graph, int cutOff, int depth) {
        ArrayList<ArrayList<Integer>> armyStates = new ArrayList<>();
        ArrayList<Integer> minArmy = new ArrayList<>();
        Color colorOpponent = Color.BLACK;
        double evaluationValue = -1000000000;
        if (depth < cutOff) {
            depth++;
            if (colorAI.equals(Color.RED)) {
                armyStates = stateGenerator(graph);
                minArmy = graph.getArmy(Color.BLUE);
                colorOpponent = Color.BLUE;
            } else if (colorAI.equals(Color.BLUE)) {
                armyStates = stateGenerator(graph);
                minArmy = graph.getArmy(Color.RED);
                colorOpponent = Color.RED;
            }
            for (ArrayList<Integer> armyState : armyStates) {
                Graph graphChild = new Graph(armyState, minArmy, colorAI, colorOpponent);
                if (redGoalCheck(graphChild) || blueGoalCheck(graphChild)) {
                    if (depth == 1) {
                        bestMove = armyState;
                        goalReached = true;
                    }
                    return evaluationFunction(graphChild);
                }
                double evaluationValuePrevious = evaluationValue;
                double tempEval = maxValue(graphChild, cutOff, depth);
                evaluationValue = Math.max(evaluationValue, tempEval);
                if (tempEval == evaluationValue && depth == 1) {
                    if (evaluationValuePrevious == evaluationValue) {
                        bestMoves.add(armyState);
                    } else {
                        bestMoves.clear();
                        bestMoves.add(armyState);
                    }
                }
            }
            return evaluationValue;
        } else {
            return evaluationFunction(graph);
        }
    }


    private void greedySearch(int cutOff, Graph board) {
        bestMove.clear();
        bestMoves.clear();
        bestValue = maxValue(board, cutOff, 0); // bestValue is the target function approximation
        if (goalReached) {
            oldArmy = board.getNodeArmy(colorAI);
            newArmy = integerToNode(bestMove, board);
            goalReached = false;
        } else {
            int index = randomGenerator.nextInt(bestMoves.size());
            oldArmy = board.getNodeArmy(colorAI);
            newArmy = integerToNode(bestMoves.get(index), board);
        }
    }


    private ArrayList<Node> integerToNode(ArrayList<Integer> army, Graph board) {
        ArrayList<Node> nodeArmy = new ArrayList<>();
        for (int i : army) {
            nodeArmy.add(board.getSecNode(i));
        }
        return nodeArmy;
    }


    private ArrayList<ArrayList<Integer>> stateGenerator(Graph graph) {
        ArrayList<Integer> AIArmy = graph.getArmy(colorAI); // |Army_1| times
        Node goalNode = graph.getAIDestinationNode(colorAI);
        ArrayList<ArrayList<Integer>> armyStates = new ArrayList<>();
        for (Integer node : AIArmy) { // |Army_1| times
            ArrayList<Node> tr = graph.popularChoice(graph.getSecNode(node)); //|Army_1|*6^(|Army_1+Army_2-1|) times
            for (Node move : tr) {
                if (!move.getLabel().equals("null")
                        && graph.stepDistance(move, goalNode) <= graph.stepDistance(graph.getSecNode(node), goalNode)
                ) {
                    ArrayList<Integer> armyState = (ArrayList<Integer>) AIArmy.clone();
                    armyState.remove(node);
                    armyState.add(graph.getNodeIndex(move));
                    Collections.sort(armyState);

                    armyStates.add(armyState);

                }
            }
        }
        return armyStates;
    }


}
