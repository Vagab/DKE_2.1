import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    private GraphSimulation boardSim;

    private Node[] nodeList;
    private int selectedNode, previousSelectedNode = -1;
    private static boolean firstMove = false;
    private int turnsCount = 1;
    private int maxPlays;
    private Node firstPlay;
    private double[] probs;

    private Node lastAIPlayed;
    private Node previousAIPlayed;

    private boolean player1 = true;
    Random rand;

    private int totalScoreBlue = 0;


    public Simulation(int[] blue, int[] red, int maxPlays, double[] probs){
        boardSim = new GraphSimulation();
        boardSim.initializePositions(blue, red);    //sets the starting positions for the simulation
        this.maxPlays = maxPlays;
        nodeList = boardSim.getNodes();
        rand = new Random();
        this.probs = probs;
    }

    public void sortNodes(Node[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j].getScore() < arr[j - 1].getScore()) {
                    Node temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }


    public Node startSimulation(){
        this.AIChoosesNode();



        for(int i=0; i<nodeList.length; i++){
            if(boardSim.getNodeColor(i).equals(Color.BLUE)){
                totalScoreBlue += boardSim.getSecNode(i).getScore();
            }
        }
        return this.firstPlay;
    }
    public int getScoreResult(){
        return (int)this.totalScoreBlue / 6;
    }

    private int getRandomChoice(){
//        return rand.nextInt(6);
        return NormalDistribution.monteCarlo(probs, rand);
    }

    private void AIChoosesNode(){

        if(turnsCount>=this.maxPlays){return;}

      //  System.out.print("...");


        Color color;
        if(player1){color = Color.BLUE;}
        else{color = Color.RED;}

        Node[] AIPOS = new Node[6]; //6 is the amount of pawns
        int iter = 0;
        for(int i=0; i<nodeList.length; i++){
            if(boardSim.getNodeColor(i).equals(color)){
                AIPOS[iter] = nodeList[i];
                iter++;
            }
        }
        sortNodes(AIPOS);
//        for (int i = 0; i < AIPOS.length; i++) {
//            System.out.println(i + ": " + AIPOS[i].getScore());
//        }
//        System.out.println();
//        for(double d:probs) {
//            System.out.println("probs: " + d);
//        }
        int i = AIPOS[getRandomChoice()].getIndex(); //Chooses a node randomly
//        int i = getRandomChoice();

        //tries to make a move

                setMove(i);


                ArrayList<Node> popChoices = boardSim.popularChoice(boardSim.getSecNode(i)); //gets popular choices of i

               // System.out.println("PopChoices size: " + popChoices.size());

                Node[] possibleChoice = new Node[popChoices.size()];

                int index = 0;
                for(int j=0; j<popChoices.size(); j++){ //convert ArrayList<Node> to Node[]
                    if(popChoices.get(j).getLabel().equals("null")
                            //forbids to make moves up
                            || popChoices.get(j).getdRight().getLabel().equals(boardSim.getSecNode(i).getLabel())
                            || popChoices.get(j).getdLeft().getLabel().equals(boardSim.getSecNode(i).getLabel())) {
                       // System.out.println("VALUEISNULL: "+popChoices.get(j).getLabel());
                    }
                    else {
                        possibleChoice[index] = popChoices.get(j);
                       // System.out.println("possibleChoice: " + possibleChoice[index]);
                        index++;
                    }
                }

                Node[] finalPossibleChoice = new Node[index];//copies array with correct size
                for(int k=0; k<index; k++){finalPossibleChoice[k]=possibleChoice[k];}


                if(index>0){//display elements
                   // System.out.print("INDEX = " + index);
                   // for(int k=0;k<finalPossibleChoice.length;k++){System.out.println(finalPossibleChoice[k].getLabel());}


                    int n;
                    Node max = new Node("max", -1, -1,-1);

                    for(int ind=0; ind<finalPossibleChoice.length;ind++){
                        if(finalPossibleChoice[ind].getScore()>max.getScore()){
                            max = finalPossibleChoice[ind];
                        }
                    }


                    if(previousAIPlayed!=max) {

                        // setMove(finalPossibleChoice[n].getIndex());//sets the next move
                        if (boardSim.getSecNode(i).getScore() != max.getScore()) {
                            previousAIPlayed = boardSim.getSecNode(i); //put the previous location of the played node
                            lastAIPlayed = max; //last node played by the AI
                            if(turnsCount==1){firstPlay = previousAIPlayed;}    //sets the first played Node
                            setMove(max.getIndex());
                        } else {
                            setMove(i);
                            AIChoosesNode();
                        }

                    } else{
                       // System.out.println("DEBUG OF INFINITE LOOP FOR AI " + previousAIPlayed.getLabel() + " " + boardSim.getSecNode(i).getLabel() + " " + max.getLabel());
                        setMove(i);
                        AIChoosesNode(); //recalls as it failed to make a good move
                    }
                }




    }

    private void setMove(int chosenNode) {
        higlight(boardSim.getSecNode(chosenNode));
        this.previousSelectedNode = this.selectedNode;
        this.selectedNode = chosenNode;

        ArrayList<Node> stopBugs = boardSim.popularChoice(nodeList[previousSelectedNode]);
        boolean allgood = false;    //gets popular choices ?
        for(int i=0; i<stopBugs.size(); i++){
            if(stopBugs.get(i)==nodeList[selectedNode]){
                allgood = true;
            }
        }
        if(allgood==false){ //removes a bug?
            removeOneHighlight(nodeList[previousSelectedNode]);
        }

        higlight(boardSim.getSecNode(chosenNode));

        if (previousSelectedNode == selectedNode) { //removes highlight from chosen node
            removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
        }

        //makes the move for either player
        if (((nodeList[previousSelectedNode].getColor().equals(Color.BLUE) && player1)
                || (nodeList[previousSelectedNode].getColor().equals(Color.RED) && !player1)

        )) {
            this.firstMove = false;


            //condition for moving a pawn only to yellow nodes
            if (selectedNode != previousSelectedNode && !nodeList[previousSelectedNode].getColor().equals(Color.YELLOW)
                    && nodeList[selectedNode].getColor().equals(Color.YELLOW)
            ) {
                //changes colors
                nodeList[selectedNode].setColor(nodeList[previousSelectedNode].getColor());
                removehighlight(nodeList[previousSelectedNode], nodeList[selectedNode]);
                nodeList[previousSelectedNode].setColor(Color.WHITE);
                if (isWinningCondition()) { // /!\

                }
                turnsCount++;
               // System.out.println(turnsCount);
                if(turnsCount>=this.maxPlays){return;}

                if(player1){player1=false;}
                else{player1 = true;}

                turnsCount++;
            }
            else{this.firstMove=true;}
        }
        else{this.firstMove=true;}

        if(!firstMove){AIChoosesNode();}

    }


    private void higlight(Node n) {
        if (player1 && n.getColor() == Color.BLUE) {
            ArrayList<Node> tr = boardSim.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        } else if (!player1 && n.getColor() == Color.RED) {
            ArrayList<Node> tr = boardSim.popularChoice(n);
            for (int i = 0; i < tr.size(); i++) {
                tr.get(i).setColor(Color.YELLOW);
            }
        }


    }

    private void removehighlight(Node n, Node m) {
        ArrayList<Node> tr = boardSim.popularChoice(n);
        for (int i = 0; i < tr.size(); i++) {
            if (tr.get(i) == m) {

            } else {
                tr.get(i).setColor(Color.WHITE);
            }
        }

        ArrayList<Node> te = boardSim.popularChoice(m);
        for (int i = 0; i < tr.size(); i++) {

            tr.get(i).setColor(Color.WHITE);

        }
    }

    private void removeOneHighlight(Node n){//method that removes the highlight when choosing another node
        ArrayList<Node> tr = boardSim.popularChoice(n);
        for(int i=0; i<tr.size(); i++){
            tr.get(i).setColor(Color.WHITE);
        }
    }

    public boolean isWinningCondition() {

        if (player1) {       //checking for blue
            for (int i = 80; i > 75; i--) {
                if (!boardSim.getNodeColor(i).equals(Color.BLUE))
                    return false;
            }
            return true;
        }

        else { //checking for red
            for (int i = 0; i < 6; i++) {
                if (!boardSim.getNodeColor(i).equals(Color.RED))
                    return false;
            }
            return true;
        }

    }


}
