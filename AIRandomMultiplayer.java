import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AIRandomMultiplayer implements AIMultiPlayer{
    private Color colorAI;

    private ArrayList<Node> oldArmy;
    private ArrayList<Node> newArmy;
    private Random randomGenerator;


    public AIRandomMultiplayer(Color colorAI) {
        this.colorAI = colorAI;
        randomGenerator = new Random();
    }

    @Override
    public Node[] performMove(GraphOscar board) {
        randomSearch(board);
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

    private void randomSearch(GraphOscar board) {
        ArrayList<ArrayList<Integer>> armyList = stateGenerator(colorAI, board);
        int index = randomGenerator.nextInt(armyList.size());
        oldArmy = board.getNodeArmy(colorAI);
        newArmy = integerToNode(armyList.get(index), board);
    }


    public Color getColor() {
        return colorAI;
    }

    private ArrayList<Node> integerToNode(ArrayList<Integer> army, GraphOscar board) {
        ArrayList<Node> nodeArmy = new ArrayList<>();
        for (int i : army) {
            nodeArmy.add(board.getSecNode(i));
        }
        return nodeArmy;
    }

    private ArrayList<ArrayList<Integer>> stateGenerator(Color color, GraphOscar graph) {
        ArrayList<Integer> AIArmy = graph.getArmy(color); // |Army_1| times
        Node goalNode = graph.getAIDestinationNode(color);
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