import java.awt.*;
import java.util.ArrayList;

public class AIMCMC implements AI1v1 {
    private Color colorAI;
    private int turnsCount;
    private LobbySimulator simulator;
    private int bestPreviousScore = -1;
    private int previousNode;
    private int newNode;

    public AIMCMC(Color colorAI, int turnsCount) {
        this.colorAI = colorAI;
        this.turnsCount = turnsCount;
    }

    @Override
    public Node[] performMove(Graph board) {
        AIChoosesNode(board);
        Node[] returnArray = {board.getSecNode(previousNode),board.getSecNode(newNode)};
        return returnArray;
    }

    private void AIChoosesNode(Graph board) {
        for (int i = 0; i < board.getBoardSize(); i++) {  //tries to make a move
            int depthSimulation;
            if (this.turnsCount <= 20) {
                depthSimulation = 20;
            } else if (this.turnsCount > 20 && this.turnsCount <= 50) {
                depthSimulation = 16;
            } else {
                depthSimulation = 10;
            }
            simulator = new LobbySimulator(board.getArmy(Color.BLUE), board.getArmy(Color.RED), depthSimulation, this.bestPreviousScore);
            simulator.launch();
            System.out.println("Launching Simulations...");
            while (!simulator.isDone()) {
            }
            System.out.println("Finished!");
            int iNode = simulator.getBestCandidate();
            int destinationNode = simulator.getBestDestination();
            previousNode = iNode;
            ArrayList<Node> popChoices = board.popularChoice(board.getSecNode(iNode)); //gets popular choices of i
            System.out.println(popChoices);
            Node[] possibleChoice = new Node[popChoices.size()];
            int index = 0;
            for (int j = 0; j < popChoices.size(); j++) { //convert ArrayList<Node> to Node[]
                if (popChoices.get(j).getLabel().equals("null")
                        //forbids to make moves up
                        || popChoices.get(j).getdRight().getLabel().equals(board.getSecNode(iNode).getLabel())
                        || popChoices.get(j).getdLeft().getLabel().equals(board.getSecNode(iNode).getLabel())) {
                } else {
                    possibleChoice[index] = popChoices.get(j);
                    index++;
                }
            }
            Node[] finalPossibleChoice = new Node[index];//copies array with correct size
            for (int k = 0; k < index; k++) {
                finalPossibleChoice[k] = possibleChoice[k];
            }
            if (index > 0) {//display elements/
                for (int k = 0; k < finalPossibleChoice.length; k++) {
                    System.out.println(finalPossibleChoice[k].getLabel());
                }
                int n;
                Node max = new Node("max", -1, -1, -1);
                for (int ind = 0; ind < finalPossibleChoice.length; ind++) {
                    if (finalPossibleChoice[ind].getScore() > max.getScore()) {
                        max = finalPossibleChoice[ind];
                    }
                }
                if (board.getSecNode(iNode).getScore() != max.getScore()) {
                    System.out.println("AI move is " + max.getLabel() + " " + max.getIndex());
                    newNode = destinationNode;
                    break;
                }
                else {
                    System.out.println("bug");
                    newNode = iNode;
                }
            }

        }
    }
}
