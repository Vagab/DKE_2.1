import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AIGreedyMultiplayer implements AIMultiPlayer {
    private Color colorAI;
    private Color[] orderOfPlay = {Color.BLUE, Color.GRAY, Color.ORANGE, Color.RED, Color.BLACK, Color.GREEN};
    private ArrayList<ArrayList<Integer>> bestMoves = new ArrayList<>();
    private ArrayList<Integer> bestMove = new ArrayList<>();

    private ArrayList<Node> oldArmy;
    private ArrayList<Node> newArmy;
    private int cutOffDepth;
    private double bestValue;
    private Random randomGenerator;
    private boolean goalReached;

    public AIGreedyMultiplayer(int cutOffDepth, Color colorAI) {
        this.randomGenerator = new Random();
        this.goalReached = false;
        this.cutOffDepth = cutOffDepth;
        this.bestValue = 0;
        this.colorAI = colorAI;
    }

    @Override
    public Node[] performMove(GraphOscar board) {
        greedySearch(cutOffDepth, board);
        return getMove();
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

    public double evaluationFunction(GraphOscar graph) {
        ArrayList<Node> army = graph.getNodeArmy(colorAI);
        Node destinationNode = graph.getAIDestinationNode(colorAI);
        double distanceToGoal = 0;
        for (Node node : army) {
            distanceToGoal += Math.pow(graph.straightLineDistance(destinationNode,node),2);
        }
        return -distanceToGoal;
    }

    private double maxValue(GraphOscar board, int cutOff, int depth) {
        if (depth >= cutOff) {
//            System.out.println("At depth " + depth + " the evaluation values are: " + Arrays.toString(evaluationFunction(board)));
            return evaluationFunction(board);
        }
        depth++;
        ArrayList<ArrayList<Integer>> armyStates = stateGenerator(board);
        ArrayList<Integer> armyOpponent1 = board.getArmy(getNextColor(colorAI,0));
        ArrayList<Integer> armyOpponent2 = board.getArmy(getNextColor(colorAI,1));
        ArrayList<Integer> armyOpponent3 = board.getArmy(getNextColor(colorAI,2));
        ArrayList<Integer> armyOpponent4 = board.getArmy(getNextColor(colorAI,3));
        ArrayList<Integer> armyOpponent5 = board.getArmy(getNextColor(colorAI,4));
        Color colorOpponent = Color.BLACK;
        double evaluationValue = -1000000000;
        if (depth < cutOff) {

            for (ArrayList<Integer> armyState : armyStates) {
                GraphOscar graphChild = new GraphOscar(6,armyState, armyOpponent1, armyOpponent2, armyOpponent3, armyOpponent4, armyOpponent5,
                        colorAI, getNextColor(colorAI,0), getNextColor(colorAI,1), getNextColor(colorAI,2), getNextColor(colorAI,3),
                        getNextColor(colorAI,4));
                if (redGoalCheck(graphChild) || blueGoalCheck(graphChild) || blackGoalCheck(graphChild) || greenGoalCheck(graphChild) || orangeGoalCheck(graphChild)
                || grayGoalCheck(graphChild)) {
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
            return evaluationFunction(board);
        }
    }


    private void greedySearch(int cutOff, GraphOscar board) {
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


    private ArrayList<Node> integerToNode(ArrayList<Integer> army, GraphOscar board) {
        ArrayList<Node> nodeArmy = new ArrayList<>();
        for (int i : army) {
            nodeArmy.add(board.getSecNode(i));
        }
        return nodeArmy;
    }


    private ArrayList<ArrayList<Integer>> stateGenerator(GraphOscar graph) {
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

    private Color getNextColor(Color color, int offSet) {
        int currentIndex = -1;
        for (int i = 0; i < orderOfPlay.length; i++) {
            if (orderOfPlay[i].equals(color)) {
                currentIndex = i;
            }
        }
        return orderOfPlay[(currentIndex + 1 + offSet) % 6];
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
