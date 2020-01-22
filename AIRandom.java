import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class AIRandom implements AI1v1 {

    private double radiusWeight;
    private double distanceFromMiddleWeight;
    private double distanceToGoalWeight;
    private Color colorAI;

    private ArrayList<Node> oldArmy;
    private ArrayList<Node> newArmy;
    private Random randomGenerator;


    public AIRandom(double distanceFromMiddleWeight, double distanceToGoalWeight, double radiusWeight, Color colorAI) {
        this.distanceFromMiddleWeight = distanceFromMiddleWeight;
        this.distanceToGoalWeight = distanceToGoalWeight;
        this.radiusWeight = radiusWeight;
        this.colorAI = colorAI;
        randomGenerator = new Random();
    }

    @Override
    public Node[] performMove(Graph board) {
        randomSearch(board);
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

    public double[] featureCalculator(Graph graph) {
        ArrayList<Node> redArmy = graph.getNodeArmy(Color.RED);
        ArrayList<Node> blueArmy = graph.getNodeArmy(Color.BLUE);

        Node destinationNodeRed = graph.getAIDestinationNode(Color.RED);
        Node destinationNodeBlue = graph.getAIDestinationNode(Color.BLUE);

        //Calculate distance to goal by treating the army as 1 single node by using its centroid
        double distanceToGoalRed = graph.centroidNodeDistance(redArmy, destinationNodeRed);
        double distanceToGoalBlue = graph.centroidNodeDistance(blueArmy, destinationNodeBlue);

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
        //Calculate features
        double[] featureScoresVector = featureCalculator(graph);
        //Evaluation function
        evaluationValue = -distanceFromMiddleWeight * featureScoresVector[0]
                - distanceToGoalWeight * featureScoresVector[1]
                - radiusWeight * featureScoresVector[2];
        if (colorAI.equals(Color.RED)) {
            return evaluationValue;
        } else { // If color is blue
            return -evaluationValue;
        }
    }

    private void randomSearch(Graph board) {
        ArrayList<ArrayList<Integer>> armyList = stateGenerator(colorAI, board);
        int index = randomGenerator.nextInt(armyList.size());
        oldArmy = board.getNodeArmy(colorAI);
        newArmy = integerToNode(armyList.get(index), board);
    }


    public Color getColor() {
        return colorAI;
    }

    @Override
    public void resetTrajectory() {

    }

    private ArrayList<Node> integerToNode(ArrayList<Integer> army, Graph board) {
        ArrayList<Node> nodeArmy = new ArrayList<>();
        for (int i : army) {
            nodeArmy.add(board.getSecNode(i));
        }
        return nodeArmy;
    }

    private ArrayList<ArrayList<Integer>> stateGenerator(Color color, Graph graph) {
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

    class blueArmyComparator implements Comparator<ArrayList<Integer>> {
        private ArrayList<Integer> stationaryArmy;

        blueArmyComparator(ArrayList<Integer> stationaryArmy) {
            this.stationaryArmy = stationaryArmy;
        }

        @Override
        public int compare(ArrayList<Integer> army1, ArrayList<Integer> army2) {
            Graph graph1 = new Graph(army1, stationaryArmy, Color.BLUE, Color.RED);
            Graph graph2 = new Graph(army2, stationaryArmy, Color.BLUE, Color.RED);
            if (evaluationFunction(graph1) > evaluationFunction(graph2)) {
                return -1;
            } else if (evaluationFunction(graph1) == evaluationFunction(graph2)) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    class redArmyComparator implements Comparator<ArrayList<Integer>> {
        private ArrayList<Integer> stationaryArmy;

        redArmyComparator(ArrayList<Integer> stationaryArmy) {
            this.stationaryArmy = stationaryArmy;
        }

        @Override
        public int compare(ArrayList<Integer> army1, ArrayList<Integer> army2) {
            Graph graph1 = new Graph(army1, stationaryArmy, Color.BLUE, Color.BLUE);
            Graph graph2 = new Graph(army2, stationaryArmy, Color.BLUE, Color.BLUE);
            if (evaluationFunction(graph1) > evaluationFunction(graph2)) {
                return -1;
            } else if (evaluationFunction(graph1) == evaluationFunction(graph2)) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
